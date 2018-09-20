package com.mikerott.springboothelloworld;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HelloworldControllerStandaloneTest {

	@Mock
	private VersionInfo versionInfo;

	@InjectMocks
	private HelloworldController healthController;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(healthController).build();
	}

	@Test
	public void getHealthStatus() throws Exception {
		mockMvc.perform(get("/sayhello"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.version_info").isMap());
	}
}
