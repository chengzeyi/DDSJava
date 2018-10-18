package ddsjava.integration;

import java.io.UnsupportedEncodingException;

public class DDSHelper {
    public static char[] pbnStringToChars(String pbn) {
        char[] result = new char[80];
        for (int i = 0; i < pbn.length(); i++) {
            result[i] = pbn.charAt(i);
        }
        result[pbn.length()] = '\0';
        return result;
    }

    public static byte[] pbnStringToBytes(String pbn) {
        byte[] result = new byte[80];
        byte[] tmp = null;
        try {
            tmp = pbn.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            System.exit(1);
        }
        for (int i = 0; i < tmp.length; i++) {
            result[i] = tmp[i];
        }
        return result;
    }
}
