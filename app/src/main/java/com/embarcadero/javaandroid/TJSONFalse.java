package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TJSONFalse extends TJSONValue {
    @Override // com.embarcadero.javaandroid.TJSONValue
    public JSONValueType getJsonValueType() {
        return JSONValueType.JSONFalse;
    }

    public String asJSONString() {
        return "false";
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public Object getInternalObject() {
        return false;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public String toString() {
        return asJSONString();
    }
}
