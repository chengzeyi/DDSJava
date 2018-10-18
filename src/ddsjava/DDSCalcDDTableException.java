package ddsjava;

public class DDSCalcDDTableException extends Exception {
    private final int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public DDSCalcDDTableException(int code)
    {
        errorCode = code;
    }
}
