package com.springboot.blockchain

import com.springboot.blockchain.dto.MongoDetails
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication


@SpringBootApplication
@EnableConfigurationProperties(MongoDetails::class)
open class Application 

	fun main(args: Array<String>) {
		runApplication<Application>(*args)
	}


 
 
 