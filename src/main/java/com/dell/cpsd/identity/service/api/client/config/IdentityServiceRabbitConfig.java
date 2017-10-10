/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.config;

import com.dell.cpsd.common.rabbitmq.context.ApplicationConfiguration;
import com.dell.cpsd.common.rabbitmq.context.ApplicationConfigurationContext;
import com.dell.cpsd.common.rabbitmq.context.RabbitContext;
import com.dell.cpsd.common.rabbitmq.context.RabbitContextListener;
import com.dell.cpsd.common.rabbitmq.context.builder.MessageMetaData;
import com.dell.cpsd.common.rabbitmq.context.builder.MessageMetaDataReader;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collection;

/**
 * <p>
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * </p>
 *
 * @since 1.0
 */
@Configuration
public class IdentityServiceRabbitConfig
{

    /**
     * @return RabbitContextBuilder instance created from ConnectionFactory, ApplicationConfiguration, collection of MessageMetaData
     * @throws IOException
     */
    @Bean(name = "rabbitContext")
    public RabbitContext rabbitContext(ConnectionFactory rabbitConnectionFactory, DelegatingMessageConsumer consumer) throws IOException
    {
        ApplicationConfiguration applicationConfiguration = ApplicationConfigurationContext.getCurrent();

        MessageMetaDataReader reader = new MessageMetaDataReader();
        Collection<MessageMetaData> metaDatas = reader.read(getClass().getClassLoader().getResourceAsStream(
                "META-INF/spring/identity-service-api/amqp.json"));

        RabbitContextBuilder contextBuilder = new RabbitContextBuilder(rabbitConnectionFactory, applicationConfiguration, metaDatas);

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
    public IdentityServiceProducer identityServiceProducer(OpinionatedRabbitTemplate rabbitTemplate)
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
