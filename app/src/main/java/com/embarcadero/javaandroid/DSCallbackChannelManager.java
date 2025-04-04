package com.embarcadero.javaandroid;

import android.util.Log;
import com.embarcadero.javaandroid.DSAdmin;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.conn.ConnectTimeoutException;

/* loaded from: classes.dex */
public class DSCallbackChannelManager {
    private String ChannelName;
    String CommunicationTimeout;
    private DSRESTConnection Connection;
    String ConnectionTimeout;
    String HostName;
    private String ManagerID;
    String Path;
    String Port;
    String Protocol;
    private String SecurityToken;
    private DSAdmin dsadmin;
    private Lock lock;
    private WorkerThread wThread;
    private int MaxRetries = 5;
    private int RetryDelay = 1000;
    private DSCallbackChannelManagerEventListener eventListener = null;

    public interface DSCallbackChannelManagerEventListener {
        void onException(DSCallbackChannelManager dSCallbackChannelManager, Throwable th);
    }

    public DSCallbackChannelManager(DSRESTConnection Connection, String ChannelName, String ManagerID) {
        Initialize(Connection, ChannelName, ManagerID);
    }

    protected void Initialize(DSRESTConnection Connection, String ChannelName, String ManagerID) {
        this.lock = new ReentrantLock();
        this.ChannelName = ChannelName;
        this.ManagerID = ManagerID;
        this.Connection = Connection;
        this.Connection.setCommunicationTimeout(0);
        this.Connection.setConnectionTimeout(30000);
        this.dsadmin = new DSAdmin(this.Connection);
        Random random = new Random();
        this.SecurityToken = String.valueOf(String.valueOf(random.nextInt(100000))) + "." + String.valueOf(random.nextInt(100000));
    }

    public DSCallbackChannelManager(DSRESTConnection Connection, String ChannelName) {
        Initialize(Connection, ChannelName, getNewManagerID());
    }

    protected Lock getLock() {
        return this.lock;
    }

    private boolean registerClientCallback(String CallbackId) throws Exception {
        return this.dsadmin.RegisterClientCallbackServer(getManagerID(), CallbackId, this.ChannelName, getSecurityToken());
    }

    public void setEventListener(DSCallbackChannelManagerEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void DoOnException(DSCallbackChannelManager mngr, Throwable e) {
        stopWThread();
        if (this.eventListener != null) {
            this.eventListener.onException(mngr, e);
        }
    }

    public boolean registerCallback(String CallbackId, DBXCallback Callback) throws Exception {
        getLock().lock();
        try {
            if (this.wThread == null) {
                this.wThread = new WorkerThread(CallbackId, Callback, this.Connection.Clone(true), this);
                this.wThread.start();
            } else {
                this.wThread.addCallback(CallbackId, Callback);
                if (!registerClientCallback(CallbackId)) {
                    this.wThread.removeCallback(CallbackId);
                    return false;
                }
            }
            return true;
        } catch (Throwable e) {
            DoOnException(this, e);
            return false;
        } finally {
            getLock().unlock();
        }
    }

    public boolean closeClientChannel() {
        getLock().lock();
        boolean res = false;
        try {
            res = this.dsadmin.CloseClientChannel(getManagerID(), getSecurityToken());
            this.wThread.stop();
            this.wThread.interrupt();
        } catch (Throwable e) {
            this.wThread.stopped = true;
            this.wThread.stop();
            DoOnException(this, e);
        } finally {
            this.wThread = null;
            getLock().unlock();
        }
        return res;
    }

    private void stopWThread() {
        this.wThread.stopped = true;
        this.wThread.stop();
    }

    public void stop() {
        closeClientChannel();
    }

    public DSAdmin.NotifyCallbackReturns notifyCallback(String CallbackId, TJSONValue Msg) {
        try {
            return this.dsadmin.NotifyCallback(getManagerID(), CallbackId, Msg);
        } catch (DBXException e) {
            DoOnException(this, e);
            return null;
        }
    }

    public boolean broadcastToChannel(TJSONValue Msg) {
        try {
            return this.dsadmin.BroadcastToChannel(getChannelName(), Msg);
        } catch (DBXException e) {
            DoOnException(this, e);
            return false;
        }
    }

    public boolean unregisterCallback(String CallbackId) throws Exception {
        getLock().lock();
        boolean res = false;
        try {
            res = this.dsadmin.UnregisterClientCallback(this.ChannelName, CallbackId, getSecurityToken());
            this.wThread.removeCallback(CallbackId);
        } catch (Exception e) {
            DoOnException(this, e);
        } finally {
            getLock().unlock();
        }
        return res;
    }

    public String getChannelName() {
        return this.ChannelName;
    }

    public String getManagerID() {
        return this.ManagerID;
    }

    public String getSecurityToken() {
        return this.SecurityToken;
    }

    static class WorkerThread extends Thread {
        private DSAdmin dsadmin;
        private String firstCallback;
        private DSCallbackChannelManager mngr;
        protected boolean stopped;
        private ReentrantLock lock = new ReentrantLock();
        private HashMap<String, DBXCallback> callbacks = new HashMap<>();

        public WorkerThread(String CallbackId, DBXCallback Callback, DSRESTConnection connection, DSCallbackChannelManager mngr) {
            this.dsadmin = new DSAdmin(connection);
            this.mngr = mngr;
            this.firstCallback = CallbackId;
            addCallback(CallbackId, Callback);
        }

        public void removeCallback(String callbackId) {
            cbListLock();
            try {
                this.callbacks.remove(callbackId);
            } finally {
                cbListUnLock();
            }
        }

        public void addCallback(String callbackId, DBXCallback callback) {
            cbListLock();
            try {
                this.callbacks.put(callbackId, callback);
            } finally {
                cbListUnLock();
            }
        }

        public void terminate() {
            this.stopped = true;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            this.stopped = false;
            try {
                try {
                    cbListLock();
                    TJSONValue t = new TJSONTrue();
                    TJSONValue res = this.dsadmin.ConsumeClientChannel(this.mngr.ChannelName, this.mngr.getManagerID(), this.firstCallback, this.mngr.ChannelName, this.mngr.getSecurityToken(), t);
                    if (((TJSONObject) res).has("error")) {
                        throw new DBXException("Cannot create callback (" + ((TJSONObject) res).getString("error") + ")");
                    }
                    boolean response = ((TJSONObject) res).getJSONArray("invoke").getAsJsonObject(1).getBoolean("created").booleanValue();
                    if (!response) {
                        throw new DBXException("Cannot create callback");
                    }
                    cbListUnLock();
                    while (!this.stopped) {
                        TJSONObject jobj = channelCallbackExecute();
                        if (jobj != null) {
                            executeCallback(jobj);
                        }
                    }
                    interrupt();
                } catch (Throwable th) {
                    cbListUnLock();
                    throw th;
                }
            } catch (Exception ex) {
                this.stopped = true;
                interrupt();
                this.mngr.eventListener.onException(this.mngr, ex);
            }
        }

        private void executeCallback(TJSONObject arg) throws DBXException {
            cbListLock();
            try {
                if (arg.has("broadcast")) {
                    broadcastEvent(arg);
                } else if (arg.has("invoke")) {
                    invokeEvent(arg);
                } else if (arg.has("close")) {
                    this.stopped = arg.getBoolean("close").booleanValue();
                } else {
                    throw new DBXException("Invalid callback result type");
                }
            } finally {
                cbListUnLock();
            }
        }

        private void invokeEvent(TJSONObject json) throws DBXException {
            TJSONArray arr = json.getJSONArray("invoke");
            String callbackID = arr.getAsJsonString(0).value;
            TJSONValue v = arr.get(1);
            int n = arr.getInt(2).intValue();
            DBXCallback cb = this.callbacks.get(callbackID);
            if (cb != null) {
                cb.execute(v, n);
                return;
            }
            throw new DBXException("Invalid callback response");
        }

        private void broadcastEvent(TJSONObject json) throws DBXException {
            Set<String> keys = this.callbacks.keySet();
            TJSONArray arr = json.getJSONArray("broadcast");
            TJSONValue value = arr.get(0);
            int n = arr.getInt(1).intValue();
            for (String callbackskeys : keys) {
                DBXCallback cb = this.callbacks.get(callbackskeys);
                if (cb != null) {
                    cb.execute(value, n);
                } else {
                    throw new DBXException("Invalid callback response");
                }
            }
        }

        private TJSONObject channelCallbackExecute() throws Exception {
            TJSONValue res = null;
            long lastRequestAttempt = 0;
            int retries = 0;
            while (true) {
                if (!this.stopped) {
                    try {
                        Log.i(DSRESTConnection.TAG, "Listening for channel " + this.mngr.getChannelName() + " (retries " + String.valueOf(retries) + ")");
                        TJSONValue Value = new TJSONTrue();
                        lastRequestAttempt = System.currentTimeMillis();
                        res = this.dsadmin.ConsumeClientChannel(this.mngr.getChannelName(), this.mngr.getManagerID(), "", this.mngr.getChannelName(), this.mngr.getSecurityToken(), Value);
                        Log.i(DSRESTConnection.TAG, "Got a message for " + this.mngr.getChannelName() + " (retries " + String.valueOf(retries) + ")");
                        break;
                    } catch (DBXException e) {
                        Throwable InternalException = e.getInternal();
                        if ((InternalException instanceof SocketTimeoutException) || (InternalException instanceof ConnectTimeoutException)) {
                            Log.i(DSRESTConnection.TAG, "Timeout for channel " + this.mngr.getChannelName() + " (retries " + String.valueOf(retries) + ")");
                            if (System.currentTimeMillis() - lastRequestAttempt >= this.dsadmin.getConnection().getConnectionTimeout() + 1000) {
                                retries = 0;
                            }
                            if (retries == this.mngr.getMaxRetries()) {
                                Log.i(DSRESTConnection.TAG, "Raising exception after " + String.valueOf(retries) + " retries for " + this.mngr.getChannelName());
                                this.mngr.DoOnException(this.mngr, InternalException);
                                res = null;
                                break;
                            }
                            retries++;
                            try {
                                Log.i(DSRESTConnection.TAG, "Sleeping between attempt (retries " + String.valueOf(retries) + ") for " + this.mngr.getChannelName());
                                Thread.sleep(this.mngr.getRetryDelay());
                            } catch (InterruptedException e2) {
                            }
                        } else {
                            Log.i(DSRESTConnection.TAG, "Raising exception for channel " + this.mngr.getChannelName() + " (retries " + String.valueOf(retries) + ")");
                            this.mngr.DoOnException(this.mngr, e);
                            res = null;
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            return (TJSONObject) res;
        }

        private void cbListLock() {
            this.lock.lock();
        }

        private void cbListUnLock() {
            this.lock.unlock();
        }
    }

    public static String getNewManagerID() {
        Random random = new Random();
        return String.valueOf(String.valueOf(random.nextInt(100000))) + "." + String.valueOf(random.nextInt(100000));
    }

    public void setMaxRetries(int maxRetries) {
        this.MaxRetries = maxRetries;
    }

    public int getMaxRetries() {
        return this.MaxRetries;
    }

    public void setRetryDelay(int retryDelay) {
        this.RetryDelay = retryDelay;
    }

    public int getRetryDelay() {
        return this.RetryDelay;
    }
}
