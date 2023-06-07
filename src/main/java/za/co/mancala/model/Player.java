package za.co.mancala.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Player {
    private String id;
    private List<Integer> pits;
    private int bigPit;

    public Player(String id, int pitCount) {
        this.id = id;
        this.pits = new ArrayList<>(Collections.nCopies(pitCount, 6));
        this.bigPit = 0;
    }
}
