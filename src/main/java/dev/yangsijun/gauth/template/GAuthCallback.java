package dev.yangsijun.gauth.template;

import gauth.exception.GAuthException;

import java.io.IOException;

@FunctionalInterface
public interface GAuthCallback<T> {
    T execute() throws IOException, GAuthException;
}
