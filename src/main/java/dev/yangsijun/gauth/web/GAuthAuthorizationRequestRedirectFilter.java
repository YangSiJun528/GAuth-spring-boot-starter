package dev.yangsijun.gauth.web;

import dev.yangsijun.gauth.registration.GAuthRegistration;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This Filter initiates the authorization code grant flow by redirecting the End-User's user-agent to the GAuth Authorization Server's Authorization Endpoint.
 *
 * @since 2.0.0
 * @author Yang Sijun
 */
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
