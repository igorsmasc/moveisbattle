package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.User;
import com.letscode.moveisbattle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUserRanking(Integer rankingSize) {
        return userRepository.getRankingBySize(rankingSize);
    }
}
