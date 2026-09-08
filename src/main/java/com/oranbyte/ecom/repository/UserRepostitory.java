package com.oranbyte.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.oranbyte.ecom.dto.UserDto;
import com.oranbyte.ecom.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepostitory extends JpaRepository<User, Long> {

	User findByEmailId(@Param("email") String email);

	List<UserDto> getAllUser();

	@Transactional
	@Modifying
	Integer updateIsActive(@Param("isActive") Boolean isActive, @Param("id") Long Id);

	List<String> getAllAdmin();

	User findByEmail(String email);

}
