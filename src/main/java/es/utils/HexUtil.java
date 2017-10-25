package es.utils;

import java.util.UUID;

public class HexUtil {
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String convertStringToHex(String input) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i += 2) {
            final String code = input.substring(i, i + 2);
            final int code2 = Integer.parseInt(code, 16);
            result.append((char) code2);
        }
        return "0x" + result.toString();
    }

    public static String unhexUUID(String input) {
        return input.replace("-", "");
    }


    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return convertStringToHex(new String(hexChars));
    }

    public static UUID toUUID(byte[] bytes) {
        if (bytes == null || bytes.equals(null)) {
            return new UUID(0, 0);
        }
        if (bytes.length != 16) {
            throw new IllegalArgumentException("uuid 参数错误 :" + new String(bytes));
        }
        int i = 0;
        long msl = 0;
        for (; i < 8; i++) {
            msl = (msl << 8) | (bytes[i] & 0xFF);
        }
        long lsl = 0;
        for (; i < 16; i++) {
            lsl = (lsl << 8) | (bytes[i] & 0xFF);
        }
        return new UUID(msl, lsl);
    }
}
