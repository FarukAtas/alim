package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXWideStringValue extends DBXValue {
    private String DBXWideStringValue;
    protected boolean ValueNull = false;

    public TDBXWideStringValue() {
        setDBXType(26);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXWideStringValue = null;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsString(String Value) throws DBXException {
        this.ValueNull = false;
        this.DBXWideStringValue = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public String GetAsString() throws DBXException {
        return this.DBXWideStringValue;
    }
}
