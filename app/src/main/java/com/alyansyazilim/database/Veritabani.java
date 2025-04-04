package com.alyansyazilim.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alyansyazilim.a_islemler.BankaIslemlerCRUD;
import com.alyansyazilim.a_islemler.CariHesapIslemlerCRUD;
import com.alyansyazilim.a_islemler.KasaIslemlerCRUD;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class Veritabani extends SQLiteOpenHelper {
    public static final int SURUM = 17;
    public static final String VERITABANI = "ilerims.db";
    private Context context;

    private void addEposta(SQLiteDatabase db) {
        Cursor c = db.query(K.TBL_CARKART, null, null, null, null, null, null);
        if (c.getColumnIndex("eposta") == -1) {
            try {
                db.execSQL("ALTER TABLE carkart add eposta TEXT");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 7:
                    doSqlSctipFromFile(db, "c.sql");
                    addEposta(db);
                    break;
                case 8:
                    doSqlSctipFromFile(db, "c009.sql");
                    break;
                case 9:
                    doSqlSctipFromFile(db, "c010.sql");
                    break;
                case 10:
                    doSqlSctipFromFile(db, "c014.sql");
                    OdmDbInfo.addDbInfo_firstrow(db);
                    break;
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        doSqlSctipFromFile(db, "c.sql");
        doSqlSctipFromFile(db, "d.sql");
        onUpgrade(db, 8, 17);
    }

    public boolean isExist() {
        String dbPath = getDbFileName(this.context);
        File file = new File(dbPath);
        return file.exists();
    }

    public SQLiteDatabase openRawDataBase() {
        String dbPath = getDbFileName(this.context);
        SQLiteDatabase dataBase = SQLiteDatabase.openDatabase(dbPath, null, 1);
        return dataBase;
    }

    public boolean passwordRequired() {
        boolean ret = false;
        if (isExist()) {
            SQLiteDatabase db = openRawDataBase();
            if (db.getVersion() > 10) {
                Cursor cur = db.query(K.TBL_DBINFO, null, "_id=1", null, null, null, null);
                cur.moveToFirst();
                ret = cur.getCount() == 1 && cur.getInt(cur.getColumnIndex("passreq")) != 0;
                cur.close();
            }
            db.close();
        }
        return ret;
    }

    public JSONObject dbinfoJson() {
        JSONObject ret = null;
        if (isExist()) {
            SQLiteDatabase db = openRawDataBase();
            if (db.getVersion() > 10) {
                OdmDbInfo dbi = new OdmDbInfo(this.context);
                dbi.getRow();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("i", dbi.getDbId());
                    jsonObject.put("u", dbi.getAdi());
                    jsonObject.put("e", dbi.getEposta());
                    jsonObject.put("t", dbi.getTelefon());
                    jsonObject.put("z", dbi.getZaman());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dbi.closecr();
                ret = jsonObject;
            }
            db.close();
        }
        return ret;
    }

    public static String getDbFileName(Context con) {
        return con.getDatabasePath(VERITABANI).getPath();
    }

    public Veritabani(Context con) {
        super(con, VERITABANI, (SQLiteDatabase.CursorFactory) null, 17);
        this.context = con;
        if (isExist()) {
            SQLiteDatabase db = openRawDataBase();
            db.getVersion();
            db.close();
        }
    }

    public void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper, java.lang.AutoCloseable
    public void close() {
        super.close();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onOpen(SQLiteDatabase db) {
        db.needUpgrade(17);
        super.onOpen(db);
    }

    public void doEmpty(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        while (c.moveToNext()) {
            String s = c.getString(0);
            if (!s.equals(K.TBL_DBINFO) && !s.equals("android_metadata") && !s.equals("sqlite_sequence")) {
                K.loge("table drop :'" + s + "'");
                db.execSQL("DROP TABLE IF EXISTS " + s);
            }
        }
        onCreate(db);
    }

    public static void doEmptyDB(Activity act) {
        Veritabani vt = new Veritabani(act);
        SQLiteDatabase db = vt.getWritableDatabase();
        vt.doEmpty(db);
        db.close();
        vt.close();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.alyansyazilim.database.Veritabani$1] */
    public static void doSampleDB(final Activity act) {
        final ProgressDialog progress1 = ProgressDialog.show(act, "Örnek Veri Hazırlanıyor", "Lütfen Bekleyin...", true);
        new Thread() { // from class: com.alyansyazilim.database.Veritabani.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Veritabani vt = new Veritabani(act);
                SQLiteDatabase db = vt.getWritableDatabase();
                vt.doSqlSctipFromFile(db, "o.sql");
                vt.doRelink();
                db.close();
                vt.close();
                progress1.dismiss();
            }
        }.start();
    }

    public String getFileContent(Resources resources, int rawId) throws IOException {
        InputStream is = resources.openRawResource(rawId);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer);
    }

    public void doSqlSctipFromFile(SQLiteDatabase db, String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = this.context.getResources().getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder sb2 = sb;
            while (true) {
                try {
                    String line = reader.readLine();
                    if (line != null) {
                        sb2.append(line);
                        if (line.length() > 0 && line.substring(line.length() - 1).equalsIgnoreCase(";")) {
                            db.execSQL(sb2.toString());
                            StringBuilder sb3 = new StringBuilder();
                            sb2 = sb3;
                        }
                    } else {
                        return;
                    }
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    return;
                }
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    public void doRelink() {
        CariHesapIslemlerCRUD.doRelink(this.context);
        KasaIslemlerCRUD.doRelink(this.context);
        BankaIslemlerCRUD.doRelink(this.context);
    }
}
