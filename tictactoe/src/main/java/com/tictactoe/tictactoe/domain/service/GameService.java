package com.tictactoe.tictactoe.domain.service;

import com.tictactoe.tictactoe.datasource.repository.GameRepository;
import com.tictactoe.tictactoe.di.aspects.ToLog;
import com.tictactoe.tictactoe.domain.bot.Bot;
import com.tictactoe.tictactoe.domain.model.TicTacToe;
import com.tictactoe.tictactoe.domain.player.Player;
import com.tictactoe.tictactoe.domain.utils.CellPos;
import com.tictactoe.tictactoe.domain.utils.GameMode;
import com.tictactoe.tictactoe.domain.utils.PlayerType;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class GameService implements GameServiceInF {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @ToLog
    public TicTacToe createNewGame(GameMode gameMode) {
        TicTacToe game = new TicTacToe(gameMode);
        fillBoard(game);
        game.setCurrentType(PlayerType.PLAYER1);
        game.setWinner("");
        gameRepository.saveGame(game);
        return game;
    }

    private void fillBoard(TicTacToe game) {
        int[][] board = game.getBoard();
        for (int[] ints : board) {
            Arrays.fill(ints, PlayerType.EMPTY.getValue());
        }
        game.setBoard(board);
    }

    @ToLog
    @Override
    public TicTacToe getNextMove(TicTacToe game, Player player) {
        if (!isBoardValid(game))
            throw new IllegalArgumentException("Invalid board");
        if (isGameOver(game)) {
            game.setWinner(checkWhoWin(game.getBoard()).toString());
            return game;
        }
        TicTacToe previousGame = gameRepository.findGameById(game.getGameId());
        if (previousGame.getGameId().equals(game.getGameId())) {
            if (game.getMode() == GameMode.PvB) {
                    if (game.getCurrentType() == PlayerType.PLAYER1) {
                        CellPos player1Pos = player.getCellPos();
                        if (setCell(game, player1Pos, game.getCurrentType())) {
                            switchPlayerType(game);
                        }
                    } else {
                        CellPos botPos = Bot.findBestMove(game.getBoard());
                        if (setCell(game, botPos, game.getCurrentType())) {
                            switchPlayerType(game);
                        }
                    }
            } else {
                CellPos playerPos = player.getCellPos();
                if (setCell(game, playerPos, player.getPlayerType())) {
                    switchPlayerType(game);
                }
            }
        }
        saveGameAfterTurn(game);
        return game;
    }

    @Override
    public boolean isBoardValid(TicTacToe game) {
        TicTacToe previousGame = gameRepository.findGameById(game.getGameId());
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard().length; j++) {
                if (game.getBoard()[i][j] != previousGame.getBoard()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isGameOver(TicTacToe game) {
        return isBoardFull(game) || hasWinner(game);
    }

    private boolean isBoardFull(TicTacToe game) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.getBoard()[i][j] == PlayerType.EMPTY.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasWinner(TicTacToe game) {
        for (int i = 0; i < 3; i++) {
            if (game.getBoard()[i][0] != PlayerType.EMPTY.getValue()
                    && game.getBoard()[i][0] == game.getBoard()[i][1]
                    && game.getBoard()[i][1] == game.getBoard()[i][2]) {
                return true;
            }

            if (game.getBoard()[0][i] != PlayerType.EMPTY.getValue()
                    && game.getBoard()[0][i] == game.getBoard()[1][i]
                    && game.getBoard()[1][i] == game.getBoard()[2][i]) {
                return true;
            }
        }

        if (game.getBoard()[0][0] != PlayerType.EMPTY.getValue()
                && game.getBoard()[0][0] == game.getBoard()[1][1]
                && game.getBoard()[1][1] == game.getBoard()[2][2]) {
            return true;
        }

        return game.getBoard()[0][2] != PlayerType.EMPTY.getValue()
                && game.getBoard()[0][2] == game.getBoard()[1][1]
                && game.getBoard()[1][1] == game.getBoard()[2][0];
    }

    private boolean setCell(TicTacToe game, CellPos cellPos, PlayerType playerType) {
        int row = cellPos.getRow();
        int column = cellPos.getColumn();

        if (row < 0 || row >= 3 || column < 0 || column >= 3) {
            return false;
        }

        if (game.getBoard()[row][column] == PlayerType.EMPTY.getValue()) {
            game.getBoard()[row][column] = playerType.getValue();
            return true;
        }
        return false;
    }

    private void switchPlayerType(TicTacToe game) {
        if (game.getMode() == GameMode.PvB) {
            if (game.getCurrentType() == PlayerType.PLAYER1) {
                game.setCurrentType(PlayerType.BOT);
            } else {
                game.setCurrentType(PlayerType.PLAYER1);
            }
        } else {
            if (game.getCurrentType() == PlayerType.PLAYER1) {
                game.setCurrentType(PlayerType.PLAYER2);
            } else {
                game.setCurrentType(PlayerType.PLAYER1);
            }
        }
    }

    private void saveGameAfterTurn(TicTacToe game) {
        gameRepository.removeGame(game.getGameId());
        gameRepository.saveGame(game);
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }

    private static PlayerType checkWhoWin(int[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != PlayerType.EMPTY.getValue()
                    && board[i][0] == board[i][1]
                    && board[i][1] == board[i][2]) {
                return PlayerType.fromValue(board[i][0]);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] != PlayerType.EMPTY.getValue()
                    && board[0][i] == board[1][i]
                    && board[1][i] == board[2][i]) {
                return PlayerType.fromValue(board[0][i]);
            }
        }

        if (board[0][0] != PlayerType.EMPTY.getValue()
                && board[0][0] == board[1][1]
                && board[1][1] == board[2][2]) {
            return PlayerType.fromValue(board[0][0]);
        }
        if (board[0][2] != PlayerType.EMPTY.getValue()
                && board[0][2] == board[1][1]
                && board[1][1] == board[2][0]) {
            return PlayerType.fromValue(board[0][2]);
        }

        return PlayerType.EMPTY;
    }
}
