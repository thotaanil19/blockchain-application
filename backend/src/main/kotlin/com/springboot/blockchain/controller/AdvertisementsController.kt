package com.springboot.blockchain.controller;

import com.springboot.blockchain.constants.SecurityConstants.AUTHORIZATION
import com.springboot.blockchain.domain.Advertisement
import com.springboot.blockchain.service.AdvertisementsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import org.springframework.util.MultiValueMap
import org.springframework.http.HttpHeaders

@RestController
@RequestMapping("/advertisements")
class AdvertisementsController {

	@Autowired
	lateinit private var advertisementsService: AdvertisementsService;

	@PostMapping
	public fun save(
		@Valid @RequestBody advertisement: Advertisement,
		@RequestHeader(AUTHORIZATION) token: String
	): ResponseEntity<Boolean> {
		var registrationStatus = advertisementsService.save(advertisement);
		return ResponseEntity<Boolean>(registrationStatus, HttpStatus.OK);
	}

	@GetMapping 
	public fun getAll(@RequestHeader(AUTHORIZATION) token: String):
			ResponseEntity<MutableList<Advertisement>> {
		var history = advertisementsService.getAll();
		
		return ResponseEntity<MutableList<Advertisement>>(history, HttpStatus.OK);
	}
	
}
