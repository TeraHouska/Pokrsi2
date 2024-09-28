package terahouska.pokrsi2.game.card;

public class Card {
    private final Number number;
    private final Suit suit;
    private boolean active = false;

    public Card(Number number, Suit suit) {
        this.number = number;
        this.suit = suit;
    }

    //region Static methods
    public static Number[] getNoEffectNumbers() {
        return new Number[] {Number.FOUR, Number.FIVE, Number.SIX, Number.EIGHT, Number.NINE, Number.TEN};
    }

    public static Number[] getLowEffectNumbers() {
        return new Number[] {Number.ACE, Number.TWO, Number.SEVEN, Number.JACK};
    }

    public static Number[] getHighEffectNumbers() {
        return new Number[] {Number.THREE, Number.QUEEN, Number.KING};
    }
    //endregion

    //region Getters and Setters

    public Number getNumber() {
        return number;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    //endregion

    @Override
    public String toString() {
        return "{" + number.toString() + "," + suit.toString() + "}";
    }
}
