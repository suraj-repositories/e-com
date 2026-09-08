package com.oranbyte.ecom.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.oranbyte.ecom.request.SignupRequest;

public interface AuthService {
	ResponseEntity<?> signUp(SignupRequest request);

	ResponseEntity<?> login(Map<String, String> requestMap);

	ResponseEntity<?> checkToken();

	ResponseEntity<?> changePassword(Map<String, String> requestMap);

	ResponseEntity<?> forgetPassword(Map<String, String> requestMap);
}
