/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
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
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * </p>
 *
 * @since SINCE-TBD
 */
public class IdentityServiceClientFactoryIntegrationTest
{
    private static ILogger               LOGGER = new LoggingManager().getLogger(IdentityServiceClientFactoryIntegrationTest.class);

    private static IdentityServiceClient client;

    @BeforeClass
    public static void setup()
    {
        ApplicationConfiguration applicationConfiguration = ApplicationConfigurationFactory.getInstance().createApplicationConfiguration(
                "appX");

        IdentityServiceClientFactory factory = new IdentityServiceClientFactory(LOGGER);
        client = factory.createClient(null, applicationConfiguration, DummyPropertySourceConfiguration.class);
    }

    @Test
    public void testDescribe() throws Exception
    {
        DescribeElementsCriteria criteria = new DescribeElementsCriteria(Arrays.asList("4864d576-25e6-48dc-9ab2-f275f30ce181"));
        ElementsDescribed elementDescribed = client.describeElements(criteria, 10000l);

        Assert.assertNotNull(elementDescribed);
    }

    @Test
    public void testIdentify() throws Exception
    {
        Identity identity = new Identity("storagearray", Classification.DEVICE, null, Arrays.asList(new BusinessKey(
                BusinessKey.BusinessKeyType.CONTEXTUAL, "alias", "1")), 2);

        IdentifyElementsCriteria criteria = new IdentifyElementsCriteria(Arrays.asList(new ElementIdentity(UUID.randomUUID().toString(),
                identity)));
        ElementsIdentified elementIdentified = client.identifyElements(criteria, 10000l);

        Assert.assertNotNull(elementIdentified);
    }
}
