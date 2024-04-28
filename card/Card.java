package comp.Box.card;

/*
Represents a single playing card.
Each card has a suit (Hearts, Diamonds, Clubs, Spades) and a rank (Ace, 2, 3, ..., 10, Jack, Queen, King).
Methods can include a way to retrieve the card's image or value if needed.
 */
public class Card {

    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit='" + suit + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
