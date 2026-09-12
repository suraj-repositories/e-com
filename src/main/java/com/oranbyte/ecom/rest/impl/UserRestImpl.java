package com.oranbyte.ecom.rest.impl;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.rest.UserRest;
import com.oranbyte.ecom.services.UserService;
import com.oranbyte.ecom.util.AppUtils;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor
public class UserRestImpl implements UserRest {

	private final UserService userService;

	@Override
	public ResponseEntity<?> update(Map<String, String> requestMap) {

		try {
			return userService.update(requestMap);
		} catch (Exception ex) {
			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage(), null);
		}
	}

	@Override
	public ResponseEntity<?> updateAvatar(MultipartFile avatar) {
		try {
			return userService.updateAvatar(avatar);
		} catch (Exception ex) {
			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage(), null);
		}

	}

}
