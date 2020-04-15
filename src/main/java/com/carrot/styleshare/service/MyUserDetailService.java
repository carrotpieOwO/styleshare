package com.carrot.styleshare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carrot.styleshare.model.user.User;
import com.carrot.styleshare.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.authentication(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("해당 유저가 없습니다.");
		}else {
			return user;
		}
	}

}
