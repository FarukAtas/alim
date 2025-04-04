package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TJSONTrue extends TJSONValue {
    public String asJSONString() {
        return "true";
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public Object getInternalObject() {
        return true;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public JSONValueType getJsonValueType() {
        return JSONValueType.JSONTrue;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public String toString() {
        return asJSONString();
    }
}
