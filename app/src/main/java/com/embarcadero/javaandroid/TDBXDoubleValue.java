package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXDoubleValue extends DBXValue {
    private double DBXDoubleValue;
    protected boolean ValueNull = false;

    public TDBXDoubleValue() {
        setDBXType(7);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXDoubleValue = 0.0d;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsDouble(double Value) throws DBXException {
        this.DBXDoubleValue = Value;
        this.ValueNull = false;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public double GetAsDouble() throws DBXException {
        return this.DBXDoubleValue;
    }
}
