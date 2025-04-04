package com.alyansyazilim.database;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.OdmBase;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class OdmKasaKartlar extends OdmBase {
    public OdmKasaKartlar(Context con) {
        super(con, "kaskart", "kaskod");
    }

    public String getSira() {
        return getI16s("sira");
    }

    public String getKasKod() {
        return getS("kaskod");
    }

    public String getKasAd() {
        return getS("adi");
    }

    public String getBakiye() {
        return getD("bakiye");
    }

    public String getBakiyeF() {
        return K.ParaYaz(getBakiye());
    }

    public String getPb() {
        return getS("pb");
    }

    public long KayitEkle(String kaskod, String adi, String pb, String sira) {
        ContentValues veriler = new ContentValues();
        veriler.put("kaskod", kaskod);
        veriler.put("adi", adi);
        veriler.put("pb", pb);
        veriler.put("sira", sira);
        return ins(veriler);
    }

    public boolean KayitDuzelt(long id, String kaskod, String adi, String pb, String sira) {
        ContentValues veriler = new ContentValues();
        veriler.put("kaskod", kaskod);
        veriler.put("adi", adi);
        veriler.put("pb", pb);
        veriler.put("sira", sira);
        return upd(veriler, id);
    }

    public boolean BakiyeDuzelt(long id, String bakiye) {
        ContentValues veriler = new ContentValues();
        veriler.put("bakiye", bakiye);
        return upd(veriler, id);
    }

    public boolean getById(long kartid) {
        query("Kasa Kartları (id ile)", null, String.valueOf(this.idColumn) + " = " + kartid, null);
        moveToFirst();
        return getCount() == 1;
    }

    public boolean getByKasKod(String kaskod) {
        query("Kasa Kartları (kaskod ile)", null, "kaskod ='" + kaskod + "'", null);
        moveToFirst();
        return getCount() == 1;
    }

    public void getByFilter(String filtre) {
        query("Kasa Kartları (Filtreli)", null, "adi like '%" + filtre + "%'", "sira, kaskod");
    }

    public void getAll() {
        query("Kasa Kartları", null, null, "sira, kaskod");
    }

    public int getHrkCount() {
        OdmKasaKartHrk islem = new OdmKasaKartHrk(this.vContext);
        int ret = islem.getHrkCount(getKasKod());
        return ret;
    }

    public long deleteById(long islemid) {
        return delById(islemid);
    }

    public void addItemsOnSpinner2(Spinner spinner2) {
        List<String> list = new ArrayList<>();
        getAll();
        if (moveToFirst()) {
            do {
                list.add(getKasKod());
            } while (moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.vContext, R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) dataAdapter);
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        getAll();
        if (moveToFirst()) {
            do {
                list.add(getKasKod());
            } while (moveToNext());
        }
        return list;
    }
}
