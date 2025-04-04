package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class DSProxy {

    public static class TServerMethods1 extends DSAdmin {
        private DSRESTParameterMetaData[] TServerMethods1_EchoString_Metadata;
        private DSRESTParameterMetaData[] TServerMethods1_GetIDG_Metadata;
        private DSRESTParameterMetaData[] TServerMethods1_GetID_Metadata;
        private DSRESTParameterMetaData[] TServerMethods1_GetVersionStr_Metadata;
        private DSRESTParameterMetaData[] TServerMethods1_GetVersion_Metadata;
        private DSRESTParameterMetaData[] TServerMethods1_QryExecR_Metadata;
        private DSRESTParameterMetaData[] TServerMethods1_QryExec_Metadata;
        private DSRESTParameterMetaData[] TServerMethods1_ReverseString_Metadata;
        private DSRESTParameterMetaData[] TServerMethods1_SqlExec_Metadata;

        public TServerMethods1(DSRESTConnection Connection) {
            super(Connection);
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_GetID_Metadata() {
            if (this.TServerMethods1_GetID_Metadata == null) {
                this.TServerMethods1_GetID_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods1_GetID_Metadata;
        }

        public String GetID() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.GetID");
            cmd.prepare(get_TServerMethods1_GetID_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_EchoString_Metadata() {
            if (this.TServerMethods1_EchoString_Metadata == null) {
                this.TServerMethods1_EchoString_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("Value", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods1_EchoString_Metadata;
        }

        public String EchoString(String Value) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.EchoString");
            cmd.prepare(get_TServerMethods1_EchoString_Metadata());
            cmd.getParameter(0).getValue().SetAsString(Value);
            getConnection().execute(cmd);
            return cmd.getParameter(1).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_ReverseString_Metadata() {
            if (this.TServerMethods1_ReverseString_Metadata == null) {
                this.TServerMethods1_ReverseString_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("Value", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods1_ReverseString_Metadata;
        }

        public String ReverseString(String Value) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.ReverseString");
            cmd.prepare(get_TServerMethods1_ReverseString_Metadata());
            cmd.getParameter(0).getValue().SetAsString(Value);
            getConnection().execute(cmd);
            return cmd.getParameter(1).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_GetVersion_Metadata() {
            if (this.TServerMethods1_GetVersion_Metadata == null) {
                this.TServerMethods1_GetVersion_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods1_GetVersion_Metadata;
        }

        public int GetVersion() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.GetVersion");
            cmd.prepare(get_TServerMethods1_GetVersion_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsInt32();
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_GetVersionStr_Metadata() {
            if (this.TServerMethods1_GetVersionStr_Metadata == null) {
                this.TServerMethods1_GetVersionStr_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods1_GetVersionStr_Metadata;
        }

        public String GetVersionStr() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.GetVersionStr");
            cmd.prepare(get_TServerMethods1_GetVersionStr_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_QryExec_Metadata() {
            if (this.TServerMethods1_QryExec_Metadata == null) {
                this.TServerMethods1_QryExec_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 23, "TDataSet")};
            }
            return this.TServerMethods1_QryExec_Metadata;
        }

        public TDataSet QryExec(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.QryExec");
            cmd.prepare(get_TServerMethods1_QryExec_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return (TDataSet) cmd.getParameter(2).getValue().GetAsTable();
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_QryExecR_Metadata() {
            if (this.TServerMethods1_QryExecR_Metadata == null) {
                this.TServerMethods1_QryExecR_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
            }
            return this.TServerMethods1_QryExecR_Metadata;
        }

        public TDBXReader QryExecR(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.QryExecR");
            cmd.prepare(get_TServerMethods1_QryExecR_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return (TDBXReader) cmd.getParameter(2).getValue().GetAsTable();
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_SqlExec_Metadata() {
            if (this.TServerMethods1_SqlExec_Metadata == null) {
                this.TServerMethods1_SqlExec_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods1_SqlExec_Metadata;
        }

        public int SqlExec(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.SqlExec");
            cmd.prepare(get_TServerMethods1_SqlExec_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return cmd.getParameter(2).getValue().GetAsInt32();
        }

        private DSRESTParameterMetaData[] get_TServerMethods1_GetIDG_Metadata() {
            if (this.TServerMethods1_GetIDG_Metadata == null) {
                this.TServerMethods1_GetIDG_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods1_GetIDG_Metadata;
        }

        public int GetIDG() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods1.GetIDG");
            cmd.prepare(get_TServerMethods1_GetIDG_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsInt32();
        }
    }

    public static class TServerMethods2 extends DSAdmin {
        private DSRESTParameterMetaData[] TServerMethods2_EchoString_Metadata;
        private DSRESTParameterMetaData[] TServerMethods2_GetIDG_Metadata;
        private DSRESTParameterMetaData[] TServerMethods2_GetID_Metadata;
        private DSRESTParameterMetaData[] TServerMethods2_GetVersionStr_Metadata;
        private DSRESTParameterMetaData[] TServerMethods2_GetVersion_Metadata;
        private DSRESTParameterMetaData[] TServerMethods2_QryExecR_Metadata;
        private DSRESTParameterMetaData[] TServerMethods2_QryExec_Metadata;
        private DSRESTParameterMetaData[] TServerMethods2_ReverseString_Metadata;
        private DSRESTParameterMetaData[] TServerMethods2_SqlExec_Metadata;

        public TServerMethods2(DSRESTConnection Connection) {
            super(Connection);
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_GetID_Metadata() {
            if (this.TServerMethods2_GetID_Metadata == null) {
                this.TServerMethods2_GetID_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods2_GetID_Metadata;
        }

        public String GetID() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.GetID");
            cmd.prepare(get_TServerMethods2_GetID_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_EchoString_Metadata() {
            if (this.TServerMethods2_EchoString_Metadata == null) {
                this.TServerMethods2_EchoString_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("Value", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods2_EchoString_Metadata;
        }

        public String EchoString(String Value) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.EchoString");
            cmd.prepare(get_TServerMethods2_EchoString_Metadata());
            cmd.getParameter(0).getValue().SetAsString(Value);
            getConnection().execute(cmd);
            return cmd.getParameter(1).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_ReverseString_Metadata() {
            if (this.TServerMethods2_ReverseString_Metadata == null) {
                this.TServerMethods2_ReverseString_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("Value", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods2_ReverseString_Metadata;
        }

        public String ReverseString(String Value) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.ReverseString");
            cmd.prepare(get_TServerMethods2_ReverseString_Metadata());
            cmd.getParameter(0).getValue().SetAsString(Value);
            getConnection().execute(cmd);
            return cmd.getParameter(1).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_GetVersion_Metadata() {
            if (this.TServerMethods2_GetVersion_Metadata == null) {
                this.TServerMethods2_GetVersion_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods2_GetVersion_Metadata;
        }

        public int GetVersion() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.GetVersion");
            cmd.prepare(get_TServerMethods2_GetVersion_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsInt32();
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_GetVersionStr_Metadata() {
            if (this.TServerMethods2_GetVersionStr_Metadata == null) {
                this.TServerMethods2_GetVersionStr_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods2_GetVersionStr_Metadata;
        }

        public String GetVersionStr() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.GetVersionStr");
            cmd.prepare(get_TServerMethods2_GetVersionStr_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_QryExec_Metadata() {
            if (this.TServerMethods2_QryExec_Metadata == null) {
                this.TServerMethods2_QryExec_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 23, "TDataSet")};
            }
            return this.TServerMethods2_QryExec_Metadata;
        }

        public TDataSet QryExec(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.QryExec");
            cmd.prepare(get_TServerMethods2_QryExec_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return (TDataSet) cmd.getParameter(2).getValue().GetAsTable();
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_QryExecR_Metadata() {
            if (this.TServerMethods2_QryExecR_Metadata == null) {
                this.TServerMethods2_QryExecR_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
            }
            return this.TServerMethods2_QryExecR_Metadata;
        }

        public TDBXReader QryExecR(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.QryExecR");
            cmd.prepare(get_TServerMethods2_QryExecR_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return (TDBXReader) cmd.getParameter(2).getValue().GetAsTable();
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_SqlExec_Metadata() {
            if (this.TServerMethods2_SqlExec_Metadata == null) {
                this.TServerMethods2_SqlExec_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods2_SqlExec_Metadata;
        }

        public int SqlExec(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.SqlExec");
            cmd.prepare(get_TServerMethods2_SqlExec_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return cmd.getParameter(2).getValue().GetAsInt32();
        }

        private DSRESTParameterMetaData[] get_TServerMethods2_GetIDG_Metadata() {
            if (this.TServerMethods2_GetIDG_Metadata == null) {
                this.TServerMethods2_GetIDG_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods2_GetIDG_Metadata;
        }

        public int GetIDG() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods2.GetIDG");
            cmd.prepare(get_TServerMethods2_GetIDG_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsInt32();
        }
    }

    public static class TServerMethods3 extends DSAdmin {
        private DSRESTParameterMetaData[] TServerMethods3_EchoString_Metadata;
        private DSRESTParameterMetaData[] TServerMethods3_GetIDG_Metadata;
        private DSRESTParameterMetaData[] TServerMethods3_GetID_Metadata;
        private DSRESTParameterMetaData[] TServerMethods3_GetVersionStr_Metadata;
        private DSRESTParameterMetaData[] TServerMethods3_GetVersion_Metadata;
        private DSRESTParameterMetaData[] TServerMethods3_QryExecR_Metadata;
        private DSRESTParameterMetaData[] TServerMethods3_QryExec_Metadata;
        private DSRESTParameterMetaData[] TServerMethods3_ReverseString_Metadata;
        private DSRESTParameterMetaData[] TServerMethods3_SqlExec_Metadata;

        public TServerMethods3(DSRESTConnection Connection) {
            super(Connection);
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_GetID_Metadata() {
            if (this.TServerMethods3_GetID_Metadata == null) {
                this.TServerMethods3_GetID_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods3_GetID_Metadata;
        }

        public String GetID() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.GetID");
            cmd.prepare(get_TServerMethods3_GetID_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_EchoString_Metadata() {
            if (this.TServerMethods3_EchoString_Metadata == null) {
                this.TServerMethods3_EchoString_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("Value", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods3_EchoString_Metadata;
        }

        public String EchoString(String Value) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.EchoString");
            cmd.prepare(get_TServerMethods3_EchoString_Metadata());
            cmd.getParameter(0).getValue().SetAsString(Value);
            getConnection().execute(cmd);
            return cmd.getParameter(1).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_ReverseString_Metadata() {
            if (this.TServerMethods3_ReverseString_Metadata == null) {
                this.TServerMethods3_ReverseString_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("Value", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods3_ReverseString_Metadata;
        }

        public String ReverseString(String Value) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.ReverseString");
            cmd.prepare(get_TServerMethods3_ReverseString_Metadata());
            cmd.getParameter(0).getValue().SetAsString(Value);
            getConnection().execute(cmd);
            return cmd.getParameter(1).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_GetVersion_Metadata() {
            if (this.TServerMethods3_GetVersion_Metadata == null) {
                this.TServerMethods3_GetVersion_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods3_GetVersion_Metadata;
        }

        public int GetVersion() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.GetVersion");
            cmd.prepare(get_TServerMethods3_GetVersion_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsInt32();
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_GetVersionStr_Metadata() {
            if (this.TServerMethods3_GetVersionStr_Metadata == null) {
                this.TServerMethods3_GetVersionStr_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 26, "string")};
            }
            return this.TServerMethods3_GetVersionStr_Metadata;
        }

        public String GetVersionStr() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.GetVersionStr");
            cmd.prepare(get_TServerMethods3_GetVersionStr_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsString();
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_QryExec_Metadata() {
            if (this.TServerMethods3_QryExec_Metadata == null) {
                this.TServerMethods3_QryExec_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 23, "TDataSet")};
            }
            return this.TServerMethods3_QryExec_Metadata;
        }

        public TDataSet QryExec(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.QryExec");
            cmd.prepare(get_TServerMethods3_QryExec_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return (TDataSet) cmd.getParameter(2).getValue().GetAsTable();
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_QryExecR_Metadata() {
            if (this.TServerMethods3_QryExecR_Metadata == null) {
                this.TServerMethods3_QryExecR_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 23, "TDBXReader")};
            }
            return this.TServerMethods3_QryExecR_Metadata;
        }

        public TDBXReader QryExecR(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.QryExecR");
            cmd.prepare(get_TServerMethods3_QryExecR_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return (TDBXReader) cmd.getParameter(2).getValue().GetAsTable();
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_SqlExec_Metadata() {
            if (this.TServerMethods3_SqlExec_Metadata == null) {
                this.TServerMethods3_SqlExec_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("AMsg", 1, 26, "string"), new DSRESTParameterMetaData("ASql", 1, 26, "string"), new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods3_SqlExec_Metadata;
        }

        public int SqlExec(String AMsg, String ASql) throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.SqlExec");
            cmd.prepare(get_TServerMethods3_SqlExec_Metadata());
            cmd.getParameter(0).getValue().SetAsString(AMsg);
            cmd.getParameter(1).getValue().SetAsString(ASql);
            getConnection().execute(cmd);
            return cmd.getParameter(2).getValue().GetAsInt32();
        }

        private DSRESTParameterMetaData[] get_TServerMethods3_GetIDG_Metadata() {
            if (this.TServerMethods3_GetIDG_Metadata == null) {
                this.TServerMethods3_GetIDG_Metadata = new DSRESTParameterMetaData[]{new DSRESTParameterMetaData("", 4, 6, "Integer")};
            }
            return this.TServerMethods3_GetIDG_Metadata;
        }

        public int GetIDG() throws DBXException {
            DSRESTCommand cmd = getConnection().CreateCommand();
            cmd.setRequestType(DSHTTPRequestType.GET);
            cmd.setText("TServerMethods3.GetIDG");
            cmd.prepare(get_TServerMethods3_GetIDG_Metadata());
            getConnection().execute(cmd);
            return cmd.getParameter(0).getValue().GetAsInt32();
        }
    }
}
