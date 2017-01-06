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
import com.dell.cpsd.identity.service.api.ElementDescribed;
import com.dell.cpsd.identity.service.api.ElementIdentified;
import com.dell.cpsd.identity.service.api.Identity;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

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
        DescribeElementCriteria criteria = new DescribeElementCriteria("78cfeff9-008a-48f2-b2cf-fdc2153e4e9e");
        ElementDescribed elementDescribed = client.describeElement(criteria, 10000l);

        Assert.assertNotNull(elementDescribed);
    }

    @Test
    public void testIdentify() throws Exception
    {
        Identity identity = new Identity("storagearray", Classification.DEVICE, null,
                Arrays.asList(new BusinessKey(BusinessKey.BusinessKeyType.CONTEXTUAL, "alias", "1")));

        IdentifyElementCriteria criteria = new IdentifyElementCriteria(identity);
        ElementIdentified elementIdentified = client.identifyElement(criteria, 10000l);

        Assert.assertNotNull(elementIdentified);
    }
}
