package dev.yangsijun.gauth.configurer;

import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.web.GAuthAuthenticationEntryPoint;
import dev.yangsijun.gauth.web.GAuthAuthenticationFilter;
import dev.yangsijun.gauth.web.GAuthAuthorizationRequestRedirectFilter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * An AbstractHttpConfigurer for GAuth Client support.
 * @since 2.0.0
 * @author Yang Sijun
 */
public final class GAuthLoginConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<GAuthLoginConfigurer<H>, H> {
    private final GAuthRegistration registration;

    private final AuthenticationConfiguration authenticationConfiguration;

    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    private AuthenticationFailureHandler failureHandler =
            new AuthenticationEntryPointFailureHandler(new GAuthAuthenticationEntryPoint());

    private String loginProcessingUrl = GAuthAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI;

    private String loginPageUrl = GAuthAuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

    public GAuthLoginConfigurer(GAuthRegistration registration, AuthenticationConfiguration authenticationConfiguration) {
        this.registration = registration;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    public GAuthLoginConfigurer<H> loginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
        return this;
    }

    public GAuthLoginConfigurer<H> loginPageUrl(String loginPageUrl) {
        this.loginPageUrl = loginPageUrl;
        return this;
    }

    public GAuthLoginConfigurer<H> successHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
        return this;
    }

    public GAuthLoginConfigurer<H> failureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
        return this;
    }

    @Override
    public void init(H http) throws Exception {
        GAuthAuthenticationFilter authenticationFilter = new GAuthAuthenticationFilter(
                this.loginProcessingUrl, this.authenticationConfiguration.getAuthenticationManager(), registration);
        authenticationFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        authenticationFilter.setAuthenticationManager(this.authenticationConfiguration.getAuthenticationManager());
        authenticationFilter.setFilterProcessesUrl(this.loginProcessingUrl);
        authenticationFilter.setAuthenticationSuccessHandler(this.successHandler);
        authenticationFilter.setAuthenticationFailureHandler(this.failureHandler);
        http.addFilterBefore(
                authenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }

    @Override
    public void configure(H http) {
        GAuthAuthorizationRequestRedirectFilter authorizationRequestFilter =
                new GAuthAuthorizationRequestRedirectFilter(
                        this.loginPageUrl, this.registration);
        http.addFilterAfter(
                authorizationRequestFilter,
                FilterSecurityInterceptor.class
        );
    }
}
