package com.embarcadero.javaandroid;

import java.util.Date;

/* loaded from: classes.dex */
public abstract class DBXValue {
    protected DBXValue DBXValueValue;
    protected int INT32Value;
    protected long INT64Value;
    protected Date TimeStampValue;
    protected long UINT32Value;
    protected long UINT64Value;
    protected double bcdValue;
    protected boolean booleanValue;
    protected Date dateTimeValue;
    protected double doubleValue;
    protected TJSONValue jsonValueValue;
    protected Object objectValue;
    protected float singleValue;
    protected TStream streamValue;
    protected String stringValue;
    protected int CurrentDBXType = 0;
    protected boolean isSimpleValueType = false;

    public void appendTo(TJSONArray json) {
        try {
            if (containsASimpleValueType()) {
                if (GetAsDBXValue().isNull()) {
                    json.add((TJSONValue) new TJSONNull());
                    return;
                } else {
                    GetAsDBXValue().appendTo(json);
                    return;
                }
            }
            switch (this.CurrentDBXType) {
                case 1:
                case DBXDataTypes.WideStringType /* 26 */:
                    json.add(GetAsString());
                    return;
                case 2:
                    json.add(DBXDefaultFormatter.getInstance().TDBXDateToString(GetAsTDBXDate()));
                    return;
                case 3:
                case 4:
                case 8:
                case 9:
                case 14:
                case 15:
                case 16:
                case 17:
                case DBXDataTypes.AdtType /* 20 */:
                case 21:
                case DBXDataTypes.RefType /* 22 */:
                case DBXDataTypes.ObjectType /* 30 */:
                case DBXDataTypes.CharArrayType /* 31 */:
                case 32:
                case DBXDataTypes.DBXConnectionType /* 34 */:
                case DBXDataTypes.VariantType /* 35 */:
                case DBXDataTypes.TimeStampOffsetType /* 36 */:
                default:
                    throw new DBXException("Cannot convert this type to string");
                case 5:
                    json.add(GetAsInt16());
                    return;
                case 6:
                    json.add(GetAsInt32());
                    return;
                case 7:
                    json.add(GetAsDouble());
                    return;
                case 10:
                    json.add(DBXDefaultFormatter.getInstance().TDBXTimeToString(GetAsTDBXTime()));
                    return;
                case DBXDataTypes.DateTimeType /* 11 */:
                    json.add(DBXDefaultFormatter.getInstance().DateTimeToString(this.dateTimeValue));
                    return;
                case DBXDataTypes.UInt16Type /* 12 */:
                    json.add(GetAsUInt16());
                    return;
                case DBXDataTypes.UInt32Type /* 13 */:
                    json.add(GetAsUInt32());
                    return;
                case DBXDataTypes.Int64Type /* 18 */:
                    json.add(GetAsInt64());
                    return;
                case 19:
                    json.add(GetAsUInt64());
                    return;
                case DBXDataTypes.TableType /* 23 */:
                    json.add((TJSONValue) ((JSONSerializable) this.objectValue).asJSONObject());
                    return;
                case DBXDataTypes.TimeStampType /* 24 */:
                    json.add(DBXDefaultFormatter.getInstance().DateTimeToString(this.TimeStampValue));
                    return;
                case DBXDataTypes.CurrencyType /* 25 */:
                    json.add(GetAsCurrency());
                    return;
                case DBXDataTypes.SingleType /* 27 */:
                    json.add(GetAsSingle());
                    return;
                case DBXDataTypes.Int8Type /* 28 */:
                    json.add(GetAsInt8());
                    return;
                case DBXDataTypes.UInt8Type /* 29 */:
                    json.add(GetAsUInt8());
                    return;
                case DBXDataTypes.BinaryBlobType /* 33 */:
                    json.add((TJSONValue) StreamToJson());
                    return;
                case DBXDataTypes.JsonValueType /* 37 */:
                    Object o = GetAsJSONValue().getInternalObject();
                    json.add(o);
                    return;
            }
        } catch (DBXException e) {
        }
    }

    protected TJSONArray StreamToJson() throws DBXException {
        try {
            TJSONArray jsArr = new TJSONArray();
            byte[] b = DBXTools.streamToByteArray(this.streamValue);
            for (byte b2 : b) {
                jsArr.add((int) b2);
            }
            return jsArr;
        } catch (Exception e) {
            throw new DBXException(e.getMessage());
        }
    }

    public int GetAsTDBXDate() throws DBXException {
        throw new DBXException("Invalid Type Access");
    }

    public void SetAsTDBXDate(int Value) throws DBXException {
        throw new DBXException("Invalid Type Access");
    }

    public void SetAsJSONValue(TJSONValue Value) throws DBXException {
        throw new DBXException("Invalid Type Access");
    }

    public void SetAsDBXValue(DBXValue Value) throws DBXException {
        throw new DBXException("Invalid Type Access");
    }

    public DBXValue GetAsDBXValue() throws DBXException {
        throw new DBXException("Invalid Type Access");
    }

    protected boolean containsASimpleValueType() {
        return this.isSimpleValueType;
    }

    public TJSONValue GetAsJSONValue() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public void SetAsStream(TStream Value) throws DBXException {
        throwInvalidAccess();
    }

    public TStream GetAsStream() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public long GetAsUInt64() throws DBXException {
        throwInvalidAccess();
        return 0L;
    }

    public long GetAsUInt32() throws DBXException {
        throwInvalidAccess();
        return 0L;
    }

    public void SetAsTable(TableType Value) throws DBXException {
        throwInvalidAccess();
    }

    public Object GetAsTable() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public DBXValue() {
        setDBXType(0);
    }

    protected void throwInvalidAccess() throws DBXException {
        throw new DBXException("Invalid type access");
    }

    public int getDBXType() {
        return this.CurrentDBXType;
    }

    protected void setDBXType(int value) {
        this.CurrentDBXType = value;
        this.isSimpleValueType = false;
    }

    public boolean isNull() {
        return this.CurrentDBXType == 0;
    }

    public void Clear() {
        setDBXType(0);
    }

    protected void throwInvalidValue() throws DBXException {
        throw new DBXException("Invalid value for param");
    }

    public void SetAsAnsiString(String value) throws DBXException {
        throwInvalidAccess();
    }

    public String GetAsAnsiString() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public void SetAsBoolean(boolean Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsUInt8(int Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsInt8(int Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsUInt16(int Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsInt16(int Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsInt32(int Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsInt64(long Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsString(String Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsSingle(float Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsDouble(double Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsDate(Date Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsTime(Date Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsDateTime(Date Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsTimeStamp(Date Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsBcd(double Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsCurrency(double Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsUInt32(int Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsUInt64(long Value) throws DBXException {
        throwInvalidAccess();
    }

    protected void checkCurrentDBXType(int value) throws DBXException {
        throw new DBXException("Invalid type access");
    }

    public boolean GetAsBoolean() throws DBXException {
        throwInvalidAccess();
        return false;
    }

    public int GetAsUInt8() throws DBXException {
        throwInvalidAccess();
        return 0;
    }

    public int GetAsInt8() throws DBXException {
        throwInvalidAccess();
        return 0;
    }

    public int GetAsUInt16() throws DBXException {
        throwInvalidAccess();
        return 0;
    }

    public int GetAsInt16() throws DBXException {
        throwInvalidAccess();
        return 0;
    }

    public int GetAsInt32() throws DBXException {
        throwInvalidAccess();
        return 0;
    }

    public long GetAsInt64() throws DBXException {
        throwInvalidAccess();
        return 0L;
    }

    public String GetAsString() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public float GetAsSingle() throws DBXException {
        throwInvalidAccess();
        return 0.0f;
    }

    public double GetAsDouble() throws DBXException {
        throwInvalidAccess();
        return 0.0d;
    }

    public Date GetAsDate() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public Date GetAsTimeStamp() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public Date GetAsTime() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public Date GetAsDateTime() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public double GetAsCurrency() throws DBXException {
        throwInvalidAccess();
        return 0.0d;
    }

    public double GetAsBcd() throws DBXException {
        throwInvalidAccess();
        return 0.0d;
    }

    public int GetAsTDBXTime() throws DBXException {
        throwInvalidAccess();
        return 0;
    }

    public void SetAsTDBXTime(int Value) throws DBXException {
        throwInvalidAccess();
    }

    public void SetAsBlob(TStream value) throws DBXException {
        throwInvalidAccess();
    }

    public TStream GetAsBlob() throws DBXException {
        throwInvalidAccess();
        return null;
    }

    public String toString() {
        try {
            if (containsASimpleValueType()) {
                if (GetAsDBXValue().isNull()) {
                    return new TJSONNull().asJSONString();
                }
                return GetAsDBXValue().toString();
            }
            switch (this.CurrentDBXType) {
                case 1:
                    return DBXDefaultFormatter.getInstance().AnsiStringToString(GetAsString());
                case 2:
                    return DBXDefaultFormatter.getInstance().TDBXDateToString(GetAsTDBXDate());
                case 3:
                case 9:
                case 14:
                case 15:
                case 16:
                case 17:
                case DBXDataTypes.AdtType /* 20 */:
                case 21:
                case DBXDataTypes.RefType /* 22 */:
                case DBXDataTypes.TableType /* 23 */:
                default:
                    throw new DBXException("Cannot convert this type to string");
                case 4:
                    return String.valueOf(GetAsBoolean());
                case 5:
                    return DBXDefaultFormatter.getInstance().Int16ToString(GetAsInt16());
                case 6:
                    return DBXDefaultFormatter.getInstance().Int32ToString(GetAsInt32());
                case 7:
                    return DBXDefaultFormatter.getInstance().doubleToString(GetAsDouble());
                case 8:
                    return String.valueOf(GetAsBcd());
                case 10:
                    return DBXDefaultFormatter.getInstance().TDBXTimeToString(GetAsTDBXTime());
                case DBXDataTypes.DateTimeType /* 11 */:
                    return DBXDefaultFormatter.getInstance().DateTimeToString(GetAsDateTime());
                case DBXDataTypes.UInt16Type /* 12 */:
                    return DBXDefaultFormatter.getInstance().UInt16ToString(GetAsUInt16());
                case DBXDataTypes.UInt32Type /* 13 */:
                    return DBXDefaultFormatter.getInstance().UInt32ToString(GetAsUInt32());
                case DBXDataTypes.Int64Type /* 18 */:
                    return DBXDefaultFormatter.getInstance().Int64ToString(GetAsInt64());
                case 19:
                    return DBXDefaultFormatter.getInstance().UInt64ToString(GetAsUInt64());
                case DBXDataTypes.TimeStampType /* 24 */:
                    return DBXDefaultFormatter.getInstance().DateTimeToString(GetAsTimeStamp());
                case DBXDataTypes.CurrencyType /* 25 */:
                    return DBXDefaultFormatter.getInstance().currencyToString(GetAsCurrency());
                case DBXDataTypes.WideStringType /* 26 */:
                    return DBXDefaultFormatter.getInstance().WideStringToString(GetAsString());
                case DBXDataTypes.SingleType /* 27 */:
                    return DBXDefaultFormatter.getInstance().floatToString(GetAsSingle());
                case DBXDataTypes.Int8Type /* 28 */:
                    return DBXDefaultFormatter.getInstance().Int8ToString(GetAsInt8());
                case DBXDataTypes.UInt8Type /* 29 */:
                    return String.valueOf(GetAsUInt8());
            }
        } catch (DBXException e) {
            return "<CANNOT CONVERT DBXType [" + String.valueOf(this.CurrentDBXType) + "] TO STRING>";
        }
    }

    public void SetTDBXNull(String TypeName) throws DBXException {
        throwInvalidAccess();
    }

    public void setNull() throws DBXException {
        throwInvalidAccess();
    }
}
