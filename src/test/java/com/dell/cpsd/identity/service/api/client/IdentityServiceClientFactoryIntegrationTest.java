/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import com.dell.cpsd.common.logging.ILogger;
import com.dell.cpsd.common.logging.LoggingManager;
import com.dell.cpsd.common.rabbitmq.context.ApplicationConfiguration;
import com.dell.cpsd.common.rabbitmq.context.ApplicationConfigurationFactory;
import com.dell.cpsd.identity.service.api.BusinessKey;
import com.dell.cpsd.identity.service.api.Classification;
import com.dell.cpsd.identity.service.api.ElementIdentity;
import com.dell.cpsd.identity.service.api.ElementsDescribed;
import com.dell.cpsd.identity.service.api.ElementsIdentified;
import com.dell.cpsd.identity.service.api.Identity;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
public class IdentityServiceClientFactoryIntegrationTest
{
    private static ILogger LOGGER = new LoggingManager().getLogger(IdentityServiceClientFactoryIntegrationTest.class);

    private static IdentityServiceClient client;

    @BeforeClass
    public static void setup()
    {
        ApplicationConfiguration applicationConfiguration =
                ApplicationConfigurationFactory.getInstance().createApplicationConfiguration("appX");

        IdentityServiceClientFactory factory = new IdentityServiceClientFactory(LOGGER);
        client = factory.createClient(applicationConfiguration, DummyPropertySourceConfiguration.class);
    }

    @Test
    public void testDescribe() throws Exception
    {
        DescribeElementsCriteria criteria = new DescribeElementsCriteria(Arrays.asList("fafcdcf4-ae37-4456-94f5-609bad2026ef"));
        ElementsDescribed elementDescribed = client.describeElements(criteria, 10000l);

        Assert.assertNotNull(elementDescribed);
    }

    @Test
    public void testIdentify() throws Exception
    {
        Identity identity = new Identity("storagearray", Classification.DEVICE, null,
                Arrays.asList(new BusinessKey(BusinessKey.BusinessKeyType.CONTEXTUAL, "alias", "1")), 2);

        IdentifyElementsCriteria criteria = new IdentifyElementsCriteria(
                Arrays.asList(new ElementIdentity(UUID.randomUUID().toString(), identity)));
        ElementsIdentified elementIdentified = client.identifyElements(criteria, 10000l);

        Assert.assertNotNull(elementIdentified);
    }
}
