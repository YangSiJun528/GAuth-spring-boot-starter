package dev.yangsijun.gauth.authentication;

import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.userinfo.GAuthAuthorizationRequest;
import dev.yangsijun.gauth.userinfo.GAuthUserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * An {@link AuthenticationProvider} implementation that retrieves GAuth user information from a {@link GAuthUserService}.
 * @since 2.0.0
 * @author Yang Sijun
 */
public class GAuthAuthenticationProvider implements AuthenticationProvider {

    private final GAuthUserService<GAuthAuthorizationRequest, GAuthUser> userService;

    public GAuthAuthenticationProvider(
            GAuthUserService<GAuthAuthorizationRequest, GAuthUser> userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        GAuthAuthenticationToken authenticationToken = (GAuthAuthenticationToken) authentication;
        Map<String, Object> additionalParameters = authenticationToken.getAdditionalParameters();
        GAuthUser gAuthUser = userService.loadUser(
                new GAuthAuthorizationRequest(authenticationToken.getCode(), additionalParameters)
        );
        GAuthAuthenticationToken authenticationResult =
                new GAuthAuthenticationToken(gAuthUser.getAuthorities(), authenticationToken.getCode(), gAuthUser);
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return GAuthAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
