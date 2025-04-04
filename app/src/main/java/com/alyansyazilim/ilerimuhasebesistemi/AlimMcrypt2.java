package com.alyansyazilim.ilerimuhasebesistemi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class AlimMcrypt2 {
    private Cipher cipher;
    private String iv = "fedcba9786543210";
    private String SecretKey = "0123456879abcdef";
    private IvParameterSpec ivspec = new IvParameterSpec(this.iv.getBytes());
    private SecretKeySpec keyspec = new SecretKeySpec(this.SecretKey.getBytes(), "AES");

    public AlimMcrypt2() {
        try {
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
    }

    public byte[] encrypt(String text) throws Exception {
        if (text == null || text.length() == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.cipher.init(1, this.keyspec, this.ivspec);
            byte[] encrypted = this.cipher.doFinal(text.getBytes("UTF-8"));
            return encrypted;
        } catch (Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }
    }

    public byte[] encrypt2(byte[] buf) throws Exception {
        if (buf == null || buf.length == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.cipher.init(1, this.keyspec, this.ivspec);
            byte[] encrypted = this.cipher.doFinal(buf);
            return encrypted;
        } catch (Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }
    }

    public byte[] decrypt(String code) throws Exception {
        if (code == null || code.length() == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.cipher.init(2, this.keyspec, this.ivspec);
            byte[] decrypted = this.cipher.doFinal(hexToBytes(code));
            return decrypted;
        } catch (Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
    }

    public byte[] decrypt2(byte[] buf) throws Exception {
        if (buf == null || buf.length == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.cipher.init(2, this.keyspec, this.ivspec);
            byte[] decrypted = this.cipher.doFinal(buf);
            return decrypted;
        } catch (Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
    }

    public static String bytesToHex(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (byte b2 : b) {
            buf.append(byteToHex(b2));
        }
        return buf.toString();
    }

    public static String byteToHex(byte b) {
        char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] a = {hexDigit[(b >> 4) & 15], hexDigit[b & 15]};
        return new String(a);
    }

    public static String str2encrypt(String str) {
        AlimMcrypt2 cry = new AlimMcrypt2();
        try {
            String ret = bytesToHex(cry.encrypt(str));
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String str2md5(String str) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(str.getBytes(), 0, str.length());
        String hash = bytesToHex(m.digest());
        return hash;
    }

    public static byte[] hexToBytes(String str) {
        byte[] buffer = null;
        if (str != null && str.length() >= 2) {
            int len = str.length() / 2;
            buffer = new byte[len];
            for (int i = 0; i < len; i++) {
                buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
            }
        }
        return buffer;
    }

    public static String padString(String source) {
        int x = source.length() % 16;
        int padLength = 16 - x;
        for (int i = 0; i < padLength; i++) {
            source = String.valueOf(source) + ' ';
        }
        return source;
    }
}
