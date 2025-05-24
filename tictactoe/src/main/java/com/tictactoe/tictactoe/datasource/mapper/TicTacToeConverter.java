package com.tictactoe.tictactoe.datasource.mapper;

import com.tictactoe.tictactoe.datasource.model.TicTacToeDTO;
import com.tictactoe.tictactoe.domain.model.TicTacToe;
import com.tictactoe.tictactoe.domain.utils.GameMode;
import com.tictactoe.tictactoe.domain.utils.PlayerType;
import java.util.Arrays;

public class TicTacToeConverter {
  public static TicTacToeDTO toTicTacToeDTO(TicTacToe game) {
    if (game == null) {
      throw new IllegalArgumentException("Game object cannot be null.");
    }

    TicTacToeDTO dto = new TicTacToeDTO();
    dto.setBoard(deepCopyBoard(game.getBoard()));
    dto.setGameId(game.getGameId());
    dto.setWinner(game.getWinner());

    switch (game.getMode()) {
      case PvB -> dto.setGameMode("PvB");
      case PvP -> dto.setGameMode("PvP");
      default -> throw new IllegalArgumentException("Unknown GameMode: " + game.getMode());
    }

    switch (game.getCurrentType()) {
      case BOT -> dto.setPlayerType("Bot");
      case PLAYER1 -> dto.setPlayerType("Player1");
      case PLAYER2 -> dto.setPlayerType("Player2");
      default -> throw new IllegalArgumentException("Unknown PlayerType: " + game.getCurrentType());
    }

    return dto;
  }


  public static TicTacToe toTicTacToe(TicTacToeDTO dto) {
    if (dto == null) {
      throw new IllegalArgumentException("DTO object cannot be null.");
    }

    if (dto.getGameMode() == null || dto.getPlayerType() == null || dto.getBoard() == null || dto.getGameId() == null) {
      throw new IllegalArgumentException("DTO fields cannot be null.");
    }

    GameMode mode;
    switch (dto.getGameMode()) {
      case "PvB" -> mode = GameMode.PvB;
      case "PvP" -> mode = GameMode.PvP;
      default -> throw new IllegalArgumentException("Unknown GameMode: " + dto.getGameMode());
    }

    TicTacToe game = new TicTacToe(mode);
    switch (dto.getPlayerType()) {
      case "Bot" -> game.setCurrentType(PlayerType.BOT);
      case "Player1" -> game.setCurrentType(PlayerType.PLAYER1);
      case "Player2" -> game.setCurrentType(PlayerType.PLAYER2);
      default -> throw new IllegalArgumentException("Unknown PlayerType: " + dto.getPlayerType());
    }

    game.setBoard(deepCopyBoard(dto.getBoard()));
    game.setGameId(dto.getGameId());
    game.setWinner(dto.getWinner());

    return game;
  }

  private static int[][] deepCopyBoard(int[][] board) {
    int[][] copy = new int[board.length][];
    for (int i = 0; i < board.length; i++) {
      copy[i] = Arrays.copyOf(board[i], board[i].length);
    }
    return copy;
  }
}
