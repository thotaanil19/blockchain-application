package com.springboot.blockchain.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="spring.data.mongodb")
@Component
public class MongoDetails {

	public String uri;
	public String database;
	
}
