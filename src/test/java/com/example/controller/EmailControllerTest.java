package com.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.EmailserviceApplicationTests;

public class EmailControllerTest extends EmailserviceApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testSuccessEmail() throws Exception {
		mockMvc.perform(post("/sendEmail").param("toMail", "gotidhavalh@gmail.com").param("subject", "EmailSubject").param("bodyText", "EmailBody")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Email sent successfully."));

	}
	
	@Test
	public void testFailEmail() throws Exception {
		mockMvc.perform(post("/sendEmail").param("toMail", "gotidhavalhgmail.com").param("subject", "EmailSubject").param("bodyText", "EmailBody")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Error while sending mail."));

	}
}

