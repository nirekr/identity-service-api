/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import com.dell.cpsd.identity.service.api.Identity;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
public class IdentifyElementCriteria
{
    private final Identity identity;

    public IdentifyElementCriteria(Identity identity)
    {
        this.identity = identity;
    }

    public Identity getIdentity()
    {
        return identity;
    }
}
