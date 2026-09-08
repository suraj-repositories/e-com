package com.oranbyte.ecom.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oranbyte.ecom.request.SignupRequest;

import jakarta.validation.Valid;

@RequestMapping(path = "/auth")
public interface AuthRest {
	
	@PostMapping(path = "/signup")
	ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signupRequest);

	@PostMapping(path = "/login")
	ResponseEntity<?> login(@RequestBody(required = true) Map<String, String> requestMap);
	
	@GetMapping(path="/checkToken")
	ResponseEntity<?> checkToken();
	
	@PostMapping(path="/changePassword")
	ResponseEntity<?> changePassword(@RequestBody(required = true) Map<String, String> requestmapMap);
	
	@PostMapping(path = "/forgotPassword")
	ResponseEntity<?> forgotPassword(@RequestBody(required = true) Map<String, String> requestMap);
}
