package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public enum DSHTTPRequestType {
    GET,
    POST,
    PUT,
    DELETE;

    /* renamed from: values, reason: to resolve conflict with enum method */
    public static DSHTTPRequestType[] valuesCustom() {
        DSHTTPRequestType[] valuesCustom = values();
        int length = valuesCustom.length;
        DSHTTPRequestType[] dSHTTPRequestTypeArr = new DSHTTPRequestType[length];
        System.arraycopy(valuesCustom, 0, dSHTTPRequestTypeArr, 0, length);
        return dSHTTPRequestTypeArr;
    }
}
