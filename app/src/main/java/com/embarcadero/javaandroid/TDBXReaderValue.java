package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXReaderValue extends DBXValue {
    protected boolean ValueNull = false;

    public TDBXReaderValue() {
        setDBXType(23);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void setNull() {
        this.ValueNull = true;
        this.objectValue = null;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean isNull() {
        return this.ValueNull;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTable(TableType Value) {
        this.objectValue = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public Object GetAsTable() throws DBXException {
        return this.objectValue;
    }

    public TDBXReader GetAsDBXReader() throws DBXException {
        return (TDBXReader) this.objectValue;
    }

    public void SetAsDBXReader(TableType Value) {
        SetAsTable(Value);
    }
}
