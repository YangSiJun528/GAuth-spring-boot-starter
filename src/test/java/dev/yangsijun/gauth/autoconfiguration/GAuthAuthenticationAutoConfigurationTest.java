package dev.yangsijun.gauth.autoconfiguration;

import dev.yangsijun.gauth.configurer.GAuthLoginConfigurer;
import dev.yangsijun.gauth.userinfo.GAuthUserService;
import dev.yangsijun.gauth.web.GAuthAuthenticationEntryPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "gauth.security.redirect-uri=redirect-uri",
        "gauth.security.client-id=client-id",
        "gauth.security.client-secret=client-secret"
})
class GAuthAuthenticationAutoConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void autoConfigurationShouldProvideBeans() {
        assertThat(context.getBean(GAuthUserService.class)).isNotNull();
        assertThat(context.getBean(GAuthLoginConfigurer.class)).isNotNull();
        assertThat(context.getBean(GAuthAuthenticationEntryPoint.class)).isNotNull();
    }
}
