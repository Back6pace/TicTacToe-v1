package com.tictactoe.tictactoe.web.controller;

import com.tictactoe.tictactoe.di.aspects.ToLog;
import com.tictactoe.tictactoe.domain.service.GameService;
import com.tictactoe.tictactoe.domain.utils.GameMode;
import com.tictactoe.tictactoe.web.WebService;
import com.tictactoe.tictactoe.web.model.WebModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    private final GameService gameService;

    @Autowired
    public WebController(GameService gameService) {
        this.gameService = gameService;
    }

    @ToLog
    @PostMapping("/start")
    public WebModel startGame(@RequestParam String gameMode) {
        GameMode mode = GameMode.valueOf(gameMode);
        WebService webService = new WebService();
        return webService.startGame(gameService, mode);
    }

    @ToLog
    @PostMapping("/move")
    public WebModel move(@RequestBody WebModel model, @RequestParam int row, @RequestParam int column) {
        WebService webService = new WebService();
        return webService.makeMove(model, gameService, row, column);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        // Логируем или обрабатываем исключение
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка на сервере: " + e.getMessage());
    }

}
