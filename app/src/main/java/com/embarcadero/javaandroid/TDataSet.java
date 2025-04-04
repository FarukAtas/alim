package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDataSet extends TDBXReader {
    public TDataSet(TParams params, TJSONObject value) {
        super(params, value);
    }

    /* renamed from: createFrom, reason: collision with other method in class */
    public static TDataSet m1createFrom(TJSONObject value) throws DBXException {
        TParams params = TParams.CreateParametersFromMetadata(value.getJSONArray("table"));
        TDataSet dst = new TDataSet(params, value);
        return dst;
    }
}
