package za.co.mancala.service;

import za.co.mancala.model.Game;
import za.co.mancala.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class GameService {
    private Map<Integer, Game> gameMap;

    private static int gameId = 0;

    public GameService() {
        this.gameMap = new HashMap<>();
    }

    public Game createGame() {
        Player player1 = new Player("Player 1", 6);
        Player player2 = new Player("Player 2", 6);
        Game game = new Game(player1, player2);
        generateGameId();
        game.setId(gameId);
        gameMap.put(game.getId(), game);
        return game;
    }

    private static void generateGameId() {
        gameId++;
    }

    public Game makeMove(int gameId, int pitIndex) {
        Game game = getGameById(gameId);
        Player currentPlayer = game.getCurrentPlayer();

        if (game.isGameFinished()) {
            throw new IllegalStateException("The game has already finished.");
        }

        if (pitIndex < 0 || pitIndex >= currentPlayer.getPits().size() || currentPlayer.getPits().get(pitIndex) == 0) {
            throw new IllegalArgumentException("Invalid pit index.");
        }

        int stones = currentPlayer.getPits().get(pitIndex);
        currentPlayer.getPits().set(pitIndex, 0);

        int currentIndex = pitIndex;
        int checkIndex = pitIndex;
        Player opponent = getOpponent(game, currentPlayer);
        int indexOpponent = opponent.getPits().size() -1;


        while (stones > 0) {

            currentIndex = (currentIndex + 1) % (currentPlayer.getPits().size() + 1);
            if (indexOpponent < 0) {
                if (checkIndex > currentPlayer.getPits().size() && currentIndex >= currentPlayer.getPits().size() - 1 ){
                    checkIndex = 0;
                    currentIndex = 0;
                }
                if (checkIndex > currentPlayer.getPits().size()){
                    indexOpponent = opponent.getPits().size() -1;
                    continue;
                }

                currentIndex = (currentIndex) % (currentPlayer.getPits().size() + 1);
                currentPlayer.getPits().set(currentIndex, currentPlayer.getPits().get(currentIndex) + 1);
                stones--;
                checkIndex++;
                continue;
            }

            if (stones == 1 && checkIndex == currentPlayer.getPits().size() -1){
                currentPlayer.setBigPit(currentPlayer.getBigPit() + 1);
                checkIndex++;
                break;
            }
            if (stones > 1 && checkIndex == currentPlayer.getPits().size() -1){
                currentPlayer.setBigPit(currentPlayer.getBigPit() + 1);
                checkIndex++;
                stones--;
                continue;
            }
            if (checkIndex >= currentPlayer.getPits().size() - 1){
                opponent.getPits().set(indexOpponent,opponent.getPits().get(indexOpponent) + 1);
                indexOpponent--;
            }else {
                currentPlayer.getPits().set(currentIndex, currentPlayer.getPits().get(currentIndex) + 1);
            }
            stones--;
            checkIndex++;
        }

        if ( currentIndex < 6 && (currentPlayer.getPits().get(currentIndex) == 1 && opponent.getPits().get(currentIndex) > 0)){
            currentPlayer.setBigPit(currentPlayer.getBigPit() + 1 + opponent.getPits().get(currentIndex));
            opponent.getPits().set(currentIndex, 0);
        }
        if (checkIndex == currentPlayer.getPits().size()) {
            game.setCurrentPlayer(currentPlayer);
        }else{
            game.setCurrentPlayer(opponent);
        }

        if (isGameFinished(game)) {
            endGame(game);
        }
        return game;
    }

    public Game getGameById(int gameId) {
        Game game = gameMap.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found.");
        }
        return game;
    }

    private Player getOpponent(Game game, Player currentPlayer) {
        return currentPlayer == game.getPlayer1() ? game.getPlayer2() : game.getPlayer1();
    }

    private boolean isGameFinished(Game game) {
        return game.getPlayer1().getPits().stream().allMatch(p -> p == 0) || game.getPlayer2().getPits().stream().allMatch(p -> p == 0);
    }

    private void endGame(Game game) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();

        for (int i = 0; i < player1.getPits().size(); i++) {
            player1.setBigPit(player1.getBigPit() + player1.getPits().get(i));
            player1.getPits().set(i, 0);
        }

        for (int i = 0; i < player2.getPits().size(); i++) {
            player2.setBigPit(player2.getBigPit() + player2.getPits().get(i));
            player2.getPits().set(i, 0);
        }
        if (player1.getBigPit() > player2.getBigPit()){
            game.setWinner("Player1");
        } else if (player2.getBigPit() > player1.getBigPit()) {
            game.setWinner("Player2");
        } else {
            game.setWinner("Is A Draw");
        }
        game.setGameFinished(true);
    }

}
