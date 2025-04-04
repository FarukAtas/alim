package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class DBXValueType {
    private String Caption;
    private int ChildPosition;
    private boolean Hidden;
    private boolean Literal;
    private String Name;
    private boolean Nullable;
    private int Ordinal;
    private int ParameterDirection;
    private long Precision;
    private int Scale;
    private long Size;
    private int SubType;
    private boolean ValueParameter;

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }

    public void setCaption(String caption) {
        this.Caption = caption;
    }

    public String getCaption() {
        return this.Caption;
    }

    public void setOrdinal(int ordinal) {
        this.Ordinal = ordinal;
    }

    public int getOrdinal() {
        return this.Ordinal;
    }

    public void setDataType(int dataType) throws DBXException {
        throw new DBXException("Must be overridden in the descendant classes");
    }

    public int getDataType() throws DBXException {
        throw new DBXException("Must be overridden in the descendant classes");
    }

    public void setSubType(int subType) {
        this.SubType = subType;
    }

    public int getSubType() {
        return this.SubType;
    }

    public void setSize(long size) {
        this.Size = size;
    }

    public long getSize() {
        return this.Size;
    }

    public void setPrecision(long precision) {
        this.Precision = precision;
    }

    public long getPrecision() {
        return this.Precision;
    }

    public void setScale(int scale) {
        this.Scale = scale;
    }

    public int getScale() {
        return this.Scale;
    }

    public void setChildPosition(int childPosition) {
        this.ChildPosition = childPosition;
    }

    public int getChildPosition() {
        return this.ChildPosition;
    }

    public void setParameterDirection(int parameterDirection) {
        this.ParameterDirection = parameterDirection;
    }

    public int getParameterDirection() {
        return this.ParameterDirection;
    }

    public boolean getNullable() {
        return this.Nullable;
    }

    public void setNullable(boolean nullable) {
        this.Nullable = nullable;
    }

    public boolean getHidden() {
        return this.Hidden;
    }

    public void setHidden(boolean hidden) {
        this.Hidden = hidden;
    }

    public boolean getValueParameter() {
        return this.ValueParameter;
    }

    public void setValueParameter(boolean valueParameter) {
        this.ValueParameter = valueParameter;
    }

    public boolean getLiteral() {
        return this.Literal;
    }

    public void setLiteral(boolean literal) {
        this.Literal = literal;
    }
}
