package dev.yangsijun.gauth.autoconfiguration;

import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.template.GAuthTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(GAuthTemplate.class)
@AutoConfigureBefore(GAuthAuthenticationAutoConfiguration.class)
@EnableConfigurationProperties(GAuthProperties.class)
public class GAuthClientAutoConfiguration {

    private final GAuthProperties properties;

    public GAuthClientAutoConfiguration(GAuthProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(GAuthRegistration.class)
    public GAuthRegistration autoGAuthRegistration() {
        return new GAuthRegistration(properties.getClientId(), properties.getClientSecret(), properties.getRedirectUri());
    }
}
