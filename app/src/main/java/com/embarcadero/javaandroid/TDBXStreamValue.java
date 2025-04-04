package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXStreamValue extends DBXValue {
    protected boolean ValueNull = false;

    public TDBXStreamValue() {
        setDBXType(33);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.streamValue = null;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsStream(TStream Value) {
        this.streamValue = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public TStream GetAsStream() throws DBXException {
        return this.streamValue;
    }
}
