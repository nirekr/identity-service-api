/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.amqp.producer;

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
public interface IdentityServiceProducer
{
    void publishIdentifyElements(IdentifyElements identity);

    void publishDescribeElements(DescribeElements identity);
}
