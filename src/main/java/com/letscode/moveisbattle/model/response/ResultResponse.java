package com.letscode.moveisbattle.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.letscode.moveisbattle.model.Question;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponse {
    private String lastGuessResult;
    private Integer points;
    private String gameStatus;
    private Question lastQuestion;
    private String gameId;
}
