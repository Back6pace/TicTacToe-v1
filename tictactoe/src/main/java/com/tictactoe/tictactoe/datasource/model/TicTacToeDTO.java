package com.tictactoe.tictactoe.datasource.model;

import java.util.UUID;

public class TicTacToeDTO {
    private UUID gameId;
    private int[][] board;
    private String gameMode;
    private String playerType;
    private String winner;

    public UUID getGameId() {
        return gameId;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
