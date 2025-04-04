package com.embarcadero.javaandroid;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;

/* loaded from: classes.dex */
public class DSRESTCommand {
    DSAdmin Admin;
    private DSRESTConnection Connection;
    private String FullyQualifiedMethodName;
    private DSHTTPRequestType RequestType;
    private List<DSRESTParameter> parameters = null;

    public DSRESTCommand(DSRESTConnection Connection) {
        this.Connection = null;
        this.Admin = null;
        this.Connection = Connection;
        this.Admin = new DSAdmin(Connection);
    }

    public void setRequestType(DSHTTPRequestType RequestType) {
        this.RequestType = RequestType;
    }

    public DSHTTPRequestType getRequestType() {
        return this.RequestType;
    }

    public void setText(String FullyQualifiedMethodName) {
        this.FullyQualifiedMethodName = FullyQualifiedMethodName;
    }

    public String getText() {
        return this.FullyQualifiedMethodName;
    }

    public void prepare(DSRESTParameterMetaData[] metadatas) {
        this.parameters = new LinkedList();
        for (DSRESTParameterMetaData param : metadatas) {
            DSRESTParameter p = new DSRESTParameter(param.Name, param.Direction, param.DBXType, param.TypeName);
            this.parameters.add(p);
        }
    }

    public void prepare() throws DBXException {
        String LMethodName = getText();
        TDBXReader MetaDatas = this.Admin.GetServerMethodParameters();
        this.parameters = new LinkedList();
        boolean Cicla = true;
        boolean IsEqual = false;
        while (MetaDatas.next()) {
            if (!Cicla || !IsEqual) {
                if (LMethodName.equals(MetaDatas.getValue("MethodAlias").GetAsString())) {
                    IsEqual = true;
                    Cicla = false;
                    if (MetaDatas.getValue("DBXType").GetAsInt32() > 0) {
                        DSRESTParameter p = new DSRESTParameter(MetaDatas.getValue("Name").GetAsString(), MetaDatas.getValue("DBXParameterDirection").GetAsInt32(), MetaDatas.getValue("DBXType").GetAsInt32(), MetaDatas.getValue("ParameterTypeName").GetAsString());
                        this.parameters.add(p);
                    }
                } else {
                    Cicla = true;
                }
            } else {
                return;
            }
        }
    }

    public List<DSRESTParameter> getParameters() {
        return this.parameters;
    }

    public DSRESTParameter getParameter(int index) {
        return this.parameters.get(index);
    }

    public DSRESTParameter getParameter(String ParamName) {
        for (DSRESTParameter p : getParameters()) {
            if (ParamName.equals(p.Name)) {
                return p;
            }
        }
        return null;
    }

    public void execute() throws ClientProtocolException, IOException, DBXException {
        this.Connection.execute(this);
    }
}
