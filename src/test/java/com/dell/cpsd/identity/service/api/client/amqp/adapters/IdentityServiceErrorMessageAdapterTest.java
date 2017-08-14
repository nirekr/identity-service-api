/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.adapters;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import com.dell.cpsd.identity.service.api.IdentityServiceError;
import com.dell.cpsd.service.common.client.callback.IServiceCallback;
import com.dell.cpsd.service.common.client.callback.ServiceResponse;
import com.dell.cpsd.service.common.client.rpc.ServiceCallbackRegistry;

/**
 * IdentityServiceErrorMessageAdapter Test.
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */

public class IdentityServiceErrorMessageAdapterTest
{

    @Spy
    IdentityServiceErrorMessageAdapter    identityServiceErrorMessageAdapter;
    ServiceCallbackRegistry               serviceCallbackRegistry;
    IdentityServiceError                  message;
    IServiceCallback                      serviceCallback;
    ServiceResponse<IdentityServiceError> resp;

    @Before
    public void setUp()
    {
        serviceCallbackRegistry = Mockito.mock(ServiceCallbackRegistry.class);
        message = Mockito.mock(IdentityServiceError.class);

        identityServiceErrorMessageAdapter = Mockito.spy(new IdentityServiceErrorMessageAdapter(serviceCallbackRegistry));
        serviceCallback = Mockito.mock(IServiceCallback.class);

        when(message.getCorrelationId()).thenReturn("dummyCorrId");
    }

    @Test
    public void transformTest()
    {

        resp = identityServiceErrorMessageAdapter.transform(message);
        identityServiceErrorMessageAdapter.consume(serviceCallback, resp);

        verify(message).getCorrelationId();
        assertEquals(resp.getRequestId(), "dummyCorrId");
        assertEquals(resp.getResponse(), message);

    }

    @Test
    public void getSourceClassTest()
    {
        assertEquals(IdentityServiceError.class, identityServiceErrorMessageAdapter.getSourceClass());
    }

    @Test
    public void consumeTest()
    {
        identityServiceErrorMessageAdapter.consume(serviceCallback, resp);
        verify(serviceCallback).handleServiceResponse(resp);

    }

    @Test
    public void takeTest()
    {
        identityServiceErrorMessageAdapter.take(message);
        verify(serviceCallbackRegistry).removeServiceCallback("dummyCorrId");

    }

}
