package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXAnsiStringValue extends DBXValue {
    private String DBXAnsiStringValue;
    protected boolean ValueNull = false;

    public TDBXAnsiStringValue() {
        setDBXType(26);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXAnsiStringValue = "";
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsString(String Value) throws DBXException {
        this.ValueNull = false;
        this.DBXAnsiStringValue = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public String GetAsString() throws DBXException {
        return this.DBXAnsiStringValue;
    }
}
