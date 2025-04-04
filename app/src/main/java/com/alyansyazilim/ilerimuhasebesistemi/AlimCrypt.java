package com.alyansyazilim.ilerimuhasebesistemi;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class AlimCrypt {
    private IvParameterSpec ivspec;
    private SecretKeySpec keyspec;

    public void test_keygen() {
        KeyGenerator keygen = null;
        this.ivspec = new IvParameterSpec("fedcba9876543210".getBytes());
        try {
            keygen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keygen.init(128);
        Key key = keygen.generateKey();
        this.keyspec = new SecretKeySpec(key.getEncoded(), "AES");
    }

    public String test_decrypt(String code) {
        Cipher cipher = null;
        byte[] decrypted = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
        try {
            cipher.init(2, this.keyspec, this.ivspec);
        } catch (InvalidAlgorithmParameterException e3) {
            e3.printStackTrace();
        } catch (InvalidKeyException e4) {
            e4.printStackTrace();
        }
        try {
            decrypted = cipher.doFinal(hexToBytes(code));
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
        } catch (IllegalBlockSizeException e6) {
            e6.printStackTrace();
        }
        return decrypted.toString();
    }

    public String test_encrypt(String test) {
        Cipher cipher = null;
        byte[] encrypted = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
        try {
            cipher.init(1, this.keyspec, this.ivspec);
        } catch (InvalidAlgorithmParameterException e3) {
            e3.printStackTrace();
        } catch (InvalidKeyException e4) {
            e4.printStackTrace();
        }
        try {
            encrypted = cipher.doFinal(padString(test).getBytes());
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
        } catch (IllegalBlockSizeException e6) {
            e6.printStackTrace();
        }
        return encrypted.toString();
    }

    private byte[] hexToBytes(String hex) {
        int l = hex.length() / 2;
        byte[] data = new byte[l];
        int j = 0;
        for (int i = 0; i < l; i++) {
            int j2 = j + 1;
            char c = hex.charAt(j);
            int n = "0123456789abcdef".indexOf(c);
            int b = (n & 15) << 4;
            j = j2 + 1;
            char c2 = hex.charAt(j2);
            int n2 = "0123456789abcdef".indexOf(c2);
            data[i] = (byte) (b + (n2 & 15));
        }
        return data;
    }

    private String padString(String source) {
        int padLength = 16 - (source.length() % 16);
        for (int i = 0; i < padLength; i++) {
            source = String.valueOf(source) + ' ';
        }
        return source;
    }
}
