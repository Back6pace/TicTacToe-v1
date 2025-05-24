package com.tictactoe.tictactoe.web;

import com.tictactoe.tictactoe.datasource.mapper.TicTacToeConverter;
import com.tictactoe.tictactoe.datasource.model.TicTacToeDTO;
import com.tictactoe.tictactoe.domain.model.TicTacToe;
import com.tictactoe.tictactoe.domain.player.Player;
import com.tictactoe.tictactoe.domain.service.GameService;
import com.tictactoe.tictactoe.domain.utils.CellPos;
import com.tictactoe.tictactoe.domain.utils.GameMode;
import com.tictactoe.tictactoe.web.mapper.WebConverter;
import com.tictactoe.tictactoe.web.model.WebModel;
import java.util.UUID;

public class WebService {
  public WebModel startGame(GameService service, GameMode gameMode) {
    TicTacToe game = service.createNewGame(gameMode);
    TicTacToeDTO dto = TicTacToeConverter.toTicTacToeDTO(game);
    return WebConverter.toWeb(dto);
  }

  public WebModel makeMove(WebModel model, GameService service, int row, int column) {
    TicTacToeDTO dto = WebConverter.fromWeb(model);
    TicTacToe game = TicTacToeConverter.toTicTacToe(dto);
    Player player = new Player();
    player.setCellPos(new CellPos(row, column));
    TicTacToe updatedGame = service.getNextMove(game, player);
    if(game.getMode() == GameMode.PvB){
      updatedGame = service.getNextMove(game, player);
    }
    TicTacToeDTO updatedDto = TicTacToeConverter.toTicTacToeDTO(updatedGame);
    return WebConverter.toWeb(updatedDto);
  }

  public WebModel findById(String id, GameService service) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
      TicTacToe findGame = service.getGameRepository().findGameById(uuid);
      TicTacToeDTO updatedDto = TicTacToeConverter.toTicTacToeDTO(findGame);
      return WebConverter.toWeb(updatedDto);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid gameId format: " + id);
    }
  }
}
