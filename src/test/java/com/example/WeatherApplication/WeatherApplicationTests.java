package com.example.WeatherApplication;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {

	}

	@Test
	void testGetWeather() throws Exception {
		// Use a tool like MockMvc to send a GET request to the /weather endpoint
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/weather?city=London")).andReturn();

		// Verify that the response is OK (200)
		assertEquals(200, result.getResponse().getStatus());
	}


	@Test
	void testGetWeatherNotFound() throws Exception {
		// Use a tool like MockMvc to send a GET request to the /weather endpoint
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/weather?city=UnknownCity")).andReturn();

		// Verify that the response is NOT FOUND (404)
		assertEquals(404, result.getResponse().getStatus());
	}


}
