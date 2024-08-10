package com.kumar.controller;

import com.kumar.entity.User;
import com.kumar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author RakeshKumar created on 19/07/23
 */

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
 private  UserService userService;

    @PostMapping(value={"/save"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public User saveUser(@RequestBody User user){

        return userService.save(user);
    }
    @GetMapping("/getusers")
    public List<User> getAllUsers(){
        return userService.geUsers();
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@RequestBody User user,@PathVariable Integer userId){
      return   userService.updateUserDetails(user,userId);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Integer userId){
        userService.deleteUserRecord(userId);
       // return "User deleted";
    }

    @PatchMapping("/partialUpdate/{id}")
    public ResponseEntity<User> partialUserUpdate( @PathVariable("id") Integer id,@RequestBody Map<Object,Object> field) {

      return   userService.partialUpdateUser(id , field);
    }

}
