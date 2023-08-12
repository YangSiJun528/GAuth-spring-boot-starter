package dev.yangsijun.gauth.template;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import gauth.exception.GAuthException;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * A template class which adds convenience functionality for GAuth-related actions.
 * @since 2.0.0
 * @author Yang Sijun
 */
public class GAuthTemplate {
    private static final String GAUTH_CLIENT_CODE = "gauth_client_error";
    private static final String GAUTH_SERVER_CODE = "gauth_server_error";

    public <R> R execute(GAuthCallback<R> action) {
        try {
            return action.execute();
        } catch (GAuthException e) {
            throw handleGAuthException(e);
        } catch (IOException e) {
            throw new GAuthAuthenticationException(GAUTH_SERVER_CODE,
                    "Something wrong access to GAuth Authorization Sever", e.getCause()
            );
        }
    }

    private GAuthAuthenticationException handleGAuthException(GAuthException ex) {
        if (is4xx(ex.getCode())) {
            return new GAuthAuthenticationException(GAUTH_CLIENT_CODE,
                    "[GAuth Authorization Server Status Code : " + HttpStatus.valueOf(ex.getCode()).value() + "] ",
                    ex.getCause()
            );
        } else {
            return new GAuthAuthenticationException(GAUTH_SERVER_CODE,
                    "[GAuth Authorization Server Status Code : " + HttpStatus.valueOf(ex.getCode()).value() + "] ",
                    ex.getCause()
            );
        }
    }

    private boolean is4xx(int code) {
        return code < 500;
    }
}
