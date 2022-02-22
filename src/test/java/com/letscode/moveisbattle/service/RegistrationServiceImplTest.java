package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.AppUser;
import com.letscode.moveisbattle.model.ConfirmationToken;
import com.letscode.moveisbattle.model.enums.UserRole;
import com.letscode.moveisbattle.model.request.RegistrationRequest;
import com.letscode.moveisbattle.service.impl.RegistrationServiceImpl;
import com.letscode.moveisbattle.validator.EmailValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailValidator emailValidator;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    private RegistrationService underTest;

    @BeforeEach
    void setup() {
        underTest = new RegistrationServiceImpl(userService, emailValidator, confirmationTokenService);
    }

    @Test
    void shouldRegisterANewUser() {
        // given
        RegistrationRequest request = new RegistrationRequest("igor", "mascarenhas", "igorsmascarenhas@gmail.com", "password");
        given(emailValidator.test(request.getEmail())).willReturn(true);

        AppUser appUser = new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.USER);

        // when
        underTest.register(request);

        // Then
        ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userService, times(1)).signUpUser(appUserArgumentCaptor.capture());
        Assertions.assertEquals(appUser.getFirstName(), appUserArgumentCaptor.getValue().getFirstName());
        Assertions.assertEquals(appUser.getLastName(), appUserArgumentCaptor.getValue().getLastName());
        Assertions.assertEquals(appUser.getEmail(), appUserArgumentCaptor.getValue().getEmail());
        Assertions.assertEquals(appUser.getPassword(), appUserArgumentCaptor.getValue().getPassword());
        Assertions.assertEquals(appUser.getAppUserRole(), appUserArgumentCaptor.getValue().getAppUserRole());
    }

    @Test
    void whenUsingInvalidEmailShouldThrowNewException() {
        // given
        RegistrationRequest request = new RegistrationRequest("igor", "mascarenhas", "invalid-mail", "password");
        given(emailValidator.test(request.getEmail())).willReturn(false);

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> underTest.register(request));
    }

    @Test
    void whenUsingValidTokenShouldConfirm() {
        // given
        String validToken = "valid-token";

        AppUser appUser = new AppUser("first name", "last name", "email@mail.com", "password", UserRole.USER);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                1L,
                validToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                null,
                appUser
        );

        given(confirmationTokenService.getToken(validToken)).willReturn(Optional.of(confirmationToken));

        // when
        String response = underTest.confirmToken(validToken);

        // Then
        verify(confirmationTokenService, times(1)).getToken(validToken);
        Assertions.assertEquals("confirmed", response);
    }

    @Test
    void whenUsingInvalidTokenShouldThrowIllegalStateException() {
        // given
        String invalidToken = "invalid-token";

        AppUser appUser = new AppUser("first name", "last name", "email@mail.com", "password", UserRole.USER);


        given(confirmationTokenService.getToken(invalidToken)).willReturn(Optional.empty());

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> underTest.confirmToken(invalidToken));
    }

    @Test
    void whenUsingAlreadyConfirmedEmailShouldThrowIllegalStateException() {
        // given
        String validToken = "valid-token";

        AppUser appUser = new AppUser("first name", "last name", "email@mail.com", "password", UserRole.USER);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                1L,
                validToken,
                LocalDateTime.now().minusMinutes(5),
                LocalDateTime.now().plusMinutes(15),
                LocalDateTime.now(),
                appUser
        );

        given(confirmationTokenService.getToken(validToken)).willReturn(Optional.of(confirmationToken));

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> underTest.confirmToken(validToken));
    }

    @Test
    void whenUsingAExpiredTokenShouldThrowIllegalStateException() {
        // given
        String expiredToken = "expired-token";

        AppUser appUser = new AppUser("first name", "last name", "email@mail.com", "password", UserRole.USER);

        ConfirmationToken expiredConfirmationToken = new ConfirmationToken(
                1L,
                expiredToken,
                LocalDateTime.now().minusMinutes(15),
                LocalDateTime.now().minusMinutes(5),
                null,
                appUser
        );

        given(confirmationTokenService.getToken(expiredToken)).willReturn(Optional.of(expiredConfirmationToken));

        // Then
        Assertions.assertThrows(IllegalStateException.class, () -> underTest.confirmToken(expiredToken));
    }

}
