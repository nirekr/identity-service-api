/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import com.dell.cpsd.identity.service.api.ElementIdentity;

import java.util.List;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
public class IdentifyElementsCriteria
{
    private final List<ElementIdentity> elementIdentities;

    public IdentifyElementsCriteria(List<ElementIdentity> elementIdentities)
    {
        this.elementIdentities = elementIdentities;
    }

    public List<ElementIdentity> getElementIdentities()
    {
        return elementIdentities;
    }
}
