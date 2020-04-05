package com.springboot.blockchain.config;

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.sendgrid.SendGrid

@Configuration
open class SendGridConfig

@Value("#{spring.sendgrid.api-key}")
lateinit var sendGridAPIKey: String;

@Bean
fun sendGridBean(): SendGrid {
	return SendGrid(sendGridAPIKey);
}

 

