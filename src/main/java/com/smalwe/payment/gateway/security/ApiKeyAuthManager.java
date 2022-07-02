package com.smalwe.payment.gateway.security;

import com.smalwe.payment.gateway.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ApiKeyAuthManager implements AuthenticationManager {

    private List<String> validApiKeys;

    public ApiKeyAuthManager(@Value("${secret.api.keys}") List<String> validApiKeys) {
        this.validApiKeys = validApiKeys;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();

        if (!validApiKeys.contains(principal)) {
            throw new AuthenticationException("API Key is not valid") {
            };
        } else {
            authentication.setAuthenticated(true);
            return authentication;
        }
    }

}
