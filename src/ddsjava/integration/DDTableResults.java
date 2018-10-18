package ddsjava.integration;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class DDTableResults extends Structure {
    public int[] resTable = new int[20];

    public static class ByValue extends DDTableResults implements Structure.ByValue {}

    public static class ByReference extends DDTableResults implements Structure.ByReference {}

    public DDTableResults() {
        super();
        setAlignType(Structure.ALIGN_DEFAULT);
    }

    public DDTableResults(Pointer pointer) {
        super(pointer);
        setAlignType(Structure.ALIGN_DEFAULT);
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("resTable");
    }
}
