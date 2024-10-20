package terahouska.pokrsi2.game;

import terahouska.pokrsi2.game.card.Card;
import terahouska.pokrsi2.game.card.Number;
import terahouska.pokrsi2.game.card.QueenCard;
import terahouska.pokrsi2.game.card.Suit;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    public static void main(String[] args) {
        Game game = new Game(3, 4, 1);
        game.start();
    }
    private final Queue<Card> deque;
    private Card table;
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

        printBoard();
    }

    /**
     * game loop
     */
    public void start() {
        boolean end = false;
        while (!end) { // round
            Player player = players.getFirst(); // current player
            Card chosenCard = choosePriorityCard(getCardOptions(player));
            if (chosenCard != null) {
                playCard(player, chosenCard);
            } else drawCard(player);
            printBoard();
            end = winCheck();
        }
        printWinners();
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

    private Card choosePriorityCard(Set<Card> options) {
        return options.stream()
                .filter(card -> Arrays.stream(Card.getNoEffectNumbers()).anyMatch(number -> number.equals(card.getNumber())))
                .findFirst().orElse(
                        options.stream()
                                .filter(card -> Arrays.stream(Card.getLowEffectNumbers()).anyMatch(number -> number.equals(card.getNumber())))
                                .findFirst().orElse(
                                        options.stream()
                                                .filter(card -> Arrays.stream(Card.getHighEffectNumbers()).anyMatch(number -> number.equals(card.getNumber())))
                                                .findFirst().orElse(null)
                                )
                );
    }

    private void playCard(Player player, Card card) {
        try {
            player.getCards().remove(card);
            deque.add(table);
            table = card;
        } catch (Exception ignored) {}
    }
    //TODO: play 2, Q, K

    /**
     * Loops through players,
     * removes all players with no cards from players,
     * adds them to winners,
     * adds last player to winners if alone
     * @return true if no players left
     */
    private boolean winCheck() {
        List<Player> winPlayers =
                players.stream().filter(player -> player.getCards().isEmpty()).toList();
        winPlayers.forEach(p -> {
            players.remove(p);
            winners.add(p);
        });
        if (players.size() == 1) {
            winners.add(players.removeFirst());
        }
        return players.isEmpty();
    }

    private void printBoard() {
        System.out.println("Deque: " + deque);
        System.out.println("Deque size: " + deque.size());
        System.out.println("Table: " + table);
        players.forEach(p -> System.out.println("player: " + p.getCards()));
        System.out.println();
    }

    private void printWinners() {
        int i = 1;
        for (Player w : winners) {
            System.out.println(i + ". " + w.toString());
            i++;
        }
    }
    //endregion
}
