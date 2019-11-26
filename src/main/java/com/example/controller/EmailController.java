package com.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dom.ApiResponse;
import com.sparkpost.Client;


@RestController
public class EmailController {
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
    private Client client;
	
	@PostMapping(value = "/sendEmail",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> sendEmail(@RequestParam("toMail") String toMail, @RequestParam("subject") String subject, @RequestParam("bodyText") String bodyText) {
		LOG.info("In Mail Method");
		ApiResponse response = new ApiResponse();
		
		try {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toMail);

        msg.setSubject(subject);
        msg.setText(bodyText);

        javaMailSender.send(msg);
        response.setMessage("Email sent successfully.");
		}
		catch(Exception e) {
			LOG.error("Error while mail sedning", e);
			try {
				client.sendMessage(client.getFromEmail(), toMail, subject, bodyText, "");
				response.setMessage("Email sent successfully.");
			}
			catch(Exception exce) {
				response.setMessage("Error while sending mail.");
				LOG.error("Error while mail sedning", exce);
			}
		}
        
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
	
}
