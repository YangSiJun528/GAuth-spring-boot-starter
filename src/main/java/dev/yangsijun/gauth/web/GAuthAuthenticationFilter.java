package dev.yangsijun.gauth.web;

import dev.yangsijun.gauth.authentication.GAuthAuthenticationToken;
import dev.yangsijun.gauth.core.GAuthAuthenticationException;
import dev.yangsijun.gauth.registration.GAuthRegistration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Collections;

public class GAuthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String NO_PARAMETER_CODE = "not_found_code_parameter";

    public static final String DEFAULT_FILTER_PROCESSES_URI = "/login/code/gauth";
    private GAuthRegistration registration;

    public GAuthAuthenticationFilter(String defaultFilterProcessesUrl, GAuthRegistration registration, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "GET"));
        this.registration = registration;
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String code = request.getParameter("code");
        if(code == null) {
            String errorMessage = "The value of the 'code' parameter in the redirect URI is missing or invalid";
            throw new GAuthAuthenticationException(NO_PARAMETER_CODE, errorMessage);
        }
        Object authenticationDetails = this.authenticationDetailsSource.buildDetails(request);
        // 현재는 파라미터로 code 외에 다른 값이 없어서 Collections.emptyMap() 반환
        GAuthAuthenticationToken authenticationRequest = new GAuthAuthenticationToken(code, registration, Collections.emptyMap());
        authenticationRequest.setDetails(authenticationDetails);
        GAuthAuthenticationToken authenticationResult = (GAuthAuthenticationToken) this.getAuthenticationManager().authenticate(authenticationRequest);
        authenticationResult.setDetails(authenticationDetails);
        return authenticationResult;
    }

}
