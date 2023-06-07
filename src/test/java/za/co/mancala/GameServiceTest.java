package za.co.mancala;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import za.co.mancala.model.Game;
import za.co.mancala.model.Player;
import za.co.mancala.service.GameService;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    void testCreateGame() {
        Game game = gameService.createGame();

        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
    }

    @Test
    void testMakeMove() {
        Game game = gameService.createGame();

        int gameId = game.getId();
        Player currentPlayer = game.getCurrentPlayer();
        int initialStonesInPit = currentPlayer.getPits().get(0);

        Game updatedGame = gameService.makeMove(gameId, 0);

        assertNotEquals(initialStonesInPit, currentPlayer.getPits().get(0));
        assertEquals(game.getCurrentPlayer(), currentPlayer);
        assertEquals(game.getWinner(), null);
        assertEquals(game.isGameFinished(), false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.makeMove(gameId, 0);
        });

        assertEquals("Invalid pit index.", exception.getMessage());

        updatedGame = gameService.makeMove(gameId, 2 );

        assertNotEquals(game.getCurrentPlayer(), currentPlayer);
    }
    
}
