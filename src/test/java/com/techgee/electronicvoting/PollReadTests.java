package com.techgee.electronicvoting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgee.electronicvoting.controller.PollContoller;
import com.techgee.electronicvoting.resource.PollResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class PollReadTests {
	
	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	@Autowired
	PollContoller pollController;

	
	@BeforeAll
	protected void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
				.build();
	}
	
	@Test
	public void getPoll() {
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult mvcResult;
		try {
			mvcResult = this.mockMvc.perform(get("/poll/get/3")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().is2xxSuccessful()).andDo(print())
					.andReturn();
			PollResource pollResource = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<PollResource>() {});
			assertEquals("title", pollResource.getTitle());
			assertEquals("desc", pollResource.getDescription());
			assertEquals(5L, pollResource.getPrPrRelationId());
		} catch (Exception e1) {
			e1.printStackTrace();
		} 

	}

}
