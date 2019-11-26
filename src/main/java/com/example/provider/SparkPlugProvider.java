package com.example.provider;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.sparkpost.Client;

public class SparkPlugProvider {
	
	@Value("${sparkplug.key}")
	private String key;
	
	@Bean
	public Client getJavaMailSender(String mailProvider) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sparkpostmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("my.email@mydomain.com");
		mailSender.setPassword("password");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		Client cli = new Client(key);
		cli.setFromEmail("from@mydomain.com");
		return cli;
	}
	
}
