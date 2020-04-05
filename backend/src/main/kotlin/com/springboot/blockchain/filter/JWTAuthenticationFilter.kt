package com.springboot.blockchain.filter


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blockchain.domain.AppUser;
import com.springboot.blockchain.exceptions.SecurityException;
import com.springboot.blockchain.util.JwtTokenUtil;

class JWTAuthenticationFilter : UsernamePasswordAuthenticationFilter {

	private var userDetailsService: UserDetailsService;

	private var bCryptPasswordEncoder: BCryptPasswordEncoder;

	private var jwtTokenUtil: JwtTokenUtil;

	public constructor(userDetailsService: UserDetailsService,
		bCryptPasswordEncoder: BCryptPasswordEncoder,
		jwtTokenUtil: JwtTokenUtil) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@Override
	public override fun doFilter(req: ServletRequest, res: ServletResponse, filterChain: FilterChain) {
		var request = req as HttpServletRequest;
		var response = res as HttpServletResponse;

		var uri = request.getRequestURI();

		if ("/user/login2".equals(uri)) {
			var json = ObjectMapper().readValue(req.getInputStream(), AppUser::class.java);
			var username = json.username;
			var password = json.password;
			var userDetails = userDetailsService.loadUserByUsername(username);
			if (null != userDetails && bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
				var auth = UsernamePasswordAuthenticationToken(userDetails, "",
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} else {
			var token: String? = jwtTokenUtil.resolveToken(request);
			if (token != null) {
				try {
					jwtTokenUtil.isTokenExistsinDB(token);
					jwtTokenUtil.validateToken(token);
				} catch (e: Exception) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
					throw SecurityException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
				}
				var auth = jwtTokenUtil.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		filterChain.doFilter(req, res);
	}


}
