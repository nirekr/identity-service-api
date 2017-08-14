/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.producer;

import com.dell.cpsd.common.rabbitmq.template.OpinionatedRabbitTemplate;
import com.dell.cpsd.identity.service.api.DescribeElements;
import com.dell.cpsd.identity.service.api.IdentifyElements;

/**
 * <p>
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * </p>
 *
 * @since 1.0
 */
public class AmqpIdentityServiceProducer implements IdentityServiceProducer
{
    private OpinionatedRabbitTemplate template;

    public AmqpIdentityServiceProducer(OpinionatedRabbitTemplate template)
    {
        this.template = template;
    }

    @Override
    public void publishIdentifyElements(IdentifyElements identity)
    {
        template.send(identity);
    }

    @Override
    public void publishDescribeElements(DescribeElements identity)
    {
        template.send(identity);
    }
}
