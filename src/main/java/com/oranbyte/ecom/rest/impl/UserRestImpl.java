package com.oranbyte.ecom.rest.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.oranbyte.ecom.rest.UserRest;
import com.oranbyte.ecom.services.UserService;
import com.oranbyte.ecom.util.AppUtils;

@RestController
public class UserRestImpl implements UserRest {

	@Autowired
	private UserService userService; 

	 

	@Override
	public ResponseEntity<?> update(Map<String, String> requestMap) {

		try {
			return userService.update(requestMap);
		} catch (Exception ex) {
			ex.printStackTrace();

			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, ex.getMessage(), null);

		}
	}

}
