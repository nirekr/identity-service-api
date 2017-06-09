/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.adapters;

import com.dell.cpsd.identity.service.api.IdentityServiceError;
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
public class IdentityServiceErrorMessageAdapter implements ServiceCallbackAdapter<IdentityServiceError, ServiceResponse<IdentityServiceError>>
{
    private ServiceCallbackRegistry serviceCallbackRegistry;

    public IdentityServiceErrorMessageAdapter(ServiceCallbackRegistry serviceCallbackRegistry)
    {
        this.serviceCallbackRegistry = serviceCallbackRegistry;
    }

    @Override
    public ServiceResponse<IdentityServiceError> transform(IdentityServiceError message)
    {
        return new ServiceResponse<>(message.getCorrelationId(), message, null);
    }

    @Override
    public void consume(IServiceCallback callback, ServiceResponse<IdentityServiceError> destination)
    {
        callback.handleServiceResponse(destination);
    }

    @Override
    public IServiceCallback take(IdentityServiceError source)
    {
        return serviceCallbackRegistry.removeServiceCallback(source.getCorrelationId());
    }

    @Override
    public Class<IdentityServiceError> getSourceClass()
    {
        return IdentityServiceError.class;
    }
}
