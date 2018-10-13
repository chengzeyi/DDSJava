package bridge.domain;

import java.util.*;

public class Rank {
    public static final Rank TWO = new Rank(2, '2', "Two");
    public static final Rank THREE = new Rank(3, '3', "Three");
    public static final Rank FOUR = new Rank(4, '4', "Four");
    public static final Rank FIVE = new Rank(5, '5', "Five");
    public static final Rank SIX = new Rank(6, '6', "Six");
    public static final Rank SEVEN = new Rank(7, '7', "Seven");
    public static final Rank EIGHT = new Rank(8, '8', "Eight");
    public static final Rank NINE = new Rank(9, '9', "Nine");
    public static final Rank TEN = new Rank(10, 'T', "Ten");
    public static final Rank JACK = new Rank(11, 'J', "Jack");
    public static final Rank QUEEN = new Rank(12, 'Q', "Queen");
    public static final Rank KING = new Rank(13, 'K', "King");
    public static final Rank ACE = new Rank(14, 'A', "Ace");

    public static final List<Rank> RANKS = Arrays.asList(TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE);

    private final int score;

    /**
     * Gets the score.
     * @return The score.
     */
    public int getScore() {
        return score;
    }
    private char shortName;

    /**
     * Gets the short name.
     * @return The short name.
     */
    public final char getShortName() {
        return shortName;
    }

    private String fullName;

    /**
     * Gets the full name.
     * @return The full name.
     */
    public final String getFullName() {
        return fullName;
    }

    /**
     * Initializes a rank to a defined type by the given score.
     * @param score The given score.
     */
    public Rank(int score) {
        this(Objects.requireNonNull(RANKS.stream().filter(rank -> rank.score == score).findAny().orElse(null)));
    }

    /**
     * Initializes a rank to a defined type by the given shortName.
     * @param shortName The given short name.
     */
    public Rank(char shortName) {
        this(Objects.requireNonNull(RANKS.stream().filter(rank -> rank.shortName == shortName).findAny().orElse(null)));
    }

    public Rank(String fullName) {
        this(Objects.requireNonNull(RANKS.stream().filter(rank -> rank.fullName.equals(fullName)).findAny().orElse(null)));
    }

    /**
     * Initializes a rank with given information of a card type.
     * @param score The score of the card.
     * @param shortName The short name of the card.
     * @param fullName The full name of the card.
     */
    private Rank(int score, char shortName, String fullName) {
        this.score = score;
        this.shortName = shortName;
        this.fullName = fullName;
    }

    /**
     * Initializes a tank with an existing rank.
     * @param rank The referred rank.
     */
    private Rank(Rank rank) {
        this.score = rank.score;
        this.shortName = rank.shortName;
        this.fullName = rank.fullName;
    }

    /**
     * Judges whether the object equals to this.
     * @param obj The object to compare.
     * @return True if their score is the same, else false.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Rank && ((Rank) obj).score == score;
    }

    /**
     * Gets the string representation of the suit.
     * @return The short name of the suit.
     */
    @Override
    public String toString() {
        return Character.toString(shortName);
    }

    /**
     * Gets the hash code of the suit.
     * The formula is hash code = (score * 397) ^ fullName.hashCode().
     * @return The hash code of the suit.
     */
    @Override
    @SuppressWarnings("unchecked")
    public int hashCode() {
        int hashCode = score;
        hashCode = (hashCode * 397) ^ (fullName != null ? fullName.hashCode() : 0);
        return hashCode;
    }
}
