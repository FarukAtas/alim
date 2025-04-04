package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXInt8Value extends DBXValue {
    private int DBXIntValue;
    protected boolean ValueNull = false;

    public TDBXInt8Value() {
        setDBXType(28);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXIntValue = 0;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsInt8(int Value) throws DBXException {
        this.DBXIntValue = Value;
        this.ValueNull = false;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsInt8() throws DBXException {
        return this.DBXIntValue;
    }
}
