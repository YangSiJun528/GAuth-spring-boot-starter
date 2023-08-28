package dev.yangsijun.gauth.userinfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.core.user.DefaultGAuthUser;
import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.registration.GAuthRegistration;
import dev.yangsijun.gauth.template.GAuthTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * A default implementation of {@link GAuthUserService} that supports GAuth providers.
 *
 * @author Yang Sijun
 * @since 2.0.0
 */
public class DefaultGAuthUserService
        implements GAuthUserService<GAuthAuthorizationRequest, GAuthUser> {
    private static final String GET_USERINFO_URL = "https://open.gauth.co.kr/user";
    private static final String GET_TOKEN_URL = "https://server.gauth.co.kr/oauth/token";
    private final ObjectMapper mapper;
    private final static String GAUTH_PREFIX = "GAUTH_";
    private final GAuthTemplate gAuthTemplate;
    private final RestTemplate restTemplate;

    public DefaultGAuthUserService() {
        this.restTemplate = new RestTemplate();
        this.gAuthTemplate = new GAuthTemplate();
        this.mapper = new ObjectMapper();
    }

    @Override
    public GAuthUser loadUser(GAuthAuthorizationRequest userRequest)
            throws AuthenticationException {
        Map<String, String> token = getToken(userRequest);
        String accessToken = token.get("accessToken");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + accessToken);

        ResponseEntity<Map<String, Object>> response = gAuthTemplate.execute(() ->
                restTemplate.exchange(
                        GET_USERINFO_URL,
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        }
                ));

        return getGAuthUser(response.getBody(), token);
    }

    private Map<String, String> getToken(GAuthAuthorizationRequest request) {
        GAuthRegistration registration = request.getRegistration();
        String code = request.getCode();
        String clientId = registration.getClientId();
        String clientSecret = registration.getClientSecret();
        String redirectUri = registration.getRedirectUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Type", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("code", code);
        body.put("clientId", clientId);
        body.put("clientSecret", clientSecret);
        body.put("redirectUri", redirectUri);

        try {
            mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new GAuthAuthenticationException("json_serialization_error", e.getMessage(), e.getCause());
        }

        ResponseEntity<Map<String, String>> response = gAuthTemplate.execute(() ->
                restTemplate.exchange(
                        GET_TOKEN_URL,
                        HttpMethod.POST,
                        new HttpEntity<>(body, headers),
                        new ParameterizedTypeReference<Map<String, String>>() {
                        }
                ));
        return response.getBody();
    }

    private GAuthUser getGAuthUser(Map<String, Object> info, Map<String, String> token) {
        String nameAttribute = "email";
        String email = (String) info.get("email");
        String name = (String) info.get("name");
        Integer grade = (Integer) info.get("grade");
        Integer classNum = (Integer) info.get("classNum");
        Integer num = (Integer) info.get("num");
        String gender = (String) info.get("gender");
        String profileUrl = (String) info.get("profileUrl");
        String role = (String) info.get("role");
        String accessToken = token.get("accessToken");
        String refreshToken = token.get("refreshToken");

        Map<String, Object> attributes = new HashMap<>(Map.of(
                nameAttribute, email,
                "name", name,
                "grade", grade,
                "classNum", classNum,
                "num", num,
                "gender", gender,
                "profileUrl", profileUrl,
                "role", role,
                "accessToken", accessToken,
                "refreshToken", refreshToken
        ));

        Collection<GrantedAuthority> authorities = new ArrayList<>(
                List.of(new SimpleGrantedAuthority(GAUTH_PREFIX + role))
        );

        return new DefaultGAuthUser(authorities, attributes, nameAttribute);
    }
}
