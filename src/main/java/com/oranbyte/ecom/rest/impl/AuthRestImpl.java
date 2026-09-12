package com.oranbyte.ecom.rest.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oranbyte.ecom.request.SignupRequest;
import com.oranbyte.ecom.rest.AuthRest;
import com.oranbyte.ecom.services.AuthService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.Language;

import jakarta.validation.Valid;

@RestController
public class AuthRestImpl implements AuthRest{

	@Autowired
	private AuthService authService;
	
	@Autowired 
	private Language lang;
	
	@Override
	public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest request) {
		try {
			return authService.signUp(request);
		}catch(Exception ex) {
			ex.printStackTrace(); 
		}
		
		return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, lang.getValue("something-went-wrong"), null);
		 
	}

	@Override
	public ResponseEntity<?> login(Map<String, String> requestMap) {

		try {
			return authService.login(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, lang.getValue("something-went-wrong"), null);
		
	}
	
	@Override
	public ResponseEntity<?> checkToken() {
		try {
			return authService.checkToken();
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
		return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, lang.getValue("something-went-wrong"), null);
		
	}

	@Override
	public ResponseEntity<?> changePassword(Map<String, String> requestMap) {
		try{
			return authService.changePassword(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, lang.getValue("something-went-wrong"), null);
		
	}

	public ResponseEntity<?> forgotPassword(@RequestBody(required = true) Map<String, String> requestMap){
		try {
			return authService.forgetPassword(requestMap);
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
		return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, lang.getValue("something-went-wrong"), null);
		
	}
}
