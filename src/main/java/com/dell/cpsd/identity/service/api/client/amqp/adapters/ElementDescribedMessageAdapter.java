/**
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.adapters;

import com.dell.cpsd.identity.service.api.ElementDescribed;
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
public class ElementDescribedMessageAdapter implements ServiceCallbackAdapter<ElementDescribed, ServiceResponse<ElementDescribed>>
{
    private ServiceCallbackRegistry serviceCallbackRegistry;

    public ElementDescribedMessageAdapter(ServiceCallbackRegistry serviceCallbackRegistry)
    {
        this.serviceCallbackRegistry = serviceCallbackRegistry;
    }

    @Override
    public ServiceResponse<ElementDescribed> transform(ElementDescribed message)
    {
        return new ServiceResponse<>(message.getCorrelationId(), message, null);
    }

    @Override
    public void consume(IServiceCallback callback, ServiceResponse<ElementDescribed> destination)
    {
        callback.handleServiceResponse(destination);
    }

    @Override
    public IServiceCallback take(ElementDescribed source)
    {
        return serviceCallbackRegistry.removeServiceCallback(source.getCorrelationId());
    }

    @Override
    public Class<ElementDescribed> getSourceClass()
    {
        return ElementDescribed.class;
    }
}
