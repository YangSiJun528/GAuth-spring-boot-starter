package dev.yangsijun.gauth.core.user;

import gauth.GAuthToken;

import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

public interface GAuthUser extends AuthenticatedPrincipal {
    @Nullable
    default <A> A getAttribute(String name) {
        return (A) getAttributes().get(name);
    }

    Map<String, Object> getAttributes();

    Collection<? extends GrantedAuthority> getAuthorities();

    GAuthToken getGAuthToken();
}
