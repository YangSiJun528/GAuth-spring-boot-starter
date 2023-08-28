package dev.yangsijun.gauth.autoconfiguration;

import dev.yangsijun.gauth.configurer.GAuthLoginConfigurer;
import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.template.GAuthTemplate;
import dev.yangsijun.gauth.userinfo.DefaultGAuthUserService;
import dev.yangsijun.gauth.userinfo.GAuthAuthorizationRequest;
import dev.yangsijun.gauth.userinfo.GAuthUserService;
import dev.yangsijun.gauth.web.GAuthAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@AutoConfiguration
@ConditionalOnClass(GAuthTemplate.class)
@AutoConfigureAfter(AuthenticationConfiguration.class)
@Import({AuthenticationConfiguration.class, GAuthClientAutoConfiguration.class})
public class GAuthAuthenticationAutoConfiguration {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final GAuthRegistration gAuthRegistration;

    public GAuthAuthenticationAutoConfiguration(AuthenticationConfiguration authenticationConfiguration, GAuthRegistration gAuthRegistration) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.gAuthRegistration = gAuthRegistration;
    }

    @Bean
    @ConditionalOnMissingBean(GAuthUserService.class)
    public GAuthUserService<GAuthAuthorizationRequest, GAuthUser> autoGAuthUserService() {
        return new DefaultGAuthUserService();
    }

    @Bean
    @ConditionalOnMissingBean(GAuthLoginConfigurer.class)
    public GAuthLoginConfigurer<HttpSecurity> autoGAuthAuthenticationConfigurer() {
        return new GAuthLoginConfigurer<>(gAuthRegistration, authenticationConfiguration);
    }

    @Bean
    @ConditionalOnMissingBean(GAuthAuthenticationEntryPoint.class)
    public GAuthAuthenticationEntryPoint autoGAuthAuthenticationEntryPoint() {
        return new GAuthAuthenticationEntryPoint();
    }
}
