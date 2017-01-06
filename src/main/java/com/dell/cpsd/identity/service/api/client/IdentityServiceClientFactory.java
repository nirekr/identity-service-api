/**
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import com.dell.cpsd.common.logging.ILogger;
import com.dell.cpsd.common.rabbitmq.context.ApplicationConfiguration;
import com.dell.cpsd.common.rabbitmq.context.ApplicationConfigurationContext;
import com.dell.cpsd.common.rabbitmq.context.RabbitContext;
import com.dell.cpsd.identity.service.api.client.amqp.producer.IdentityServiceProducer;
import com.dell.cpsd.identity.service.api.client.config.IdentityServiceRabbitConfig;
import com.dell.cpsd.service.common.client.rpc.DefaultMessageConsumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <p>
 * &copy; 2016 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
public class IdentityServiceClientFactory
{
    private static final String ACTIVE_PROFILE_PROPERTY = "activeProfile";

    private ILogger logger;

    public IdentityServiceClientFactory(ILogger logger)
    {
        this.logger = logger;
    }

    public IdentityServiceClient createClient(ApplicationConfiguration applicationConfiguration, Class... propertySourceConfigurations)
    {
        ApplicationConfigurationContext.setCurrent(applicationConfiguration);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        final String activeProfile = System.getProperty(ACTIVE_PROFILE_PROPERTY, "production");
        context.getEnvironment().setActiveProfiles(activeProfile);
        context.register(IdentityServiceRabbitConfig.class);

        for (Class configurationClass : propertySourceConfigurations)
        {
            context.register(configurationClass);
        }

        context.refresh();

        RabbitContext rabbitContext = context.getBean(RabbitContext.class);
        DefaultMessageConsumer consumer = context.getBean(DefaultMessageConsumer.class);
        IdentityServiceProducer producer = context.getBean(IdentityServiceProducer.class);

        IdentityServiceClient client = new IdentityServiceClient(logger, rabbitContext, consumer, producer);

        rabbitContext.start();
        return client;
    }
}
