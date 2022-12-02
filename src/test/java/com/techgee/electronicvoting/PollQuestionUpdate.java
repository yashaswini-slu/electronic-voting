package com.techgee.electronicvoting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.techgee.electronicvoting.controller.PollQuestionController;
import com.techgee.electronicvoting.model.AllowedResponseOption;
import com.techgee.electronicvoting.resource.PollQuestionOptionResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class PollQuestionUpdate {

	@Autowired
	PollQuestionController pollQuestionController;
	
	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;

	
	@BeforeAll
	protected void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
				.build();
	}
	
	@Test
	public void pollQuestionUpdateTest() {
		PollQuestionOptionResource pollQuestionResource = setPollQuestionResource();
		try {
			this.mockMvc.perform(put("/pollQuestion/update/9")
					.content(asJsonString(pollQuestionResource))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().is2xxSuccessful()).andDo(print());
		} catch (Exception e) {
			  e.printStackTrace();
		}
	}


	private PollQuestionOptionResource setPollQuestionResource() {
		PollQuestionOptionResource pollQuestionResource = new PollQuestionOptionResource();
		pollQuestionResource.setQuestion("Question update 66");
		List<AllowedResponseOption> optionList = new ArrayList<>();
		AllowedResponseOption option1 = new AllowedResponseOption();
		option1.setOption("option 1");
		option1.setCorrect(false);
		optionList.add(option1);
		AllowedResponseOption option2 = new AllowedResponseOption();
		option2.setOption("option 2");
		option2.setCorrect(false);
		optionList.add(option2);
		pollQuestionResource.setOptions(optionList);
		return pollQuestionResource;
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
