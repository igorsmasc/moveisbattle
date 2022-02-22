package com.letscode.moveisbattle;

import com.letscode.moveisbattle.controller.GameController;
import com.letscode.moveisbattle.controller.RegistrationController;
import com.letscode.moveisbattle.controller.UserStatusController;
import com.letscode.moveisbattle.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MoveisbattleApplicationTests {

    // Controllers

    @Autowired
    private GameController gameController;

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private UserStatusController userStatusController;

    // Services

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private GameService gameService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserStatusService userStatusService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(gameController);
        Assertions.assertNotNull(registrationController);
        Assertions.assertNotNull(userStatusController);

        Assertions.assertNotNull(confirmationTokenService);
        Assertions.assertNotNull(gameService);
        Assertions.assertNotNull(movieService);
        Assertions.assertNotNull(questionService);
        Assertions.assertNotNull(registrationService);
        Assertions.assertNotNull(userService);
        Assertions.assertNotNull(userStatusService);
    }

    @Test
    public void applicationContextTest() {
        MoveisbattleApplication.main(new String[]{});
    }

}
