package dev.yangsijun.gauth.userinfo;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.core.user.GAuthUser;
import org.springframework.security.core.AuthenticatedPrincipal;

/**
 * An interface for loading GAuth users based on GAuth Authorization Requests.
 * The implementation is responsible for retrieving user information from GAuth and constructing
 * instances of GAuthUser.
 *
 * @param <R> The type of GAuth Authorization Request
 * @param <U> The type of GAuth User
 * @since 2.0.0
 * @see GAuthAuthorizationRequest
 * @see GAuthUser
 * @author Yang Sijun
 */
@FunctionalInterface
public interface GAuthUserService<R extends GAuthAuthorizationRequest, U extends GAuthUser> {
    U loadUser(R userRequest) throws GAuthAuthenticationException;

}
