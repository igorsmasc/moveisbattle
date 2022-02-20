package com.letscode.moveisbattle.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private Integer allTimeRightAnswers;
    private Integer allTimeWrongAnswers;
    private Integer quizzes;
    private Double userRate;
    private Double score;

    public User(String name) {
        this.name = name;
    }

    public void setGameAnswersStatus(Integer rightAnswers, Integer wrongAnswers) {
        this.allTimeRightAnswers += rightAnswers;
        this.allTimeWrongAnswers += wrongAnswers;
        this.quizzes += 1;

        Integer totalAnswers = allTimeRightAnswers + allTimeWrongAnswers;

        if (totalAnswers == 0) return;

        Double calcRate = (Double.valueOf(allTimeRightAnswers) / totalAnswers) * 100;
        setUserRate(calcRate);

        this.score = this.quizzes * this.userRate;
    }

}
