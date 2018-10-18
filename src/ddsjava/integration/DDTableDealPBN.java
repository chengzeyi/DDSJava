package ddsjava.integration;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class DDTableDealPBN extends Structure {
    public byte[] cards = new byte[80];

    public static class ByValue extends DDTableDealPBN implements Structure.ByValue {}

    public static class ByReference extends DDTableDealPBN implements  Structure.ByReference {}

    public DDTableDealPBN() {
        super();
        setAlignType(Structure.ALIGN_DEFAULT);
    }

    public DDTableDealPBN(Pointer pointer) {
        super(pointer);
        setAlignType(Structure.ALIGN_DEFAULT);
    }

    public void setCards(byte[] pbnCards) {
        for (int i = 0; i < pbnCards.length; i++) {
            cards[i] = pbnCards[i];
        }
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("cards");
    }
}
