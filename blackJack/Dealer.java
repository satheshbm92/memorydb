package comp.Box.blackJack;

class Dealer {
    private Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    public void play(Deck deck) {
        while (hand.getValue() < 17) {
            hand.addCard(deck.drawCard());
        }
    }

    public Hand getHand() {
        return hand;
    }
}