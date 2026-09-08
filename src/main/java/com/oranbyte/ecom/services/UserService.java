package com.oranbyte.ecom.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.oranbyte.ecom.dto.UserDto;
import com.oranbyte.ecom.request.SignupRequest;

public interface UserService {
 
	UserDto createUser(SignupRequest request);

	ResponseEntity<?> update(Map<String, String> requestMap);


}
