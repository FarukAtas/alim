package com.alyansyazilim.database;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.alyansyazilim.ilerimuhasebesistemi.OdmBase;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class OdmParaBirimKartlar extends OdmBase {
    public OdmParaBirimKartlar(Context con) {
        super(con, "pb", "pbkod");
    }

    public String getKod() {
        return getS("pbkod");
    }

    public String getAck() {
        return getS("ack");
    }

    public boolean getYerelPB() {
        return getB("yerel");
    }

    public int getSira() {
        return getI16("sira");
    }

    public long KayitEkle(String pbkod, String ack, boolean yerel) {
        ContentValues veriler = new ContentValues();
        veriler.put("pbkod", pbkod);
        veriler.put("ack", ack);
        veriler.put("sira", (Integer) 99);
        veriler.put("yerel", Integer.valueOf(yerel ? 1 : 0));
        return ins(veriler);
    }

    public boolean KayitDuzelt(long id, String pbkod, String ack, boolean yerel) {
        ContentValues veriler = new ContentValues();
        veriler.put("ack", ack);
        veriler.put("yerel", Integer.valueOf(yerel ? 1 : 0));
        return upd(veriler, id);
    }

    public boolean getById(long kartid) {
        query("ParaBirim Kartları (id ile)", null, String.valueOf(this.idColumn) + " = " + kartid, null);
        moveToFirst();
        return getCount() == 1;
    }

    public boolean getByKod(String kod) {
        query("ParaBirim Kartları (kod ile)", null, "pbkod ='" + kod + "'", null);
        moveToFirst();
        return getCount() == 1;
    }

    public String getYerelPBKod() {
        query("ParaBirim Kartları (yerel pb)", null, "yerel = 1", null);
        return (moveToFirst() && getCount() == 1) ? getKod() : "";
    }

    public void getByFilter(String filtre) {
        query("ParaBirim Kartları (Filtreli)", null, "ack like '%" + filtre + "%'", "pbkod");
    }

    public void getAll() {
        query("ParaBirim Kartları", null, null, "sira, pbkod");
    }

    public void addItemsOnSpinner2(Spinner spinner2) {
        List<String> list = new ArrayList<>();
        getAll();
        if (moveToFirst()) {
            do {
                list.add(getKod());
            } while (moveToNext());
        }
        closecr();
        ArrayAdapter<String> aAdpt = new ArrayAdapter<>(this.vContext, R.layout.simple_spinner_item, list);
        aAdpt.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) aAdpt);
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        getAll();
        if (moveToFirst()) {
            do {
                list.add(getKod());
            } while (moveToNext());
        }
        closecr();
        return list;
    }
}
