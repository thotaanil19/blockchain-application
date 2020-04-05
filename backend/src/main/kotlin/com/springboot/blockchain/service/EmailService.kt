package com.springboot.blockchain.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.springboot.blockchain.dto.EmailDTO;

@Service
class EmailService {

	@Autowired
	lateinit private var sendGrid: SendGrid;

	private var template: String = "Hi %s, \n\n\n %s \n\n\n\n\n \nThanks,\n\n Application Support team,\n\n\n athota1@lakeheadu.ca";

	public fun sendEmail(emailDTO: EmailDTO) {
		var from = Email("blockchain@example.com");
		var subject = emailDTO.subject;
		var to = Email(emailDTO.to);
		var body = String.format(template, emailDTO.recipientName, emailDTO.body);
		var content = Content("text/plain", body);
		var mail = Mail(from, subject, to, content);
		var request = Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			var res = sendGrid.api(request);
			System.out.println(res.getStatusCode());
		} catch (ex: IOException) {
			ex.printStackTrace();
		}
	}
}
