package com.letscode.moveisbattle.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserStatus {
    @Id
    private Long userId;
    private Integer allTimeRightAnswers = 0;
    private Integer allTimeWrongAnswers = 0;
    private Integer quizzes = 0;
    private Double userRate = 0.0;
    private Double score = 0.0;

    public UserStatus(Long userId) {
        this.userId = userId;
    }

    public void setGameAnswersStatus(Integer rightAnswers, Integer wrongAnswers) {
        this.allTimeRightAnswers += rightAnswers;
        this.allTimeWrongAnswers += wrongAnswers;
        this.quizzes += 1;

        Integer totalAnswers = allTimeRightAnswers + allTimeWrongAnswers;

        if (totalAnswers == 0) return;

        Double calcRate = (Double.valueOf(allTimeRightAnswers) / totalAnswers);
        setUserRate(calcRate);

        this.score = this.quizzes * this.userRate;
    }
}
