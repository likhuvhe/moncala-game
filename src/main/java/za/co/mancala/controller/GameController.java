package za.co.mancala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import za.co.mancala.model.Game;
import za.co.mancala.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

@GetMapping()
    public String getGame() {
        return "index";
    }

    @GetMapping("/{gameId}")
    public String getGame(@PathVariable int gameId, Model model) {
        Game game = gameService.getGameById(gameId);
        model.addAttribute("game", game);
        return "game";
    }


    @PostMapping("/create")
    public ResponseEntity<Game> createGame() {
        return ResponseEntity.ok(gameService.createGame());
    }

    @PostMapping("/move/{gameId}/{pitIndex}")
    public ResponseEntity<Game> makeMove(@PathVariable int gameId, @PathVariable int pitIndex) {
        return ResponseEntity.ok(gameService.makeMove(gameId, pitIndex));
    }

}