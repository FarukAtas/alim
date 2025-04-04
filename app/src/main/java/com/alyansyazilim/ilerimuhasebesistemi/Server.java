package com.alyansyazilim.ilerimuhasebesistemi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Handler;
import android.os.Process;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.database.Veritabani;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class Server {
    private Context sCtx;
    private ServerSocket sss;
    private boolean isRunning = true;
    Handler handler = new Handler();

    public Server(Context ctx) {
        this.sCtx = ctx;
    }

    public static String Cursor2JSON(Cursor cr) {
        JSONObject jSONObject = new JSONObject();
        JSONObject cols = new JSONObject();
        JSONArray rows = new JSONArray();
        int colc = cr.getColumnCount();
        for (int i = 0; i < colc; i++) {
            try {
                String cn = cr.getColumnName(i).toLowerCase(Locale.US);
                String ct = "s";
                if (cn.equals("srcmdl") || cn.equals("srcid") || cn.equals("srctur") || cn.equals("kht") || cn.equals("ikod") || cn.equals("_id") || cn.equals("sira")) {
                    ct = "i";
                }
                if (cn.equals("borc") || cn.equals("alck") || cn.equals("tutar") || cn.equals("kalan") || cn.equals("alis") || cn.equals("satis") || cn.equals("bakiye")) {
                    ct = "c";
                }
                cols.put(cr.getColumnName(i), String.valueOf(ct) + ";" + i + ";s");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (cr.moveToFirst()) {
            do {
                JSONObject obj = new JSONObject();
                for (int i2 = 0; i2 < colc; i2++) {
                    if (!K.isNOE(cr.getString(i2))) {
                        obj.put(cr.getColumnName(i2), cr.getString(i2));
                    }
                }
                rows.put(obj);
            } while (cr.moveToNext());
        }
        jSONObject.put("cols", cols);
        jSONObject.put("rows", rows);
        return jSONObject.toString();
    }

    protected void start() {
        new Thread(new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.Server.1
            @Override // java.lang.Runnable
            public void run() {
                String s;
                K.loge("Secure Web Server is starting up on port 8080");
                try {
                    Server.this.sss = new ServerSocket(8080);
                    K.loge("Waiting for connection");
                    while (Server.this.isRunning) {
                        try {
                            Socket socket = Server.this.sss.accept();
                            K.loge("Connected, sending data.");
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()), 8192);
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            out.println("Hello, now:" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ";PID:" + Process.myPid() + ";TID:" + Thread.currentThread().getId() + ";V:" + K.vVersionName + ";ID:" + K.vAndroidID + ";");
                            out.println("your ip:" + socket.getRemoteSocketAddress().toString() + "");
                            String str = in.readLine();
                            String[] sa = str.split(";");
                            if (sa.length > 0) {
                                s = sa[0];
                            } else {
                                s = "";
                            }
                            K.loge("read finish, send response");
                            if (s.compareTo("q") == 0) {
                                K.loge("SORGU: " + sa[1]);
                                Veritabani vt = new Veritabani(Server.this.sCtx);
                                SQLiteDatabase db = vt.getReadableDatabase();
                                try {
                                    Cursor cr = db.rawQuery(sa[1], null);
                                    out.println(Server.Cursor2JSON(cr));
                                    cr.close();
                                    db.close();
                                } catch (Exception e) {
                                    K.loge("Error: " + e);
                                    out.println("Error: " + e);
                                }
                            }
                            if (s.compareTo("i") == 0) {
                                Veritabani vt2 = new Veritabani(Server.this.sCtx);
                                SQLiteDatabase db2 = vt2.getWritableDatabase();
                                try {
                                    SQLiteStatement stm = db2.compileStatement(sa[1]);
                                    out.println(stm.executeInsert());
                                    stm.close();
                                    db2.close();
                                } catch (Exception e2) {
                                    K.loge("Error: " + e2);
                                    out.println("Error: " + e2);
                                }
                            }
                            if (s.compareTo("ts") == 0) {
                                K.loge("tarih saat soruldu");
                                out.println(new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(new Date()));
                            }
                            if (s.compareTo("cn") == 0) {
                                K.loge("Makine adı soruldu");
                                out.println(K.ConnectID);
                            }
                            if (s.compareTo("li") == 0) {
                                K.loge("login soruldu");
                                out.println(K.vVersionName);
                                Runnable runnable = new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.Server.1.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        try {
                                            WebServices.getWebServiceStatus(Server.this.sCtx, Server.this.handler, "MainW");
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                };
                                new Thread(runnable).start();
                            }
                            if (s.compareTo("ver") == 0) {
                                K.loge("versiyon soruldu");
                                out.println(K.vVersionName);
                            }
                            if (s.compareTo("ck") == 0) {
                                K.loge("cari kartlar soruldu");
                                OdmCariKartlar ck = new OdmCariKartlar(Server.this.sCtx);
                                ck.getAll();
                                out.println(Server.Cursor2JSON(ck.getCr()));
                                ck.closecr();
                                K.loge("cari kartlart gönderildi");
                            }
                            out.flush();
                            socket.close();
                        } catch (Exception e3) {
                            K.loge("Error: " + e3);
                            e3.printStackTrace();
                        }
                    }
                } catch (Exception e4) {
                    System.out.println("Error: " + e4);
                }
            }
        }).start();
    }

    protected void stop() {
        K.loge("Secure Web Server is stopping");
        try {
            this.isRunning = false;
            if (this.sss != null) {
                this.sss.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        K.loge("Secure Web Server is stopped");
    }
}
