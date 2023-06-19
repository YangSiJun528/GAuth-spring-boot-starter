package dev.yangsijun.gauth.autoconfigure;

import dev.yangsijun.gauth.authentication.GAuthAuthenticationProvider;
import dev.yangsijun.gauth.configurer.GAuthLoginConfigurer;
import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.userinfo.DefaultGAuthUserService;
import dev.yangsijun.gauth.userinfo.GAuthUserService;
import dev.yangsijun.gauth.web.GAuthAuthenticationEntryPoint;
import gauth.GAuth;
import gauth.impl.GAuthImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

@Configuration
@EnableConfigurationProperties(GAuthProperties.class)
@ConditionalOnClass(GAuthUserService.class)
public class GAuthAutoConfiguration {

    private GAuthProperties properties;

    public GAuthAutoConfiguration(GAuthProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public GAuth gauth() {
        return new GAuthImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public GAuthUserService autoGAuthUserService() {
        return new DefaultGAuthUserService(gauth());
    }

    @Bean
    @ConditionalOnMissingBean
    public GAuthRegistration autoGAuthRegistration() {
        return new GAuthRegistration(properties.clientId(), properties.clientSecret(), properties.redirectUri());
    }

    @Bean
    @ConditionalOnMissingBean
    public GAuthLoginConfigurer autoGAuthAuthenticationConfigurer() {
        return new GAuthLoginConfigurer(autoGAuthRegistration(), autoAuthenticationManager());
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager autoAuthenticationManager() {
        return new ProviderManager(
                autoGAuthAuthenticationProvider()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public GAuthAuthenticationProvider autoGAuthAuthenticationProvider() {
        return new GAuthAuthenticationProvider(autoGAuthUserService());
    }

    @Bean
    @ConditionalOnMissingBean
    public GAuthAuthenticationEntryPoint autoGAuthAuthenticationEntryPoint() {
        return new GAuthAuthenticationEntryPoint();
    }

}
