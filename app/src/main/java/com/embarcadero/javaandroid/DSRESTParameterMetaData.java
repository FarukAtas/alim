package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class DSRESTParameterMetaData {
    public int DBXType;
    public int Direction;
    public String Name;
    public String TypeName;

    public DSRESTParameterMetaData(String Name, int Direction, int DBXType, String TypeName) {
        this.Name = Name;
        this.Direction = Direction;
        this.DBXType = DBXType;
        this.TypeName = TypeName;
    }
}
