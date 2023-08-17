package dev.yangsijun.gauth.autoconfiguration;

import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.template.GAuthTemplate;
import gauth.GAuth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class GAuthClientAutoConfigurationTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(GAuthClientAutoConfiguration.class))
            .withPropertyValues(
                    "gauth.security.client-id=qwertyuiopasdfghjklzxcvbnm",
                    "gauth.security.client-secret=zxcvbnmasdfghjklqwertyuiop",
                    "gauth.security.redirect-uri=http://localhost:8080/login/code/gauth"
            );

    @Test
    void autoConfigurationShouldLoad() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(GAuthClientAutoConfiguration.class);
        });
    }

    @Test
    void autoConfigurationShouldProvideBeans() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(GAuth.class);
            assertThat(context).hasSingleBean(GAuthTemplate.class);
            assertThat(context).hasSingleBean(GAuthRegistration.class);
        });
    }

    @Test
    void autoConfigurationShouldUseProperties() {
        contextRunner.run(context -> {
            GAuthProperties properties = context.getBean(GAuthProperties.class);
            assertThat(properties.getClientId()).isEqualTo("qwertyuiopasdfghjklzxcvbnm");
            assertThat(properties.getClientSecret()).isEqualTo("zxcvbnmasdfghjklqwertyuiop");
            assertThat(properties.getRedirectUri()).isEqualTo("http://localhost:8080/login/code/gauth");
        });
    }
}
