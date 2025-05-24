package com.tictactoe.tictactoe.domain.utils;

public enum PlayerType {
    EMPTY(0, " "),
    PLAYER1(1, "X"),
    PLAYER2(2, "O"),
    BOT(-1, "O");

    private final int value;
    private final String symbol;

    PlayerType(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public static PlayerType fromValue(int value) {
        for (PlayerType type : values()) {
            if (type.value == value)
                return type;
        }
        return EMPTY;
    }
}
