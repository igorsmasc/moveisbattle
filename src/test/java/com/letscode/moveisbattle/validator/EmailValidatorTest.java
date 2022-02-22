package com.letscode.moveisbattle.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailValidatorTest {

    private EmailValidator underTest;

    @BeforeEach
    void setup() {
        underTest = new EmailValidator();
    }

    @Test
    void shouldTestValidEmail() {
        // Given
        String validEmail = "igorsmascarenhas@gmail.com";

        // when
        boolean isValid = underTest.test(validEmail);

        // should
        Assertions.assertTrue(isValid);
    }

    @Test
    void shouldTestInvalidEmail() {
        // Given
        String invalidEmail = "invalid-email";

        // when
        boolean isValid = underTest.test(invalidEmail);

        // should
        Assertions.assertFalse(isValid);
    }
}
