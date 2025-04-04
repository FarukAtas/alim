package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* loaded from: classes.dex */
public class ServerService extends Service {
    private Server sws;

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.sws = new Server(this);
        this.sws.start();
        createNotification();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.sws.stop();
        stopForeground(true);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotification() {
    }
}
