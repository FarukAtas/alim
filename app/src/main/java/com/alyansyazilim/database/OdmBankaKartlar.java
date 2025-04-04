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
public class OdmBankaKartlar extends OdmBase {
    public OdmBankaKartlar(Context con) {
        super(con, "bankart", "bankod");
    }

    public String getBanKod() {
        return getS("bankod");
    }

    public String getBanAd() {
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

    public long KayitEkle(String bankod, String adi, String pb) {
        ContentValues veriler = new ContentValues();
        veriler.put("bankod", bankod);
        veriler.put("adi", adi);
        veriler.put("pb", pb);
        return ins(veriler);
    }

    public boolean KayitDuzelt(long id, String bankod, String adi, String pb) {
        ContentValues veriler = new ContentValues();
        veriler.put("bankod", bankod);
        veriler.put("adi", adi);
        veriler.put("pb", pb);
        return upd(veriler, id);
    }

    public boolean BakiyeDuzelt(long id, String bakiye) {
        ContentValues veriler = new ContentValues();
        veriler.put("bakiye", bakiye);
        return upd(veriler, id);
    }

    public boolean getById(long kartid) {
        query("Banka Kartları (id ile)", null, String.valueOf(this.idColumn) + " = " + kartid, null);
        moveToFirst();
        return getCount() == 1;
    }

    public boolean getByBanKod(String bankod) {
        query("Banka Kartları (carikod ile)", null, "bankod ='" + bankod + "'", null);
        moveToFirst();
        return getCount() == 1;
    }

    public void getByFilter(String filtre) {
        query("Banka Kartları (Filtreli)", null, "adi like '%" + filtre + "%'", "bankod");
    }

    public void getAll() {
        query("Banka Kartları", null, null, "bankod");
    }

    public int getHrkCount() {
        OdmBankaKartHrk islem = new OdmBankaKartHrk(this.vContext);
        int ret = islem.getHrkCount(getBanKod());
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
                list.add(getBanKod());
            } while (moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.vContext, R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) dataAdapter);
        K.loge("odmbanka_add items on spinner");
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        getAll();
        if (moveToFirst()) {
            do {
                list.add(getBanKod());
            } while (moveToNext());
        }
        closecr();
        return list;
    }
}
