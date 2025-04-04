package com.embarcadero.javaandroid;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/* loaded from: classes.dex */
public class DSRESTConnection {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$embarcadero$javaandroid$DSHTTPRequestType = null;
    public static final String TAG = "DataSnap";
    private DSRESTSSLFactory SSLFactory;
    private String SessionID;
    private long SessionIDExpires;
    private boolean isHttps;
    private int Port = 0;
    private String UrlPath = "";
    private String Host = "";
    private String Protocol = "";
    private String Context = "";
    private String UserName = "";
    private String Password = "";
    private int connectionTimeout = 5000;
    private int communicationTimeout = 0;

    static /* synthetic */ int[] $SWITCH_TABLE$com$embarcadero$javaandroid$DSHTTPRequestType() {
        int[] r0 = $SWITCH_TABLE$com$embarcadero$javaandroid$DSHTTPRequestType;
        if (r0 == null) {
            r0 = new int[DSHTTPRequestType.valuesCustom().length];
            try {
                r0[DSHTTPRequestType.DELETE.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                r0[DSHTTPRequestType.GET.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                r0[DSHTTPRequestType.POST.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                r0[DSHTTPRequestType.PUT.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$com$embarcadero$javaandroid$DSHTTPRequestType = r0;
        }
        return r0;
    }

    private String encodeURIComponent(String value) {
        try {
            String encodedURI = URLEncoder.encode(value, "UTF-8");
            return encodedURI.replaceAll("\\+", "%20").replaceAll("\\%21", "!").replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private String encodeURIComponent(DSRESTParameter parameter) {
        return encodeURIComponent(parameter.getValue().toString());
    }

    public DSRESTConnection() {
        InitSSLFactory();
        CloseSession();
    }

    private void InitSSLFactory() {
        if (this.SSLFactory == null) {
            try {
                this.SSLFactory = new DSRESTSSLFactory(null);
            } catch (Exception e) {
            }
        }
    }

    public DSRESTConnection Clone() {
        return Clone(false);
    }

    public DSRESTConnection Clone(boolean includeSession) {
        DSRESTConnection connection = new DSRESTConnection();
        connection.setHost(getHost());
        connection.setPort(getPort());
        connection.setProtocol(getProtocol());
        connection.setUserName(getUserName());
        connection.setPassword(getPassword());
        connection.setUrlPath(getUrlPath());
        connection.setCommunicationTimeout(getCommunicationTimeout());
        connection.setConnectionTimeout(getConnectionTimeout());
        if (includeSession) {
            connection.setSessionID(getSessionID());
            connection.SessionIDExpires = this.SessionIDExpires;
        }
        return connection;
    }

    public DSRESTCommand CreateCommand() {
        return new DSRESTCommand(this);
    }

    private String BuildRequestURL(DSRESTCommand command) {
        String LMethodName;
        String LPathPrefix = getUrlPath();
        int LPort = getPort();
        String LHost = getHost();
        String LMethodName2 = command.getText();
        String LProtocol = getProtocol();
        if (!LProtocol.equals("https")) {
            LProtocol = "http";
        }
        this.isHttps = LProtocol.equals("https");
        if (LHost.equals("")) {
            LHost = "localhost";
        }
        if (!LPathPrefix.equals("")) {
            LPathPrefix = "/" + LPathPrefix;
        }
        String LPortString = "";
        if (LPort > 0) {
            LPortString = ":" + String.valueOf(LPort);
        }
        if (command.getRequestType() == DSHTTPRequestType.GET || command.getRequestType() == DSHTTPRequestType.DELETE) {
            LMethodName = LMethodName2.replace(".", "/").replaceAll("\"", "");
        } else {
            LMethodName = String.valueOf(LMethodName2.replace(".", "/%22").replaceAll("\"", "%22")) + "%22";
        }
        String LContext = getContext();
        if (LContext.equals("")) {
            LContext = "datasnap/";
        }
        String LUrl = String.valueOf(LProtocol) + "://" + encodeURIComponent(LHost) + LPortString + LPathPrefix + "/" + LContext + "rest/" + LMethodName + "/";
        this.SessionID = getSessionID();
        return LUrl;
    }

    private HttpClient getHttpClient() {
        if (this.communicationTimeout <= 0 && this.connectionTimeout <= 0) {
            return new DefaultHttpClient();
        }
        HttpParams httpParameters = new BasicHttpParams();
        if (this.connectionTimeout > 0) {
            HttpConnectionParams.setConnectionTimeout(httpParameters, this.connectionTimeout);
        }
        if (this.communicationTimeout > 0) {
            HttpConnectionParams.setSoTimeout(httpParameters, this.communicationTimeout);
        }
        return new DefaultHttpClient(httpParameters);
    }

    public void execute(DSRESTCommand command) throws DBXException {
        HttpClient client;
        HttpUriRequest method = CreateRequest(command);
        try {
            if (this.isHttps) {
                if (this.SSLFactory != null) {
                    client = this.SSLFactory.getHttpClient(this.connectionTimeout, this.communicationTimeout);
                } else {
                    throw new DBXException("Cannot create https connection");
                }
            } else {
                client = getHttpClient();
            }
            HttpResponse response = client.execute(method);
            setSessionIdentifier(response);
            throwExceptionIfNeeded(response);
            if (isThereOnlyOneStreamInOutput(command.getParameters())) {
                InputStream inputstream = response.getEntity().getContent();
                byte[] b1 = DBXTools.streamToByteArray(inputstream);
                TStream is = new TStream(b1);
                for (DSRESTParameter param : command.getParameters()) {
                    if (param.Direction == 4 || param.Direction == 3 || param.Direction == 2) {
                        if (param.TypeName.startsWith("TDBX") && param.TypeName.endsWith("Value")) {
                            param.getValue().GetAsDBXValue().SetAsStream(is);
                            return;
                        } else {
                            param.getValue().SetAsStream(is);
                            return;
                        }
                    }
                }
                return;
            }
            try {
                String s = EntityUtils.toString(response.getEntity());
                TJSONObject json = TJSONObject.Parse(s);
                throwExceptionIfNeeded(json);
                TJSONArray results = json.getJSONArray("result");
                int returnParIndex = 0;
                for (DSRESTParameter param2 : command.getParameters()) {
                    if (param2.Direction == 4 || param2.Direction == 3 || param2.Direction == 2) {
                        DBXJSONTools.JSONtoDBX(results.get(returnParIndex), param2.getValue(), param2.TypeName);
                        returnParIndex++;
                    }
                }
            } catch (DBXException e) {
                throw new DBXException(e);
            }
        } catch (Exception e2) {
            throw new DBXException(e2);
        }
    }

    private void setSessionIdentifier(HttpResponse response) {
        boolean found = false;
        Header[] pragma = response.getHeaders("Pragma");
        for (Header h : pragma) {
            if (h.getValue().indexOf("dssession") >= 0) {
                for (HeaderElement e : h.getElements()) {
                    if (e.getName().equals("dssession")) {
                        this.SessionID = e.getValue();
                        found = true;
                    }
                    if (e.getName().equals("dssessionexpires")) {
                        this.SessionIDExpires = Long.valueOf(e.getValue()).longValue();
                    }
                }
            }
        }
        if (!found) {
            CloseSession();
        }
    }

    private boolean isThereOnlyOneStreamInOutput(List<DSRESTParameter> parameters) {
        if (isOnlyOneParameterInOutput(parameters)) {
            for (DSRESTParameter param : parameters) {
                if (param.Direction == 4 || param.Direction == 3 || param.Direction == 2) {
                    if (param.getDBXType() == 33) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isOnlyOneParameterInOutput(List<DSRESTParameter> parameters) {
        int Count = 0;
        for (DSRESTParameter param : parameters) {
            if (param.Direction == 4 || param.Direction == 3 || param.Direction == 2) {
                Count++;
            }
        }
        return Count == 1;
    }

    private void throwExceptionIfNeeded(HttpResponse response) throws DBXException {
        if (response.getStatusLine().getStatusCode() != 200) {
            TJSONObject json = null;
            try {
                json = TJSONObject.Parse(EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
            }
            if (json == null) {
                throw new DBXException(response.getStatusLine().getReasonPhrase());
            }
            throwExceptionIfNeeded(json);
        }
    }

    private void throwExceptionIfNeeded(TJSONObject json) throws DBXException {
        if (json.has("error")) {
            throw new DBXException(json.getString("error"));
        }
        if (json.has("SessionExpired")) {
            CloseSession();
            throw new DBXException(json.getString("SessionExpired"));
        }
    }

    public void CloseSession() {
        this.SessionID = null;
        this.SessionIDExpires = -1L;
    }

    private HttpUriRequest CreateRequest(DSRESTCommand command) throws DBXException {
        String URL = BuildRequestURL(command);
        TJSONArray _parameters = null;
        List<DSRESTParameter> ParametersToSend = new LinkedList<>();
        if (command.getParameters().size() > 0) {
            for (DSRESTParameter parameter : command.getParameters()) {
                if (parameter.Direction == 1 || parameter.Direction == 3) {
                    ParametersToSend.add(parameter);
                }
            }
        }
        if (command.getRequestType() == DSHTTPRequestType.GET || command.getRequestType() == DSHTTPRequestType.DELETE) {
            Iterator<DSRESTParameter> it = ParametersToSend.iterator();
            while (it.hasNext()) {
                URL = String.valueOf(URL) + encodeURIComponent(it.next()) + '/';
            }
        } else {
            boolean CanAddParamsToUrl = true;
            _parameters = new TJSONArray();
            for (DSRESTParameter parameter2 : ParametersToSend) {
                if (CanAddParamsToUrl && isURLParameter(parameter2)) {
                    URL = String.valueOf(URL) + encodeURIComponent(parameter2) + '/';
                } else {
                    CanAddParamsToUrl = false;
                    parameter2.getValue().appendTo(_parameters);
                }
            }
        }
        HttpUriRequest req = BuildRequest(command.getRequestType(), URL, _parameters);
        SetUpHeaders(req);
        return req;
    }

    private HttpUriRequest BuildRequest(DSHTTPRequestType requestType, String URL, TJSONArray _parameters) throws DBXException {
        switch ($SWITCH_TABLE$com$embarcadero$javaandroid$DSHTTPRequestType()[requestType.ordinal()]) {
            case 1:
                return new HttpGet(URL);
            case 2:
                try {
                    HttpPost p = new HttpPost(URL);
                    if (_parameters == null) {
                        throw new DBXException("Parameters cannot be null in a POST request");
                    }
                    if (_parameters.size() > 1) {
                        TJSONObject body = new TJSONObject();
                        body.addPairs("_parameters", _parameters);
                        p.setEntity(new StringEntity(body.toString(), "utf-8"));
                        return p;
                    }
                    if (_parameters.get(0) != null) {
                        p.setEntity(new StringEntity(_parameters.get(0).toString(), "utf-8"));
                        return p;
                    }
                    p.setEntity(new StringEntity("null", "utf-8"));
                    return p;
                } catch (Exception ex) {
                    throw new DBXException(ex.getMessage());
                }
            case 3:
                try {
                    HttpPut p2 = new HttpPut(URL);
                    if (_parameters == null) {
                        throw new DBXException("Parameters cannot be null in a POST request");
                    }
                    if (_parameters.size() > 1) {
                        TJSONObject body2 = new TJSONObject();
                        body2.addPairs("_parameters", _parameters);
                        p2.setEntity(new StringEntity(body2.toString(), "utf-8"));
                        return p2;
                    }
                    p2.setEntity(new StringEntity(_parameters.get(0).toString(), "utf-8"));
                    return p2;
                } catch (Exception ex2) {
                    throw new DBXException(ex2.getMessage());
                }
            case 4:
                return new HttpDelete(URL);
            default:
                return null;
        }
    }

    private void SetUpSessionHeader(HttpUriRequest request) {
        if (this.SessionID != null) {
            request.addHeader("Pragma", "dssession=" + this.SessionID);
        }
    }

    private void SetUpAuthorizationHeader(HttpUriRequest request) {
        if (this.UserName == null || this.UserName.equals("")) {
            request.addHeader("Authorization", "Basic Og==");
        } else {
            String auth = DBXDefaultFormatter.getInstance().Base64Encode(String.valueOf(this.UserName) + ":" + this.Password);
            request.addHeader("Authorization", "Basic " + auth);
        }
    }

    private void SetUpHeaders(HttpUriRequest request) {
        request.addHeader("If-Modified-Since", "Mon, 1 Oct 1990 05:00:00 GMT");
        request.addHeader("Connection", "Keep-Alive");
        request.addHeader("Content-Type", "text/plain;charset=UTF-8");
        request.addHeader("Accept", "application/JSON");
        request.addHeader("Accept-Encoding", "identity");
        request.addHeader("User-Agent", "Mozilla/3.0 (compatible; Indy Library)");
        if (this.SessionID == null) {
            SetUpAuthorizationHeader(request);
        } else {
            SetUpSessionHeader(request);
        }
    }

    private boolean isURLParameter(DSRESTParameter parameter) {
        return (parameter.getDBXType() == 37 || parameter.getDBXType() == 33 || parameter.getDBXType() == 23) ? false : true;
    }

    public String getSessionID() {
        return this.SessionID;
    }

    public long getSessionExpires() {
        return this.SessionIDExpires;
    }

    public String getContext() {
        return this.Context;
    }

    public String getProtocol() {
        return this.Protocol;
    }

    public String getHost() {
        return this.Host;
    }

    public int getPort() {
        return this.Port;
    }

    public void setPort(int Port) {
        this.Port = Port;
    }

    public String getUrlPath() {
        return this.UrlPath;
    }

    public void setUrlPath(String urlPath) {
        this.UrlPath = urlPath;
    }

    public void setHost(String host) {
        this.Host = host;
    }

    public void setProtocol(String protocol) {
        this.Protocol = protocol;
    }

    public void setContext(String context) {
        this.Context = context;
    }

    protected void setSessionID(String sessionID) {
        this.SessionID = sessionID;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPassword() {
        return this.Password;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getCommunicationTimeout() {
        return this.communicationTimeout;
    }

    public void setCommunicationTimeout(int communicationTimeout) {
        this.communicationTimeout = communicationTimeout;
    }
}
