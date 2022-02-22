package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.dto.NewUserDTO;
import com.letscode.moveisbattle.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    NewUserDTO signUpUser(AppUser appUser);

    int enableAppUser(String email);
}
