/**
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import com.dell.cpsd.common.logging.ILogger;
import com.dell.cpsd.common.rabbitmq.context.RabbitContext;
import com.dell.cpsd.identity.service.api.DescribeElements;
import com.dell.cpsd.identity.service.api.ElementsDescribed;
import com.dell.cpsd.identity.service.api.ElementsIdentified;
import com.dell.cpsd.identity.service.api.IdentifyElements;
import com.dell.cpsd.identity.service.api.IdentityServiceError;
import com.dell.cpsd.identity.service.api.client.amqp.adapters.ElementsDescribedMessageAdapter;
import com.dell.cpsd.identity.service.api.client.amqp.adapters.ElementsIdentifiedMessageAdapter;
import com.dell.cpsd.identity.service.api.client.amqp.adapters.IdentityServiceErrorMessageAdapter;
import com.dell.cpsd.identity.service.api.client.amqp.producer.IdentityServiceProducer;
import com.dell.cpsd.service.common.client.callback.ServiceResponse;
import com.dell.cpsd.service.common.client.exception.ServiceExecutionException;
import com.dell.cpsd.service.common.client.exception.ServiceTimeoutException;
import com.dell.cpsd.service.common.client.rpc.AbstractServiceClient;
import com.dell.cpsd.service.common.client.rpc.DelegatingMessageConsumer;
import com.dell.cpsd.service.common.client.rpc.ServiceRequestCallback;

/**
 * <p>
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
public class IdentityServiceClient extends AbstractServiceClient
{
    private final DelegatingMessageConsumer consumer;
    private final IdentityServiceProducer producer;
    private final RabbitContext rabbitContext;

    IdentityServiceClient(ILogger logger, RabbitContext rabbitContext, DelegatingMessageConsumer consumer, IdentityServiceProducer producer)
    {
        super(logger);

        this.consumer = consumer;
        this.producer = producer;
        this.rabbitContext = rabbitContext;

        initCallbacks();
    }

    private void initCallbacks()
    {
        this.consumer.addAdapter(new ElementsDescribedMessageAdapter(this));
        this.consumer.addAdapter(new ElementsIdentifiedMessageAdapter(this));
        this.consumer.addAdapter(new IdentityServiceErrorMessageAdapter(this));
    }

    /**
     * Identifies element
     *
     * @param criteria
     * @param timeout
     * @return
     * @throws ServiceExecutionException
     * @throws ServiceTimeoutException
     */
    public ElementsIdentified identifyElements(
            final IdentifyElementsCriteria criteria, final long timeout)
            throws ServiceExecutionException, ServiceTimeoutException
    {
        final IdentifyElements element = new IdentifyElements(timestamp(), uuid(), replyTo(IdentifyElements.class, ElementsIdentified.class),
                criteria.getElementIdentities());

        ServiceResponse<?> response = processRequest(timeout, new ServiceRequestCallback()
        {
            @Override
            public String getRequestId()
            {
                return element.getCorrelationId();
            }

            @Override
            public void executeRequest(String requestId) throws Exception
            {
                producer.publishIdentifyElements(element);
            }
        });

        return processResponse(response, ElementsIdentified.class);
    }

    /**
     * Describe element
     *
     * @param criteria
     * @param timeout
     * @return
     * @throws ServiceExecutionException
     * @throws ServiceTimeoutException
     */
    public ElementsDescribed describeElements(
            final DescribeElementsCriteria criteria, final long timeout)
            throws ServiceExecutionException, ServiceTimeoutException
    {
        final DescribeElements element = new DescribeElements(timestamp(), uuid(), replyTo(DescribeElements.class, ElementsDescribed.class),
                criteria.getElementUuid());

        ServiceResponse<ElementsDescribed> response = processRequest(timeout, new ServiceRequestCallback()
        {
            @Override
            public String getRequestId()
            {
                return element.getCorrelationId();
            }

            @Override
            public void executeRequest(String requestId) throws Exception
            {
                producer.publishDescribeElements(element);
            }
        });

        return processResponse(response, ElementsDescribed.class);
    }

    private String replyTo(Class request, Class reply)
    {
        return rabbitContext.getReplyTo(request, reply);
    }

    private <R> R processResponse(ServiceResponse<?> response, Class<R> expectedResponse) throws ServiceExecutionException
    {
        Object responseMessage = response.getResponse();
        if (responseMessage == null)
        {
            return null;
        }

        if (expectedResponse.isAssignableFrom(responseMessage.getClass()))
        {
            return (R)responseMessage;
        }
        else if (responseMessage instanceof IdentityServiceError)
        {
            IdentityServiceError error = (IdentityServiceError)responseMessage;
            throw new ServiceExecutionException(error.getErrorMessage());
        }
        else
        {
            throw new UnsupportedOperationException("Unexpected response message: " + responseMessage);
        }
    }
}
