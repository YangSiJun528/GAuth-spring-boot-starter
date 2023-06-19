package dev.yangsijun.gauth.userinfo;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.core.user.DefaultGAuthUser;
import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.registration.GAuthRegistration;
import gauth.GAuth;
import gauth.GAuthToken;
import gauth.GAuthUserInfo;
import gauth.exception.GAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.*;

public class DefaultGAuthUserService implements GAuthUserService<GAuthAuthorizationRequest, GAuthUser> {

    private final GAuth gAuth;

    private static final String GAUTH_CLIENT_CODE = "gauth_client_error";

    private static final String GAUTH_SERVER_CODE = "gauth_server_error";

    public DefaultGAuthUserService(GAuth gAuth) {
        this.gAuth = gAuth;
    }

    @Override
    public GAuthUser loadUser(GAuthAuthorizationRequest userRequest) throws AuthenticationException {
        GAuthToken token = getToken(userRequest);

        GAuthUserInfo info;
        //TODO 나중에 중복 try-catch 해소
        try {
            info = gAuth.getUserInfo(token.getAccessToken());
        } catch (GAuthException e) {
            throw handleGAuthException(e);
        } catch (IOException e) {
            throw new GAuthAuthenticationException(GAUTH_SERVER_CODE,
                    "Something wrong access to GAuth Authorization Sever", e.getCause());
        }
        return getGAuthUser(info, token);
    }

    private GAuthToken getToken(GAuthAuthorizationRequest request) {
        GAuthRegistration registration = request.getgauthRegistration();
        String code = request.getCode();
        String clientId = registration.getClientId();
        String clientSecret = registration.getClientSecret();
        String redirectUri = registration.getRedirectUri();
        GAuthToken token;
        try {
            token = gAuth.generateToken(code, clientId, clientSecret, redirectUri);
        } catch (GAuthException e) {
            throw handleGAuthException(e);
        } catch (IOException e) {
            throw new GAuthAuthenticationException(GAUTH_SERVER_CODE,
                    "Something wrong access to GAuth Authorization Sever", e.getCause());
        }
        return token;
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
                "role", role
        ));
        Collection<GrantedAuthority> authorities = new ArrayList<>(List.of(new SimpleGrantedAuthority(role)));
        return new DefaultGAuthUser(authorities, attributes, nameAttribute, token);
    }

    private GAuthAuthenticationException handleGAuthException(GAuthException ex) {
        if (is4xx(ex.getCode())) {
            return new GAuthAuthenticationException(GAUTH_CLIENT_CODE,
                    "[GAuth Authorization Server Status Code : " + HttpStatus.valueOf(ex.getCode()).value() + "] ", ex.getCause());
        } else {
            return new GAuthAuthenticationException(GAUTH_SERVER_CODE,
                    "[GAuth Authorization Server Status Code : " + HttpStatus.valueOf(ex.getCode()).value() + "] ", ex.getCause());
        }
    }

    private boolean is4xx(int code) {
        return code < 500;
    }
}
