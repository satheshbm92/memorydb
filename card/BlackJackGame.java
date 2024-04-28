package comp.Box.card;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class BlackJackGame extends Game {
    private Player dealer;  // Represents the dealer, who is also a kind of player but with specific rules.

    public BlackJackGame() {
        super();  // Initializes the deck and shuffles it.
        dealer = new Player();  // Creates a dealer.
        players.add(new Player());  // Adds a single player. This can be expanded to multiple players.
    }

    @Override
    void startGame() {
        System.out.println("Starting Blackjack Game");

        // Deal two cards to each player and the dealer
        for (Player player : players) {
            player.drawCard(deck);
            player.drawCard(deck);
        }
        dealer.drawCard(deck);
        dealer.drawCard(deck);

        // Player's turn
        playTurns();

        // Dealer's turn
        dealerTurn();

        // Determine and announce the winner
        determineWinner();
    }

    @Override
    void endGame() {
        System.out.println("Game Over");
    }

    /**
     * Handles the player's turn.
     * Allows the player to choose between "hit" (drawing a card) or "stand" (ending their turn).
     * If the player's score exceeds 21, they bust and their turn ends.
     */
    private void playTurns() {
        Scanner scanner = new Scanner(System.in);
        for (Player player : players) {
            System.out.println("Player's Hand: " + player.getHand());
            while (true) {
                System.out.println("Hit or Stand? (hit/stand): ");
                String decision = scanner.nextLine();
                if ("hit".equalsIgnoreCase(decision)) {
                    player.drawCard(deck);
                    System.out.println("Player drew: " + player.getHand().get(player.getHand().size() - 1));
                    System.out.println("Player's Hand: " + player.getHand());
                    if (calculateScore(player.getHand()) > 21) {
                        System.out.println("Player busts!");
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Handles the dealer's turn.
     * The dealer keeps drawing cards until their score reaches at least 17.
     * The dealer's final hand is displayed.
     */
    private void dealerTurn() {
        System.out.println("Dealer's Hand: " + dealer.getHand());
        while (calculateScore(dealer.getHand()) < 17) {
            dealer.drawCard(deck);
            System.out.println("Dealer drew: " + dealer.getHand().get(dealer.getHand().size() - 1));
        }
        System.out.println("Dealer's Final Hand: " + dealer.getHand());
    }

    /**
     * Determines the winner based on the scores of the dealer and each player.
     * Compares the scores and announces the result.
     */
    private void determineWinner() {
        int dealerScore = calculateScore(dealer.getHand());
        System.out.println("Dealer's Score: " + dealerScore);
        for (Player player : players) {
            int playerScore = calculateScore(player.getHand());
            System.out.println("Player's Score: " + playerScore);
            if (playerScore > 21) {
                System.out.println("Player busts! Dealer wins.");
            } else if (dealerScore > 21 || playerScore > dealerScore) {
                System.out.println("Player wins!");
            } else if (playerScore < dealerScore) {
                System.out.println("Dealer wins.");
            } else {
                System.out.println("It's a tie!");
            }
        }
    }


    /**
     * Calculates the score of a given hand.
     * Takes into account the special value of the Ace (11 or 1) and the face cards (Jack, Queen, King) being worth 10 points.
     * If the score exceeds 21 and there are Aces in the hand, it adjusts the score by treating the Aces as 1 instead of 11.
     *
     * @param hand The hand to calculate the score for.
     * @return The calculated score of the hand.
     */
    private int calculateScore(List<Card> hand) {
        int score = 0;
        int aceCount = 0;
        for (Card card : hand) {
            if ("Ace".equals(card.getRank())) {
                aceCount++;
                score += 11;
            } else if (Arrays.asList("Jack", "Queen", "King").contains(card.getRank())) {
                score += 10;
            } else {
                score += Integer.parseInt(card.getRank());
            }
        }
        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }
        return score;
    }

    public static void main(String[] args) {
        BlackJackGame game = new BlackJackGame();
        game.startGame();  // This method starts the game
    }
}
