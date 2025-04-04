package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TJSONPair {
    public String name;
    public TJSONValue value;

    public TJSONPair(String name, TJSONValue value) {
        this.name = name;
        this.value = value;
    }

    public TJSONPair(String name, String value) {
        this.name = name;
        this.value = new TJSONString(value);
    }
}
