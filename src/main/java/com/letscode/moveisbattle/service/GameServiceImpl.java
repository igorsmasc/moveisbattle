package com.letscode.moveisbattle.service;

import com.letscode.moveisbattle.dto.MovieDTO;
import com.letscode.moveisbattle.model.Game;
import com.letscode.moveisbattle.model.Movie;
import com.letscode.moveisbattle.model.Question;
import com.letscode.moveisbattle.model.request.GuessResquest;
import com.letscode.moveisbattle.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private MovieService movieService;

    @Override
    public Question startGame(String id) {
        Game game = new Game(id);
        game = saveGame(game);

        Question question = generateQuestion(game);
        game.setLastQuestion(question.getQuestionId());

        saveGame(game);

        return question;
    }

    @Override
    public Question generateQuestion(Game game) {

        Boolean validMovies = false;

        MovieDTO m1 = null, m2 = null;
        String questionHash01, questionHash02;

        while (validMovies == false) {
            m1 = MovieDTO.fromDomain(movieService.getMovie("1"));
            m2 = MovieDTO.fromDomain(movieService.getMovie("2"));
            questionHash01 = game.getId()+m1.getId()+m2.getId();
            questionHash02= game.getId()+m2.getId()+m1.getId();

            if(!game.getPreviousQuestions().contains(questionHash01) && !game.getPreviousQuestions().contains(questionHash02)) {
                validMovies = true;
            }
        }

        Question q1 = new Question(game.getId(), m1, m2);

        return q1;
    }

    @Override
    public ResponseEntity<?> guess(GuessResquest guessResquest) {
        Optional<Game> gameOptional = getGame(guessResquest.getGameId());

        if(gameOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Game not found");
        }

        Game game = gameOptional.get();

        Movie movie01 = movieService.getMovie(guessResquest.getMovie01());
        Movie movie02 = movieService.getMovie(guessResquest.getMovie02());
        String questionHash = game.getId()+movie01.getId()+movie02.getId();
        HashMap<String, Object> result = new HashMap();

        if(!questionHash.equals(game.getLastQuestion())) {
            return ResponseEntity.status(400).body("Questão inválida para jogo selecionado");
        }

        if(game.getWrongAnswers() < 3) {
            String bestMovieId;

            if(movie01.getRating() > movie02.getRating()) {
                bestMovieId = movie01.getId();
            } else if(movie01.getRating() < movie02.getRating()) {
                bestMovieId = movie02.getId();
            } else {
                bestMovieId = "same";
            }

            if(bestMovieId.equals(guessResquest.getGuess()) || bestMovieId.equals("same")) {
                result.put("Resultado", "Voce acertou!");
                game.setRightAnswers(game.getRightAnswers()+1);
            } else {
                result.put("Resultado", "Ooops! Voce errou!");
                game.setWrongAnswers(game.getWrongAnswers()+1);
            }
        }


        result.put("Pontuacao", game.getRightAnswers());

        if(game.getWrongAnswers() < 3) {
            Question newQuestion = generateQuestion(game);
            game.setLastQuestion(newQuestion.getQuestionId());
            saveGame(game);
            result.put("Nova questao", newQuestion);
            return ResponseEntity.ok(result);
        }

        saveGame(game);

        result.put("Status", "Fim de jogo");
        result.remove("Resultado");
        result.put("Game", game);

        return ResponseEntity.ok(result);
    }

    @Override
    @Transactional
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Optional<Game> getGame(String id) {
        return gameRepository.findById(id);
    }
}
