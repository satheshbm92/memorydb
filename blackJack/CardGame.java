package comp.Box.blackJack;

import java.util.ArrayList;
import java.util.List;

abstract class CardGame {
    protected Deck deck;
    protected List<Player> players;

    public CardGame() {
        deck = new Deck();
        players = new ArrayList<>();
    }

    public void play() {
        deck.shuffle();
        initializeHands();
        playGame();
        determineWinner();
    }

    protected abstract void initializeHands();

    protected abstract void playGame();

    protected abstract void determineWinner();
}