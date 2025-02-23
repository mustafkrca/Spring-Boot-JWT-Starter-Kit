package com.example.jwtstarterkit.controllers;

import com.example.jwtstarterkit.dtos.responses.SuccessResponse;
import com.example.jwtstarterkit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/health")
    public ResponseEntity<SuccessResponse<String>> health() {
        try {
            String data = userService.healthCheck();
            SuccessResponse response = new SuccessResponse(data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
