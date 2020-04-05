package com.springboot.blockchain.controller;


import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springboot.blockchain.dao.JwtTokenRepository;
import com.springboot.blockchain.dao.UserRepository;
import com.springboot.blockchain.domain.AppUser;
import com.springboot.blockchain.domain.JwtToken;
import com.springboot.blockchain.exceptions.UserAlreadyExistsException;
import com.springboot.blockchain.service.EmailService;
import com.springboot.blockchain.util.JwtTokenUtil;
import com.springboot.blockchain.util.Utils;
import org.springframework.security.core.userdetails.UsernameNotFoundException
import com.springboot.blockchain.constants.SecurityConstants
import com.springboot.blockchain.dto.*
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value

@RestController
@RequestMapping("/user")
class LoginController {

	@Autowired
	lateinit private var userRepository: UserRepository;

	@Autowired
	lateinit private var bCryptPasswordEncoder: BCryptPasswordEncoder;

	@Autowired
	lateinit private var jwtTokenUtil: JwtTokenUtil;

	@Autowired
	lateinit private var authenticationManager: AuthenticationManager;

	@Autowired
	lateinit private var jwtTokenRepository: JwtTokenRepository;

	@Autowired
	lateinit private var emailService: EmailService;
	
	//var applicationPath: String = "http://localhost:4200";
 	var applicationPath: String = "https://blockchain-ui.cfapps.io";

	@PostMapping("/register")
	public fun register(@RequestHeader(SecurityConstants.AUTHORIZATION) token2: String,
			res: HttpServletResponse, @RequestBody signUpRequestDTO: SignUpRequestDTO): ResponseEntity<LoginSuccessDTO> {
		
		var userInDB: AppUser? = userRepository.findByUsername(signUpRequestDTO.username);

		if (userInDB != null) {
			throw UserAlreadyExistsException(signUpRequestDTO.username!!);
		}
		var password: String = Utils.generateRandomPassword();  //signUpRequestDTO.getPassword();
		
		var user: AppUser = AppUser(null, signUpRequestDTO.name, signUpRequestDTO.email, signUpRequestDTO.socialInsuranceNumber,
				signUpRequestDTO.username, bCryptPasswordEncoder.encode(password),signUpRequestDTO.role,
				signUpRequestDTO.phone,true
				);


		var token: String = JWT.create().withSubject(user.username)
				.withExpiresAt(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()));
		res.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + token);

		userRepository.save(user);
		
		emailService.sendEmail(EmailDTO(signUpRequestDTO.name, signUpRequestDTO.email, "Account Created",
				"Congratulations, account has been created for you. Your temporary password is: " + password + "." +
		"Please login to application and change password immediately" + "\n\n"+
		applicationPath));

		var loginSuccessDTO =  LoginSuccessDTO(user.username, token, user.username, user.email, user.phone, null,true, user.socialInsuranceNumber);

		var response = ResponseEntity<LoginSuccessDTO>(loginSuccessDTO, HttpStatus.OK);
		return response;
	}

	@PostMapping("/unregister")
	public fun unregister(res: HttpServletResponse, @RequestHeader(SecurityConstants.AUTHORIZATION) token: String): ResponseEntity<String> {

		var auth: Authentication = SecurityContextHolder.getContext().getAuthentication();

		/*
		 * AppUser user = userRepository.findByUsername(auth.getName());
		 * user.setActive(false); userRepository.save(user);
		 */
		var user: AppUser? = userRepository.findByUsername(auth.getName());
		
		if (user == null) {
			throw UsernameNotFoundException("User not found");
		}
		userRepository.delete(user);
		user._id = null;
		user.isActive = false;
		userRepository.save(user);

		var response = ResponseEntity<String>("Un-register is success", HttpStatus.OK);
		return response;

	}

	@PostMapping("/login")
	public fun login(res: HttpServletResponse, @RequestBody user: LoginRequestDTO): ResponseEntity<LoginSuccessDTO>? {
		var authentication = authenticationManager
				.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		var userInDB: AppUser? = userRepository.findByUsername(authentication.getName());
		if (userInDB == null) {
			throw UsernameNotFoundException("User not found");
		}
		
		var jwt = jwtTokenUtil.createJWT(user.username, user.username, user.username,
				SecurityConstants.EXPIRATION_TIME);
		res.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + jwt);
		
		// Delete all existing tokens from DB
		jwtTokenRepository.deleteAllByUsername(userInDB.username!!);
		
 		var loginSuccessDTO =  LoginSuccessDTO(userInDB.name, jwt, userInDB.username, userInDB.email, userInDB.phone, userInDB.role,true, userInDB.socialInsuranceNumber);

		// Save generated JWT token into DB
		jwtTokenRepository.save(JwtToken(userInDB.username, jwt));

		emailService.sendEmail(EmailDTO(userInDB.name, userInDB.email, "Login Success",
				"Login is Successful. If it is not done by you, please change password immediately."));

		var response = ResponseEntity<LoginSuccessDTO>(loginSuccessDTO, HttpStatus.OK);
		return response;
	 
	}

	@PostMapping("/logout")
	@ResponseBody
	public fun logout(@RequestHeader(SecurityConstants.AUTHORIZATION) token: String): ResponseEntity<String> {
		var headers = HttpHeaders();
		var jwtToken = jwtTokenRepository.findByToken(token);
		if (jwtToken != null) {
			//jwtTokenRepository.delete(new JwtToken(Utils.getLoggedInUserName(), token));
			jwtTokenRepository.deleteByToken(token);
			return ResponseEntity<String>("Logout success", headers, HttpStatus.OK);
		}

		return ResponseEntity<String>("Invalid Token", headers, HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("/changePassword")
	@ResponseBody
	public fun changePassword(res: HttpServletResponse,
			@RequestHeader(SecurityConstants.AUTHORIZATION) token: String,
			@RequestBody passwordChangeRequestDTO: PasswordChangeRequestDTO): ResponseEntity<LoginSuccessDTO> {
		var jwtToken = jwtTokenRepository.findByToken(token);
		if (jwtToken != null) {
			var authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
					passwordChangeRequestDTO.username, passwordChangeRequestDTO.password));
			if (null != authentication) {
				jwtTokenRepository.deleteAllByUsername(authentication.getName());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				var userInDB: AppUser? = userRepository.findByUsername(authentication.getName());
				
				if (userInDB == null) {
					throw UsernameNotFoundException("User not found");
				}
				
				userRepository.delete(userInDB);
				
				userInDB._id = null;
				userInDB.password =(bCryptPasswordEncoder.encode(passwordChangeRequestDTO.newPassword));
				userRepository.save(userInDB);

				var jwt = jwtTokenUtil.createJWT(passwordChangeRequestDTO.username,
						passwordChangeRequestDTO.username, passwordChangeRequestDTO.username,
						SecurityConstants.EXPIRATION_TIME);
				res.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + jwt);

 				var loginSuccessDTO =  LoginSuccessDTO(userInDB.username, jwt, userInDB.username, userInDB.email, userInDB.phone, userInDB.role,true, userInDB.socialInsuranceNumber);

				// Save generated JWT token into DB
				jwtTokenRepository.save(JwtToken(userInDB.username, jwt));

				emailService.sendEmail(EmailDTO(userInDB.name, userInDB.email, "Password Changed",
						"Your password for Property Registration App is changed."
								+ " If it is not done by you, please contact support team immediately."));
			
				var response = ResponseEntity<LoginSuccessDTO>(loginSuccessDTO,
						HttpStatus.OK);
				return response;
			}

		}
		return ResponseEntity<LoginSuccessDTO>(HttpStatus.UNAUTHORIZED);

	}
	
	@PostMapping("/signup")
	public fun signup(
			res: HttpServletResponse, @RequestBody signUpRequestDTO: SignUpRequestDTO): ResponseEntity<LoginSuccessDTO> {
		
		var userInDB: AppUser? = userRepository.findByUsername(signUpRequestDTO.username);

		if (userInDB != null) {
			throw UserAlreadyExistsException(signUpRequestDTO.username!!);
		}
		var password: String? = null;
		/*if(StringUtils.isNotEmpty(signUpRequestDTO.password)) {
			password = signUpRequestDTO.password;
		} else {
			password = Utils.generateRandomPassword();
		};*/

		password = Utils.generateRandomPassword();

		var user: AppUser = AppUser(null, signUpRequestDTO.name, signUpRequestDTO.email, signUpRequestDTO.socialInsuranceNumber,
				signUpRequestDTO.username, bCryptPasswordEncoder.encode(password),signUpRequestDTO.role,
				signUpRequestDTO.phone,true
				);


		var token: String = JWT.create().withSubject(user.username)
				.withExpiresAt(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()));
		res.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + token);

		userRepository.save(user);
		
		emailService.sendEmail(EmailDTO(signUpRequestDTO.name, signUpRequestDTO.email, "Account Created",
				"Congratulations, account has been created for you. Your temporary password is: " + password + "." +
		"Please login to application and change password immediately" + "\n\n"+
		applicationPath));

		var loginSuccessDTO =  LoginSuccessDTO(user.username, token, user.username, user.email, user.phone, null,true, user.socialInsuranceNumber);

		var response = ResponseEntity<LoginSuccessDTO>(loginSuccessDTO, HttpStatus.OK);
		return response;
	}

}
