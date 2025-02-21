package com.example.jwtstarterkit.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    //TODO authority tablosuna ROLE_ADMIN ekle
    //TODO user_authority_mapping tablosuna kullanıcı ID'si ve ROLE_ADMIN yetkisi tanımla

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Bu, sadece adminlerin erişebildiği bir sayfadır!";
    }

}
