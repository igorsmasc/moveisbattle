package com.letscode.moveisbattle.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Long userId;
    private Integer wrongAnswers = 0;
    private Integer rightAnswers = 0;
    private String lastQuestionId = "";
    private String previousQuestions = "";
    private boolean isValidGame = true;

    public Game(Long userId) {
        this.userId = userId;
    }

    public void setLastQuestion(String lastQuestion) {
        this.previousQuestions += lastQuestion + ";";
        this.lastQuestionId = lastQuestion;
    }

    public boolean isValidGame() {
        return isValidGame;
    }

    public void setValidGame(boolean validGame) {
        isValidGame = validGame;
    }
}
