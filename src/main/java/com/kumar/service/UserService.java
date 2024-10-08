package com.kumar.service;

import com.kumar.entity.User;
import com.kumar.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author RakeshKumar created on 19/07/23
 */
@Service
public class UserService {
    private static Logger logger= LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    public User save(User user) {
        logger.info("Saving user:Service/DAO");
      return   userRepository.save(user);
    }

    public List<User> geUsers() {
        logger.info("Retrieving all users record from Service/DAO");
        return userRepository.findAll();
    }

    public Optional<User> updateUserDetails(User newUser, Integer userId) {
        logger.info("Updating  user data from Service/DAO");

        return Optional.of(userRepository.findById(userId)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    user.setAddress(newUser.getAddress());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setUserId(userId);
                    return userRepository.save(newUser);
                }));


    }
    public Boolean deleteUserRecord(Integer userId) {
        logger.info("Deleting user data from Service/DAO");
        if (userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
            return true;
        }
        else
            return false;
    }


    public ResponseEntity<User> partialUpdateUser(Integer id, Map<Object,Object> fields) {
        Optional<User> user=userRepository.findById(id);
       if (user.isPresent()){
           fields.forEach( (key,value)->{
               Field field= ReflectionUtils.findField(User.class,(String) key);
               field.setAccessible(true);
               ReflectionUtils.setField(field,user.get(),value);
                   }
           );
User updatedUser=userRepository.save(user.get());
return new ResponseEntity<>(updatedUser, HttpStatus.OK);
       }
        return null;
    }

    public Optional<User> findUserById(Integer userId) {
        logger.info("Getting  user data from Repository");
      return   userRepository.findById(userId);
    }
}
