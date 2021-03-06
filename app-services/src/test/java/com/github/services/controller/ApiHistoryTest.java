package com.github.services.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.github.services.boot.App;
import com.github.services.model.History;
import com.github.services.repositories.HistoryRepository;

/**
 * Test 
 * @author omar.yepez
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class ApiHistoryTest {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	@Autowired
	private HistoryRepository historyRepo;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
	
	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        historyRepo.deleteAll();
        historyRepo.save(new History("1", "google"));
        historyRepo.save(new History("2", "facebook"));
    }
	
	@Test
    public void getAllHistory() throws Exception {
        mockMvc.perform(get("/history/"))
        	   .andExpect(status().isOk())
        	   .andExpect(content().contentType(contentType))
        	   .andExpect(jsonPath("$", hasSize(2)));
    }
	
	@Test
    public void getHistoryByApi() throws Exception {
        mockMvc.perform(get("/history/facebook"))
        	   .andExpect(status().isOk())
        	   .andExpect(content().contentType(contentType))
        	   .andExpect(jsonPath("$", hasSize(1)));
    }
	
	@Test
    public void saveHistory() throws Exception {
        mockMvc.perform(
        			post("/history")
        			.content(json(new History("3", "google")))
        			.contentType(contentType))
        	   .andExpect(status().isOk())
        	   .andExpect(content().contentType(contentType));        	   
    }
	
	@Test
    public void deleteHistory() throws Exception {
        mockMvc.perform(delete("/history/1"))
        	   .andExpect(status().isOk());
    }
	
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}