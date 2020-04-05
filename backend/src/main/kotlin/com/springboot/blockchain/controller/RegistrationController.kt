package com.springboot.blockchain.controller;

import com.springboot.blockchain.constants.SecurityConstants.AUTHORIZATION
import com.springboot.blockchain.domain.Block
import com.springboot.blockchain.domain.Registration
import com.springboot.blockchain.service.RegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/registration")
class RegistrationController {
	
	@Autowired
	lateinit private var registrationService: RegistrationService;
	
	@PostMapping
	public fun saveRegistration(@Valid @RequestBody registration: Registration,
								@RequestHeader(AUTHORIZATION) token: String): ResponseEntity<Boolean> {
		
		var registrationStatus = registrationService.saveRegistration(registration);
		return ResponseEntity<Boolean>(registrationStatus, HttpStatus.OK);
		
	}
	
	@GetMapping
	public fun getRegistrationHistory(@RequestHeader(AUTHORIZATION) token: String): ResponseEntity<MutableList<Block>> {
		
		var history = registrationService.getRegistrationHistory();
		return ResponseEntity<MutableList<Block>>(history, HttpStatus.OK);
		
	}

}
