package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXInt32Value extends DBXValue {
    private int DBXInt32Value;
    protected boolean ValueNull = false;

    public TDBXInt32Value() {
        setDBXType(6);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXInt32Value = 0;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsInt32(int Value) throws DBXException {
        this.ValueNull = false;
        this.DBXInt32Value = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsInt32() throws DBXException {
        return this.DBXInt32Value;
    }
}
