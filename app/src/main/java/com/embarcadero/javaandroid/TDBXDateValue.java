package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXDateValue extends DBXValue {
    private int DBXDateValue;
    protected boolean ValueNull = false;

    public TDBXDateValue() {
        setDBXType(2);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXDateValue = 0;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTDBXDate(int Value) throws DBXException {
        this.DBXDateValue = Value;
        this.ValueNull = false;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsTDBXDate() throws DBXException {
        return this.DBXDateValue;
    }
}
