package com.letscode.moveisbattle.dto;

import com.letscode.moveisbattle.model.User;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private String id;
    private String name;
    private Integer allTimeRightAnswers;
    private Integer allTimeWrongAnswers;
    private Double userRate;

    public UserDTO fromDomain(User user) {
        return UserDTO.builder().id(user.getId()).name(user.getName()).allTimeRightAnswers(user.getAllTimeRightAnswers()).allTimeWrongAnswers(user.getAllTimeWrongAnswers()).userRate(user.getUserRate()).build();
    }
}
