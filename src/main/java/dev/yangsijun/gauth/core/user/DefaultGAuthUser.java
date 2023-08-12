package dev.yangsijun.gauth.core.user;

import dev.yangsijun.gauth.core.GAuthPluginVersion;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.*;

/**
 * The default implementation of the {@link GAuthUser} interface/.
 * @since 2.0.0
 * @author Yang Sijun
 */
public class DefaultGAuthUser implements GAuthUser {
    private static final long serialVersionUID = GAuthPluginVersion.SERIAL_VERSION_UID;

    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;

    public DefaultGAuthUser(
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attributes,
            String nameAttributeKey
    ) {
        Assert.notEmpty(attributes, "attributes cannot be empty");
        Assert.hasText(nameAttributeKey, "nameAttributeKey cannot be empty");

        if (!attributes.containsKey(nameAttributeKey))
            throw new IllegalArgumentException("Missing attribute '" + nameAttributeKey + "' in attributes");

        this.authorities = (authorities != null) ? Collections.unmodifiableCollection(authorities) : Collections.emptyList();
        this.attributes = Collections.unmodifiableMap(new LinkedHashMap<>(attributes));
        this.nameAttributeKey = nameAttributeKey;
    }

    @Override
    public String getName() {
        return Objects.requireNonNull(this.getAttribute(this.nameAttributeKey)).toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String toString() {
        return "DefaultGAuthUser{" +
                "authorities=" + authorities +
                ", attributes=" + attributes +
                ", nameAttributeKey='" + nameAttributeKey + '\'' +
                '}';
    }
}
