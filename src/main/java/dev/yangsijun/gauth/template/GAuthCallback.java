package dev.yangsijun.gauth.template;

import gauth.exception.GAuthException;

import java.io.IOException;

/**
 A functional interface for defining callback actions used with GAuth operations.
 This interface is employed by {@link GAuthTemplate}.
 * @since 2.0.0
 * @author Yang Sijun
 *
 * @param <T> The type of result returned by the callback action.
 * @see GAuthTemplate
 */

@FunctionalInterface
public interface GAuthCallback<T> {
    T execute() throws IOException, GAuthException;
}
