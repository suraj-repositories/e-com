package com.oranbyte.ecom.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oranbyte.ecom.config.JwtFilter;
import com.oranbyte.ecom.dto.UserDto;
import com.oranbyte.ecom.entity.User;
import com.oranbyte.ecom.mapper.UserMapper;
import com.oranbyte.ecom.repository.UserRepostitory;
import com.oranbyte.ecom.request.SignupRequest;
import com.oranbyte.ecom.services.FileService;
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
	private final FileService fileService;

	@Override
	public User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new RuntimeException("User is not authenticated");
		}

		String username = authentication.getName();
		log.info("Current User : {}", username);
		User user = userRepository.findByUsername(username);

		if (user == null) {
		    throw new RuntimeException("User not found");
		}

		return user;
	}

	@Override
	public UserDto createUser(SignupRequest request) {

		User user = new User();
		user.setName(request.getName());
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setContactNumber(request.getContactNumber());
		user.setPassword(request.getPassword());
		user.setRole("user");
		user.setIsActive(true);

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

	@Override
	public ResponseEntity<?> updateAvatar(MultipartFile avatar) {
		try {
			User user = getCurrentUser();
			if (user != null) {
				fileService.deleteIfExists(user.getAvatar());
			}

			String uploadFile = fileService.uploadFile(avatar, "avatars");
			user.setAvatar(uploadFile);
			userRepository.save(user);

			return AppUtils.getApiResponse(false, "Avatar updated successfully!", null);
		} catch (IOException e) {
			return AppUtils.getApiResponse(false, e.getMessage(), null);
		}
	}
}