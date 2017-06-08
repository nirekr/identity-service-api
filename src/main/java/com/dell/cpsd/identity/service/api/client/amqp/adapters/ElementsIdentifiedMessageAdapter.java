/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.adapters;

import com.dell.cpsd.identity.service.api.ElementsIdentified;
import com.dell.cpsd.service.common.client.callback.IServiceCallback;
import com.dell.cpsd.service.common.client.callback.ServiceResponse;
import com.dell.cpsd.service.common.client.rpc.ServiceCallbackAdapter;
import com.dell.cpsd.service.common.client.rpc.ServiceCallbackRegistry;

/**
 * <p>
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * </p>
 *
 * @since SINCE-TBD
 */
public class ElementsIdentifiedMessageAdapter implements ServiceCallbackAdapter<ElementsIdentified, ServiceResponse<ElementsIdentified>>
{
    private ServiceCallbackRegistry serviceCallbackRegistry;

    public ElementsIdentifiedMessageAdapter(ServiceCallbackRegistry serviceCallbackRegistry)
    {
        this.serviceCallbackRegistry = serviceCallbackRegistry;
    }

    @Override
    public ServiceResponse<ElementsIdentified> transform(ElementsIdentified elementIdentified)
    {
        return new ServiceResponse<>(elementIdentified.getCorrelationId(), elementIdentified, null);
    }

    @Override
    public void consume(IServiceCallback iServiceCallback, ServiceResponse<ElementsIdentified> elementIdentifiedServiceResponse)
    {
        iServiceCallback.handleServiceResponse(elementIdentifiedServiceResponse);
    }

    @Override
    public IServiceCallback take(ElementsIdentified elementIdentified)
    {
        return serviceCallbackRegistry.removeServiceCallback(elementIdentified.getCorrelationId());
    }

    @Override
    public Class<ElementsIdentified> getSourceClass()
    {
        return ElementsIdentified.class;
    }
}
