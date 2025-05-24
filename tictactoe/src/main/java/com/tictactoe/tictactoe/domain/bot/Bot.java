package com.tictactoe.tictactoe.domain.bot;

import com.tictactoe.tictactoe.domain.utils.CellPos;
import com.tictactoe.tictactoe.domain.utils.PlayerType;
import java.util.HashMap;
import java.util.Map;

public class Bot {

    private static Map<CellPos, Integer> moveEvaluations = new HashMap<>();

    public static CellPos findBestMove(int[][] board) {
        int bestMove = Integer.MIN_VALUE;
        CellPos bestMovePos = null;

        moveEvaluations.clear();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == PlayerType.EMPTY.getValue()) {
                    board[i][j] = PlayerType.BOT.getValue();
                    int score = minimax(board, 0, false);
                    board[i][j] = PlayerType.EMPTY.getValue();

                    CellPos move = new CellPos(i, j);
                    moveEvaluations.put(move, score);
                    if (score > bestMove) {
                        bestMove = score;
                        bestMovePos = move;
                    }
                }
            }
        }
        return bestMovePos;
    }

    private static int minimax(int[][] board, int depth, boolean isMaximizing) {
        PlayerType winner = checkWhoWin(board);
        if (winner == PlayerType.BOT)
            return 10 - depth;
        if (winner == PlayerType.PLAYER1)
            return depth - 10;
        if (isBoardFull(board))
            return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == PlayerType.EMPTY.getValue()) {
                        board[i][j] = PlayerType.BOT.getValue();
                        int score = minimax(board, depth + 1, false);
                        board[i][j] = PlayerType.EMPTY.getValue();
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == PlayerType.EMPTY.getValue()) {
                        board[i][j] = PlayerType.PLAYER1.getValue();
                        int score = minimax(board, depth + 1, true);
                        board[i][j] = PlayerType.EMPTY.getValue();
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
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

    private static boolean isBoardFull(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == PlayerType.EMPTY.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Map<CellPos, Integer> getMoveEvaluations() {
        return moveEvaluations;
    }
}