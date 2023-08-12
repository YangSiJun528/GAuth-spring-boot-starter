package dev.yangsijun.gauth.core;

import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * This exception is thrown for all GAuth related Authentication errors.
 * @since 2.0.0
 * @author Yang Sijun
 */
public class GAuthAuthenticationException extends AuthenticationException {

    private final String errorCode;
    private final String description;

    public GAuthAuthenticationException(String errorCode, String description, Throwable cause) {
        super(errorCode + description, cause);
        Assert.hasText(errorCode, "errorCode cannot be empty");
        this.errorCode = errorCode;
        this.description = description;
    }

    public GAuthAuthenticationException(String errorCode, String description) {
        this(errorCode, description, null);
    }

    public final String getErrorCode() {
        return this.errorCode;
    }

    public final String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getErrorCode() + "] " + ((this.getDescription() != null) ? this.getDescription() : "");
    }
}
