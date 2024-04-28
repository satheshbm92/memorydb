package comp.Box.card;
import java.util.*;

public abstract class Game {
    protected List<Player> players;
    protected Deck deck;

    public Game() {
        this.players = new ArrayList<>();
        this.deck = new Deck();
        this.deck.shuffle();
    }

    abstract void startGame();
    abstract void endGame();

}
