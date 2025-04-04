package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXBooleanValue extends DBXValue {
    private boolean DBXBooleanValue;
    protected boolean ValueNull = false;

    public TDBXBooleanValue() {
        setDBXType(4);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXBooleanValue = false;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsBoolean(boolean Value) throws DBXException {
        this.DBXBooleanValue = Value;
        this.ValueNull = false;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean GetAsBoolean() throws DBXException {
        return this.DBXBooleanValue;
    }
}
