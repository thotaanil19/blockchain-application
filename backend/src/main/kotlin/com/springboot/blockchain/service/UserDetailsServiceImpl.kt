package com.springboot.blockchain.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blockchain.dao.UserRepository;
import com.springboot.blockchain.domain.AppUser;

@Service
class UserDetailsServiceImpl : UserDetailsService {

	@Autowired
	lateinit private var repository: UserRepository;

	@Override
	public override fun loadUserByUsername(username: String) : UserDetails {
		var user: AppUser? = repository.findByUsername(username);

		if (user == null) {
			throw UsernameNotFoundException("User not found");
		}

		var authorities = Arrays.asList(SimpleGrantedAuthority(user.role));
		return User(user.username, user.password, authorities);
	}
}