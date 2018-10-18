package ddsjava.integration;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class DDDealPBN extends Structure {
    public int trump;

    public int first;

    public int[] currentTrickSuit = new int[3];

    public int[] currentTrickRank = new int[3];

    public byte[] remainCards = new byte[80];

    public static class ByValue extends DDDealPBN implements Structure.ByValue {}

    public static class ByReference extends DDDealPBN implements Structure.ByReference {}

    public DDDealPBN() {
        super();
        this.setAlignType(Structure.ALIGN_DEFAULT);
    }

    public DDDealPBN(Pointer pointer) {
        super(pointer);
        setAlignType(Structure.ALIGN_DEFAULT);
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("trump", "first", "currentTrickSuit", "currentTrickRank", "remainCards");
    }
}
