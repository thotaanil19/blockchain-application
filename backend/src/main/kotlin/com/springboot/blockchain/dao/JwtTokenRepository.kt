package com.springboot.blockchain.dao;

import com.springboot.blockchain.domain.JwtToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JwtTokenRepository : MongoRepository<JwtToken,String> {

    fun findByToken(token: String): JwtToken?;
    fun deleteByToken(token: String): Long;
    fun deleteAllByUsername(username: String): Long;
}
