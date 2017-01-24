/**
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import com.dell.cpsd.common.logging.ILogger;
import com.dell.cpsd.common.rabbitmq.context.RabbitContext;
import com.dell.cpsd.identity.service.api.DescribeElement;
import com.dell.cpsd.identity.service.api.ElementDescribed;
import com.dell.cpsd.identity.service.api.ElementIdentified;
import com.dell.cpsd.identity.service.api.IdentifyElement;
import com.dell.cpsd.identity.service.api.IdentityServiceError;
import com.dell.cpsd.identity.service.api.client.amqp.adapters.ElementDescribedMessageAdapter;
import com.dell.cpsd.identity.service.api.client.amqp.adapters.ElementIdentifiedMessageAdapter;
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
        this.consumer.addAdapter(new ElementDescribedMessageAdapter(this));
        this.consumer.addAdapter(new ElementIdentifiedMessageAdapter(this));
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
    public ElementIdentified identifyElement(
            final IdentifyElementCriteria criteria, final long timeout)
            throws ServiceExecutionException, ServiceTimeoutException
    {
        final IdentifyElement element = new IdentifyElement(timestamp(), uuid(), replyTo(IdentifyElement.class, ElementIdentified.class),
                criteria.getIdentity());

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
                producer.publishIdentifyElement(element);
            }
        });

        return processResponse(response, ElementIdentified.class);
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
    public ElementDescribed describeElement(
            final DescribeElementCriteria criteria, final long timeout)
            throws ServiceExecutionException, ServiceTimeoutException
    {
        final DescribeElement element = new DescribeElement(timestamp(), uuid(), replyTo(DescribeElement.class, ElementDescribed.class),
                criteria.getElementUuid());

        ServiceResponse<ElementDescribed> response = processRequest(timeout, new ServiceRequestCallback()
        {
            @Override
            public String getRequestId()
            {
                return element.getCorrelationId();
            }

            @Override
            public void executeRequest(String requestId) throws Exception
            {
                producer.publishDescribeElement(element);
            }
        });

        return processResponse(response, ElementDescribed.class);
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
