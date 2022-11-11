package com.techgee.electronicvoting;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class SignUpTests {

protected MockMvc mockMvc;
	
	@Autowired
	protected WebApplicationContext webApplicationContext;

	
	@BeforeAll
	protected void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
				.build();
	}
}
