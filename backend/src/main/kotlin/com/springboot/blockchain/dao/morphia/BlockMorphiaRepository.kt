package com.springboot.blockchain.dao.morphia

import com.mongodb.MongoClient
import com.springboot.blockchain.domain.Block
import com.springboot.blockchain.dto.MongoDetails
import org.mongodb.morphia.Morphia
import org.mongodb.morphia.dao.BasicDAO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.context.annotation.Lazy

//@Repository
class BlockMorphiaRepository @Autowired constructor(mongo: MongoClient, mongoDetails: MongoDetails) : BasicDAO<Block, String>(mongo, Morphia(), mongoDetails.database)