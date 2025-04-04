package com.embarcadero.javaandroid;

import android.annotation.SuppressLint;

@SuppressLint({"UseValueOf"})
/* loaded from: classes.dex */
public final class TJSONNumber extends TJSONValue {
    protected Double value;

    public TJSONNumber() {
        this.value = null;
    }

    public Double getValue() {
        if (this.value == null) {
            return null;
        }
        return this.value;
    }

    public TJSONNumber(String value) {
        this.value = Double.valueOf(Double.parseDouble(value));
    }

    public TJSONNumber(double value) {
        this.value = new Double(value);
    }

    public TJSONNumber(long value) {
        this.value = new Double(value);
    }

    public TJSONNumber(int value) {
        this.value = new Double(value);
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public Object getInternalObject() {
        return this.value;
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public String toString() {
        return this.value != null ? this.value.toString() : "null";
    }

    @Override // com.embarcadero.javaandroid.TJSONValue
    public JSONValueType getJsonValueType() {
        return JSONValueType.JSONNumber;
    }
}
