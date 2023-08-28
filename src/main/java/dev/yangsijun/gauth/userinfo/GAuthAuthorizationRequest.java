package dev.yangsijun.gauth.userinfo;

import dev.yangsijun.gauth.registration.GAuthRegistration;

/**
 * Represents a request the {@link GAuthUserService} uses when initiating a request to
 * the UserInfo Endpoint.
 *
 * @author Yang Sijun
 * @see dev.yangsijun.gauth.registration.GAuthRegistration
 * @see GAuthUserService
 * @since 2.0.0
 */
public class GAuthAuthorizationRequest {

    private final String code;
    private final GAuthRegistration registration;

    public GAuthAuthorizationRequest(String code, GAuthRegistration registration) {
        this.code = code;
        this.registration = registration;
    }

    public String getCode() {
        return code;
    }

    public GAuthRegistration getRegistration() {
        return registration;
    }
}
