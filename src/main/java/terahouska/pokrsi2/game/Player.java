package terahouska.pokrsi2.game;

import terahouska.pokrsi2.game.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> cards;
    private final boolean player;
    private boolean frozen = false;

    public Player(boolean player) {
        this.player = player;
        this.cards = new ArrayList<>();
    }

    //region Getters and Setters

    public List<Card> getCards() {
        return cards;
    }

    public boolean isPlayer() {
        return player;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    //endregion

}
