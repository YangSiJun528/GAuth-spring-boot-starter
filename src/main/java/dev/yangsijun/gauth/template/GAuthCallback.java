package dev.yangsijun.gauth.template;

import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import org.springframework.web.client.HttpStatusCodeException;

/**
 * A functional interface for defining callback actions used with GAuth operations.
 * This interface is employed by {@link GAuthTemplate}.
 *
 * @param <T> The type of result returned by the callback action.
 * @author Yang Sijun
 * @see GAuthTemplate
 * @since 2.0.0
 */

@FunctionalInterface
public interface GAuthCallback<T> {
    T execute() throws HttpStatusCodeException, GAuthAuthenticationException;
}
