package com.embarcadero.javaandroid;

import org.json.JSONObject;

/* loaded from: classes.dex */
public class TJSONNull extends TJSONValue {
    @Override // com.embarcadero.javaandroid.TJSONValue
    public JSONValueType getJsonValueType() {
        return JSONValueType.JSONNull;
    }

    public String asJSONString() {
        return "null";
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public Object getInternalObject() {
        return JSONObject.NULL;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public String toString() {
        return asJSONString();
    }
}
