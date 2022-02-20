package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Question;

public interface QuestionService {
    Question generateQuestion(Game game);
}
