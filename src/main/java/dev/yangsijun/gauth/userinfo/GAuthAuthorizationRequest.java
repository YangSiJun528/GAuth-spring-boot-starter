package dev.yangsijun.gauth.userinfo;

import dev.yangsijun.gauth.registration.GAuthRegistration;

import java.util.Collections;
import java.util.Map;

public class GAuthAuthorizationRequest {

    private final String code;
    private final GAuthRegistration gauthRegistration;
    private final Map<String, Object> additionalParameters;

    public GAuthAuthorizationRequest(String code, GAuthRegistration gauthRegistration, Map<String, Object> additionalParameters) {
        this.code = code;
        this.gauthRegistration = gauthRegistration;
        this.additionalParameters = additionalParameters;
    }

    public GAuthAuthorizationRequest(String code, GAuthRegistration gauthRegistration) {
        this.code = code;
        this.gauthRegistration = gauthRegistration;
        this.additionalParameters = Collections.emptyMap();
    }

    public GAuthRegistration getgauthRegistration() {
        return gauthRegistration;
    }

    public Map<String, Object> getAdditionalParameters() {
        return additionalParameters;
    }

    public String getCode() {
        return code;
    }

}
