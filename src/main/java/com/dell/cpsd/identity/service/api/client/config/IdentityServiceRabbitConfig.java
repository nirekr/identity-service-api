/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.config;

import com.dell.cpsd.common.rabbitmq.config.RabbitMqProductionConfig;
import com.dell.cpsd.common.rabbitmq.context.ApplicationConfiguration;
import com.dell.cpsd.common.rabbitmq.context.ApplicationConfigurationContext;
import com.dell.cpsd.common.rabbitmq.context.RabbitContext;
import com.dell.cpsd.common.rabbitmq.context.RabbitContextListener;
import com.dell.cpsd.common.rabbitmq.context.builder.RabbitContextBuilder;
import com.dell.cpsd.common.rabbitmq.template.OpinionatedRabbitTemplate;
import com.dell.cpsd.identity.service.api.DescribeElements;
import com.dell.cpsd.identity.service.api.ElementsDescribed;
import com.dell.cpsd.identity.service.api.ElementsIdentified;
import com.dell.cpsd.identity.service.api.IdentifyElements;
import com.dell.cpsd.identity.service.api.IdentityServiceError;
import com.dell.cpsd.identity.service.api.client.amqp.producer.AmqpIdentityServiceProducer;
import com.dell.cpsd.identity.service.api.client.amqp.producer.IdentityServiceProducer;
import com.dell.cpsd.service.common.client.rpc.DefaultMessageConsumer;
import com.dell.cpsd.service.common.client.rpc.DelegatingMessageConsumer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
@Configuration
@Import({RabbitMqProductionConfig.class, IdentityServicePropertiesConfig.class})
public class IdentityServiceRabbitConfig
{
    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Autowired
    private DelegatingMessageConsumer consumer;

    @Autowired
    private OpinionatedRabbitTemplate rabbitTemplate;

    @Bean
    public RabbitContext rabbitContext()
    {
        ApplicationConfiguration applicationConfiguration = ApplicationConfigurationContext.getCurrent();
        RabbitContextBuilder contextBuilder = new RabbitContextBuilder(rabbitConnectionFactory, applicationConfiguration);

        contextBuilder.requestsAndReplies(IdentifyElements.class, queueName(applicationConfiguration, "dell.cpsd.eids.element.identified"),
                true, consumer, ElementsIdentified.class, IdentityServiceError.class);
        contextBuilder.requestsAndReplies(DescribeElements.class, queueName(applicationConfiguration, "dell.cpsd.eids.element.described"),
                true, consumer, ElementsDescribed.class, IdentityServiceError.class);

        return contextBuilder.build();
    }

    @Bean
    public DelegatingMessageConsumer identityServiceConsumer()
    {
        return new DefaultMessageConsumer();
    }

    @Bean
    public IdentityServiceProducer identityServiceProducer()
    {
        return new AmqpIdentityServiceProducer(rabbitTemplate);
    }

    @Bean
    public OpinionatedRabbitTemplate rabbitTemplate()
    {
        return new OpinionatedRabbitTemplate();
    }

    @Bean
    public RabbitContextListener rabbitContextListener()
    {
        return new RabbitContextListener();
    }

    private String queueName(ApplicationConfiguration appConfig, String base)
    {
        return "queue." + base + "." + appConfig.getApplicationName();
    }
}
