package com.oranbyte.ecom.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.oranbyte.ecom.config.CustomerUserDetailService;
import com.oranbyte.ecom.config.JwtFilter;
import com.oranbyte.ecom.config.JwtUtil;
import com.oranbyte.ecom.entity.User;
import com.oranbyte.ecom.mapper.UserMapper;
import com.oranbyte.ecom.repository.UserRepostitory;
import com.oranbyte.ecom.request.SignupRequest;
import com.oranbyte.ecom.services.AuthService;
import com.oranbyte.ecom.services.UserService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.EmailUtils;
import com.oranbyte.ecom.util.Language;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepostitory userRepository;
	private final AuthenticationManager authenticationManager;
	private final CustomerUserDetailService customerUserDetailService;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final Language lang;
	private final EmailUtils emailUtils;
	private final JwtFilter jwtFilter;
	private final UserService userService;
	private final UserMapper userMapper;

	@Override
	public ResponseEntity<?> signUp(SignupRequest request) {

		log.info("Inside signup {}", request);

		try {
			 
			User user = userRepository.findByEmailId(request.getEmail());

			if (!Objects.isNull(user)) {
				return AppUtils.getValidationErrorResponse("email", "Email already exists!");
			} 
			
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			
			userService.createUser(request);  
			return AppUtils.getApiResponse(HttpStatus.CREATED, true, "Successfully Registered!", null);

		} catch (Exception ex) {
			log.error("Error while registering user", ex);

			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false,
					lang.getValue("something-went-wrong"), null);
		}
	}
	
	@Override
	public ResponseEntity<?> login(Map<String, String> requestMap) {

		log.info("Inside login {}", requestMap);

		try {
			
			User user = null;
			if(requestMap.get("username") != null) {
				user = userRepository.findByUsername(requestMap.get("username"));
			}else if(requestMap.get("email") != null) {
				user = userRepository.findByEmail(requestMap.get("email"));
			} 
			
			if(user == null) {
				return AppUtils.getApiResponse(HttpStatus.BAD_REQUEST, false, "Wrong Credentials!", null);
			}
			 
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), requestMap.get("password")));

			if (auth.isAuthenticated()) {
				User userDetail = customerUserDetailService.getUserDetail(user.getUsername());

				if (userDetail != null && Boolean.TRUE.equals(userDetail.getIsActive())) {
					String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

					Map<String, Object> data = new HashMap<>();
					data.put("token", token);
                    data.put("user", userMapper.toDto(userDetail));
					return AppUtils.getApiResponse(true, "Login successful!", data);
				}

				return AppUtils.getApiResponse(HttpStatus.BAD_REQUEST, false, "Wait for admin approval!", null);
			}

		} catch (Exception ex) {
			log.error("Error while login", ex);
		}

		return AppUtils.getApiResponse(HttpStatus.BAD_REQUEST, false, "Wrong Credentials!", null);
	}

	@Override
	public ResponseEntity<?> checkToken() { 
		return AppUtils.getApiResponse(true, "Token is valid", null);
	}

	@Override
	public ResponseEntity<?> changePassword(Map<String, String> requestMap) {

		try {
			User user = userRepository.findByEmail(jwtFilter.getCurrentUser());

			if (Objects.isNull(user)) {
				return AppUtils.getApiResponse(HttpStatus.BAD_REQUEST, false, "User not found", null);
			}

			if (!passwordEncoder.matches(requestMap.get("oldPassword"), user.getPassword())) {
				return AppUtils.getApiResponse(HttpStatus.BAD_REQUEST, false, "Incorrect Old Password", null);
			}

			user.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));

			userRepository.save(user);

			return AppUtils.getApiResponse(true, "Password Updated Successfully!", null);

		} catch (Exception ex) {
			log.error("Error while changing password", ex);

			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false,
					lang.getValue("something-went-wrong"), null);
		}
	}

	@Override
	public ResponseEntity<?> forgetPassword(Map<String, String> requestMap) {

		try {
			User user = userRepository.findByEmail(requestMap.get("email"));

			if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
				String password = String.format("%06d", new Random().nextInt(1_000_000));
				user.setPassword(passwordEncoder.encode(password));
				emailUtils.forgotMail(user.getEmail(), "Credentials For app", password);
				userRepository.save(user);
				return AppUtils.getApiResponse(true, "Check your email for credentials!", null);
			}

			return AppUtils.getApiResponse(HttpStatus.BAD_REQUEST, false, "Invalid email address.", null);

		} catch (Exception ex) {
			log.error("Error while processing forgot password", ex);

			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false,
					lang.getValue("something-went-wrong"), null);
		}
	}
}