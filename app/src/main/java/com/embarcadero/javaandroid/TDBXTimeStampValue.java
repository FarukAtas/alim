package com.embarcadero.javaandroid;

import java.util.Date;

/* loaded from: classes.dex */
public class TDBXTimeStampValue extends DBXValue {
    private Date DBXTimeStampValue;
    protected boolean ValueNull = false;

    public TDBXTimeStampValue() {
        setDBXType(24);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.DBXTimeStampValue = null;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTimeStamp(Date Value) throws DBXException {
        this.DBXTimeStampValue = Value;
        this.ValueNull = false;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public Date GetAsTimeStamp() throws DBXException {
        return this.DBXTimeStampValue;
    }
}
