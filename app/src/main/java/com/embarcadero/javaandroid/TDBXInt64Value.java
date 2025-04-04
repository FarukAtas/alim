package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXInt64Value extends DBXValue {
    private long DBXInt64Value;
    protected boolean ValueNull = false;

    public TDBXInt64Value() {
        setDBXType(18);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() throws DBXException {
        this.ValueNull = true;
        this.DBXInt64Value = 0L;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsInt64(long Value) throws DBXException {
        this.DBXInt64Value = Value;
        this.ValueNull = false;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public long GetAsInt64() throws DBXException {
        return this.DBXInt64Value;
    }
}
