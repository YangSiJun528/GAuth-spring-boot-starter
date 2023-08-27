package dev.yangsijun.gauth.userinfo;

import dev.yangsijun.gauth.core.user.DefaultGAuthUser;
import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.template.GAuthTemplate;
import gauth.GAuth;
import gauth.GAuthToken;
import gauth.GAuthUserInfo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 * A default implementation of {@link GAuthUserService} that supports GAuth providers.
 *
 * @author Yang Sijun
 * @since 2.0.0
 */
public class DefaultGAuthUserService
        implements GAuthUserService<GAuthAuthorizationRequest, GAuthUser> {

    private final GAuth gAuth;
    private final GAuthRegistration registration;
    private final GAuthTemplate gAuthTemplate;
    private final static String GAUTH_PREFIX = "GAUTH_";

    public DefaultGAuthUserService(GAuth gAuth, GAuthRegistration registration) {
        this.gAuth = gAuth;
        this.registration = registration;
        this.gAuthTemplate = new GAuthTemplate();
    }

    @Override
    public GAuthUser loadUser(GAuthAuthorizationRequest userRequest)
            throws AuthenticationException {
        GAuthToken token = getToken(userRequest);
        String accessToken = token.getAccessToken();

        GAuthUserInfo info = gAuthTemplate.execute(() ->
                gAuth.getUserInfo(accessToken));
        return getGAuthUser(info, token);
    }

    private GAuthToken getToken(GAuthAuthorizationRequest request) {
        String code = request.getCode();
        String clientId = registration.getClientId();
        String clientSecret = registration.getClientSecret();
        String redirectUri = registration.getRedirectUri();
        return gAuthTemplate.execute(() ->
                gAuth.generateToken(code, clientId, clientSecret, redirectUri));
    }

    private GAuthUser getGAuthUser(GAuthUserInfo info, GAuthToken token) {
        String nameAttribute = "email";
        String email = info.getEmail();
        String name = info.getName();
        Integer grade = info.getGrade();
        Integer classNum = info.getClassNum();
        Integer num = info.getNum();
        String gender = info.getGender();
        String profileUrl = info.getProfileUrl();
        String role = info.getRole();

        Map<String, Object> attributes = new HashMap<>(Map.of(
                nameAttribute, email,
                "name", name,
                "grade", grade,
                "classNum", classNum,
                "num", num,
                "gender", gender,
                "profileUrl", profileUrl,
                "role", role,
                "accessToken", token.getAccessToken(),
                "refreshToken", token.getRefreshToken()
        ));

        Collection<GrantedAuthority> authorities = new ArrayList<>(
                List.of(new SimpleGrantedAuthority(GAUTH_PREFIX + role))
        );

        return new DefaultGAuthUser(authorities, attributes, nameAttribute);
    }
}
