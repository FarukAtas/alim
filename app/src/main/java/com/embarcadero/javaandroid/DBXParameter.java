package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class DBXParameter extends DBXValueType {
    private DBXWritableValue Value = new DBXWritableValue();

    public DBXParameter() {
        this.Value.Clear();
    }

    public DBXWritableValue getValue() {
        return this.Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValueType
    public void setDataType(int dataType) {
        this.Value.setDBXType(dataType);
    }

    public TJSONArray tojson() {
        TJSONArray arr = new TJSONArray();
        arr.add(getName());
        arr.add(getDataType());
        arr.add(getOrdinal());
        arr.add(getSubType());
        arr.add(getScale());
        arr.add(getSize());
        arr.add(getPrecision());
        arr.add(getChildPosition());
        arr.add(getNullable());
        arr.add(getHidden());
        arr.add(getParameterDirection());
        arr.add(getValueParameter());
        arr.add(getLiteral());
        return arr;
    }

    @Override // com.embarcadero.javaandroid.DBXValueType
    public int getDataType() {
        return getValue().getDBXType();
    }
}
