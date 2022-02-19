package com.letscode.moveisbattle.model.request;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class GuessResquest {
    private String gameId;
    private String movie01;
    private String movie02;
    private String guess;

    public GuessResquest(String gameId, String movie01, String movie02, String guess) {
        this.gameId = gameId;
        this.movie01 = movie01;
        this.movie02 = movie02;
        this.guess = guess;
    }
}
