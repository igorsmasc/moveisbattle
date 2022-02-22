package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.AppUser;
import com.letscode.moveisbattle.model.ConfirmationToken;
import com.letscode.moveisbattle.model.enums.UserRole;
import com.letscode.moveisbattle.repository.ConfirmationTokenRepository;
import com.letscode.moveisbattle.service.impl.ConfirmationTokenServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConfirmationTokenImplTest {

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    private ConfirmationTokenService underTest;

    @BeforeEach
    void setup() {
        underTest = new ConfirmationTokenServiceImpl(confirmationTokenRepository);
    }

    @Test
    void shouldSaveConfirmationToken() {
        // Given
        AppUser appUser = new AppUser("first name", "last name", "email@mail.com", "password", UserRole.USER);

        ConfirmationToken confirmationToken = new ConfirmationToken("token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);

        // When
        underTest.saveConfirmationToken(confirmationToken);

        // Then
        ArgumentCaptor<ConfirmationToken> confirmationTokenArgumentCaptor = ArgumentCaptor.forClass(ConfirmationToken.class);
        verify(confirmationTokenRepository, times(1)).save(confirmationTokenArgumentCaptor.capture());
        Assertions.assertTrue(confirmationToken.equals(confirmationTokenArgumentCaptor.getValue()));
    }

    @Test
    void shouldGetConfirmationToken() {
        // Given
        String token = "token";

        // When
        underTest.getToken(token);

        // Then
        verify(confirmationTokenRepository, times(1)).findByToken(token);
    }

    @Test
    void whenSetConfirmedAtShouldUpdateConfirmedToken() {
        // Given
        AppUser appUser = new AppUser("first name", "last name", "email@mail.com", "password", UserRole.USER);

        ConfirmationToken confirmationToken = new ConfirmationToken("token", LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);

        // When
        underTest.setConfirmedAt(confirmationToken.getToken());

        // Then
        verify(confirmationTokenRepository, times(1)).updateConfirmedAt(any(), any());
    }

}
