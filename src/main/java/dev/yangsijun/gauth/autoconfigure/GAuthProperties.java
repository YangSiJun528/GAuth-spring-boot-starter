package dev.yangsijun.gauth.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

@ConfigurationProperties(prefix = "gauth", ignoreUnknownFields = false)
public final class GAuthProperties {
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    public GAuthProperties(

            /**
             * GAuth client id.
             */
            String clientId,

            /**
             * GAuth client secret key.
             */
            String clientSecret,

            /**
             * GAuth redirect uri.
             */
            String redirectUri
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    public String clientId() {
        return clientId;
    }

    public String clientSecret() {
        return clientSecret;
    }

    public String redirectUri() {
        return redirectUri;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GAuthProperties) obj;
        return Objects.equals(this.clientId, that.clientId) &&
                Objects.equals(this.clientSecret, that.clientSecret) &&
                Objects.equals(this.redirectUri, that.redirectUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientSecret, redirectUri);
    }

    @Override
    public String toString() {
        return "GAuthProperties[" +
                "clientId=" + clientId + ", " +
                "clientSecret=" + clientSecret + ", " +
                "redirectUri=" + redirectUri + ']';
    }
}
