package com.techgee.electronicvoting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.techgee.electronicvoting.controller.PollContoller;
import com.techgee.electronicvoting.resource.PollResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class PollUpdateTests {
	
	@Autowired
	PollContoller pollcontroller;
	
	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;

	
	@BeforeAll
	protected void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
				.build();
	}
	
	@Test
	public void pollUpdateTest() {
		PollResource pollResource = setPollResource();
		try {
			this.mockMvc.perform(put("/poll/update/9")
					.content(asJsonString(pollResource))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andDo(print());
		} catch (Exception e) {
			  e.printStackTrace();
		}
	}

	private PollResource setPollResource() {
		PollResource pollResource = new PollResource();
		pollResource.setDescription("Test Description update 1");
		pollResource.setTitle("Test Title update 1");
		pollResource.setStartDate(LocalDate.now().plusMonths(1).plusDays(3));
		pollResource.setEndDate(LocalDate.now().plusMonths(3));
		return pollResource;
	}
	
	protected String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			final String jsonContent = mapper.writeValueAsString(obj);
			System.out.println("jsonContent : " + jsonContent);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
