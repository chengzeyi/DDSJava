package bridge.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * The suit of the card.
 * There are four types in total: spades, hearts, diamonds and clubs.
 * Also each one has its unique order, short name and full name.
 */
public class Suit {
    public static final Suit SPADES = new Suit(0, 'S', "Spades");
    public static final Suit HEARTS = new Suit(1, 'H', "Hearts");
    public static final Suit DIAMONDS = new Suit(2, 'D', "Diamonds");
    public static final Suit CLUBS = new Suit(3, 'C', "Clubs");
    public static final Suit NO_TRUMP = new Suit(4, 'N', "NoTrump");

    public static final List<Suit> SUITS = Arrays.asList(SPADES, HEARTS, DIAMONDS, CLUBS);

    private final int order;

    /**
     * Gets the order of the suit.
     * @return The order of the suit.
     */
    public int getOrder() {
        return order;
    }

    private final char shortName;

    /**
     * Gets the short name of the suit.
     * @return The short name of the suit.
     */
    public char getShortName() {
        return shortName;
    }

    private final String fullName;

    /**
     * Gets the full name of the suit.
     * @return The full name of the suit.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Initializes the suit by an existing suit type that has the same order.
     * @param order The order for selecting a suit type.
     */
    public Suit(int order) {
        this(SUITS.stream().filter(suit -> suit.order == order).findAny().orElse(null));
    }

    public Suit(char shortName) {
        this(SUITS.stream().filter(suit -> suit.shortName == shortName).findAny().orElse(null));
    }

    /**
     * Initializes the suit by the given suit.
     * @param suit The given suit.
     */
    private Suit(Suit suit) {
        this(suit.order, suit.shortName, suit.fullName);
    }

    /**
     * Initializes the suit with given information.
     * @param order The order of this suit.
     * @param shortName The short name of this suit.
     * @param fullName The full name of this suit.
     */
    private Suit(int order, char shortName, String fullName) {
        this.order = order;
        this.shortName = shortName;
        this.fullName = fullName;
    }

    /**
     * Judges whether the object is the same as this.
     * @param obj The object to compare with.
     * @return True if the object has the same order of this.
     * Else false.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Suit && ((Suit) obj).order == order;
    }

    /**
     * Returns the string representation of the suit.
     * For shades type, returns ♠.
     * For hearts type, returns ♥.
     * For clubs type, returns ♣.
     * For diamonds type, returns ♦.
     * For other types, returns the short name of it.
     * @return The string representation of the suit.
     */
    @Override
    public String toString() {
        switch (shortName)
        {
            case 'S': return "♠";
            case 'H': return "♥";
            case 'C': return "♣";
            case 'D': return "♦";
            default: return Character.toString(shortName);
        }
    }

    /**
     * Gets the hash code of the suit.
     * Hash code equals to (score * 397) ^ fullName.hashCode().
     * @return The hash code of the suit.
     */
    @Override
    @SuppressWarnings("unchecked")
    public int hashCode() {
        int hashCode = order;
        hashCode = (hashCode * 397) ^ (fullName != null ? fullName.hashCode() : 0);
        return hashCode;
    }
}
