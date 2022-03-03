package com.example.demoapp.controllers;

import com.example.demoapp.DemoAppApplication;
import com.example.demoapp.views.UserView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * This test creates a {@link MockMvc} instance that uses a fully initialized WebApplicationContext. Functional tests
 * like this are useful for verifying end-to-end functionality of an endpoint in a running system.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {DemoAppApplication.class})
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class UserControllerFunctionalTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void testGetUsers() throws Exception {

        ResultActions results = mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = results.andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        List<UserView> userViews = new ObjectMapper().readValue(contentAsString, new TypeReference<>() {
        });

        // This assertion could be stricter if we could control the data that's being returned by the external system,
        // which could be possible in an automated test pipeline.
        assertThat(userViews).isNotEmpty();
    }
}
