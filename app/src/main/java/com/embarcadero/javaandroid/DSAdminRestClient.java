package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class DSAdminRestClient {
    private DSRESTConnection Connection;

    protected DSRESTConnection getConnection() {
        return this.Connection;
    }

    public DSAdminRestClient(DSRESTConnection Connection) {
        this.Connection = null;
        this.Connection = Connection;
    }
}
