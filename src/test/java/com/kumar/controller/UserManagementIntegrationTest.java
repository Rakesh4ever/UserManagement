package com.kumar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author RakeshKumar created on 05/09/24
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserManagementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    void setup(){
      this.mockMvc= MockMvcBuilders.standaloneSetup(UserController.class).build();
    }

}
