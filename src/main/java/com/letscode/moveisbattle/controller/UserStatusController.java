package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.UserStatus;
import com.letscode.moveisbattle.service.UserStatusService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "users/status")
public class UserStatusController {

    private final UserStatusService userStatusService;

    @GetMapping("/ranking/{rankingSize}")
    public List<UserStatus> getRanking(@PathVariable Integer rankingSize) {
        return userStatusService.getUserStatusRanking(rankingSize);
    }

}
