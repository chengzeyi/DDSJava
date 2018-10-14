package bridge.domain;

public class Trick {
    private PlayerPosition trickDealer;

    public PlayerPosition getTrickDealer() {
        return trickDealer;
    }

    public void setTrickDealer(PlayerPosition trickDealer) {
        this.trickDealer = trickDealer;
    }

    private Deck deck;

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    private PlayerPosition trickWinner;

    public PlayerPosition getTrickWinner() {
        return trickWinner;
    }

    public void setTrickWinner(PlayerPosition trickWinner) {
        this.trickWinner = trickWinner;
    }

    public Suit getTrickDealerSuit() {
        return deck.getBottomCard().getSuit();
    }

    public Trick() {
        deck = new Deck();
    }
}
