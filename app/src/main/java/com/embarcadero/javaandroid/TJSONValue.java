package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public abstract class TJSONValue {
    protected final String NullString = "null";

    public abstract Object getInternalObject();

    public abstract JSONValueType getJsonValueType();

    public abstract String toString();
}
