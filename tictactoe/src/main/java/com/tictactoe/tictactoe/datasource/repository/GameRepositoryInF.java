package com.tictactoe.tictactoe.datasource.repository;

import com.tictactoe.tictactoe.domain.model.TicTacToe;
import java.util.UUID;

public interface GameRepositoryInF {
    TicTacToe findGameById(UUID gameId);

    void saveGame(TicTacToe game);

    void removeGame(UUID gameId);
}
