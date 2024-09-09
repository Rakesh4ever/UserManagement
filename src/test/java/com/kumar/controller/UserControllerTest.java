package com.kumar.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumar.entity.User;
import com.kumar.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.kumar.mocks.UserMock.getUser;
import static com.kumar.mocks.UserMock.getUsers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private  UserController userController;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext){
        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void testSaveUser() throws Exception {
    given(userService.save(any(User.class))).willReturn(getUser());
    mockMvc.perform(post("/api/user/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(getUser())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.name").value("MyName"))
            .andExpect(jsonPath("$.email").value("myname@gmail.com"))
            .andExpect(jsonPath("$.password").value("123"))
            .andExpect(jsonPath("$.address").value("MyAddress"));
    verify(userService,times(1)).save(any(User.class));

    }

    /**
     * Method under test: {@link UserController#saveUser(User)}
     */
    @Test
    void testSaveUser2() {

        User user = mock(User.class);
        doNothing().when(user).setAddress(Mockito.<String>any());
        doNothing().when(user).setEmail(Mockito.<String>any());
        doNothing().when(user).setName(Mockito.<String>any());
        doNothing().when(user).setPassword(Mockito.<String>any());
        doNothing().when(user).setUserId(Mockito.<Integer>any());
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setName("Name");
        user.setPassword("password");
        user.setUserId(1);
        userController.saveUser(user);
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() throws Exception {
        given(userService.geUsers()).willReturn(getUsers());
        mockMvc.perform(get("/api/user/getusers")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].name").value("MyName"));
        (new UserController()).getAllUsers();

    }

    /**
     * Method under test: {@link UserController#findUserById(Integer)}
     */
    @Test
    @Disabled(" need to complete this test")
    void testFindUserById() {

      //  (new UserController()).findUserById(1);
    }

    /**
     * Method under test: {@link UserController#updateUser(User, Integer)}
     */
    @Test
    void testUpdateUser() {
        User user=getUser();
        user.setName("Hari");
        userController.updateUser(user, 1);
    }

    /**
     * Method under test: {@link UserController#updateUser(User, Integer)}
     */
    @Test
    void updateUserWhoIsNotExistTest() throws Exception {
        User user=getUser();
        Integer userId=1;
        when(userService.updateUserDetails(user,userId)).thenReturn(Optional.empty());
        mockMvc.perform(put("/update/{userId}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getUser())))
                        .andExpect(status().isNotFound());
    }


    /**
     * Method under test: {@link UserController#partialUserUpdate(Integer, Map)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testPartialUserUpdate() {

        UserController userController = new UserController();
        userController.partialUserUpdate(1, new HashMap<>());
    }
}

