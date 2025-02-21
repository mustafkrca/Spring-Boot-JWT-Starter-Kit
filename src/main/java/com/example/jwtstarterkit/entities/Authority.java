package com.example.jwtstarterkit.entities;

import jakarta.persistence.*;

@Entity
public class Authority {

    @Id
    private String authority;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
