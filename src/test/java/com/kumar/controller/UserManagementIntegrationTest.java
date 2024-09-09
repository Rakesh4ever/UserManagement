package com.kumar.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.entity.User;
import com.kumar.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.kumar.mocks.UserMock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserManagementIntegrationTest {
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Container
    static  MySQLContainer mySQLContainer= new MySQLContainer<>("mysql:latest");
@DynamicPropertySource
   static void configureProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
       dynamicPropertyRegistry.add("spring.datasource.url",mySQLContainer::getJdbcUrl);
       dynamicPropertyRegistry.add("spring.datasource.username",mySQLContainer::getUsername);
       dynamicPropertyRegistry.add("spring.datasource.password",mySQLContainer::getPassword);
   }
   @BeforeAll
   static  void beforeAll(){
       mySQLContainer.start();
   }
   @AfterAll
    static  void afterAll(){
        mySQLContainer.stop();
    }
    @Before
    void setup(){
      this.mockMvc= MockMvcBuilders.standaloneSetup(UserController.class).build();
    }

    @Test
    public void testSaveUser() throws Exception {
      mockMvc.perform(post("/api/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(saveUser()))
                        .accept("application/json"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userId").doesNotExist());
             /*    .andExpect(jsonPath("$.name").value("MyName"))
                .andExpect(jsonPath("$.email").value("myname@gmail.com"))
                .andExpect(jsonPath("$.password").value("123"))
                .andExpect(jsonPath("$.address").value("MyAddress"));*/
        verify(userService, times(1)).save(any(User.class));

    }
    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
