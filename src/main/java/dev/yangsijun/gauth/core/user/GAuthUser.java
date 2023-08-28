package dev.yangsijun.gauth.core.user;

import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * A representation of a user Principal that is registered with an GAuth Provider.
 * @since 2.0.0
 * @author Yang Sijun
 */
public interface GAuthUser extends AuthenticatedPrincipal {
    @Nullable
    default <A> A getAttribute(String name) {
        return (A) getAttributes().get(name);
    }

    Map<String, Object> getAttributes();

    Collection<? extends GrantedAuthority> getAuthorities();
}