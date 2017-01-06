/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
public class DescribeElementCriteria
{
    private final String elementUuid;

    public DescribeElementCriteria(String elementUuid)
    {
        this.elementUuid = elementUuid;
    }

    public String getElementUuid()
    {
        return elementUuid;
    }
}
