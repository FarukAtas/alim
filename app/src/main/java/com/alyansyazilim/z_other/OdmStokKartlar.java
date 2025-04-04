package com.alyansyazilim.z_other;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.alyansyazilim.database.Veritabani;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class OdmStokKartlar {
    public Cursor cr;
    private Context vContext;
    public Veritabani vt;

    public OdmStokKartlar(Context con) {
        this.vt = new Veritabani(con);
        this.vContext = con;
    }

    protected void finalize() throws Throwable {
        this.vt.close();
        super.finalize();
    }

    public String getStkKod() {
        return this.cr.getString(this.cr.getColumnIndex("stkkod"));
    }

    public String getCarAd() {
        return this.cr.getString(this.cr.getColumnIndex("adi"));
    }

    public String getBakiye() {
        return this.cr.getString(this.cr.getColumnIndex("kalan"));
    }

    public String getBakiyeFmt() {
        BigInteger b = new BigInteger(getBakiye());
        return String.format("%6.2f", Float.valueOf(b.floatValue()));
    }

    public Cursor Kayitlar() {
        SQLiteDatabase db = this.vt.getReadableDatabase();
        this.cr = db.query("stkkart", null, null, null, null, null, "stkkod");
        return this.cr;
    }

    public Cursor getAllForCB() {
        SQLiteDatabase db = this.vt.getReadableDatabase();
        this.cr = db.query("stkkart", null, null, null, null, null, "_id");
        return this.cr;
    }

    public void addItemsOnSpinner2(Spinner spinner2) {
        List<String> list = new ArrayList<>();
        Cursor c = getAllForCB();
        if (c.moveToFirst()) {
            do {
                list.add(String.valueOf(getStkKod()) + "=>" + getBakiye());
            } while (c.moveToNext());
        }
        c.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.vContext, R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) dataAdapter);
    }
}
