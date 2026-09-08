package com.oranbyte.ecom.util;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppUtils { 
	
	public static ResponseEntity<?> getApiResponse(HttpStatus status, Boolean success, String message, Object data) {
		return ResponseEntity.status(status).body(new ApiResponse<>(success, message, data));
	}

	public static ResponseEntity<?> getApiResponse(Boolean success, String message, Object data) {
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(success, message, data));
	}

	public static ResponseEntity<?> getValidationErrorResponse(String field, String message) {

		Map<String, String> errors = new HashMap<>();
		errors.put(field, message);

		ValidationErrorResponse response = new ValidationErrorResponse(false, "Validation failed",
				HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	public static ResponseEntity<?> getValidationErrorResponse(Map<String, String> errors) {
		ValidationErrorResponse response = new ValidationErrorResponse(false, "Validation failed",
				HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	public static String getUUID() {
		Date date = new Date();
		long time = date.getTime();
		return "U" + time;
	}

	public static JSONArray jsonArrayFromString(String data) throws JSONException {
		try {
			return new JSONArray(data);
		} catch (Exception e) {
			log.error("Error parsing JSON array: {}", e.getMessage());
			return new JSONArray();
		}
	}

	public static Map<String, Object> getMapFromJson(String data) {
		if (!Strings.isNullOrEmpty(data)) {
			return new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {
				private static final long serialVersionUID = 1L;
			}.getType());
		}
		return new HashMap<>();
	}
 

}
