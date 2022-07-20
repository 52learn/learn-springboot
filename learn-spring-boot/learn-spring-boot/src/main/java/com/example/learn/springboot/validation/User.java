package com.example.learn.springboot.validation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class User {
 
    @UserNameUnique
    private String userName;
    private char[] password;
 
    private User() {
        // no-arg Jackson constructor
    }
 
    public User(String userName, char[] password) {
        Objects.requireNonNull(userName);
        Objects.requireNonNull(password);
        this.userName = userName;
        this.password = password;
    }
    // getters omitted
}