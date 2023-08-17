package dev.yangsijun.gauth.autoconfiguration;

import dev.yangsijun.gauth.authentication.GAuthAuthenticationProvider;
import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.template.GAuthTemplate;
import dev.yangsijun.gauth.userinfo.GAuthAuthorizationRequest;
import dev.yangsijun.gauth.userinfo.GAuthUserService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnClass(GAuthTemplate.class)
@AutoConfigureAfter(GAuthAuthenticationAutoConfiguration.class)
@Import({GAuthAuthenticationAutoConfiguration.class})
public class GAuthAuthenticationProviderAutoConfiguration {

    private final GAuthUserService<GAuthAuthorizationRequest, GAuthUser> gAuthUserService;

    public GAuthAuthenticationProviderAutoConfiguration(GAuthUserService<GAuthAuthorizationRequest, GAuthUser> gAuthUserService) {
        this.gAuthUserService = gAuthUserService;
    }

    @Bean
    @ConditionalOnMissingBean(GAuthAuthenticationProvider.class)
    public GAuthAuthenticationProvider autoGAuthAuthenticationProvider() {
        return new GAuthAuthenticationProvider(gAuthUserService);
    }

}
