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
    
    public Contract(String contractShortStr) {
        playerPosition = new PlayerPosition(contractShortStr.charAt(0));
        if (contractShortStr.length() == 4) {
            value = Integer.parseInt(contractShortStr.substring(2, 3));
            trump = new Trump(contractShortStr.charAt(3));
        } else {
            value = Integer.parseInt(contractShortStr.substring(2, 4));
            trump = new Trump(contractShortStr.charAt(4));
        }
    }

    public Contract() {
        trump = Trump.NO_TRUMP;
    }

    public String getShortString()
    {
        return playerPosition.getFirstLetter() + ":" + Integer.toString(value) + trump.getShortName();
    }

    @Override
    public String toString() {
        return playerPosition + ":" + Integer.toString(value) + trump;
    }
}
