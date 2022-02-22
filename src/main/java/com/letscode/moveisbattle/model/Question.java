package com.letscode.moveisbattle.model;

import com.letscode.moveisbattle.dto.MovieDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Question {
    private String questionId;
    private String gameId;
    private MovieDTO movie01;
    private MovieDTO movie02;

    public Question(String gameId, MovieDTO movie01, MovieDTO movie02) {
        this.questionId = gameId + movie01.getId() + movie02.getId();
        this.gameId = gameId;
        this.movie01 = movie01;
        this.movie02 = movie02;
    }
}
