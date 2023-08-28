package dev.yangsijun.gauth.template;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientResponseException;

/**
 * A template class which adds convenience functionality for GAuth-related actions.
 *
 * @author Yang Sijun
 * @since 2.0.0
 */
public class GAuthTemplate {
    private static final String GAUTH_CLIENT_CODE = "gauth_client_error";
    private static final String GAUTH_SERVER_CODE = "gauth_server_error";

    public <R> R execute(GAuthCallback<R> action) throws RestClientResponseException, GAuthAuthenticationException {
        try {
            return action.execute();
        } catch (HttpClientErrorException e) {
            throw new GAuthAuthenticationException(GAUTH_CLIENT_CODE,
                    "[GAuth Authorization Server Status Code : " + e.getStatusCode().value() + "] ",
                    e.getCause()
            );
        } catch (HttpServerErrorException e) {
            throw new GAuthAuthenticationException(GAUTH_SERVER_CODE,
                    "Something wrong access to GAuth Authorization Sever", e.getCause()
            );
        } catch (RestClientResponseException e) {
            throw new GAuthAuthenticationException(e.getMessage(), e.getStatusText(), e.getCause());
        }
    }
}
