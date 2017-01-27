/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import java.util.List;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
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
