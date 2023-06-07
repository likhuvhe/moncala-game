package za.co.mancala.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {
    private int id;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameFinished;
    private String winner;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.gameFinished = false;
    }

}