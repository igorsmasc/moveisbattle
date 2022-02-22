package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.AppUser;
import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.enums.UserRole;
import com.letscode.moveisbattle.repository.UserRepository;
import com.letscode.moveisbattle.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    private UserService underTest;

    @BeforeEach
    void setup() {
        underTest = new UserServiceImpl(userRepository, bCryptPasswordEncoder, confirmationTokenService);
    }

    @Test
    void shouldLoadUserByUsername() {
        // Given
        String userEmail = "igorsmascarenhas@gmail.com";
        AppUser appUser = new AppUser(
                "igor",
                "mascarenhas",
                "igorsmascarenhas@gmail.com",
                "password",
                UserRole.USER);

        given(userRepository.findByEmail(userEmail)).willReturn(Optional.of(appUser));

        // When
        underTest.loadUserByUsername(userEmail);

        // Then
        verify(userRepository, times(1)).findByEmail(userEmail);
    }

    @Test
    void whenLoadUserByUsernameWithInvalidEmailShouldThrowUsernameNotFoundException() {
        // Given
        String userEmail = "notfoundemail-test@gmail.com";
        given(userRepository.findByEmail(userEmail)).willReturn(Optional.empty());

        // Then
        Assertions.assertThrows(UsernameNotFoundException.class, () -> underTest.loadUserByUsername(userEmail));
        verify(userRepository, times(1)).findByEmail(userEmail);
    }

    @Test
    void shouldEnableAppUser() {
        // Given
        String userEmail = "igorsmascarenhas@gmail.com";

        // When
        underTest.enableAppUser(userEmail);

        // Then
        verify(userRepository, times(1)).enableAppUser(userEmail);
    }

    @Test
    void shouldSignUpUser() {
        //given
        AppUser appUser = new AppUser(
                "igor",
                "mascarenhas",
                "igorsmascarenhas@gmail.com",
                "password",
                UserRole.USER);

        given(userRepository.findByEmail(appUser.getEmail())).willReturn(Optional.empty());

        // when
        underTest.signUpUser(appUser);

        // then
        ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository, times(1)).save(appUserArgumentCaptor.capture());
        Assertions.assertTrue(appUser.equals(appUserArgumentCaptor.getValue()));

        Assertions.assertEquals(appUser.getAuthorities(), appUserArgumentCaptor.getValue().getAuthorities());
        Assertions.assertEquals(appUser.getUsername(), appUserArgumentCaptor.getValue().getUsername());
        Assertions.assertEquals(appUser.isAccountNonExpired(), appUserArgumentCaptor.getValue().isAccountNonExpired());
        Assertions.assertEquals(appUser.isAccountNonLocked(), appUserArgumentCaptor.getValue().isAccountNonLocked());
        Assertions.assertEquals(appUser.isCredentialsNonExpired(), appUserArgumentCaptor.getValue().isCredentialsNonExpired());
        Assertions.assertEquals(appUser.isEnabled(), appUserArgumentCaptor.getValue().isEnabled());
    }

    @Test
    void whenEmailAlreadyTakenShouldThrownIllegalStateException() {
        //given
        AppUser appUser = new AppUser(
                "igor",
                "mascarenhas",
                "igorsmascarenhas@gmail.com",
                "password",
                UserRole.USER);

        given(userRepository.findByEmail(appUser.getEmail())).willReturn(Optional.of(appUser));

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> underTest.signUpUser(appUser));
    }
}
