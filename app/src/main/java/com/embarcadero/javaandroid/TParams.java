package com.embarcadero.javaandroid;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class TParams implements JSONSerializable, TableType {
    private List<DBXParameter> Params = new ArrayList();

    public DBXParameter findParamByName(String Value) {
        for (DBXParameter p : this.Params) {
            if (p.getName().equals(Value)) {
                return p;
            }
        }
        return null;
    }

    public DBXParameter getParamByName(String Value) throws DBXException {
        DBXParameter p = findParamByName(Value);
        if (p != null) {
            return p;
        }
        throw new DBXException("Parameter not found [" + Value + "]");
    }

    public TParams addParameter(DBXParameter parameter) throws DBXException {
        if (findParamByName(parameter.getName()) == null) {
            this.Params.add(parameter);
            return this;
        }
        throw new DBXException("Parameter name must be unique");
    }

    public DBXParameter getParameter(int Index) {
        return this.Params.get(Index);
    }

    public int size() {
        return this.Params.size();
    }

    public static TParams CreateFrom(TJSONObject value) throws DBXException {
        TParams params = CreateParametersFromMetadata(value.getJSONArray("table"));
        LoadParametersValues(params, value);
        return params;
    }

    public static boolean LoadParametersValues(TParams params, TJSONObject value, int Offset) throws DBXException {
        if (params.size() <= 0) {
            return false;
        }
        for (int i = 0; i < params.size(); i++) {
            DBXParameter par = params.getParameter(i);
            TJSONArray parValue = value.getJSONArray(par.getName());
            if (parValue.size() < Offset + 1) {
                return false;
            }
            DBXJSONTools.JSONtoDBX(parValue.get(Offset), par.getValue(), "");
        }
        return true;
    }

    public static void LoadParametersValues(TParams params, TJSONObject value) throws DBXException {
        LoadParametersValues(params, value, 0);
    }

    public static TParams CreateParametersFromMetadata(TJSONArray paramsMetadata) throws DBXException {
        TParams o = new TParams();
        for (int i = 0; i < paramsMetadata.size(); i++) {
            TJSONArray paramMetadata = paramsMetadata.getJSONArray(i);
            DBXParameter parameter = new DBXParameter();
            DBXJSONTools.JSONToValueType(paramMetadata, parameter);
            o.addParameter(parameter);
        }
        return o;
    }

    @Override // com.embarcadero.javaandroid.JSONSerializable
    public TJSONObject asJSONObject() throws DBXException {
        return DBXJSONTools.DBXParametersToJSONObject(this);
    }
}
