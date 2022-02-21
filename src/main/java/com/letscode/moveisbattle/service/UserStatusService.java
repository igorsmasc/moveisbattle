package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserStatusService {
    Optional<UserStatus> getUserStatus(Long id);

    List<UserStatus> getUserStatusRanking(Integer rankingSize);

    UserStatus saveUserStatus(UserStatus userStatus);
}
