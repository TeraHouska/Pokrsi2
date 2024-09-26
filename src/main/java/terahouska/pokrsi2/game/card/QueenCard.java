package terahouska.pokrsi2.game.card;

public class QueenCard extends Card {

    private Suit changedSuit;

    public QueenCard(Number number, Suit suit) {
        super(number, suit);
    }

    //region Getters and Setters
    public Suit getChangedSuit() {
        return changedSuit;
    }

    public void setChangedSuit(Suit changedSuit) {
        this.changedSuit = changedSuit;
    }
    //endregion
}
