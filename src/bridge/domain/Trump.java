package bridge.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Trump {
    public static final Trump SPADES = new Trump(Suit.SPADES);
    public static final Trump HEARTS = new Trump(Suit.HEARTS);
    public static final Trump DIAMONDS = new Trump(Suit.DIAMONDS);
    public static final Trump CLUBS = new Trump(Suit.CLUBS);
    public static final Trump NO_TRUMP = new Trump(Suit.NO_TRUMP);

    public static final List<Trump> TRUMPS = Arrays.asList(SPADES, HEARTS, DIAMONDS, CLUBS, NO_TRUMP);

    private final Suit suit;

    /**
     * Gets the suit of this trump.
     * @return The suit of this trump.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Gets the order of the suit of this trump.
     * @return The order of the suit of this trump.
     */
    public int getOrder() {
        return suit.getOrder();
    }

    /**
     * Gets the short name of the suit of this trump.
     * @return The short name of the suit of this trump.
     */
    public char getShortName() {
        return suit.getShortName();
    }

    /**
     * Gets the full name of the suit of this trump.
     * @return The full name of the suit of this trump.
     */
    public String getFullName() {
        return suit.getFullName();
    }

    /**
     * Initializes the trump to an existing trump that has the same trump.
     * @param order
     */
    public Trump(int order) {
        this(TRUMPS.stream().filter(trump -> trump.getOrder() == order).findAny().orElse(null));
    }

    public Trump(char shortName) {
        this(TRUMPS.stream().filter(trump -> trump.getShortName() == shortName).findAny().orElse(null));
    }

    public Trump(Trump trump)
    {
        this(trump.suit);
    }

    /**
     * Initializes this trump by the given suit.
     * @param suit The given suit.
     */
    private Trump(Suit suit) {
        this.suit = suit;
    }


    /**
     * Judges whether the object equals to this trump.
     * @param obj The object to compare with.
     * @return True if their suits equal.
     * Else false.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Trump && ((Trump) obj).suit.equals(suit);
    }

    /**
     * Gets the string representation of this trump.
     * It is the same as it's suit.toString().
     * @return The string representation of this trump.
     */
    @Override
    public String toString() {
        return suit.toString();
    }

    /**
     * Gets the hash code of this trump.
     * The hash code equals to:
     * (order * 397) ^ fullName.hashCode().
     * @return The hash code of this trump.
     */
    @Override
    @SuppressWarnings("unchecked")
    public int hashCode() {
        String fullName = getFullName();
        int hashCode = getOrder();
        hashCode = (hashCode * 397) ^ (fullName != null ? fullName.hashCode() : 0);
        return hashCode;
    }
}
