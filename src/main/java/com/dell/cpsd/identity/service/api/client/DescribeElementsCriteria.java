/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import java.util.List;

/**
 * <p>
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * </p>
 *
 * @since 1.0
 */
public class DescribeElementsCriteria
{
    private final List<String> elementUuids;

    public DescribeElementsCriteria(List<String> elementUuids)
    {
        this.elementUuids = elementUuids;
    }

    public List<String> getElementUuid()
    {
        return elementUuids;
    }
}
