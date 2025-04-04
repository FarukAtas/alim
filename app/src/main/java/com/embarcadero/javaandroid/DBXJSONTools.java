package com.embarcadero.javaandroid;

import java.util.Date;

/* loaded from: classes.dex */
public class DBXJSONTools {
    public static DBXValueType JSONToValueType(TJSONArray json) throws DBXException {
        DBXValueType vt = new DBXValueType();
        JSONToValueType(json, vt);
        return vt;
    }

    public static void JSONToValueType(TJSONArray json, DBXValueType vt) throws DBXException {
        vt.setName(json.getString(0));
        vt.setDataType(json.getInt(1).intValue());
        vt.setOrdinal(json.getInt(2).intValue());
        vt.setSubType(json.getInt(3).intValue());
        vt.setScale(json.getInt(4).intValue());
        vt.setSize(json.getInt(5).intValue());
        vt.setPrecision(json.getInt(6).intValue());
        vt.setChildPosition(json.getInt(7).intValue());
        vt.setNullable(json.getBoolean(8).booleanValue());
        vt.setHidden(json.getBoolean(9).booleanValue());
        vt.setParameterDirection(json.getInt(10).intValue());
        vt.setValueParameter(json.getBoolean(11).booleanValue());
        vt.setLiteral(json.getBoolean(12).booleanValue());
    }

    public static TJSONArray ValueTypeToJSON(DBXValueType DataType) throws DBXException {
        TJSONArray Meta = new TJSONArray();
        Meta.add(DataType.getName());
        Meta.add(DataType.getDataType());
        Meta.add(DataType.getOrdinal());
        Meta.add(DataType.getSubType());
        Meta.add(DataType.getScale());
        Meta.add(DataType.getSize());
        Meta.add(DataType.getPrecision());
        Meta.add(DataType.getChildPosition());
        Meta.add(DataType.getNullable());
        Meta.add(DataType.getHidden());
        Meta.add(DataType.getParameterDirection());
        Meta.add(DataType.getValueParameter());
        Meta.add(DataType.getLiteral());
        return Meta;
    }

    public static Object JSONToTableType(Object value, String DBXTypeName) throws DBXException {
        if (DBXTypeName.equals("TParams")) {
            return TParams.CreateFrom((TJSONObject) value);
        }
        if (DBXTypeName.equals("TDBXReader") || DBXTypeName.equals("TDBXReaderValue")) {
            return TDBXReader.createFrom((TJSONObject) value);
        }
        if (DBXTypeName.equals("TDataSet")) {
            return TDataSet.m1createFrom((TJSONObject) value);
        }
        throw new DBXException(String.valueOf(DBXTypeName) + " is not a table type");
    }

    public static TJSONObject DBXParametersToJSONObject(TParams dbxParameters) {
        TJSONObject tJSONObject = new TJSONObject();
        TJSONArray arrParameters = new TJSONArray();
        for (int i = 0; i < dbxParameters.size(); i++) {
            TJSONArray arrParam = new TJSONArray();
            arrParam.add(dbxParameters.getParameter(i).getName());
            arrParam.add(dbxParameters.getParameter(i).getDataType());
            arrParam.add(dbxParameters.getParameter(i).getOrdinal());
            arrParam.add(dbxParameters.getParameter(i).getSubType());
            arrParam.add(dbxParameters.getParameter(i).getScale());
            arrParam.add(dbxParameters.getParameter(i).getSize());
            arrParam.add(dbxParameters.getParameter(i).getPrecision());
            arrParam.add(dbxParameters.getParameter(i).getChildPosition());
            arrParam.add(dbxParameters.getParameter(i).getNullable());
            arrParam.add(dbxParameters.getParameter(i).getHidden());
            arrParam.add(dbxParameters.getParameter(i).getParameterDirection());
            arrParam.add(dbxParameters.getParameter(i).getValueParameter());
            arrParam.add(dbxParameters.getParameter(i).getLiteral());
            arrParameters.add((TJSONValue) arrParam);
        }
        tJSONObject.addPairs("table", arrParameters);
        for (int i2 = 0; i2 < dbxParameters.size(); i2++) {
            TJSONArray arrParamsValue = new TJSONArray();
            arrParamsValue.add(dbxParameters.getParameter(i2).getValue().toString());
            tJSONObject.addPairs(dbxParameters.getParameter(i2).getName(), arrParamsValue);
        }
        return tJSONObject;
    }

    public static TJSONObject DBXReaderToJSONObject(TDBXReader dbxReader) throws DBXException {
        TJSONObject json = new TJSONObject();
        TParams columns = dbxReader.getColumns();
        TJSONArray arrayParams = new TJSONArray();
        for (int i = 0; i < columns.size(); i++) {
            arrayParams.add((TJSONValue) columns.getParameter(i).tojson());
            json.addPairs(columns.getParameter(i).getName(), new TJSONArray());
        }
        while (dbxReader.next()) {
            for (int c = 0; c < columns.size(); c++) {
                dbxReader.getColumns().getParameter(c).getValue().appendTo(json.getJSONArray(columns.getParameter(c).getName()));
            }
        }
        json.addPairs("table", arrayParams);
        return json;
    }

    public static TStream JSONToStream(TJSONArray value) throws DBXException {
        return TStream.CreateFrom(value);
    }

    public static void JSONtoDBX(Object o, DBXValue value, String DBXTypeName) throws DBXException {
        try {
            if (DBXTypeName.startsWith("TDBX") && DBXTypeName.endsWith("Value") && (o instanceof TJSONNull)) {
                value.GetAsDBXValue().setNull();
                return;
            }
            if (!(o instanceof TJSONNull) || !DBXTypeName.equals("")) {
                switch (value.getDBXType()) {
                    case 1:
                        if (DBXTypeName.equals("TDBXAnsiStringValue") || DBXTypeName.equals("TDBXStringValue") || DBXTypeName.equals("TDBXAnsiCharsValue")) {
                            value.GetAsDBXValue().SetAsAnsiString(((TJSONString) o).toString());
                            return;
                        } else {
                            value.SetAsAnsiString(((TJSONString) o).toString());
                            return;
                        }
                    case 2:
                        int t = DBXDefaultFormatter.getInstance().StringToTDBXDate(((TJSONString) o).toString());
                        if (DBXTypeName.equals("TDBXDateValue")) {
                            value.GetAsDBXValue().SetAsTDBXDate(t);
                            return;
                        } else {
                            value.SetAsTDBXDate(t);
                            return;
                        }
                    case 3:
                        value.SetAsBlob(JSONToStream((TJSONArray) o));
                        return;
                    case 4:
                        boolean val = ((Boolean) ((TJSONValue) o).getInternalObject()).booleanValue();
                        if (DBXTypeName.equals("TDBXBooleanValue")) {
                            value.GetAsDBXValue().SetAsBoolean(val);
                            return;
                        } else {
                            value.SetAsBoolean(val);
                            return;
                        }
                    case 5:
                        if (DBXTypeName.equals("TDBXInt16Value")) {
                            value.GetAsDBXValue().SetAsInt16(((TJSONNumber) o).getValue().intValue());
                            return;
                        } else {
                            value.SetAsInt16(((TJSONNumber) o).getValue().intValue());
                            return;
                        }
                    case 6:
                        if (DBXTypeName.equals("TDBXInt32Value")) {
                            value.GetAsDBXValue().SetAsInt32(((TJSONNumber) o).getValue().intValue());
                            return;
                        } else {
                            value.SetAsInt32(((TJSONNumber) o).getValue().intValue());
                            return;
                        }
                    case 7:
                        if (DBXTypeName.equals("TDBXDoubleValue")) {
                            value.GetAsDBXValue().SetAsDouble(((TJSONNumber) o).getValue().doubleValue());
                            return;
                        } else {
                            value.SetAsDouble(((TJSONNumber) o).getValue().doubleValue());
                            return;
                        }
                    case 8:
                        if (DBXTypeName.equals("TDBXBcdValue")) {
                            value.GetAsDBXValue().SetAsBcd(DBXDefaultFormatter.getInstance().StringToDouble(o.toString()));
                            return;
                        } else {
                            value.SetAsBcd(DBXDefaultFormatter.getInstance().StringToDouble(o.toString()));
                            return;
                        }
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
                        throw new DBXException("Cannot convert datatype " + String.valueOf(value.getDBXType()));
                    case 10:
                        int t2 = DBXDefaultFormatter.getInstance().StringToTDBXTime(((TJSONString) o).toString());
                        if (DBXTypeName.equals("TDBXTimeValue")) {
                            value.GetAsDBXValue().SetAsTDBXTime(t2);
                            return;
                        } else {
                            value.SetAsTDBXTime(t2);
                            return;
                        }
                    case DBXDataTypes.DateTimeType /* 11 */:
                        Date d = DBXDefaultFormatter.getInstance().StringToDateTime(((TJSONString) o).toString());
                        if (d == null) {
                            throw new DBXException("Invalid date");
                        }
                        value.SetAsDateTime(d);
                        return;
                    case DBXDataTypes.UInt16Type /* 12 */:
                        if (DBXTypeName.equals("TDBXUInt16Value")) {
                            value.GetAsDBXValue().SetAsUInt16(((TJSONNumber) o).getValue().intValue());
                            return;
                        } else {
                            value.SetAsUInt16(((TJSONNumber) o).getValue().intValue());
                            return;
                        }
                    case DBXDataTypes.UInt32Type /* 13 */:
                        if (DBXTypeName.equals("TDBXUInt32Value")) {
                            value.GetAsDBXValue().SetAsUInt32(((TJSONNumber) o).getValue().intValue());
                            return;
                        } else {
                            value.SetAsUInt32(((TJSONNumber) o).getValue().intValue());
                            return;
                        }
                    case DBXDataTypes.Int64Type /* 18 */:
                        if (DBXTypeName.equals("TDBXInt64Value")) {
                            value.GetAsDBXValue().SetAsInt64(((TJSONNumber) o).getValue().longValue());
                            return;
                        } else {
                            value.SetAsInt64(((TJSONNumber) o).getValue().longValue());
                            return;
                        }
                    case 19:
                        if (DBXTypeName.equals("TDBXUInt64Value")) {
                            value.GetAsDBXValue().SetAsUInt64(((TJSONNumber) o).getValue().longValue());
                            return;
                        } else {
                            value.SetAsUInt64(((TJSONNumber) o).getValue().longValue());
                            return;
                        }
                    case DBXDataTypes.TableType /* 23 */:
                        if (DBXTypeName.equals("TDBXReaderValue")) {
                            value.GetAsDBXValue().SetAsTable((TableType) JSONToTableType(o, DBXTypeName));
                            return;
                        } else {
                            value.SetAsTable((TableType) JSONToTableType(o, DBXTypeName));
                            return;
                        }
                    case DBXDataTypes.TimeStampType /* 24 */:
                        Date d2 = DBXDefaultFormatter.getInstance().StringToDateTime(((TJSONString) o).toString());
                        if (d2 == null) {
                            throw new DBXException("Invalid date");
                        }
                        if (DBXTypeName.equals("TDBXTimeStampValue")) {
                            value.GetAsDBXValue().SetAsTimeStamp(d2);
                            return;
                        } else {
                            value.SetAsTimeStamp(d2);
                            return;
                        }
                    case DBXDataTypes.CurrencyType /* 25 */:
                        value.SetAsCurrency(((TJSONNumber) o).getValue().doubleValue());
                        return;
                    case DBXDataTypes.WideStringType /* 26 */:
                        if (DBXTypeName.equals("TDBXAnsiStringValue") || DBXTypeName.equals("TDBXStringValue") || DBXTypeName.equals("TDBXAnsiCharsValue") || DBXTypeName.equals("TDBXWideStringValue")) {
                            value.GetAsDBXValue().SetAsString(((TJSONString) o).toString());
                            return;
                        } else {
                            value.SetAsString(((TJSONString) o).toString());
                            return;
                        }
                    case DBXDataTypes.SingleType /* 27 */:
                        if (DBXTypeName.equals("TDBXSingleValue")) {
                            value.GetAsDBXValue().SetAsSingle(((TJSONNumber) o).getValue().floatValue());
                            return;
                        } else {
                            value.SetAsSingle(((TJSONNumber) o).getValue().floatValue());
                            return;
                        }
                    case DBXDataTypes.Int8Type /* 28 */:
                        if (DBXTypeName.equals("TDBXInt8Value")) {
                            value.GetAsDBXValue().SetAsInt8(((TJSONNumber) o).getValue().intValue());
                            return;
                        } else {
                            value.SetAsInt8(((TJSONNumber) o).getValue().intValue());
                            return;
                        }
                    case DBXDataTypes.UInt8Type /* 29 */:
                        if (DBXTypeName.equals("TDBXUInt8Value")) {
                            value.GetAsDBXValue().SetAsUInt8(((TJSONNumber) o).getValue().intValue());
                            return;
                        } else {
                            value.SetAsUInt8(((TJSONNumber) o).getValue().intValue());
                            return;
                        }
                    case DBXDataTypes.BinaryBlobType /* 33 */:
                        if (DBXTypeName.equals("TDBXStreamValue")) {
                            value.GetAsDBXValue().SetAsStream(JSONToStream((TJSONArray) o));
                            return;
                        } else {
                            value.SetAsStream(JSONToStream((TJSONArray) o));
                            return;
                        }
                    case DBXDataTypes.JsonValueType /* 37 */:
                        value.SetAsJSONValue(JSONToJSONValue(o));
                        return;
                }
            }
            value.Clear();
        } catch (Exception ex) {
            throw new DBXException(ex.getMessage());
        }
    }

    private static TJSONValue JSONToJSONValue(Object o) throws DBXException {
        if (o instanceof TJSONNull) {
            return new TJSONNull();
        }
        if (o instanceof TJSONObject) {
            return TJSONObject.Parse(o.toString());
        }
        if (o instanceof TJSONArray) {
            return TJSONArray.Parse(o.toString());
        }
        if (o instanceof TJSONTrue) {
            return new TJSONTrue();
        }
        if (o instanceof TJSONFalse) {
            return new TJSONFalse();
        }
        throw new DBXException(String.valueOf(o.getClass().toString()) + " is not a valid JSONValue");
    }
}
