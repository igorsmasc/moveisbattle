package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.service.UserStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserStatusControllerTest {

    @Mock
    private UserStatusService userStatusService;

    private UserStatusController underTest;

    @BeforeEach
    void setup() {
        underTest = new UserStatusController(userStatusService);
    }

    @Test
    void shouldGetRanking() {
        // given
        int rankingSize = 10;

        // when
        underTest.getRanking(rankingSize);

        // then
        verify(userStatusService, times(1)).getUserStatusRanking(rankingSize);
    }

}
