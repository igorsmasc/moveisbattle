package com.letscode.moveisbattle.service.impl;

import com.letscode.moveisbattle.model.UserStatus;
import com.letscode.moveisbattle.repository.UserStatusRepository;
import com.letscode.moveisbattle.service.UserStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {

    private final UserStatusRepository userStatusRepository;

    @Override
    public Optional<UserStatus> getUserStatus(Long id) {
        return userStatusRepository.findById(id);
    }

    @Override
    public List<UserStatus> getUserStatusRanking(Integer rankingSize) {
        return userStatusRepository.getRankingBySize(rankingSize);
    }

    @Override
    public UserStatus saveUserStatus(UserStatus userStatus) {
        return userStatusRepository.save(userStatus);
    }
}
