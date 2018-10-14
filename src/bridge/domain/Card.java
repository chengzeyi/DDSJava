package bridge.domain;

/**
 * This class contains the basic information of a card:
 * color, rank, suit, and its player position.
 */
public class Card implements Comparable<Card> {
    private PlayerPosition playerPosition;

    /**
     * Gets the player position of this card.
     * @return The player position of this card.
     */
    public PlayerPosition getPlayerPosition() {
        return playerPosition;
    }

    /**
     * Sets the player position of this card.
     * @param playerPosition The player position of this card.
     */
    public void setPlayerPosition(PlayerPosition playerPosition) {
        this.playerPosition = playerPosition;
    }

    private final Rank rank;

    /**
     * Gets the rank of this card.
     * @return The rank of this card.
     */
    public Rank getRank() {
        return rank;
    }

    private final Suit suit;

    /**
     * Gets the suit of this card.
     * @return The suit of this card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Initializes the card by the given rank and suit.
     * @param rank The given rank.
     * @param suit The given suit.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Initializes the card by the given rankScore and suit.
     * @param rankScore The given rank score.
     * @param suit The given suit.
     */
    public Card(int rankScore, Suit suit) {
        this.rank = new Rank(rankScore);
        this.suit = suit;
    }

    /**
     * Gets the color of this card.
     * If the suit of this card is spades or clubs,
     * the color is black.
     * Else the color is red.
     * @return The color of this card.
     */
    public CardColor getColor() {
        if (suit.equals(Suit.SPADES) || suit.equals(Suit.CLUBS)) {
            return CardColor.BLACK;
        } else {
            return CardColor.RED;
        }
    }

    /**
     * Compares this and another card by the scores of
     * this card's rank and another card's rank.
     * @param o The another card to compare with.
     * @return If its is greater than other's, returns 1.
     * Else if its is less than other's, returns -1.
     * Else returns 0.
     */
    @Override
    public int compareTo(Card o) {
        int value1 = rank.getScore();
        int value2 = o.rank.getScore();

        return Integer.compare(value1, value2);
    }

    /**
     * Judges whether this card equals to another object.
     * @param obj The another object.
     * @return True if their suit and rank equal.
     * Else false.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card && ((Card) obj).suit.equals(suit) && ((Card) obj).rank.equals(rank);
    }

    /**
     * Gets the string representation of this card.
     * It is just the card's rank joints its suit.
     * @return The string representation of this card.
     */
    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }

    /**
     * Gets the hash code of this card.
     * The hash code is calculated as followings:
     * int hashCode = (playerPosition != null ? playerPosition.hashCode() : 0);
     * hashCode = (hashCode * 397) ^ (rank != null ? rank.hashCode() : 0);
     * hashCode = (hashCode * 397) ^ (rank != null ? rank.hashCode() : 0);
     * @return The hash code of the card.
     */
    @Override
    @SuppressWarnings("unchecked")
    public int hashCode() {
        int hashCode = (playerPosition != null ? playerPosition.hashCode() : 0);
        hashCode = (hashCode * 397) ^ (rank != null ? rank.hashCode() : 0);
        hashCode = (hashCode * 397) ^ (rank != null ? rank.hashCode() : 0);
        return hashCode;
    }
}
