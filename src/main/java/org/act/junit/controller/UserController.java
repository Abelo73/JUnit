package org.act.junit.controller;

import org.act.junit.entity.User;
import org.act.junit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        System.out.println("User id for searching by id: "+id);
        Optional<User> user = userService.getUserById(id);
        System.out.println("Response :"+user);

        return user.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping ("/{id}")
    ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> existingUser = userService.getUserById(id);

        if(existingUser.isPresent()){
            User savedUser = existingUser.get();
            savedUser.setName(user.getName());
            savedUser.setEmail(user.getEmail());
            savedUser.setAge(user.getAge());

            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
