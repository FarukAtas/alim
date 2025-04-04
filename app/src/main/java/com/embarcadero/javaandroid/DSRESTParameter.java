package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class DSRESTParameter {
    public int Direction;
    public String Name;
    public String TypeName;
    private DBXValue value = new DBXWritableValue();

    public int getDBXType() {
        return getValue().getDBXType();
    }

    public DBXValue getValue() {
        return this.value;
    }

    public DSRESTParameter(String Name, int Direction, int DBXType, String TypeName) {
        this.Name = Name;
        this.Direction = Direction;
        this.TypeName = TypeName;
        this.value.setDBXType(DBXType);
    }
}
