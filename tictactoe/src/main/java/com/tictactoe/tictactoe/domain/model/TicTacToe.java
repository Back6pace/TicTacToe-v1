package com.tictactoe.tictactoe.domain.model;

import com.tictactoe.tictactoe.di.aspects.ToLog;
import com.tictactoe.tictactoe.domain.utils.GameMode;
import com.tictactoe.tictactoe.domain.utils.PlayerType;
import java.util.UUID;

public class TicTacToe {
    private int[][] board;
    private UUID gameId;
    private GameMode mode;
    private PlayerType currentType;
    private String winner;

    public TicTacToe(GameMode mode) {
        this.board = new int[3][3];
        this.gameId = UUID.randomUUID();
        this.mode = mode;
    }

    public int[][] getBoard() {
        return board;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public void setGameId(UUID id) {
        this.gameId = id;
    }

    public GameMode getMode() {
        return mode;
    }

    public PlayerType getCurrentType() {
        return currentType;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public void setCurrentType(PlayerType currentType) {
        this.currentType = currentType;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
