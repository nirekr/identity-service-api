/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import com.dell.cpsd.identity.service.api.ElementIdentity;

import java.util.List;

/**
 * <p>
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
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
