package com.letscode.moveisbattle.controller;

import com.letscode.moveisbattle.model.User;
import com.letscode.moveisbattle.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "users")
public class UserController {
    private final UserService userService;

    @GetMapping("/ranking/{rankingSize}")
    public List<User> getRanking(@PathVariable Integer rankingSize) {
        return userService.getUserRanking(rankingSize);
    }
}
