package com.example.repository;

import com.example.model.User;
import java.util.List;

public interface UserDao {
    void save(User user);
    User findById(Long id);
    List<User> findAll();
    void deleteById(Long id);
}
