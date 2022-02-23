package com.baeldung.resource.service.impl;

import java.util.Map;

import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.stereotype.Component;

@Component
public class ClaimVerifier implements JwtClaimsSetVerifier {

	@Override
	public void verify(Map<String, Object> claims) throws InvalidTokenException {
		String preferredUsername = (String) claims.get("preferred_username");
		if (!preferredUsername.endsWith("@bealdung.com")) {
			throw new InvalidTokenException("Sorry !! Your are not a Bealdung member!");
		}
	}
}
