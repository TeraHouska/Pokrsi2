package terahouska.pokrsi2.game;

import terahouska.pokrsi2.game.card.Card;
import terahouska.pokrsi2.game.card.Number;
import terahouska.pokrsi2.game.card.QueenCard;
import terahouska.pokrsi2.game.card.Suit;

import java.util.*;

public class Game {
    public static void main(String[] args) {
        Game game = new Game(3, 4, 1);
        game.start();
    }
    private final Queue<Card> deque;
    private final Card table;
    private final List<Player> players;
    private final List<Player> winners = new ArrayList<>();

    public Game(int playerCount, int cardsPerPlayer, int playerNumber) {
        // create cards and add them to the deque
        List<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Number number : Number.values()) {
                if (number.equals(Number.QUEEN))
                    cards.add(new QueenCard(number, suit));
                else
                    cards.add(new Card(number, suit));
            }
        }
        Collections.shuffle(cards);
        this.deque = new LinkedList<>(cards);

        // create players and give them cards
        this.players = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            Player player = new Player(i == playerNumber - 1);
            for (int j = 0; j < cardsPerPlayer; j++) {
                player.getCards().add(deque.poll());
            }
            players.add(player);
        }

        table = deque.poll();
        assert table != null;
        table.setActive(true);

        System.out.println(deque);
        System.out.println(deque.size());
        System.out.println(table);
        players.forEach(p -> System.out.println(p.getCards()));
    }

    /**
     * game loop
     */
    public void start() {
        boolean end = false;
        while (!end) {
            Player player = players.getFirst(); // current player
            System.out.println(getCardOptions(player));
            end = true;
        }
    }

    //region Private methods
    private void drawCard(Player player) {
        player.getCards().add(deque.poll());
    }

    private void drawCard(Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            player.getCards().add(deque.poll());
        }
    }

    /**
     * Checks if last turn was played ACE, SEVEN or QUEEN therefore having a lasting effect
     * @return boolean whether there is active A|7|Q on the table
     */
    private boolean laterEffectCardPlayed() {
        return (table.isActive() && (
                        table.getNumber().equals(Number.ACE) ||
                        table.getNumber().equals(Number.SEVEN) ||
                        table.getNumber().equals(Number.QUEEN)
                ));
    }

    private Set<Card> getCardOptions(Player player) {
        Set<Card> options = new HashSet<>();
        for (Card card : player.getCards()) {
            if (laterEffectCardPlayed()) {
                if (card.getNumber().equals(table.getNumber())) {
                    options.add(card);
                }
                if (table instanceof QueenCard) {
                    if (card.getSuit().equals(((QueenCard) table).getChangedSuit()))
                        options.add(card);
                }
            } else {
                if (card.getNumber().equals(table.getNumber()) ||
                    card.getNumber().equals(Number.QUEEN) ||
                    card.getSuit().equals(table.getSuit())) {
                    options.add(card);
                }
            }
            if (card.getNumber().equals(Number.THREE))
                options.add(card);
        }
        return options;
    }
    //endregion
}
