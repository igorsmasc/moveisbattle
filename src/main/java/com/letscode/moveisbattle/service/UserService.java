package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String id);

    User saveUser(User user);

    List<User> getUserRanking(Integer rankingSize);
}
