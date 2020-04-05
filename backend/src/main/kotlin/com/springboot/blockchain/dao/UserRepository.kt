package com.springboot.blockchain.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.springboot.blockchain.domain.AppUser;

public interface UserRepository : MongoRepository<AppUser, String> {

	fun findByUsername(username: String?): AppUser?;
	
}
