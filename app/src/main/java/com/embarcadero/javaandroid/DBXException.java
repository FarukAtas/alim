package com.embarcadero.javaandroid;

/* loaded from: classes.dex */
public class DBXException extends Exception {
    private static final long serialVersionUID = 154857855606412539L;
    private Throwable Internal;

    public DBXException(String value) {
        super(value);
    }

    public DBXException(Throwable value) {
        super(value.getMessage());
        this.Internal = value;
    }

    public Throwable getInternal() {
        return this.Internal;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String res = super.getMessage();
        if (res == null && getInternal() != null) {
            return getThrowableMessage(getInternal());
        }
        return res;
    }

    private String getThrowableMessage(Throwable t) {
        if (t == null) {
            return null;
        }
        String res = t.getMessage();
        if (res == null && t.getCause() != null) {
            return getThrowableMessage(t.getCause());
        }
        return res;
    }
}
