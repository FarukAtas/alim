package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TJSONString extends TJSONValue {
    protected String value;

    public TJSONString() {
        this.value = null;
    }

    public TJSONString(String value) {
        this.value = value;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public Object getInternalObject() {
        return this.value;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public String toString() {
        return this.value != null ? this.value : "null";
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public JSONValueType getJsonValueType() {
        return JSONValueType.JSONString;
    }

    public String getValue() {
        return this.value;
    }
}
