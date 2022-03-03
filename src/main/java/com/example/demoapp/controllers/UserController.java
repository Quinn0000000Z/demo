package com.example.demoapp.controllers;

import com.example.demoapp.services.UserService;
import com.example.demoapp.views.UserView;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserView>> getUsers() {

        List<UserView> userViews = userService.getUserViews();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .cacheControl(CacheControl.noCache()) // prevents caching which can be error-prone.
                .body(userViews);
    }
}
