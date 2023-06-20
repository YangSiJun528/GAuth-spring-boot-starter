package dev.yangsijun.gauth.configurer;

import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.web.GAuthAuthenticationEntryPoint;
import dev.yangsijun.gauth.web.GAuthAuthenticationFilter;
import dev.yangsijun.gauth.web.GAuthAuthorizationRequestRedirectFilter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

public final class GAuthLoginConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<GAuthLoginConfigurer<H>, H> {
    private final GAuthRegistration registration;
    private AuthenticationManager authenticationManager;

    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();

    private AuthenticationFailureHandler failureHandler =
            new AuthenticationEntryPointFailureHandler(new GAuthAuthenticationEntryPoint());

    private String loginProcessingUrl = GAuthAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI;

    private String loginPageUrl = GAuthAuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI;

    public GAuthLoginConfigurer(GAuthRegistration registration, AuthenticationManager authenticationManager) {
        this.registration = registration;
        this.authenticationManager = authenticationManager;
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
    public void init(H http) {
        GAuthAuthenticationFilter authenticationFilter = new GAuthAuthenticationFilter(
                this.loginProcessingUrl, this.registration, this.authenticationManager);
        authenticationFilter.setSecurityContextHolderStrategy(getSecurityContextHolderStrategy());
        authenticationFilter.setSecurityContextRepository(new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository()));
        authenticationFilter.setAuthenticationManager(this.authenticationManager);
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