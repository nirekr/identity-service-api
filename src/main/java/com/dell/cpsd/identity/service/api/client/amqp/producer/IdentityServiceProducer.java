/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.producer;

import com.dell.cpsd.common.rabbitmq.context.RabbitContextAware;
import com.dell.cpsd.identity.service.api.DescribeElement;
import com.dell.cpsd.identity.service.api.IdentifyElement;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
public interface IdentityServiceProducer
{
    void publishIdentifyElement(IdentifyElement identity);

    void publishDescribeElement(DescribeElement identity);
}
