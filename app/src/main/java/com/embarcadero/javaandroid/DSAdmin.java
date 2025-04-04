package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class DSAdmin {
    private DSRESTParameterMetaData[] DSAdmin_BroadcastObjectToChannel_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_BroadcastToChannel_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_ClearResources_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_CloseClientChannel_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_ConsumeClientChannelTimeout_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_ConsumeClientChannel_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_CreateServerClasses_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_CreateServerMethods_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_DescribeClass_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_DescribeMethod_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_DropServerClasses_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_DropServerMethods_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_FindClasses_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_FindMethods_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_FindPackages_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_GetDSServerName_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_GetDatabaseConnectionProperties_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_GetPlatformName_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_GetServerClasses_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_GetServerMethodParameters_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_GetServerMethods_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_ListClasses_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_ListMethods_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_NotifyCallback_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_NotifyObject_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_RegisterClientCallbackServer_Metadata;
    private DSRESTParameterMetaData[] DSAdmin_UnregisterClientCallback_Metadata;
    private DSRESTConnection connection;

    public static class NotifyCallbackReturns {
        public TJSONValue Response;
        public boolean returnValue;
    }

    public static class NotifyObjectReturns {
        public TJSONObject Response;
        public boolean returnValue;
    }

    public DSRESTConnection getConnection() {
        return this.connection;
    }

    public DSAdmin(DSRESTConnection Connection) {
        this.connection = Connection;
    }

    private DSRESTParameterMetaData[] get_DSAdmin_GetPlatformName_Metadata() {
        if (this.DSAdmin_GetPlatformName_Metadata == null) {
            this.DSAdmin_GetPlatformName_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 26, "string")};
        }
        return this.DSAdmin_GetPlatformName_Metadata;
    }

    public String GetPlatformName() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.GetPlatformName");
        cmd.prepare(get_DSAdmin_GetPlatformName_Metadata());
        getConnection().execute(cmd);
        return cmd.getParameter(0).getValue().GetAsString();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_ClearResources_Metadata() {
        if (this.DSAdmin_ClearResources_Metadata == null) {
            this.DSAdmin_ClearResources_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 4, "Boolean")};
        }
        return this.DSAdmin_ClearResources_Metadata;
    }

    public boolean ClearResources() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.ClearResources");
        cmd.prepare(get_DSAdmin_ClearResources_Metadata());
        getConnection().execute(cmd);
        return cmd.getParameter(0).getValue().GetAsBoolean();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_FindPackages_Metadata() {
        if (this.DSAdmin_FindPackages_Metadata == null) {
            this.DSAdmin_FindPackages_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
        }
        return this.DSAdmin_FindPackages_Metadata;
    }

    public TDBXReader FindPackages() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.FindPackages");
        cmd.prepare(get_DSAdmin_FindPackages_Metadata());
        getConnection().execute(cmd);
        return (TDBXReader) cmd.getParameter(0).getValue().GetAsTable();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_FindClasses_Metadata() {
        if (this.DSAdmin_FindClasses_Metadata == null) {
            this.DSAdmin_FindClasses_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("PackageName", 1, 26, "string"), new DSRESTParameterMetaData("ClassPattern", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
        }
        return this.DSAdmin_FindClasses_Metadata;
    }

    public TDBXReader FindClasses(String PackageName, String ClassPattern) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.FindClasses");
        cmd.prepare(get_DSAdmin_FindClasses_Metadata());
        cmd.getParameter(0).getValue().SetAsString(PackageName);
        cmd.getParameter(1).getValue().SetAsString(ClassPattern);
        getConnection().execute(cmd);
        return (TDBXReader) cmd.getParameter(2).getValue().GetAsTable();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_FindMethods_Metadata() {
        if (this.DSAdmin_FindMethods_Metadata == null) {
            this.DSAdmin_FindMethods_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("PackageName", 1, 26, "string"), new DSRESTParameterMetaData("ClassPattern", 1, 26, "string"), new DSRESTParameterMetaData("MethodPattern", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
        }
        return this.DSAdmin_FindMethods_Metadata;
    }

    public TDBXReader FindMethods(String PackageName, String ClassPattern, String MethodPattern) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.FindMethods");
        cmd.prepare(get_DSAdmin_FindMethods_Metadata());
        cmd.getParameter(0).getValue().SetAsString(PackageName);
        cmd.getParameter(1).getValue().SetAsString(ClassPattern);
        cmd.getParameter(2).getValue().SetAsString(MethodPattern);
        getConnection().execute(cmd);
        return (TDBXReader) cmd.getParameter(3).getValue().GetAsTable();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_CreateServerClasses_Metadata() {
        if (this.DSAdmin_CreateServerClasses_Metadata == null) {
            this.DSAdmin_CreateServerClasses_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ClassReader", 1, 23, "TDBXReader")};
        }
        return this.DSAdmin_CreateServerClasses_Metadata;
    }

    public void CreateServerClasses(TDBXReader ClassReader) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.CreateServerClasses");
        cmd.prepare(get_DSAdmin_CreateServerClasses_Metadata());
        cmd.getParameter(0).getValue().SetAsTable(ClassReader);
        getConnection().execute(cmd);
    }

    private DSRESTParameterMetaData[] get_DSAdmin_DropServerClasses_Metadata() {
        if (this.DSAdmin_DropServerClasses_Metadata == null) {
            this.DSAdmin_DropServerClasses_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ClassReader", 1, 23, "TDBXReader")};
        }
        return this.DSAdmin_DropServerClasses_Metadata;
    }

    public void DropServerClasses(TDBXReader ClassReader) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.DropServerClasses");
        cmd.prepare(get_DSAdmin_DropServerClasses_Metadata());
        cmd.getParameter(0).getValue().SetAsTable(ClassReader);
        getConnection().execute(cmd);
    }

    private DSRESTParameterMetaData[] get_DSAdmin_CreateServerMethods_Metadata() {
        if (this.DSAdmin_CreateServerMethods_Metadata == null) {
            this.DSAdmin_CreateServerMethods_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("MethodReader", 1, 23, "TDBXReader")};
        }
        return this.DSAdmin_CreateServerMethods_Metadata;
    }

    public void CreateServerMethods(TDBXReader MethodReader) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.CreateServerMethods");
        cmd.prepare(get_DSAdmin_CreateServerMethods_Metadata());
        cmd.getParameter(0).getValue().SetAsTable(MethodReader);
        getConnection().execute(cmd);
    }

    private DSRESTParameterMetaData[] get_DSAdmin_DropServerMethods_Metadata() {
        if (this.DSAdmin_DropServerMethods_Metadata == null) {
            this.DSAdmin_DropServerMethods_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("MethodReader", 1, 23, "TDBXReader")};
        }
        return this.DSAdmin_DropServerMethods_Metadata;
    }

    public void DropServerMethods(TDBXReader MethodReader) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.DropServerMethods");
        cmd.prepare(get_DSAdmin_DropServerMethods_Metadata());
        cmd.getParameter(0).getValue().SetAsTable(MethodReader);
        getConnection().execute(cmd);
    }

    private DSRESTParameterMetaData[] get_DSAdmin_GetServerClasses_Metadata() {
        if (this.DSAdmin_GetServerClasses_Metadata == null) {
            this.DSAdmin_GetServerClasses_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
        }
        return this.DSAdmin_GetServerClasses_Metadata;
    }

    public TDBXReader GetServerClasses() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.GetServerClasses");
        cmd.prepare(get_DSAdmin_GetServerClasses_Metadata());
        getConnection().execute(cmd);
        return (TDBXReader) cmd.getParameter(0).getValue().GetAsTable();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_ListClasses_Metadata() {
        if (this.DSAdmin_ListClasses_Metadata == null) {
            this.DSAdmin_ListClasses_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 37, "TJSONArray")};
        }
        return this.DSAdmin_ListClasses_Metadata;
    }

    public TJSONArray ListClasses() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.ListClasses");
        cmd.prepare(get_DSAdmin_ListClasses_Metadata());
        getConnection().execute(cmd);
        return (TJSONArray) cmd.getParameter(0).getValue().GetAsJSONValue();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_DescribeClass_Metadata() {
        if (this.DSAdmin_DescribeClass_Metadata == null) {
            this.DSAdmin_DescribeClass_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ClassName", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 37, "TJSONObject")};
        }
        return this.DSAdmin_DescribeClass_Metadata;
    }

    public TJSONObject DescribeClass(String ClassName) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.DescribeClass");
        cmd.prepare(get_DSAdmin_DescribeClass_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ClassName);
        getConnection().execute(cmd);
        return (TJSONObject) cmd.getParameter(1).getValue().GetAsJSONValue();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_ListMethods_Metadata() {
        if (this.DSAdmin_ListMethods_Metadata == null) {
            this.DSAdmin_ListMethods_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ClassName", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 37, "TJSONArray")};
        }
        return this.DSAdmin_ListMethods_Metadata;
    }

    public TJSONArray ListMethods(String ClassName) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.ListMethods");
        cmd.prepare(get_DSAdmin_ListMethods_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ClassName);
        getConnection().execute(cmd);
        return (TJSONArray) cmd.getParameter(1).getValue().GetAsJSONValue();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_DescribeMethod_Metadata() {
        if (this.DSAdmin_DescribeMethod_Metadata == null) {
            this.DSAdmin_DescribeMethod_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ServerMethodName", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 37, "TJSONObject")};
        }
        return this.DSAdmin_DescribeMethod_Metadata;
    }

    public TJSONObject DescribeMethod(String ServerMethodName) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.DescribeMethod");
        cmd.prepare(get_DSAdmin_DescribeMethod_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ServerMethodName);
        getConnection().execute(cmd);
        return (TJSONObject) cmd.getParameter(1).getValue().GetAsJSONValue();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_GetServerMethods_Metadata() {
        if (this.DSAdmin_GetServerMethods_Metadata == null) {
            this.DSAdmin_GetServerMethods_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
        }
        return this.DSAdmin_GetServerMethods_Metadata;
    }

    public TDBXReader GetServerMethods() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.GetServerMethods");
        cmd.prepare(get_DSAdmin_GetServerMethods_Metadata());
        getConnection().execute(cmd);
        return (TDBXReader) cmd.getParameter(0).getValue().GetAsTable();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_GetServerMethodParameters_Metadata() {
        if (this.DSAdmin_GetServerMethodParameters_Metadata == null) {
            this.DSAdmin_GetServerMethodParameters_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
        }
        return this.DSAdmin_GetServerMethodParameters_Metadata;
    }

    public TDBXReader GetServerMethodParameters() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.GetServerMethodParameters");
        cmd.prepare(get_DSAdmin_GetServerMethodParameters_Metadata());
        getConnection().execute(cmd);
        return (TDBXReader) cmd.getParameter(0).getValue().GetAsTable();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_GetDatabaseConnectionProperties_Metadata() {
        if (this.DSAdmin_GetDatabaseConnectionProperties_Metadata == null) {
            this.DSAdmin_GetDatabaseConnectionProperties_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
        }
        return this.DSAdmin_GetDatabaseConnectionProperties_Metadata;
    }

    public TDBXReader GetDatabaseConnectionProperties() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.GetDatabaseConnectionProperties");
        cmd.prepare(get_DSAdmin_GetDatabaseConnectionProperties_Metadata());
        getConnection().execute(cmd);
        return (TDBXReader) cmd.getParameter(0).getValue().GetAsTable();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_GetDSServerName_Metadata() {
        if (this.DSAdmin_GetDSServerName_Metadata == null) {
            this.DSAdmin_GetDSServerName_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 26, "string")};
        }
        return this.DSAdmin_GetDSServerName_Metadata;
    }

    public String GetDSServerName() throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.GetDSServerName");
        cmd.prepare(get_DSAdmin_GetDSServerName_Metadata());
        getConnection().execute(cmd);
        return cmd.getParameter(0).getValue().GetAsString();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_ConsumeClientChannel_Metadata() {
        if (this.DSAdmin_ConsumeClientChannel_Metadata == null) {
            this.DSAdmin_ConsumeClientChannel_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ChannelName", 1, 26, "string"), new DSRESTParameterMetaData("ClientManagerId", 1, 26, "string"), new DSRESTParameterMetaData("CallbackId", 1, 26, "string"), new DSRESTParameterMetaData("ChannelNames", 1, 26, "string"), new DSRESTParameterMetaData("SecurityToken", 1, 26, "string"), new DSRESTParameterMetaData("ResponseData", 1, 37, "TJSONValue"), new DSRESTParameterMetaData("", 4, 37, "TJSONValue")};
        }
        return this.DSAdmin_ConsumeClientChannel_Metadata;
    }

    public TJSONValue ConsumeClientChannel(String ChannelName, String ClientManagerId, String CallbackId, String ChannelNames, String SecurityToken, TJSONValue ResponseData) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.ConsumeClientChannel");
        cmd.prepare(get_DSAdmin_ConsumeClientChannel_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ChannelName);
        cmd.getParameter(1).getValue().SetAsString(ClientManagerId);
        cmd.getParameter(2).getValue().SetAsString(CallbackId);
        cmd.getParameter(3).getValue().SetAsString(ChannelNames);
        cmd.getParameter(4).getValue().SetAsString(SecurityToken);
        cmd.getParameter(5).getValue().SetAsJSONValue(ResponseData);
        getConnection().execute(cmd);
        return cmd.getParameter(6).getValue().GetAsJSONValue();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_ConsumeClientChannelTimeout_Metadata() {
        if (this.DSAdmin_ConsumeClientChannelTimeout_Metadata == null) {
            this.DSAdmin_ConsumeClientChannelTimeout_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ChannelName", 1, 26, "string"), new DSRESTParameterMetaData("ClientManagerId", 1, 26, "string"), new DSRESTParameterMetaData("CallbackId", 1, 26, "string"), new DSRESTParameterMetaData("ChannelNames", 1, 26, "string"), new DSRESTParameterMetaData("SecurityToken", 1, 26, "string"), new DSRESTParameterMetaData("Timeout", 1, 6, "Integer"), new DSRESTParameterMetaData("ResponseData", 1, 37, "TJSONValue"), new DSRESTParameterMetaData("", 4, 37, "TJSONValue")};
        }
        return this.DSAdmin_ConsumeClientChannelTimeout_Metadata;
    }

    public TJSONValue ConsumeClientChannelTimeout(String ChannelName, String ClientManagerId, String CallbackId, String ChannelNames, String SecurityToken, int Timeout, TJSONValue ResponseData) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.ConsumeClientChannelTimeout");
        cmd.prepare(get_DSAdmin_ConsumeClientChannelTimeout_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ChannelName);
        cmd.getParameter(1).getValue().SetAsString(ClientManagerId);
        cmd.getParameter(2).getValue().SetAsString(CallbackId);
        cmd.getParameter(3).getValue().SetAsString(ChannelNames);
        cmd.getParameter(4).getValue().SetAsString(SecurityToken);
        cmd.getParameter(5).getValue().SetAsInt32(Timeout);
        cmd.getParameter(6).getValue().SetAsJSONValue(ResponseData);
        getConnection().execute(cmd);
        return cmd.getParameter(7).getValue().GetAsJSONValue();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_CloseClientChannel_Metadata() {
        if (this.DSAdmin_CloseClientChannel_Metadata == null) {
            this.DSAdmin_CloseClientChannel_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ChannelId", 1, 26, "string"), new DSRESTParameterMetaData("SecurityToken", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 4, "Boolean")};
        }
        return this.DSAdmin_CloseClientChannel_Metadata;
    }

    public boolean CloseClientChannel(String ChannelId, String SecurityToken) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.CloseClientChannel");
        cmd.prepare(get_DSAdmin_CloseClientChannel_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ChannelId);
        cmd.getParameter(1).getValue().SetAsString(SecurityToken);
        getConnection().execute(cmd);
        return cmd.getParameter(2).getValue().GetAsBoolean();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_RegisterClientCallbackServer_Metadata() {
        if (this.DSAdmin_RegisterClientCallbackServer_Metadata == null) {
            this.DSAdmin_RegisterClientCallbackServer_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ChannelId", 1, 26, "string"), new DSRESTParameterMetaData("CallbackId", 1, 26, "string"), new DSRESTParameterMetaData("ChannelNames", 1, 26, "string"), new DSRESTParameterMetaData("SecurityToken", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 4, "Boolean")};
        }
        return this.DSAdmin_RegisterClientCallbackServer_Metadata;
    }

    public boolean RegisterClientCallbackServer(String ChannelId, String CallbackId, String ChannelNames, String SecurityToken) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.RegisterClientCallbackServer");
        cmd.prepare(get_DSAdmin_RegisterClientCallbackServer_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ChannelId);
        cmd.getParameter(1).getValue().SetAsString(CallbackId);
        cmd.getParameter(2).getValue().SetAsString(ChannelNames);
        cmd.getParameter(3).getValue().SetAsString(SecurityToken);
        getConnection().execute(cmd);
        return cmd.getParameter(4).getValue().GetAsBoolean();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_UnregisterClientCallback_Metadata() {
        if (this.DSAdmin_UnregisterClientCallback_Metadata == null) {
            this.DSAdmin_UnregisterClientCallback_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ChannelId", 1, 26, "string"), new DSRESTParameterMetaData("CallbackId", 1, 26, "string"), new DSRESTParameterMetaData("SecurityToken", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 4, "Boolean")};
        }
        return this.DSAdmin_UnregisterClientCallback_Metadata;
    }

    public boolean UnregisterClientCallback(String ChannelId, String CallbackId, String SecurityToken) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.GET);
        cmd.setText("DSAdmin.UnregisterClientCallback");
        cmd.prepare(get_DSAdmin_UnregisterClientCallback_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ChannelId);
        cmd.getParameter(1).getValue().SetAsString(CallbackId);
        cmd.getParameter(2).getValue().SetAsString(SecurityToken);
        getConnection().execute(cmd);
        return cmd.getParameter(3).getValue().GetAsBoolean();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_BroadcastToChannel_Metadata() {
        if (this.DSAdmin_BroadcastToChannel_Metadata == null) {
            this.DSAdmin_BroadcastToChannel_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ChannelName", 1, 26, "string"), new DSRESTParameterMetaData("Msg", 1, 37, "TJSONValue"), new DSRESTParameterMetaData("", 4, 4, "Boolean")};
        }
        return this.DSAdmin_BroadcastToChannel_Metadata;
    }

    public boolean BroadcastToChannel(String ChannelName, TJSONValue Msg) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.BroadcastToChannel");
        cmd.prepare(get_DSAdmin_BroadcastToChannel_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ChannelName);
        cmd.getParameter(1).getValue().SetAsJSONValue(Msg);
        getConnection().execute(cmd);
        return cmd.getParameter(2).getValue().GetAsBoolean();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_BroadcastObjectToChannel_Metadata() {
        if (this.DSAdmin_BroadcastObjectToChannel_Metadata == null) {
            this.DSAdmin_BroadcastObjectToChannel_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ChannelName", 1, 26, "string"), new DSRESTParameterMetaData("Msg", 1, 37, "TObject"), new DSRESTParameterMetaData("", 4, 4, "Boolean")};
        }
        return this.DSAdmin_BroadcastObjectToChannel_Metadata;
    }

    public boolean BroadcastObjectToChannel(String ChannelName, TJSONObject Msg) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.BroadcastObjectToChannel");
        cmd.prepare(get_DSAdmin_BroadcastObjectToChannel_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ChannelName);
        cmd.getParameter(1).getValue().SetAsJSONValue(Msg);
        getConnection().execute(cmd);
        return cmd.getParameter(2).getValue().GetAsBoolean();
    }

    private DSRESTParameterMetaData[] get_DSAdmin_NotifyCallback_Metadata() {
        if (this.DSAdmin_NotifyCallback_Metadata == null) {
            this.DSAdmin_NotifyCallback_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ClientId", 1, 26, "string"), new DSRESTParameterMetaData("CallbackId", 1, 26, "string"), new DSRESTParameterMetaData("Msg", 1, 37, "TJSONValue"), new DSRESTParameterMetaData("Response", 2, 37, "TJSONValue"), new DSRESTParameterMetaData("", 4, 4, "Boolean")};
        }
        return this.DSAdmin_NotifyCallback_Metadata;
    }

    public NotifyCallbackReturns NotifyCallback(String ClientId, String CallbackId, TJSONValue Msg) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.NotifyCallback");
        cmd.prepare(get_DSAdmin_NotifyCallback_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ClientId);
        cmd.getParameter(1).getValue().SetAsString(CallbackId);
        cmd.getParameter(2).getValue().SetAsJSONValue(Msg);
        getConnection().execute(cmd);
        NotifyCallbackReturns ret = new NotifyCallbackReturns();
        ret.Response = cmd.getParameter(3).getValue().GetAsJSONValue();
        ret.returnValue = cmd.getParameter(4).getValue().GetAsBoolean();
        return ret;
    }

    private DSRESTParameterMetaData[] get_DSAdmin_NotifyObject_Metadata() {
        if (this.DSAdmin_NotifyObject_Metadata == null) {
            this.DSAdmin_NotifyObject_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("ClientId", 1, 26, "string"), new DSRESTParameterMetaData("CallbackId", 1, 26, "string"), new DSRESTParameterMetaData("Msg", 1, 37, "TObject"), new DSRESTParameterMetaData("Response", 2, 37, "TObject"), new DSRESTParameterMetaData("", 4, 4, "Boolean")};
        }
        return this.DSAdmin_NotifyObject_Metadata;
    }

    public NotifyObjectReturns NotifyObject(String ClientId, String CallbackId, TJSONObject Msg) throws DBXException {
        DSRESTCommand cmd = getConnection().CreateCommand();
        cmd.setRequestType(DSHTTPRequestType.POST);
        cmd.setText("DSAdmin.NotifyObject");
        cmd.prepare(get_DSAdmin_NotifyObject_Metadata());
        cmd.getParameter(0).getValue().SetAsString(ClientId);
        cmd.getParameter(1).getValue().SetAsString(CallbackId);
        cmd.getParameter(2).getValue().SetAsJSONValue(Msg);
        getConnection().execute(cmd);
        NotifyObjectReturns ret = new NotifyObjectReturns();
        ret.Response = (TJSONObject) cmd.getParameter(3).getValue().GetAsJSONValue();
        ret.returnValue = cmd.getParameter(4).getValue().GetAsBoolean();
        return ret;
    }
}
