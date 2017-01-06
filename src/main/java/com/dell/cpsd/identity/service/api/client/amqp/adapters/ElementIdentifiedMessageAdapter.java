/**
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.adapters;

import com.dell.cpsd.identity.service.api.ElementIdentified;
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
public class ElementIdentifiedMessageAdapter implements ServiceCallbackAdapter<ElementIdentified, ServiceResponse<ElementIdentified>>
{
    private ServiceCallbackRegistry serviceCallbackRegistry;

    public ElementIdentifiedMessageAdapter(ServiceCallbackRegistry serviceCallbackRegistry)
    {
        this.serviceCallbackRegistry = serviceCallbackRegistry;
    }

    @Override
    public ServiceResponse<ElementIdentified> transform(ElementIdentified elementIdentified)
    {
        return new ServiceResponse<>(elementIdentified.getCorrelationId(), elementIdentified, null);
    }

    @Override
    public void consume(IServiceCallback iServiceCallback, ServiceResponse<ElementIdentified> elementIdentifiedServiceResponse)
    {
        iServiceCallback.handleServiceResponse(elementIdentifiedServiceResponse);
    }

    @Override
    public IServiceCallback take(ElementIdentified elementIdentified)
    {
        return serviceCallbackRegistry.removeServiceCallback(elementIdentified.getCorrelationId());
    }

    @Override
    public Class<ElementIdentified> getSourceClass()
    {
        return ElementIdentified.class;
    }
}
