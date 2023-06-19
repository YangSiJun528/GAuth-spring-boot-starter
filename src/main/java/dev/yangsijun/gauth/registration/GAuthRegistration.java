package dev.yangsijun.gauth.registration;

import dev.yangsijun.gauth.core.GAuthSpringSecurityPluginVersion;

public class GAuthRegistration {
    private static final long serialVersionUID = GAuthSpringSecurityPluginVersion.SERIAL_VERSION_UID;

    private String clientId;

    private String clientSecret;

    private String redirectUri;

    public GAuthRegistration(String clientId, String clientSecret, String redirectUri) {
        if (clientId == null || clientSecret == null || redirectUri == null) {
            throw new IllegalArgumentException("clientId, clientSecret, and redirectUri must not be null");
        }
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
