package com.letscode.moveisbattle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewUserDTO {
    private Long userId;
    private String userToken;
}
