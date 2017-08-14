/**
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * Dell EMC Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client.config;

import com.dell.cpsd.common.rabbitmq.config.RabbitMQPropertiesConfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * <p>
 * Copyright © 2017 Dell Inc. or its subsidiaries.  All Rights Reserved.
 * </p>
 *
 * @since SINCE-TBD
 */
@Configuration
@PropertySources({
       // @PropertySource(value = "classpath:META-INF/spring/identity-service-api/rabbitmq-config.properties", ignoreResourceNotFound = true),
        @PropertySource("classpath:rabbitmq-config.properties")
})
@Qualifier("rabbitPropertiesConfig")
public class DummyRabbitPropertiesConfig extends RabbitMQPropertiesConfig
{
    @Value("${remote.dell.amqp.rabbitHostname}")
    private String rabbitHostname;

   
    @Value("${remote.dell.amqp.rabbitIsSslEnabled}")
    private String rabbitIsSslEnabled;
    
    @Value("${remote.dell.amqp.rabbitTlsVersion}")
    private String rabbitTlsVersion;

   
    @Value("${remote.dell.amqp.rabbitPort}")
    private String rabbitPort;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
}
