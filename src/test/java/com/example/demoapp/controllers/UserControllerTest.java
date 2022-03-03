package com.example.demoapp.controllers;

import com.example.demoapp.services.UserService;
import com.example.demoapp.views.UserView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebMvc
@AutoConfigureMockMvc
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void testGetUsers() throws Exception {
        UserView userView = createUser();
        List<UserView> users = List.of(userView);
        given(userService.getUserViews()).willReturn(users);

        ResultActions results = mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String contentAsString = results.andReturn().getResponse().getContentAsString();
        List<UserView> userViews = new ObjectMapper().readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(userViews).hasSize(1);
        assertThat(userViews.get(0)).isEqualTo(userView);

        Mockito.verify(userService).getUserViews();

    }

    private static UserView createUser() {
        UserView user = new UserView();

        user.setId("11");
        user.setCity("testCity");
        user.setCompany("testCompany");
        user.setCountry("testCountry");
        user.setFirstName("testFirst");
        user.setLastName("testLast");
        user.setOrganizationType("testOrgType");
        user.setPhone("testPhone");
        user.setState("testState");
        user.setZipCode("testZip");
        user.setDisclaimerAccepted(true);
        user.setEmailAddress("testEmail");
        user.setLanguageCode("testLanguageCode");
        user.setRegistrationId("testRegistrationId");
        user.setRegistrationIdGeneratedTime("testRegistrationIdGeneratedTime");
        user.setProjectIds(List.of("11", "22"));

        return user;
    }
}