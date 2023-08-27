package dev.yangsijun.gauth.userinfo;

import dev.yangsijun.gauth.registration.GAuthRegistration;

import java.util.Collections;
import java.util.Map;

/**
 * Represents a request the {@link GAuthUserService} uses when initiating a request to
 * the UserInfo Endpoint.
 * @author Yang Sijun
 * @since 2.0.0
 * @see dev.yangsijun.gauth.registration.GAuthRegistration
 * @see GAuthUserService
 */
public class GAuthAuthorizationRequest {

    private final String code;
    private final Map<String, Object> additionalParameters;
    private final GAuthRegistration registration;

    public GAuthAuthorizationRequest(
            String code,
            Map<String, Object> additionalParameters,
            GAuthRegistration registration) {
        this.code = code;
        this.additionalParameters = additionalParameters;
        this.registration = registration;
    }

    public GAuthAuthorizationRequest(String code, GAuthRegistration registration) {
        this.code = code;
        this.registration = registration;
        this.additionalParameters = Collections.emptyMap();
    }

    public Map<String, Object> getAdditionalParameters() {
        return additionalParameters;
    }

    public String getCode() {
        return code;
    }

    public GAuthRegistration getRegistration() {
        return registration;
    }
}
