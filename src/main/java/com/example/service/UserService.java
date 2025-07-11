package com.example.service;

import com.example.model.User;
import java.util.List;

public interface UserService {
    List<User> getAll();
    void save(User user);
    void delete(Long id);
    User getById(Long id);
}
