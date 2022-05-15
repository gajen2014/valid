package com.validate.app.controller;

import com.validate.app.entity.User;
import com.validate.app.exception.ResourceNotFoundException;
import com.validate.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/")
    public User createUser(@RequestBody @Valid User user){
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id")long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found in ID: "+ id));

    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id")long userId){
        User editUser = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("user not found by id :"+ userId));
        editUser.setFirstName(user.getFirstName());
        editUser.setLastName(user.getLastName());
        editUser.setEmail(user.getEmail());
        return userRepository.save(editUser);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id")Long userId){
        User userThere = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not Found with id :" + userId));
        userRepository.delete(userThere);
        return ResponseEntity.ok().build();

    }

}
