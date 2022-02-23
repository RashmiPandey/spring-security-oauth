package com.baeldung.resource.spring;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.DelegatingJwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.baeldung.resource.service.impl.ClaimVerifier;

@Configuration
public class SecurityConfig implements ResourceServerConfigurer {

	@Override
	public void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.cors()
            .and()
              .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
                  .hasAuthority("SCOPE_read")
                .antMatchers(HttpMethod.POST, "/api/foos")
                  .hasAuthority("SCOPE_write")
                .anyRequest()
                  .authenticated()
            .and()
              .oauth2ResourceServer()
                .jwt();
    }//@formatter:on

	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(tokenStore());
		return tokenServices;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter accessTokenconverter = new JwtAccessTokenConverter();
		accessTokenconverter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());
		return accessTokenconverter;
	}

	@Bean
	public JwtClaimsSetVerifier jwtClaimsSetVerifier() {
		return new DelegatingJwtClaimsSetVerifier(Arrays.asList(jwtClaimVerifier()));
	}

	@Bean
	public JwtClaimsSetVerifier jwtClaimVerifier() {
		return new ClaimVerifier();
	}
}