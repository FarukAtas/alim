package com.alyansyazilim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CheckBox;
import android.widget.TextView;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.OdmBase;
import com.alyansyazilim.ilerimuhasebesistemi.RegSrv;
import java.util.UUID;

/* loaded from: classes.dex */
public class OdmDbInfo extends OdmBase {
    public OdmDbInfo(Context con) {
        super(con, K.TBL_DBINFO);
    }

    public String getDbId() {
        return getS("dbid");
    }

    public String getAdi() {
        return getS("adi");
    }

    public String getEposta() {
        return getS("eposta");
    }

    public String getTelefon() {
        return getS("telefon");
    }

    public String getZaman() {
        return getS("zaman");
    }

    public String getPassw() {
        return getS("password");
    }

    public boolean passwordEqual(TextView pass) {
        getRow();
        return pass.getText().toString().compareTo(getPassw()) == 0;
    }

    public boolean getPassReq() {
        return getB("passreq");
    }

    public static long addDbInfo_firstrow(SQLiteDatabase db) {
        Cursor cur = db.query(K.TBL_DBINFO, null, "_id=1", null, null, null, null);
        cur.moveToFirst();
        if (cur.getCount() != 0) {
            return 0L;
        }
        ContentValues veriler = new ContentValues();
        veriler.put("_id", (Integer) 1);
        veriler.put("dbid", UUID.randomUUID().toString());
        veriler.put("adi", "Buraya Adınızı Yazın");
        veriler.put("eposta", "alyansyazilim@gmail.com");
        veriler.put("zaman", K.NowSql());
        veriler.put("passreq", (Integer) 0);
        veriler.put("meta", "{}");
        return db.insertOrThrow(K.TBL_DBINFO, null, veriler);
    }

    public static void setGlobalHash(Context con) {
        K.vHash = RegSrv.getHash(con);
        K.vAdiTxt.setText(RegSrv.getDbAdi(con));
    }

    public boolean KayitDuzelt(long id, TextView adi, TextView eposta, TextView telefon, CheckBox passreq, TextView pass) {
        ContentValues veriler = new ContentValues();
        veriler.put("adi", adi.getText().toString());
        veriler.put("eposta", eposta.getText().toString());
        veriler.put("telefon", telefon.getText().toString());
        veriler.put("passreq", Integer.valueOf(passreq.isChecked() ? 1 : 0));
        veriler.put("password", pass.getText().toString());
        boolean ret = upd(veriler, id);
        setGlobalHash(this.vContext);
        return ret;
    }

    public boolean getRow() {
        return getById_(1L);
    }

    public boolean getById_(long kartid) {
        query("DB Info (id ile)", null, String.valueOf(this.idColumn) + " = " + kartid, null);
        int rc = getCount();
        if (rc > 0) {
            moveToFirst();
        }
        return rc == 1;
    }
}
