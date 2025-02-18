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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void testSaveUser(){
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("John", savedUser.getName());
    }
}
