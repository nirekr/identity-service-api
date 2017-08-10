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

import com.dell.cpsd.identity.service.api.ElementsIdentified;
import com.dell.cpsd.service.common.client.callback.IServiceCallback;
import com.dell.cpsd.service.common.client.callback.ServiceResponse;
import com.dell.cpsd.service.common.client.rpc.ServiceCallbackRegistry;

/**
 * ElementsIdentifiedMessageAdapter Test.
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */
public class ElementsIdentifiedMessageAdapterTest
{

    @Spy
    ElementsIdentifiedMessageAdapter    elementsIdentifiedMessageAdapter;
    ServiceCallbackRegistry             serviceCallbackRegistry;
    ElementsIdentified                  message;
    IServiceCallback                    serviceCallback;
    ServiceResponse<ElementsIdentified> resp;

    @Before
    public void setUp()
    {
        serviceCallbackRegistry = Mockito.mock(ServiceCallbackRegistry.class);
        message = Mockito.mock(ElementsIdentified.class);
        elementsIdentifiedMessageAdapter = Mockito.spy(new ElementsIdentifiedMessageAdapter(serviceCallbackRegistry));
        serviceCallback = Mockito.mock(IServiceCallback.class);

        when(message.getCorrelationId()).thenReturn("dummyCorrId");
    }

    @Test
    public void transformTest()
    {

        resp = elementsIdentifiedMessageAdapter.transform(message);
        elementsIdentifiedMessageAdapter.consume(serviceCallback, resp);

        verify(message).getCorrelationId();
        assertEquals(resp.getRequestId(), "dummyCorrId");
        assertEquals(resp.getResponse(), message);

    }

    @Test
    public void getSourceClassTest()
    {
        assertEquals(ElementsIdentified.class, elementsIdentifiedMessageAdapter.getSourceClass());
    }

    @Test
    public void consumeTest()
    {
        elementsIdentifiedMessageAdapter.consume(serviceCallback, resp);
        verify(serviceCallback).handleServiceResponse(resp);

    }

    @Test
    public void takeTest()
    {
        elementsIdentifiedMessageAdapter.take(message);
        verify(serviceCallbackRegistry).removeServiceCallback("dummyCorrId");

    }

}
