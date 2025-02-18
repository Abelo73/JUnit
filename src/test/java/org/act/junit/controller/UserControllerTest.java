package org.act.junit.controller;

import org.act.junit.entity.User;
import org.act.junit.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setAge(30);
    }

    @Test
    void testGetAllUsers(){
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        List<User> users = userController.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
    }

//    User exists

    @Test
    void testGetUserByIdWhenUserExists(){
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1L);


        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }
// User not found test

    @Test
    void testGetUserByIdWhenUserNotExists(){
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(1L);

        assertEquals(NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteUserWhenUserExists(){
        when(userService.deleteUser(1L)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Object> response = userController.deleteUser(1L);

        assertEquals(NO_CONTENT, response.getStatusCode());
    }


}
