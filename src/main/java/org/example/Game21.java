package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Game21 {
    public static void main(String[] args) {
        new Game21(args.length == 0 ? "" : args[0]).play();
    }

    private static final int BLACKJACK = 21;
    private static final int SAM_THRESHOLD = 17;

    private List<String> deck;
    private List<String> samHand;
    private List<String> dealerHand;

    public Game21(String path) {
        initDeck(path);
        this.samHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
    }

    private void initDeck(String path) {
        if (!path.isBlank()) {
            try {
                deck = Files.readAllLines(new File(path).toPath());
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        deck = new ArrayList<>();
        List<String> suits = Arrays.asList("C", "D", "H", "S");
        List<String> values = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
        values.forEach(value -> suits.forEach(suit -> deck.add(suit.concat(value))));
        shuffleDeck();
    }

    public void play() {
        dealCards();

        // Check for blackjack
        if (hasBlackjack(samHand)) {
            printWinner("sam");
            return;
        } else if (hasBlackjack(dealerHand)) {
            printWinner("dealer");
            return;
        }

        // Sam draws cards until their total is 17 or higher
        while (getHandValue(samHand) < SAM_THRESHOLD) {
            samHand.add(drawCard());
            if (getHandValue(samHand) > BLACKJACK) {
                printWinner("dealer");
                return;
            }
        }

        // Dealer draws cards until their total is higher than Sam
        while (getHandValue(dealerHand) <= getHandValue(samHand)) {
            dealerHand.add(drawCard());
            if (getHandValue(dealerHand) > BLACKJACK) {
                printWinner("sam");
                return;
            }
        }

        // Determine winner
        if (getHandValue(samHand) > getHandValue(dealerHand)) {
            printWinner("sam");
        } else {
            printWinner("dealer");
        }
    }

    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    private void dealCards() {
        samHand.add(drawCard());
        dealerHand.add(drawCard());
        samHand.add(drawCard());
        dealerHand.add(drawCard());
    }

    private String drawCard() {
        if (deck.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return deck.remove(0);
    }

    private boolean hasBlackjack(List<String> hand) {
        return getHandValue(hand) == BLACKJACK && hand.size() == 2;
    }

    private int getHandValue(List<String> hand) {
        int value = 0;
        for (String card : hand) {
            switch (card.substring(1)) {
                case "2", "3", "4", "5", "6", "7", "8", "9", "10" -> value += Integer.parseInt(card.substring(1));
                case "J", "Q", "K" -> value += 10;
                case "A" -> value += 11;
                default -> throw new IllegalArgumentException("Invalid card: " + card);
            }
        }
        return value;
    }

    private void printWinner(String winner) {
        System.out.println(winner);
        System.out.println("sam: " + String.join(", ", samHand));
        System.out.println("dealer: " + String.join(", ", dealerHand));
    }

    public List<String> getDeck() {
        return deck;
    }

    public void setDeck(List<String> deck) {
        this.deck = deck;
    }

    public List<String> getSamHand() {
        return samHand;
    }

    public void setSamHand(List<String> samHand) {
        this.samHand = samHand;
    }

    public List<String> getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(List<String> dealerHand) {
        this.dealerHand = dealerHand;
    }
}
