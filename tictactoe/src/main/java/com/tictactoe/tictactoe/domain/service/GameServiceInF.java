package com.tictactoe.tictactoe.domain.service;

import com.tictactoe.tictactoe.domain.model.TicTacToe;
import com.tictactoe.tictactoe.domain.player.Player;
import java.util.UUID;

public interface GameServiceInF {
    TicTacToe getNextMove(TicTacToe game, Player player);

    boolean isBoardValid(TicTacToe game);

    boolean isGameOver(TicTacToe game);
}
