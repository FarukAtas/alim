package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXBcdValue extends DBXValue {
    protected boolean ValueNull = false;

    public TDBXBcdValue() {
        setDBXType(8);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.bcdValue = 0.0d;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsBcd(double Value) throws DBXException {
        this.bcdValue = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public double GetAsBcd() throws DBXException {
        return this.bcdValue;
    }
}
