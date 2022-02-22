package com.letscode.moveisbattle.service.impl;

import com.letscode.moveisbattle.dto.NewUserDTO;
import com.letscode.moveisbattle.model.AppUser;
import com.letscode.moveisbattle.model.ConfirmationToken;
import com.letscode.moveisbattle.model.UserStatus;
import com.letscode.moveisbattle.model.enums.UserRole;
import com.letscode.moveisbattle.model.request.RegistrationRequest;
import com.letscode.moveisbattle.service.ConfirmationTokenService;
import com.letscode.moveisbattle.service.RegistrationService;
import com.letscode.moveisbattle.service.UserService;
import com.letscode.moveisbattle.service.UserStatusService;
import com.letscode.moveisbattle.validator.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;
    private final UserStatusService userStatusService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid!");
        }

        NewUserDTO newUserDTO = userService.signUpUser(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.USER
        ));

        UserStatus userStatus = new UserStatus(newUserDTO.getUserId());
        userStatusService.saveUserStatus(userStatus);

        return newUserDTO.getUserToken();
    }

    @Override
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        userService.enableAppUser(
                confirmationToken.getAppUser().getEmail());

        return "confirmed";
    }
}
