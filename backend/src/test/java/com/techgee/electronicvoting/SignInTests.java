package com.techgee.electronicvoting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.techgee.electronicvoting.controller.SignUpSignInController;
import com.techgee.electronicvoting.model.Login;
import com.techgee.electronicvoting.resource.SignUpSignInResource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class SignInTests {
	
	protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;
	
	@Autowired
	SignUpSignInController signUpSignInController;

	
	@BeforeAll
	protected void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
				.build();
	}
	@Test
	public void signUp() {
		SignUpSignInResource signUpSignInResource = setSignUpSignInResource();
		ObjectMapper objectMapper = new ObjectMapper();
			MvcResult mvcResult;
			try {
				mvcResult = this.mockMvc.perform(post("/authorise/signIn")
						.content(asJsonString(signUpSignInResource))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().is2xxSuccessful()).andDo(print())
						.andReturn();
				Login login = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Login>() {});
				assertEquals("test.junit@ggg.com", login.getUserId());
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		
	}

	private SignUpSignInResource setSignUpSignInResource() {
		SignUpSignInResource signUpSignInResource = new SignUpSignInResource();
		signUpSignInResource.setUserName("test.junit@ggg.com");
		signUpSignInResource.setPassword("12345");
		return signUpSignInResource;
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
