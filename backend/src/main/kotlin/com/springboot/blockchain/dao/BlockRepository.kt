package com.springboot.blockchain.dao

import com.springboot.blockchain.domain.Block
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface BlockRepository : MongoRepository<Block, String> {
	fun findAllByOrderByTimeStampDesc(): Optional<MutableList<Block>>
}