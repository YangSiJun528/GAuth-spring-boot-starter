package dev.yangsijun.gauth.authentication;

import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.userinfo.GAuthAuthorizationRequest;
import dev.yangsijun.gauth.userinfo.GAuthUserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;

public class GAuthAuthenticationProvider implements AuthenticationProvider {

    private final GAuthUserService<GAuthAuthorizationRequest, GAuthUser> userService;

    public GAuthAuthenticationProvider(GAuthUserService<GAuthAuthorizationRequest, GAuthUser> userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        GAuthAuthenticationToken authenticationToken = (GAuthAuthenticationToken) authentication;
        Map<String, Object> additionalParameters = authenticationToken.getAdditionalParameters();
        GAuthUser gAuthUser = userService.loadUser(new GAuthAuthorizationRequest(
                authenticationToken.getCode(), authenticationToken.getgauthRegistration(), additionalParameters));
        GAuthAuthenticationToken authenticationResult = new GAuthAuthenticationToken(gAuthUser.getAuthorities(),
                authenticationToken.getCode(), authenticationToken.getgauthRegistration(), gAuthUser, gAuthUser.getGAuthToken());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return GAuthAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
