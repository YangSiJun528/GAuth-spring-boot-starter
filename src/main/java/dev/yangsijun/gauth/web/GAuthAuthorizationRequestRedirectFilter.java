package dev.yangsijun.gauth.web;

import dev.yangsijun.gauth.registration.GAuthRegistration;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GAuthAuthorizationRequestRedirectFilter extends OncePerRequestFilter {

    public static final String DEFAULT_AUTHORIZATION_REQUEST_BASE_URI = "/gauth/authorization";

    private RedirectStrategy authorizationRedirectStrategy = new DefaultRedirectStrategy();

    private String gauthAuthorizationRequestUri;

    private final GAuthRegistration registration;

    public GAuthAuthorizationRequestRedirectFilter(
            String gauthAuthorizationRequestUri,
            GAuthRegistration registration
    ) {
        this.gauthAuthorizationRequestUri = gauthAuthorizationRequestUri;
        this.registration = registration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!request.getServletPath().equals(gauthAuthorizationRequestUri)) {
            filterChain.doFilter(request, response);
        } else {
            this.authorizationRedirectStrategy.sendRedirect(request, response, getRedirectUri());
        }
    }

    private String getRedirectUri() {
        return "https://gauth.co.kr/login?client_id=" + registration.getClientId() + "&redirect_uri=" + registration.getRedirectUri();
    }
}
