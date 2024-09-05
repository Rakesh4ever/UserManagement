package com.kumar.mocks;

import com.kumar.entity.User;

import java.util.List;

/**
 * @author RakeshKumar created on 05/09/24
 */

public class UserMock {
    public static User getUser(){
       return User.builder().userId(1).name("MyName").email("myname@gmail.com").password("123").address("MyAddress").build();
    }

    public static List<User> getUsers(){
        return List.of( User.builder().userId(1).name("MyName").email("myname@gmail.com").password("123").address("MyAddress").build(),User.builder().userId(2).name("MyName2").email("myname2@gmail.com").password("1234").address("MyAddress2").build());
    }
}
