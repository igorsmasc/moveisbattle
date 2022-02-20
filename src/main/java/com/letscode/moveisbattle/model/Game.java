package com.letscode.moveisbattle.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Getter
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String userId;
    private Integer wrongAnswers = 0;
    private Integer rightAnswers = 0;
    private String lastQuestion = "";
    private String previousQuestions = "";

    public Game(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWrongAnswers(Integer wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public void setRightAnswers(Integer rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public void setLastQuestion(String lastQuestion) {
        this.previousQuestions += lastQuestion + ";";
        this.lastQuestion = lastQuestion;
    }
}
