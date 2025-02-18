package org.act.junit.service;

import org.act.junit.entity.User;
import org.act.junit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    private User user;


    @BeforeEach
    void setUp(){
       user =  new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("johndoe@example.com");
        user.setAge(30);
    }

    @Test
    void testGetAllUsers(){
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("John", users.get(0).getName());
    }

    @Test
    void testGetUserByIdFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.getUserById(1L);
        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getName());
    }

    @Test
    void testGetUserByIdNotFound(){
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserById(2L);
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testSaveUser(){
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getName());
    }
}
