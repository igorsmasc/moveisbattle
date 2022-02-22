package com.letscode.moveisbattle.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.letscode.moveisbattle.model.Question;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse {
    private String lastGuessResult;
    private Integer points;
    private String gameStatus;
    private Question lastQuestion;
    private String lastQuestionId;
    private String gameId;

    public ResultResponse(String lastGuessResult, Integer points, String gameStatus, Question lastQuestion, String gameId) {
        this.lastGuessResult = lastGuessResult;
        this.points = points;
        this.gameStatus = gameStatus;
        this.lastQuestion = lastQuestion;
        this.gameId = gameId;
    }

    public ResultResponse(Integer points, String gameStatus, String lastQuestionId, String gameId) {
        this.points = points;
        this.gameStatus = gameStatus;
        this.lastQuestionId = lastQuestionId;
        this.gameId = gameId;
    }

    public ResultResponse(Integer points, String gameStatus, String gameId) {
        this.points = points;
        this.gameStatus = gameStatus;
        this.gameId = gameId;
    }
}
