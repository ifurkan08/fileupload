package com.ets.fileupload;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUploadApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	protected Principal mockPrincipal;

	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockPrincipal = Mockito.mock(Principal.class);
		Mockito.when(mockPrincipal.getName()).thenReturn("furkan");
	}
	protected MockMvc mockMvc;
	@Test
	public void contextLoads() {

	}
}
