package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.User;
import com.letscode.moveisbattle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService underTest;

    @BeforeEach
    void setup() {
        underTest = new UserServiceImpl(userRepository);
    }

    @Test
    void canGetUser() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        // When
        underTest.getUser(user.getId());

        // Then
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void canSaveUser() {
        // Given
        User user = new User("Test User");
        user.setId("test-user-id");

        // When
        underTest.saveUser(user);

        // Then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void canGetUserRanking() {
        // Given
        int rakingSize = 10;

        // When
        underTest.getUserRanking(rakingSize);

        // Then
        verify(userRepository, times(1)).getRankingBySize(rakingSize);
    }

}
