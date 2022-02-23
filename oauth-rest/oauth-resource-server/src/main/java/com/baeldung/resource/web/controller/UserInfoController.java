package com.baeldung.resource.web.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.resource.service.impl.ClaimVerifier;

@RestController
public class UserInfoController {

	@Autowired
	ClaimVerifier claimVerifier;

	@GetMapping("/user/info")
	public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt principal) {
		claimVerifier.verify(principal.getClaims());
		return Collections.singletonMap("user_name", principal.getClaimAsString("preferred_username"));
	}
	
	@GetMapping("/superuser")
    public String superuser() {
        return "Hello superuser!";
    }
}