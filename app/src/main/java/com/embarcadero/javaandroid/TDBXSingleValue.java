package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXSingleValue extends DBXValue {
    private float DBXSingleValue;
    protected boolean ValueNull = false;

    public TDBXSingleValue() {
        setDBXType(27);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXSingleValue = 0.0f;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsSingle(float Value) throws DBXException {
        if (!isNull()) {
            this.DBXSingleValue = Value;
        }
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public float GetAsSingle() throws DBXException {
        return this.DBXSingleValue;
    }
}
