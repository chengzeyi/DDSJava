package ddsjava.integration;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface DDSImport extends Library {
    DDSImport instance = (DDSImport) Native.load("dds", DDSImport.class);

    public int CalcDDtablePBN(DDTableDealPBN.ByValue tableDealPBN, DDTableResults.ByReference tableResults);

    public int SolveBoardPBN(DDDealPBN.ByValue dealPBN, int target, int solutions, int mode, DDFutureTricks.ByReference futureTricks, int threadIndex);
}