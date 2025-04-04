package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXInt16Value extends DBXValue {
    private int DBXInt16Value;
    protected boolean ValueNull = false;

    public TDBXInt16Value() {
        setDBXType(5);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXInt16Value = 0;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsInt16(int Value) throws DBXException {
        this.ValueNull = false;
        this.DBXInt16Value = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsInt16() throws DBXException {
        return this.DBXInt16Value;
    }
}
