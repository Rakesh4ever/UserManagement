package com.kumar.controller;

import com.kumar.entity.User;
import com.kumar.exception.UserNotFoundException;
import com.kumar.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author RakeshKumar created on 19/07/23
 */

@RestController
@RequestMapping("/api/user")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private  static  final Logger logger=LoggerFactory.getLogger(UserController.class);
    @Autowired
 private  UserService userService;

    @PostMapping(value = {"/save"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public User saveUser(@RequestBody User user) {
        logger.info("Saving user data: controller");
        return userService.save(user);
    }
    @GetMapping("/getusers")
    public List<User> getAllUsers(){
        logger.info("Getting all users data...Controller");
        return userService.geUsers();
    }

    @GetMapping("/{userId}")
    public Optional<User> findUserById(@PathVariable Integer userId){
        logger.info("Getting  user data from Controller");
       Optional<User> user= userService.findUserById(userId);
       if(user.isPresent())
           return user;
       else
         throw new UserNotFoundException("User with ID: "+ userId+"  not available");
    }

    @PutMapping("/update/{userId}")
    public Optional<User> updateUser(@RequestBody User user, @PathVariable Integer userId){
        logger.info("Updating user data from Controller");
      return   userService.updateUserDetails(user,userId);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
        logger.info("Deleting user data from Controller");
        Boolean status=userService.deleteUserRecord(userId);
        if (status) {
            return new ResponseEntity<String>("User deleted", HttpStatus.OK);
        }
        else {
            throw new UserNotFoundException("User with ID: "+ userId+"  not available");
        }
    }
    @PatchMapping("/partialUpdate/{id}")
    public ResponseEntity<User> partialUserUpdate( @PathVariable("id") Integer id,@RequestBody Map<Object,Object> field) {

      return   userService.partialUpdateUser(id , field);
    }

}
