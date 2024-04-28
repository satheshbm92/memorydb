package comp.Box.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck(){
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck(){
        String[] suits = {"Hearts", "Diamond", "Clubs", "Spades"};
        String[] ranks = {"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};

        for(String suit: suits){
            for(String rank: ranks){
                cards.add(new Card(suit, rank));
            }
        }
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card dealCard(){
        if(!cards.isEmpty()){
            return cards.remove(cards.size() -1);
        }else{
            return null;
        }
    }

}
