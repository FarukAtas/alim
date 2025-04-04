package com.embarcadero.javaandroid;

import java.io.ByteArrayInputStream;

/* loaded from: classes.dex */
public class TStream extends ByteArrayInputStream {
    public TStream(byte[] buf) {
        super(buf);
    }

    public static TStream CreateFrom(TJSONArray value) throws DBXException {
        byte[] b1 = new byte[(int) value.size()];
        for (int i = 0; i < value.size(); i++) {
            b1[i] = value.getInt(i).byteValue();
        }
        return new TStream(b1);
    }

    public byte[] asByteArray() {
        return (byte[]) ((ByteArrayInputStream) this).buf.clone();
    }
}
