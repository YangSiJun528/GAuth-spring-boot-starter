package dev.yangsijun.gauth.template;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.IOException;

/**
 * A template class which adds convenience functionality for GAuth-related actions.
 *
 * @author Yang Sijun
 * @since 2.0.0
 */
public class GAuthTemplate {
    private static final String GAUTH_CLIENT_CODE = "gauth_client_error";
    private static final String GAUTH_SERVER_CODE = "gauth_server_error";

    // todo 근데 이거 굳이 에외처리 해야하나
    //  그냥 RestClientResponseException 하위 에러 그대로 던져도 충분히 상황 이해할만한데
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
        }
    }
}
