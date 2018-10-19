package ddsjava;

import java.util.Dictionary;
import java.util.Hashtable;

public class DDSException extends Exception {
    private static final Dictionary<Integer, String> errorCodeMessageMapping = new Hashtable<Integer, String>() {
        {
            put(-1, "Unknown fault");
            put(-2, "Zero cards");
            put(-3, "target > Number of tricks left");
            put(-4, "Duplicated cards");
            put(-5, "target < -1");
            put(-7, "target > 13");
            put(-8, "solutions < 1");
            put(-9, "solutions > 3");
            put(-10, "No of cards > 52");
            put(-11, "Not used");
            put(-12, "Suit or rank value out of range for deal.currentTrickSuit or deal.currentTrickRank");
            put(-13, "Card already played in the current trick is also defined as a remaining card to play");
            put(-14, "Wrong number of remaining cards for a hand");
            put(-15, "threadIndex < 0 or >= noOfThreads, noOfThreads is the configured maximum number of threads");
            put(-16, "mode is less than 0");
            put(-17, "mode is greater than 2");
            put(-18, "trump is not one of 0, 1, 2, 3, 4");
            put(-19, "first is not one of 0, 1, 2");
            put(-98, "AnalysePlay*() family of functions. (a) Less than 0 or more than 52 cards supplied.  (b) Invalid suit or rank supplied. (c) A played card is not held by the right player.");
            put(-99, "Faulty PBN string");
            put(-101, "Too many threads");
            put(-102, "Failed to create thread");
            put(-103, "Failed to wait for all threads to complete");
            put(-201, "No suit");
            put(-202, "Too many tables");
            put(-301, "Chunk size is less than 1");
        }
    };

    private final int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public DDSException(int code)
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
