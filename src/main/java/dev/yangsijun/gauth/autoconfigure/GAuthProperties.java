package dev.yangsijun.gauth.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

@ConfigurationProperties(prefix = "gauth", ignoreUnknownFields = false)
public record GAuthProperties(

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
}
