package dev.yangsijun.gauth.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 An implementation of the {@link AuthenticationEntryPoint} that handles unauthorized requests.
 *
 * @since 2.0.0
 * @author Yang Sijun
 */
public class GAuthAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter writer = response.getWriter();
        writer.print(
                "{\"status\":" + HttpStatus.UNAUTHORIZED.value() +
                ",\"message\":\"" + authException.toString() + "\"}"
        );
        writer.flush();
    }
}
