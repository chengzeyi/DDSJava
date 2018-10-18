package ddsjava.integration;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class DDFutureTricks extends Structure {
    public int nodes;

    public int cards;

    public int[] suit = new int[13];

    public int[] rank = new int[13];

    public int[] equals = new int[13];

    public int[] score = new int[13];

    public static class ByValue extends DDFutureTricks implements Structure.ByValue {}

    public static class ByReference extends DDFutureTricks implements Structure.ByReference {}

    public DDFutureTricks() {
        super();
        setAlignType(Structure.ALIGN_DEFAULT);
    }

    public DDFutureTricks(Pointer pointer) {
        super(pointer);
        setAlignType(Structure.ALIGN_DEFAULT);
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("nodes", "cards", "suit", "rank", "equals", "score");
    }
}
