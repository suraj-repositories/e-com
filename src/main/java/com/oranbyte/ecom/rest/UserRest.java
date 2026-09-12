package com.oranbyte.ecom.rest;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "/user")
public interface UserRest {

	@PostMapping("/update")
	ResponseEntity<?> update(@RequestBody(required = true) Map<String, String> requestMap);

	@PutMapping("/avatar")
	ResponseEntity<?> updateAvatar(@RequestParam("avatar") MultipartFile avatar);
	
}
