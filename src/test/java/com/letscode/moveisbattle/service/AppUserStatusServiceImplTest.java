package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.UserStatus;
import com.letscode.moveisbattle.repository.UserStatusRepository;
import com.letscode.moveisbattle.service.impl.UserStatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AppUserStatusServiceImplTest {

    @Mock
    private UserStatusRepository userStatusRepository;

    private UserStatusService underTest;

    @BeforeEach
    void setup() {
        underTest = new UserStatusServiceImpl(userStatusRepository);
    }

    @Test
    void shouldGetUserStatus() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        // When
        underTest.getUserStatus(userStatus.getUserId());

        // Then
        verify(userStatusRepository, times(1)).findById(userStatus.getUserId());
    }

    @Test
    void shouldSaveUserStatus() {
        // Given
        UserStatus userStatus = new UserStatus(1L);

        // When
        underTest.saveUserStatus(userStatus);

        // Then
        verify(userStatusRepository, times(1)).save(userStatus);
    }

    @Test
    void canGetUserRanking() {
        // Given
        int rakingSize = 10;

        // When
        underTest.getUserStatusRanking(rakingSize);

        // Then
        verify(userStatusRepository, times(1)).getRankingBySize(rakingSize);
    }
}
