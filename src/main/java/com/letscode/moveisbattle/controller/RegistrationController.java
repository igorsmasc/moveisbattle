package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.request.RegistrationRequest;
import com.letscode.moveisbattle.service.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/user/registration")
@Api(value = "Registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    @ApiOperation(value = "Cadastrar um novo usuário")
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    @ApiOperation(value = "Confirmar token de novo usuário")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

}
