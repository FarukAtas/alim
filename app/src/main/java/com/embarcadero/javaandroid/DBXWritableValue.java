package com.embarcadero.javaandroid;

import java.math.BigDecimal;
import java.util.Date;

/* loaded from: classes.dex */
public class DBXWritableValue extends DBXValue {
    @Override // com.embarcadero.javaandroid.DBXValue
    public void Clear() {
        this.booleanValue = false;
        this.INT32Value = 0;
        this.UINT32Value = 0L;
        this.INT64Value = 0L;
        this.UINT64Value = 0L;
        this.stringValue = "";
        this.singleValue = 0.0f;
        this.doubleValue = 0.0d;
        this.dateTimeValue = null;
        this.streamValue = null;
        this.objectValue = null;
        this.jsonValueValue = null;
        this.bcdValue = 0.0d;
        this.DBXValueValue = null;
        this.TimeStampValue = null;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsTDBXDate() throws DBXException {
        checkCurrentDBXType(2);
        return this.INT32Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTDBXDate(int Value) {
        Clear();
        this.INT32Value = Value;
        setDBXType(2);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsJSONValue(TJSONValue Value) {
        Clear();
        this.jsonValueValue = Value;
        setDBXType(37);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public TJSONValue GetAsJSONValue() throws DBXException {
        checkCurrentDBXType(37);
        return this.jsonValueValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsStream(TStream Value) {
        Clear();
        this.streamValue = Value;
        setDBXType(33);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public TStream GetAsStream() throws DBXException {
        checkCurrentDBXType(33);
        return this.streamValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public long GetAsUInt64() throws DBXException {
        checkCurrentDBXType(19);
        return this.UINT64Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public long GetAsUInt32() throws DBXException {
        checkCurrentDBXType(13);
        return this.UINT32Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTable(TableType Value) {
        Clear();
        this.objectValue = Value;
        setDBXType(23);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public Object GetAsTable() throws DBXException {
        checkCurrentDBXType(23);
        return this.objectValue;
    }

    public static Double CurrencyRound(double value) {
        BigDecimal bd = new BigDecimal(value);
        return Double.valueOf(bd.setScale(4, 0).doubleValue());
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    protected void throwInvalidValue() throws DBXException {
        throw new DBXException("Invalid value for param");
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsAnsiString(String value) {
        SetAsString(value);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public String GetAsAnsiString() throws DBXException {
        checkCurrentDBXType(26);
        return this.stringValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsBoolean(boolean Value) {
        Clear();
        this.booleanValue = Value;
        setDBXType(4);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsUInt8(int Value) throws DBXException {
        if (Value >= 0 && Value <= 255) {
            Clear();
            this.INT32Value = Value;
            setDBXType(29);
            return;
        }
        throwInvalidValue();
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsInt8(int Value) throws DBXException {
        if (Value >= -127 && Value <= 128) {
            Clear();
            this.INT32Value = Value;
            setDBXType(28);
            return;
        }
        throwInvalidValue();
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsUInt16(int Value) throws DBXException {
        if (Value >= 0 && Value <= 65535) {
            Clear();
            this.INT32Value = Value;
            setDBXType(12);
            return;
        }
        throwInvalidValue();
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsInt16(int Value) throws DBXException {
        if (Value >= -32768 && Value <= 32767) {
            Clear();
            this.INT32Value = Value;
            setDBXType(5);
            return;
        }
        throwInvalidValue();
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsInt32(int Value) throws DBXException {
        if (Value >= Integer.MIN_VALUE && Value <= Integer.MAX_VALUE) {
            Clear();
            this.INT32Value = Value;
            setDBXType(6);
            return;
        }
        throwInvalidValue();
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsInt64(long Value) throws DBXException {
        if (Value >= Long.MIN_VALUE && Value <= Long.MAX_VALUE) {
            Clear();
            this.INT64Value = Value;
            setDBXType(18);
            return;
        }
        throwInvalidValue();
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsString(String Value) {
        Clear();
        this.stringValue = Value;
        setDBXType(26);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsSingle(float Value) throws DBXException {
        Clear();
        this.singleValue = Value;
        setDBXType(27);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsDouble(double Value) throws DBXException {
        Clear();
        this.doubleValue = Value;
        setDBXType(7);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsDate(Date Value) {
        Clear();
        this.dateTimeValue = Value;
        setDBXType(2);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTime(Date Value) {
        Clear();
        this.dateTimeValue = Value;
        setDBXType(10);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsDateTime(Date Value) {
        Clear();
        this.dateTimeValue = Value;
        setDBXType(11);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTimeStamp(Date Value) {
        Clear();
        this.TimeStampValue = Value;
        setDBXType(24);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsBcd(double Value) throws DBXException {
        Clear();
        this.bcdValue = Value;
        setDBXType(8);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsCurrency(double Value) throws DBXException {
        Clear();
        this.doubleValue = Value;
        setDBXType(25);
    }

    public void SetAsUInt32(long Value) throws DBXException {
        if (Value >= 0 && Value <= 4294967295L) {
            Clear();
            this.UINT32Value = Value;
            setDBXType(13);
            return;
        }
        throwInvalidValue();
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsUInt64(long Value) throws DBXException {
        Clear();
        this.UINT64Value = Value;
        setDBXType(19);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    protected void checkCurrentDBXType(int value) throws DBXException {
        if (value != this.CurrentDBXType) {
            throw new DBXException("Incorrect type in DBXValue");
        }
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public boolean GetAsBoolean() throws DBXException {
        checkCurrentDBXType(4);
        return this.booleanValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsUInt8() throws DBXException {
        checkCurrentDBXType(29);
        return this.INT32Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsInt8() throws DBXException {
        checkCurrentDBXType(28);
        return this.INT32Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsUInt16() throws DBXException {
        checkCurrentDBXType(12);
        return this.INT32Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsInt16() throws DBXException {
        checkCurrentDBXType(5);
        return this.INT32Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsInt32() throws DBXException {
        checkCurrentDBXType(6);
        return this.INT32Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public long GetAsInt64() throws DBXException {
        checkCurrentDBXType(18);
        return this.INT64Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public String GetAsString() throws DBXException {
        checkCurrentDBXType(26);
        return this.stringValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public float GetAsSingle() throws DBXException {
        checkCurrentDBXType(27);
        return this.singleValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public double GetAsDouble() throws DBXException {
        setDBXType(7);
        return this.doubleValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public Date GetAsDate() throws DBXException {
        checkCurrentDBXType(2);
        return this.dateTimeValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public Date GetAsTime() throws DBXException {
        checkCurrentDBXType(10);
        return this.dateTimeValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public Date GetAsDateTime() throws DBXException {
        checkCurrentDBXType(11);
        return this.dateTimeValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public Date GetAsTimeStamp() throws DBXException {
        checkCurrentDBXType(24);
        return this.TimeStampValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public double GetAsCurrency() throws DBXException {
        checkCurrentDBXType(25);
        return this.doubleValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public double GetAsBcd() throws DBXException {
        checkCurrentDBXType(8);
        return this.bcdValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public int GetAsTDBXTime() throws DBXException {
        checkCurrentDBXType(10);
        return this.INT32Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsTDBXTime(int Value) {
        Clear();
        this.INT32Value = Value;
        setDBXType(10);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsBlob(TStream value) {
        Clear();
        this.objectValue = value;
        setDBXType(3);
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public TStream GetAsBlob() throws DBXException {
        checkCurrentDBXType(3);
        return (TStream) this.objectValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetAsDBXValue(DBXValue Value) throws DBXException {
        setDBXType(Value.getDBXType());
        this.isSimpleValueType = true;
        this.DBXValueValue = Value;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public DBXValue GetAsDBXValue() throws DBXException {
        if (!this.isSimpleValueType) {
            throw new DBXException("Invalid DBX type");
        }
        return this.DBXValueValue;
    }

    @Override // com.embarcadero.javaandroid.DBXValue
    public void SetTDBXNull(String TypeName) throws DBXException {
        if (TypeName.equals("TDBXStringValue")) {
            TDBXStringValue v = new TDBXStringValue();
            v.setNull();
            SetAsDBXValue(v);
            return;
        }
        if (TypeName.equals("TDBXAnsiCharsValue")) {
            TDBXAnsiCharsValue v2 = new TDBXAnsiCharsValue();
            v2.setNull();
            SetAsDBXValue(v2);
            return;
        }
        if (TypeName.equals("TDBXAnsiStringValue")) {
            TDBXAnsiStringValue v3 = new TDBXAnsiStringValue();
            v3.setNull();
            SetAsDBXValue(v3);
            return;
        }
        if (TypeName.equals("TDBXSingleValue")) {
            TDBXSingleValue v4 = new TDBXSingleValue();
            v4.setNull();
            SetAsDBXValue(v4);
            return;
        }
        if (TypeName.equals("TDBXWideStringValue")) {
            TDBXWideStringValue v5 = new TDBXWideStringValue();
            v5.setNull();
            SetAsDBXValue(v5);
            return;
        }
        if (TypeName.equals("TDBXDateValue")) {
            TDBXDateValue v6 = new TDBXDateValue();
            v6.setNull();
            SetAsDBXValue(v6);
            return;
        }
        if (TypeName.equals("TDBXTimeValue")) {
            TDBXTimeValue v7 = new TDBXTimeValue();
            v7.setNull();
            SetAsDBXValue(v7);
            return;
        }
        if (TypeName.equals("TDBXBooleanValue")) {
            TDBXBooleanValue v8 = new TDBXBooleanValue();
            v8.setNull();
            SetAsDBXValue(v8);
            return;
        }
        if (TypeName.equals("TDBXDoubleValue")) {
            TDBXDoubleValue v9 = new TDBXDoubleValue();
            v9.setNull();
            SetAsDBXValue(v9);
            return;
        }
        if (TypeName.equals("TDBXInt64Value")) {
            TDBXInt64Value v10 = new TDBXInt64Value();
            v10.setNull();
            SetAsDBXValue(v10);
            return;
        }
        if (TypeName.equals("TDBXInt32Value")) {
            TDBXInt32Value v11 = new TDBXInt32Value();
            v11.setNull();
            SetAsDBXValue(v11);
            return;
        }
        if (TypeName.equals("TDBXInt16Value")) {
            TDBXInt16Value v12 = new TDBXInt16Value();
            v12.setNull();
            SetAsDBXValue(v12);
            return;
        }
        if (TypeName.equals("TDBXInt8Value")) {
            TDBXInt8Value v13 = new TDBXInt8Value();
            v13.setNull();
            SetAsDBXValue(v13);
        } else if (TypeName.equals("TDBXStreamValue")) {
            TDBXStreamValue v14 = new TDBXStreamValue();
            v14.setNull();
            SetAsDBXValue(v14);
        } else if (TypeName.equals("TDBXReaderValue")) {
            TDBXReaderValue v15 = new TDBXReaderValue();
            v15.setNull();
            SetAsDBXValue(v15);
        }
    }
}
