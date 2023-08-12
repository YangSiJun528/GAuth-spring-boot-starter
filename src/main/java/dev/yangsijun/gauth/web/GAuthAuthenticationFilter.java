package dev.yangsijun.gauth.web;

import dev.yangsijun.gauth.authentication.GAuthAuthenticationToken;
import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

/**
 * An implementation of an {@link AbstractAuthenticationProcessingFilter} for GAuth Login.
 *
 * @since 2.0.0
 * @author Yang Sijun
 */
public class GAuthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String NO_PARAMETER_CODE = "not_found_code_parameter";

    public static final String DEFAULT_FILTER_PROCESSES_URI = "/login/code/gauth";

    public GAuthAuthenticationFilter(
            String defaultFilterProcessesUrl,
            AuthenticationManager authenticationManager
    ) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "GET"));
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        String code = request.getParameter("code");
        if (code == null) {
            String errorMessage = "The value of the 'code' parameter in the redirect URI is missing or invalid";
            throw new GAuthAuthenticationException(NO_PARAMETER_CODE, errorMessage);
        }
        Object authenticationDetails = this.authenticationDetailsSource.buildDetails(request);
        GAuthAuthenticationToken authenticationRequest =
                new GAuthAuthenticationToken(code, Collections.emptyMap());
        authenticationRequest.setDetails(authenticationDetails);
        GAuthAuthenticationToken authenticationResult =
                (GAuthAuthenticationToken) this.getAuthenticationManager().authenticate(authenticationRequest);
        authenticationResult.setDetails(authenticationDetails);
        return authenticationResult;
    }

}
