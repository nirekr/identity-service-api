/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.producer;

import com.dell.cpsd.common.rabbitmq.template.OpinionatedRabbitTemplate;
import com.dell.cpsd.identity.service.api.DescribeElements;
import com.dell.cpsd.identity.service.api.IdentifyElements;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
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
