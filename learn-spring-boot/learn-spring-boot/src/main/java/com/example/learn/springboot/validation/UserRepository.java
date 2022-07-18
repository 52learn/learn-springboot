package com.example.learn.springboot.validation;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private List<User> registeredUsers = new LinkedList<>();
    public UserRepository(){
        User user = new User("kim","12345".toCharArray());
        registeredUsers.add(user);
    }

    void save(User user) {
        registeredUsers.add(user);
    }

    Optional<User> findByUserName(String userName) {
        return registeredUsers.stream()
            .filter(user -> user.getUserName().equals(userName))
            .findFirst();
    }
}
