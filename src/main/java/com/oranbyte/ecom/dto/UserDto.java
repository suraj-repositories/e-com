package com.oranbyte.ecom.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

	private Long id;

	private String name;
	
	private String username;

	private String email;

	private String contactNumber;

	private Boolean isActive;

	public UserDto(Long id, String name, String username, String email, String contactNumber, Boolean isActive) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.contactNumber = contactNumber;
		this.isActive = isActive;
	}

}
