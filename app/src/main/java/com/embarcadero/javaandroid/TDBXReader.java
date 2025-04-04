package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class TDBXReader implements JSONSerializable, TableType {
    private TParams columns;
    protected long currentPosition = -1;
    private TJSONObject internalDataStore;

    protected void setParameters(TParams params) {
        this.columns = params;
    }

    public TDBXReader(TParams params, TJSONObject value) {
        this.internalDataStore = value;
        setParameters(params);
    }

    public TParams getColumns() {
        return this.columns;
    }

    public DBXWritableValue getValue(int position) {
        return this.columns.getParameter(position).getValue();
    }

    public DBXWritableValue getValue(String name) throws DBXException {
        return this.columns.getParamByName(name).getValue();
    }

    public boolean next() {
        this.currentPosition++;
        try {
            return TParams.LoadParametersValues(this.columns, this.internalDataStore, (int) this.currentPosition);
        } catch (Exception e) {
            return false;
        }
    }

    public static TDBXReader createFrom(TJSONObject value) throws DBXException {
        TParams params = TParams.CreateParametersFromMetadata(value.getJSONArray("table"));
        TDBXReader rdr = new TDBXReader(params, value);
        return rdr;
    }

    @Override // com.embarcadero.javaandroid.JSONSerializable
    public TJSONObject asJSONObject() throws DBXException {
        long lastPosition = this.currentPosition;
        try {
            reset();
            TJSONObject result = DBXJSONTools.DBXReaderToJSONObject(this);
            return result;
        } finally {
            this.currentPosition = lastPosition;
        }
    }

    public void reset() {
        this.currentPosition = -1L;
    }
}
