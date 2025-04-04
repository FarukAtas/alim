package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public enum JSONValueType {
    JSONObject,
    JSONArray,
    JSONString,
    JSONNumber,
    JSONTrue,
    JSONFalse,
    JSONNull;

    /* renamed from: values, reason: to resolve conflict with enum method */
    public static JSONValueType[] valuesCustom() {
        JSONValueType[] valuesCustom = values();
        int length = valuesCustom.length;
        JSONValueType[] jSONValueTypeArr = new JSONValueType[length];
        System.arraycopy(valuesCustom, 0, jSONValueTypeArr, 0, length);
        return jSONValueTypeArr;
    }
}
