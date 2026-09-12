package com.oranbyte.ecom.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oranbyte.ecom.repository.UserRepostitory;
import com.oranbyte.ecom.util.Language;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepostitory userRepo;
    
    @Autowired
    private Language lang;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}", username);
        
        com.oranbyte.ecom.entity.User userDetail = userRepo.findByUsername(username);  
        
        log.info("User is here: {}", userDetail);

        if (userDetail != null) {
            return new User(userDetail.getUsername(), userDetail.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(lang.getValue("user-not-found"));
        }
    }

    public com.oranbyte.ecom.entity.User getUserDetail(String username) {
        log.info("Fetching user details for username: {}", username);
        return userRepo.findByUsername(username);  
    }
}
