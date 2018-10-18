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
    
    public Contract(String contract) {
        playerPosition = new PlayerPosition(contract.charAt(0));
        value = Integer.parseInt(Character.toString(contract.charAt(1)));
        trump = new Trump(contract.charAt(2));
    }

    public Contract() {
        trump = Trump.NO_TRUMP;
    }

    public String getShortString()
    {
        return playerPosition.getFirstLetter() + Integer.toString(value) + trump.getShortName();
    }

    @Override
    public String toString() {
        return playerPosition + ":" + Integer.toString(value) + trump;
    }
}
