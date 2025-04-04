package com.embarcadero.javaandroid;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/* loaded from: classes.dex */
public class DBXTools {
    public static final byte[] streamToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        if (in != null) {
            transfer(in, out);
        }
        out.close();
        return out.toByteArray();
    }

    public static final long transfer(InputStream in, OutputStream out) throws IOException {
        long totalBytes = 0;
        byte[] buf = new byte[4096];
        while (true) {
            int bytesInBuf = in.read(buf);
            if (bytesInBuf != -1) {
                out.write(buf, 0, bytesInBuf);
                totalBytes += bytesInBuf;
            } else {
                return totalBytes;
            }
        }
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while (true) {
                    int n = reader.read(buffer);
                    if (n != -1) {
                        writer.write(buffer, 0, n);
                    } else {
                        is.close();
                        return writer.toString();
                    }
                }
            } catch (Throwable th) {
                is.close();
                throw th;
            }
        } else {
            return "";
        }
    }
}
