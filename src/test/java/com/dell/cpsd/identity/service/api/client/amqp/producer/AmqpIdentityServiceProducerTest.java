/**
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.producer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.dell.cpsd.common.rabbitmq.template.OpinionatedRabbitTemplate;
import com.dell.cpsd.identity.service.api.DescribeElements;
import com.dell.cpsd.identity.service.api.IdentifyElements;

/**
 * AmqpIdentityServiceProducer Test.
 * <p>
 * Copyright &copy; 2017 Dell Inc. or its subsidiaries. All Rights Reserved. 
 * Dell EMC Confidential/Proprietary Information
 * </p>
 *
 * @since 1.0
 */
public class AmqpIdentityServiceProducerTest
{

    AmqpIdentityServiceProducer amqpIdentityServiceProducer;
    OpinionatedRabbitTemplate   template;
    IdentifyElements            identityElements;
    DescribeElements            describeElements;

    @Before
    public void setUp()
    {
        template = Mockito.mock(OpinionatedRabbitTemplate.class);
        amqpIdentityServiceProducer = new AmqpIdentityServiceProducer(template);
        identityElements = Mockito.mock(IdentifyElements.class);
        describeElements = Mockito.mock(DescribeElements.class);
    }

    @Test
    public void publishIdentifyElementsTest()
    {
        amqpIdentityServiceProducer.publishIdentifyElements(identityElements);
        verify(template).send(any(IdentifyElements.class));
    }

    @Test
    public void publishDescribeElementsTest()
    {
        amqpIdentityServiceProducer.publishDescribeElements(describeElements);
        verify(template).send(any(DescribeElements.class));
    }

}
