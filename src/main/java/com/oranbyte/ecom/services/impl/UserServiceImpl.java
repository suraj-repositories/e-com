package com.oranbyte.ecom.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.oranbyte.ecom.config.JwtFilter;
import com.oranbyte.ecom.dto.UserDto;
import com.oranbyte.ecom.entity.User;
import com.oranbyte.ecom.mapper.UserMapper;
import com.oranbyte.ecom.repository.UserRepostitory;
import com.oranbyte.ecom.request.SignupRequest;
import com.oranbyte.ecom.services.UserService;
import com.oranbyte.ecom.util.AppUtils;
import com.oranbyte.ecom.util.EmailUtils;
import com.oranbyte.ecom.util.Language;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepostitory userRepository;
	private final JwtFilter jwtFilter;
	private final EmailUtils emailUtils;
	private final Language lang;
	private final UserMapper userMapper;

	 @Override
	public UserDto createUser(SignupRequest request) {

		 User user = new User();
		 user.setName(request.getName());
		 user.setUsername(request.getUsername());
		 user.setEmail(request.getEmail());
		 user.setContactNumber(request.getContactNumber());
		 user.setPassword(request.getPassword());
		 
		 User createdUser = userRepository.save(user);
		 
		 return userMapper.toDto(createdUser);
		 
	 }

	@Override
	public ResponseEntity<?> update(Map<String, String> requestMap) {

		try {

			if (!jwtFilter.isAdmin()) {
				return AppUtils.getApiResponse(HttpStatus.UNAUTHORIZED, false, lang.getValue("unauthorized-access"),
						null);
			}

			Long id = Long.parseLong(requestMap.get("id"));

			Optional<User> optional = userRepository.findById(id);

			if (optional.isEmpty()) {
				return AppUtils.getApiResponse(HttpStatus.NOT_FOUND, false, "User Id doesn't Exist!", null);
			}

			boolean isActive = Boolean.parseBoolean(requestMap.get("isActive"));
			userRepository.updateIsActive(isActive, id);
			sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userRepository.getAllAdmin());

			return AppUtils.getApiResponse(true, "User Status Updated Successfully!", null);

		} catch (NumberFormatException ex) {

			log.error("Invalid user ID", ex);

			return AppUtils.getApiResponse(HttpStatus.BAD_REQUEST, false, "Invalid User ID!", null);

		} catch (Exception ex) {

			log.error("Error while updating user status", ex);

			return AppUtils.getApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false,
					lang.getValue("something-went-wrong"), null);
		}
	}

	private void sendMailToAllAdmin(String status, String email, List<String> allAdmin) {

		allAdmin.remove(jwtFilter.getCurrentUser());

		if (status != null && status.equalsIgnoreCase("true")) {

			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved",
					"USER : " + email + "\n is approved by \n ADMIN : " + jwtFilter.getCurrentUser(), allAdmin);

		} else {

			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled",
					"USER : " + email + "\n is disabled by \n ADMIN : " + jwtFilter.getCurrentUser(), allAdmin);
		}
	}
}