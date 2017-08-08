package com.timurtatarshaov.demoforum;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoforumApplicationTests {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private Filter springSecurityFilterChain;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain)
				.defaultRequest(get("/").with(testSecurityContext())).build();
	}
	
	@Test
	public void redirectToLoginPageByDefault() throws Exception {
		mvc.perform(get("/")).andExpect(redirectedUrl("/users/login"));
	}
	/*
	@Test
	public void redirectToLoginPageForUnauthorized() throws Exception {
		mvc.perform(get("/topics")).andExpect(redirectedUrl("/user/login"));
	}
	*/
	@Test
	public void loginSuccess() throws Exception {
		mvc.perform(formLogin("/users/login").user("famer")
				.password("123")).andExpect(authenticated()).andExpect(redirectedUrl("/topics/"));
	}
	
	@Test
	public void loginFailure() throws Exception {
		mvc.perform(formLogin("/users/login").password("invalid")).andExpect(
				redirectedUrl("/users/login?error"));
	}
	
	@Test
	@WithMockUser
	public void requestProtectedResourceWithUser() throws Exception {
		mvc.perform(get("/topics/post")).andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	public void createTopicRequiresCsrfToken() throws Exception {
		MockHttpServletRequestBuilder createTopic = post("/topics/post").param("title",
				"Title");

		mvc.perform(createTopic).andExpect(status().isForbidden());
	}
	
	
	@Test
	public void contextLoads() {
	}

}
