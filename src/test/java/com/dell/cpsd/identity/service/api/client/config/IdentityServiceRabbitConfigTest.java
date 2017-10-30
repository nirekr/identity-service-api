/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.config;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.dell.cpsd.common.rabbitmq.context.RabbitContextListener;
import com.dell.cpsd.common.rabbitmq.template.OpinionatedRabbitTemplate;
import com.dell.cpsd.identity.service.api.client.amqp.producer.IdentityServiceProducer;
import com.dell.cpsd.service.common.client.rpc.DefaultMessageConsumer;

/**
 * IdentityServiceRabbitConfig Test.
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */
public class IdentityServiceRabbitConfigTest
{
    @Spy
    @InjectMocks
    IdentityServiceRabbitConfig identityServiceRabbitConfig;
    Object                      returnObj;
    OpinionatedRabbitTemplate   rabbitTemplate;

    @Before
    public void setUp()
    {
        identityServiceRabbitConfig = Mockito.spy(new IdentityServiceRabbitConfig());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void rabbitContextTest() throws IOException
    {
        // RabbitContext rabbitContext = identityServiceRabbitConfig.rabbitContext();

    }

    @Test
    public void identityServiceConsumerTest()
    {
        returnObj = identityServiceRabbitConfig.identityServiceConsumer();
        assertTrue(returnObj instanceof DefaultMessageConsumer);
    }

    @Test
    public void identityServiceProducerTest()
    {
        returnObj = identityServiceRabbitConfig.identityServiceProducer(rabbitTemplate);
        assertTrue(returnObj instanceof IdentityServiceProducer);
    }

    @Test
    public void rabbitTemplateTest()
    {
        returnObj = identityServiceRabbitConfig.rabbitTemplate();
        assertTrue(returnObj instanceof OpinionatedRabbitTemplate);
    }

    @Test
    public void rabbitContextListenerTest()
    {
        returnObj = identityServiceRabbitConfig.rabbitContextListener();
        assertTrue(returnObj instanceof RabbitContextListener);
    }

}
