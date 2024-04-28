package comp.Box.card;
import java.util.*;

/*
Represents a participant in the card game.
Each player has a hand (collection of cards).
Methods for actions like playing a card, drawing a card, or showing hand.
 */
public class Player {

    private List<Card> hand;

    public Player() {
        this.hand = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void drawCard(Deck deck){
        Card card = deck.dealCard();
        if(card !=null) hand.add(card);
    }

    public void playCard(Card card) {
        hand.remove(card);
    }



}
