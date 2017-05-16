/**
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 */

package com.dell.cpsd.identity.service.api.client;

import com.dell.cpsd.common.rabbitmq.config.RabbitMQPropertiesConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * <p>
 * &copy; 2017 VCE Company, LLC. All rights reserved.
 * VCE Confidential/Proprietary Information
 * </p>
 *
 * @since SINCE-TBD
 */
@Configuration
@PropertySources({
        @PropertySource(value = "classpath:META-INF/spring/identity-service-api/rabbitmq.properties"),
        @PropertySource(value = "file:/etc/rabbitmq/client/rabbitmq-config.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${CREDENTIALS}", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${PASSPHRASES}", ignoreResourceNotFound = true)
})
@Qualifier("rabbitPropertiesConfig")
public class DummyPropertySourceConfiguration extends RabbitMQPropertiesConfig
{
}
