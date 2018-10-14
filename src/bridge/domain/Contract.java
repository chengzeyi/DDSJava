package bridge.domain;

public class Contract {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private Trump trump;

    public Trump getTrump() {
        return trump;
    }

    public void setTrump(Trump trump) {
        this.trump = trump;
    }

    private PlayerPosition playerPosition;

    public PlayerPosition getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(PlayerPosition playerPosition) {
        this.playerPosition = playerPosition;
    }

    public Contract(String contract, PlayerPosition declarer) {
        value = Integer.parseInt(Character.toString(contract.charAt(0)));
        playerPosition = declarer;
        trump = new Trump(contract.charAt(1));
    }

    public Contract() {
        trump = Trump.NO_TRUMP;
    }

    @Override
    public String toString() {
        return playerPosition + ":" + Integer.toString(value) + trump;
    }
}
