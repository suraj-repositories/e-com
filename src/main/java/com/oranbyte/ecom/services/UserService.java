package com.oranbyte.ecom.services;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.dto.UserDto;
import com.oranbyte.ecom.entity.User;
import com.oranbyte.ecom.request.SignupRequest;

public interface UserService {

	User getCurrentUser();
	
	UserDto createUser(SignupRequest request);

	ResponseEntity<?> update(Map<String, String> requestMap);

	ResponseEntity<?> updateAvatar( MultipartFile avatar);
}
