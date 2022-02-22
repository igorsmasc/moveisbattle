package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.request.RegistrationRequest;

public interface RegistrationService {
    String register(RegistrationRequest request);

    String confirmToken(String token);
}
