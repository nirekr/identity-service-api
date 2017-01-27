/**
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.adapters;

import com.dell.cpsd.identity.service.api.ElementsDescribed;
import com.dell.cpsd.service.common.client.callback.IServiceCallback;
import com.dell.cpsd.service.common.client.callback.ServiceResponse;
import com.dell.cpsd.service.common.client.rpc.ServiceCallbackAdapter;
import com.dell.cpsd.service.common.client.rpc.ServiceCallbackRegistry;

/**
 * <p>
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
public class ElementsDescribedMessageAdapter implements ServiceCallbackAdapter<ElementsDescribed, ServiceResponse<ElementsDescribed>>
{
    private ServiceCallbackRegistry serviceCallbackRegistry;

    public ElementsDescribedMessageAdapter(ServiceCallbackRegistry serviceCallbackRegistry)
    {
        this.serviceCallbackRegistry = serviceCallbackRegistry;
    }

    @Override
    public ServiceResponse<ElementsDescribed> transform(ElementsDescribed message)
    {
        return new ServiceResponse<>(message.getCorrelationId(), message, null);
    }

    @Override
    public void consume(IServiceCallback callback, ServiceResponse<ElementsDescribed> destination)
    {
        callback.handleServiceResponse(destination);
    }

    @Override
    public IServiceCallback take(ElementsDescribed source)
    {
        return serviceCallbackRegistry.removeServiceCallback(source.getCorrelationId());
    }

    @Override
    public Class<ElementsDescribed> getSourceClass()
    {
        return ElementsDescribed.class;
    }
}
