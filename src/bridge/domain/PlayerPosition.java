package bridge.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerPosition {
    public static final PlayerPosition NORTH = new PlayerPosition(0, 1, "North");
    public static final PlayerPosition EAST = new PlayerPosition(1, 2, "East");
    public static final PlayerPosition SOUTH = new PlayerPosition(2, 3, "South");
    public static final PlayerPosition WEST = new PlayerPosition(3, 0, "West");

    public static final List<PlayerPosition> PLAYERS = Arrays.asList(NORTH, EAST, SOUTH, WEST);

    private final int order;

    /**
     * Gets the order to this player position.
     * @return The order to this player position.
     */
    public int getOrder() {
        return order;
    }

    private final int pbnIndex;

    /**
     * Gets the pbn index of this player position.
     * @return The pbn index of this player position.
     */
    public int getPbnIndex() {
        return pbnIndex;
    }

    private final String fullName;

    /**
     * Gets the full name of this player position.
     * @return The full name of this player position.
     */
    public String getFullName() {
        return fullName;
    }

    private final char firstLetter;

    /**
     * Gets the first letter of its full name.
     * @return The first letter of its full name.
     */
    public char getFirstLetter() {
        return firstLetter;
    }

    /**
     * Initialzes the player position by the existing player position type that
     * has the same order.
     * @param order The given order.
     */
    public PlayerPosition(int order) {
        this(Objects.requireNonNull(PLAYERS.stream().filter(playerPosition -> playerPosition.order == order).findAny().orElse(null)));
    }

    /**
     * Initializes the player position by the existing player position type that
     * has the same first letter of its full name.
     * @param firstLetter The given first letter of its full name.
     */
    public PlayerPosition(char firstLetter) {
        this(Objects.requireNonNull(PLAYERS.stream().filter(playerPosition -> playerPosition.firstLetter == firstLetter).findAny().orElse(null)));
    }

    /**
     * Initializes the player position by the given player position.
     * @param playerPosition The given player position.
     */
    private PlayerPosition(PlayerPosition playerPosition)
    {
        this(playerPosition.order, playerPosition.pbnIndex, playerPosition.fullName);
    }

    /**
     * Initializes the player position by the given order, pbn index and full name.
     * @param order The given order.
     * @param pbnIndex The given pbn index.
     * @param fullName The given full name.
     */
    private PlayerPosition(int order, int pbnIndex, String fullName)
    {
        this.order = order;
        this.pbnIndex = pbnIndex;
        this.fullName = fullName;
        this.firstLetter = fullName.charAt(0);
    }

    /**
     * Judges whether the object equals to this.
     * @param obj The object to compare.
     * @return True if the object equals to this, else false.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerPosition && ((PlayerPosition) obj).order == order;
    }

    /**
     * Returns the string representation of the player position and
     * in fact, just the full name.
     * @return The full name of the player position.
     */
    @Override
    public String toString() {
        return fullName;
    }

    /**
     * Returns the hash code of this player position.
     * The hash code equals to (order * 397) ^ fullName.hashCode().
     * @return The hash code of this player position.
     */
    @Override
    @SuppressWarnings("unchecked")
    public int hashCode() {
        int hashCode = order;
        hashCode = (hashCode * 397) ^ (fullName != null ? fullName.hashCode() : 0);
    }
}
