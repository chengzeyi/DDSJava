package ddsjava;

import java.util.Dictionary;
import java.util.Hashtable;

public class DDSSolveBoardException extends Exception {
    private static final Dictionary<Integer, String> errorCodeMessageMapping = new Hashtable<Integer, String>() {
        {
            put(-1, "Unknown fault");
            put(-2, "No of cards = 0");
            put(-3, "target > Number of tricks left");
            put(-4, "Duplicated cards");
            put(-5, "target < -1");
            put(-7, "target > 13");
            put(-8, "solutions < 1");
            put(-9, "solutions > 3");
            put(-10, "No of cards > 52");
            put(-11, "Not used");
            put(-12, "Suit or rank value out of range for deal.currentTrickSuit or deal.currentTrickRank");
            put(-13, "Card already player in the current trick is also defined as a remaining card to play");
            put(-14, "Wrong number of remaining cards for a hand");
            put(-15, "threadIndex < 0 or >= noOfThreads, noOfThreads is the configured maximum number of threads");
        }
    };

    private final int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public DDSSolveBoardException(int code)
    {
        if (errorCodeMessageMapping.get(code) == null) {
            errorCode = -1;
        } else {
            errorCode = code;
        }
    }

    @Override
    public String getMessage() {
        return errorCodeMessageMapping.get(errorCode);
    }
}
