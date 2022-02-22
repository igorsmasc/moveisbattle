package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.UserStatus;
import com.letscode.moveisbattle.service.UserStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/users/status")
@Api(value = "User Status")
@CrossOrigin(origins = "*")
public class UserStatusController {

    private final UserStatusService userStatusService;

    @GetMapping("/ranking/{rankingSize}")
    @ApiOperation(value = "Buscar um determinado raking por jogadores")
    public List<UserStatus> getRanking(@PathVariable Integer rankingSize) {
        return userStatusService.getUserStatusRanking(rankingSize);
    }
}
