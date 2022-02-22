package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.request.RegistrationRequest;
import com.letscode.moveisbattle.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    private RegistrationController underTest;

    @BeforeEach
    void setup() {
        underTest = new RegistrationController(registrationService);
    }

    @Test
    void shouldRegister() {
        // given
        RegistrationRequest request = new RegistrationRequest("first", "last", "mail@mail.com", "password");

        // when
        underTest.register(request);

        // then
        verify(registrationService, times(1)).register(request);
    }

    @Test
    void shouldConfirm() {
        // given
        String token = "token";

        // when
        underTest.confirm(token);

        // then
        verify(registrationService, times(1)).confirmToken(token);
    }


}
