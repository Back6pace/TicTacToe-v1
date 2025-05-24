package com.tictactoe.tictactoe.domain.player;

import com.tictactoe.tictactoe.domain.utils.CellPos;
import com.tictactoe.tictactoe.domain.utils.PlayerType;

public class Player {
    private CellPos cellPos;
    private PlayerType playerType;

    public CellPos getCellPos() {
        return cellPos;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setCellPos(CellPos cellPos) {
        this.cellPos = cellPos;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }
}
