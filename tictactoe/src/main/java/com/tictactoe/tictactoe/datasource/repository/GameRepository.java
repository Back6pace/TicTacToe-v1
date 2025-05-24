package com.tictactoe.tictactoe.datasource.repository;

import com.tictactoe.tictactoe.datasource.mapper.TicTacToeConverter;
import com.tictactoe.tictactoe.datasource.model.TicTacToeDTO;
import com.tictactoe.tictactoe.di.aspects.ToLog;
import com.tictactoe.tictactoe.domain.model.TicTacToe;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository implements GameRepositoryInF {

    private final ConcurrentHashMap<UUID, TicTacToeDTO> games;

    public GameRepository() {
        this.games = new ConcurrentHashMap<>();
    }

    @ToLog
    @Override
    public TicTacToe findGameById(UUID gameId) {
        return TicTacToeConverter.toTicTacToe(games.get(gameId));
    }

    @ToLog
    @Override
    public void saveGame(TicTacToe game) {
        TicTacToeDTO gameDTO = TicTacToeConverter.toTicTacToeDTO(game);
        games.put(gameDTO.getGameId(), gameDTO);
    }

    @ToLog
    @Override
    public void removeGame(UUID gameId) {
        games.remove(gameId);
    }
}
