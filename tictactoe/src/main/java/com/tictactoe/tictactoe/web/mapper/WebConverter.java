package com.tictactoe.tictactoe.web.mapper;

import com.tictactoe.tictactoe.datasource.model.TicTacToeDTO;
import com.tictactoe.tictactoe.di.aspects.ToLog;
import com.tictactoe.tictactoe.web.model.WebModel;
import java.util.Arrays;

public class WebConverter {

    @ToLog
    public static WebModel toWeb(TicTacToeDTO dto) {
        WebModel model = new WebModel();
        model.setGameId(dto.getGameId());
        model.setBoard(deepCopyBoard(dto.getBoard()));
        model.setPlayerType(dto.getPlayerType());
        model.setGameMode(dto.getGameMode());
        model.setWinner(dto.getWinner());
        return model;
    }

    @ToLog
    public static TicTacToeDTO fromWeb(WebModel webModel) {
        TicTacToeDTO dto = new TicTacToeDTO();
        dto.setGameId(webModel.getGameId());
        dto.setBoard(deepCopyBoard(webModel.getBoard()));
        dto.setPlayerType(webModel.getPlayerType());
        dto.setGameMode(webModel.getGameMode());
        dto.setWinner(webModel.getWinner());
        return dto;
    }

    private static int[][] deepCopyBoard(int[][] board) {
        int[][] copy = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length);
        }
        return copy;
    }
}
