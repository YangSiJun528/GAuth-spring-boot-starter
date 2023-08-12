package dev.yangsijun.gauth.registration;

import dev.yangsijun.gauth.core.GAuthPluginVersion;
import org.springframework.util.Assert;

/**
 * This class is used to map OAuth2ClientProperties to client registrations.
 * @since 2.0.0
 * @author Yang Sijun
 */
public class GAuthRegistration {
    private static final long serialVersionUID = GAuthPluginVersion.SERIAL_VERSION_UID;

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    public GAuthRegistration(String clientId, String clientSecret, String redirectUri) {
        Assert.notNull(clientId, "clientId must not be null");
        Assert.notNull(clientSecret, "clientSecret must not be null");
        Assert.notNull(redirectUri, "redirectUri must not be null");
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
