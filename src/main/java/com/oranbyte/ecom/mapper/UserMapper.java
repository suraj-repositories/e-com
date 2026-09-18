package com.oranbyte.ecom.mapper;

import org.springframework.stereotype.Component;

import com.oranbyte.ecom.dto.UserDto;
import com.oranbyte.ecom.entity.User;

@Component
public class UserMapper {

	public User toEntity(UserDto dto) {
		
		User user = new User();
		
     	user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getUsername());
		user.setContactNumber(dto.getContactNumber());
		user.setIsActive(dto.getIsActive());
		user.setCreatedAt(dto.getCreatedAt());
		user.setUpdatedAt(dto.getUpdatedAt());
		return user;
	}
	
	public UserDto toDto(User user) {
		UserDto dto = new UserDto();
		
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setUsername(user.getUsername());
		dto.setContactNumber(user.getContactNumber());
		dto.setIsActive(user.getIsActive());
		dto.setCreatedAt(user.getCreatedAt());
		dto.setUpdatedAt(user.getUpdatedAt());
		return dto;
	}
	
	 
	
}
