package com.kumar.controller;

import com.kumar.TestH2Repository;
import com.kumar.entity.User;
import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.kumar.mocks.UserMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author RakeshKumar created on 05/09/24
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserManagementIntegrationTest2 {//Another wayb to write Integration test using RestTemplate. RestTemplate and TestRestTemplate almost same.
    @LocalServerPort
    private Integer PORT;
    private String BASE_URL="http://localhost:";
    private  static RestTemplate restTemplate;
    @Autowired
    private TestH2Repository testH2Repository;

    public UserManagementIntegrationTest2() {
    }

    @BeforeAll
    public static void init(){
        restTemplate=new RestTemplate();
    }
@BeforeEach
    public void setUp(){
        BASE_URL=BASE_URL.concat(PORT +"").concat("/api/user");
    }
    @Test
    public void testsaveUser(){
        User user=saveUser();
        User userResponse=restTemplate.postForObject(BASE_URL.concat("/save"),user,User.class);
        assertEquals("MyName",userResponse.getName());
        assertEquals(1,testH2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO user(user_id,name,email,password,address) VALUES(1,'sayps','sayps@gmail.com','1234','Bangalore');",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from user WHERE user_id=1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllUserTest(){
  List<User> users=restTemplate.getForObject(BASE_URL.concat("/getusers"), List.class);
  assertEquals(1,users.size());
  assertEquals(1,testH2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO user(user_id,name,email,password,address) VALUES(1,'Tiger','tiger@gmail.com','1235','Bangalore');",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from user WHERE user_id=1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindUserById(){
       User user= restTemplate.getForObject(BASE_URL+"/{userId}",User.class,1);
       assertAll(
               ()->assertNotNull(user),
               ()->assertEquals(Optional.of(1),Optional.of(user.getUserId())),
        ()->assertEquals("Tiger",user.getName())
       );

    }

    @Test
    @Sql(statements = "INSERT INTO user(user_id,name,email,password,address) VALUES(4,'Mohan','myname@gmail.com','1239','Ranchi');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE from user WHERE user_id=4", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateUserTest() {
        User user = updatetUser();// update user data that comes from updatetUser()
        restTemplate.put(BASE_URL+"/update/{userId}",user,4);
        User user1=testH2Repository.findById(4).get();
        assertAll(
                ()->assertNotNull(user1),
                ()->assertEquals(Optional.of(4),Optional.of(user.getUserId())),
                ()->assertEquals("MyName",user1.getName()),
                ()->assertEquals("MyAddress",user1.getAddress())
        );
    }
    @Test
    @Sql(statements = "INSERT INTO user(user_id,name,email,password,address) VALUES(5,'Aryan','aryan@gmail.com','1230','HZB');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public  void deleteUserTest(){
        int recordsCount=testH2Repository.findAll().size();
        assertEquals(1,recordsCount);
        restTemplate.delete(BASE_URL+"/delete/{userId}",5);
        assertEquals(0,testH2Repository.findAll().size());

    }


}
