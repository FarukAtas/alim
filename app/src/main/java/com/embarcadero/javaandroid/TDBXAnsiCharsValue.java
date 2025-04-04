package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXAnsiCharsValue extends DBXValue {
    private String DBXStringValue;
    protected boolean ValueNull = false;

    public TDBXAnsiCharsValue() {
        setDBXType(26);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXStringValue = "";
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsString(String Value) throws DBXException {
        this.ValueNull = false;
        this.DBXStringValue = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public String GetAsString() throws DBXException {
        return this.DBXStringValue;
    }
}
