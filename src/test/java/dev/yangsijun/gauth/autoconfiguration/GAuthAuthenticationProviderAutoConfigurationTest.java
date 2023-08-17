package dev.yangsijun.gauth.autoconfiguration;

import dev.yangsijun.gauth.authentication.GAuthAuthenticationProvider;
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
class GAuthAuthenticationProviderAutoConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void autoConfigurationShouldProvideBeans() {
        assertThat(context.getBean(GAuthAuthenticationProvider.class)).isNotNull();
    }
}
