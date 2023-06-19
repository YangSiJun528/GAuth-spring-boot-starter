package dev.yangsijun.gauth.userinfo;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.core.user.GAuthUser;

@FunctionalInterface
public interface GAuthUserService<R extends GAuthAuthorizationRequest, U extends GAuthUser> {
    U loadUser(R userRequest) throws GAuthAuthenticationException;

}
