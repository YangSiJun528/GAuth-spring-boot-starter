package dev.yangsijun.gauth.authentication;

import dev.yangsijun.gauth.core.GAuthPluginVersion;
import dev.yangsijun.gauth.core.user.GAuthUser;
import dev.yangsijun.gauth.registration.GAuthRegistration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of an {@link AbstractAuthenticationToken} that represents an GAuth Authentication.
 * @since 2.0.0
 * @author Yang Sijun
 */
public class GAuthAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = GAuthPluginVersion.SERIAL_VERSION_UID;
    private String code;
    private GAuthUser principal;
    private GAuthRegistration registration;
    private Map<String, Object> additionalParameters = new HashMap<>();

    public GAuthAuthenticationToken(
            Collection<? extends GrantedAuthority> authorities,
            String code,
            GAuthUser principal
    ) {
        super(authorities);
        this.code = code;
        this.principal = principal;
        this.setAuthenticated(true);
    }

    public GAuthAuthenticationToken(
            String code,
            Map<String, Object> additionalParameters,
            GAuthRegistration registration
    ) {
        super(Collections.emptyList());
        this.code = code;
        this.additionalParameters = additionalParameters;
        this.setAuthenticated(false);
    }

    @Override
    public GAuthUser getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        // Credentials are never exposed (by the Provider) for an GAuth User
        return "";
    }

    public String getCode() {
        return code;
    }

    public Map<String, Object> getAdditionalParameters() {
        return additionalParameters;
    }

    public GAuthRegistration getRegistration() {
        return registration;
    }

    @Override
    public String toString() {
        return "GAuthAuthenticationToken{" +
                "code='" + code + '\'' +
                ", principal=" + principal +
                ", additionalParameters=" + additionalParameters +
                '}';
    }
}
