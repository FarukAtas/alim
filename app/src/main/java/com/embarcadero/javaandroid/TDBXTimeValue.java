package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXTimeValue extends DBXValue {
    private int DBXTimeValue;
    protected boolean ValueNull = false;

    public TDBXTimeValue() {
        setDBXType(10);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXTimeValue = 0;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTDBXTime(int Value) throws DBXException {
        this.DBXTimeValue = Value;
        this.ValueNull = false;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsTDBXTime() throws DBXException {
        return this.DBXTimeValue;
    }
}
