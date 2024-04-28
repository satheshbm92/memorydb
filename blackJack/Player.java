package comp.Box.blackJack;

class Player {
    private Hand hand;

    public Player() {
        hand = new Hand();
    }

    public void hit(Deck deck) {
        hand.addCard(deck.drawCard());
    }

    public boolean stand() {
        return true;
    }

    public Hand getHand() {
        return hand;
    }
}