package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class Base64 {
    static String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    static byte[] encodeData = new byte[64];

    static {
        for (int i = 0; i < 64; i++) {
            byte c = (byte) charSet.charAt(i);
            encodeData[i] = c;
        }
    }

    private Base64() {
    }

    public static String encode(String s) {
        return encode(s.getBytes());
    }

    public static String encode(byte[] src) {
        return encode(src, 0, src.length);
    }

    public static String encode(byte[] src, int start, int length) {
        int dstIndex;
        byte[] dst = new byte[(((length + 2) / 3) * 4) + (length / 72)];
        int state = 0;
        int old = 0;
        int len = 0;
        int max = length + start;
        int srcIndex = start;
        int dstIndex2 = 0;
        while (srcIndex < max) {
            int x = src[srcIndex];
            state++;
            switch (state) {
                case 1:
                    dst[dstIndex2] = encodeData[(x >> 2) & 63];
                    dstIndex2++;
                    break;
                case 2:
                    dst[dstIndex2] = encodeData[((old << 4) & 48) | ((x >> 4) & 15)];
                    dstIndex2++;
                    break;
                case 3:
                    int dstIndex3 = dstIndex2 + 1;
                    dst[dstIndex2] = encodeData[((old << 2) & 60) | ((x >> 6) & 3)];
                    dstIndex2 = dstIndex3 + 1;
                    dst[dstIndex3] = encodeData[x & 63];
                    state = 0;
                    break;
            }
            old = x;
            len++;
            if (len >= 72) {
                dstIndex = dstIndex2 + 1;
                dst[dstIndex2] = 10;
                len = 0;
            } else {
                dstIndex = dstIndex2;
            }
            srcIndex++;
            dstIndex2 = dstIndex;
        }
        switch (state) {
            case 1:
                int dstIndex4 = dstIndex2 + 1;
                dst[dstIndex2] = encodeData[(old << 4) & 48];
                int dstIndex5 = dstIndex4 + 1;
                dst[dstIndex4] = 61;
                int r1 = dstIndex5 + 1;
                dst[dstIndex5] = 61;
                break;
            case 2:
                int dstIndex6 = dstIndex2 + 1;
                dst[dstIndex2] = encodeData[(old << 2) & 60];
                dstIndex2 = dstIndex6 + 1;
                dst[dstIndex6] = 61;
        }
        return new String(dst);
    }

    public static byte[] decode(String s) {
        int code;
        int dst;
        int end = 0;
        if (s.endsWith("=")) {
            end = 0 + 1;
        }
        if (s.endsWith("==")) {
            end++;
        }
        int len = (((s.length() + 3) / 4) * 3) - end;
        byte[] result = new byte[len];
        int src = 0;
        int dst2 = 0;
        while (src < s.length() && (code = charSet.indexOf(s.charAt(src))) != -1) {
            try {
                switch (src % 4) {
                    case 0:
                        result[dst2] = (byte) (code << 2);
                        dst = dst2;
                        break;
                    case 1:
                        dst = dst2 + 1;
                        try {
                            result[dst2] = (byte) (result[dst2] | ((byte) ((code >> 4) & 3)));
                            result[dst] = (byte) (code << 4);
                            break;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                    case 2:
                        dst = dst2 + 1;
                        result[dst2] = (byte) (result[dst2] | ((byte) ((code >> 2) & 15)));
                        result[dst] = (byte) (code << 6);
                        break;
                    case 3:
                        dst = dst2 + 1;
                        result[dst2] = (byte) (result[dst2] | ((byte) (code & 63)));
                        break;
                    default:
                        dst = dst2;
                        break;
                }
                src++;
                dst2 = dst;
            } catch (ArrayIndexOutOfBoundsException e2) {
            }
        }
        return result;
    }
}
