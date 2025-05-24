package com.tictactoe.tictactoe.domain;

import com.tictactoe.tictactoe.datasource.repository.GameRepository;
import com.tictactoe.tictactoe.domain.bot.Bot;
import com.tictactoe.tictactoe.domain.model.TicTacToe;
import com.tictactoe.tictactoe.domain.player.Player;
import com.tictactoe.tictactoe.domain.service.GameService;
import com.tictactoe.tictactoe.domain.utils.CellPos;
import com.tictactoe.tictactoe.domain.utils.GameMode;
import com.tictactoe.tictactoe.domain.utils.PlayerType;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameRepository gameRepository = new GameRepository();
        GameService gameService = new GameService(gameRepository);
        TicTacToe newGame = gameService.createNewGame(GameMode.PvB);
        Player player = new Player();
        while (!gameService.isGameOver(newGame)) {
            if (newGame.getCurrentType() == PlayerType.PLAYER1) {
                System.out.println("Player turn");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                player.setCellPos(new CellPos(row, col));
                newGame = gameService.getNextMove(newGame, player);
            } else {
                System.out.println("Bot turn");
                newGame = gameService.getNextMove(newGame, player);
            }
            printMoveEvaluations(Bot.getMoveEvaluations());
            printBoard(newGame);
        }
        PlayerType playerType = checkWhoWin(newGame.getBoard());
        if (playerType == PlayerType.PLAYER1) {
            System.out.println("Winner is X");
        } else if (playerType == PlayerType.BOT) {
            System.out.println("Winner is O");
        } else {
            System.out.println("Winner is EMPTY");
        }

    }

    public static void printMoveEvaluations(Map<CellPos, Integer> moveEvaluations) {
        if (moveEvaluations == null || moveEvaluations.isEmpty()) {
            System.out.println("No move evaluations available.");
            return;
        }

        System.out.println("Move Evaluations:");
        for (Map.Entry<CellPos, Integer> entry : moveEvaluations.entrySet()) {
            CellPos cellPos = entry.getKey();
            Integer evaluation = entry.getValue();
            System.out.printf("  Position: (%d, %d), Evaluation: %d%n",
                    cellPos.getRow(), cellPos.getColumn(), evaluation);
        }
    }

    private static void printBoard(TicTacToe game) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(PlayerType.fromValue(game.getBoard()[i][j]).getSymbol() + " ");
            }
            System.out.println();
        }
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
