package com.smalwe.payment.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String API_KEY_AUTH_HEADER = "API_KEY";

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private ApiKeyAuthManager apiKeyAuthManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(API_KEY_AUTH_HEADER);
        filter.setAuthenticationManager(apiKeyAuthManager);

        http.antMatcher("/v1/payments").
                        csrf().
                        disable().
                        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                        and()
                .addFilter(filter).exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }
}
