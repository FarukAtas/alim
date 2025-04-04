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
public class OdmCariKartlar extends OdmBase {
    public OdmCariKartlar(Context con) {
        super(con, K.TBL_CARKART);
    }

    public String getCarKod() {
        return getS("carkod");
    }

    public String getCarAd() {
        return getS("adi");
    }

    public String getEposta() {
        return getS("eposta");
    }

    public String getBakiye() {
        return getD("bakiye");
    }

    public String getBakiyeF() {
        return K.ParaYaz(getBakiye());
    }

    public String getTelefon() {
        return getS("telefon");
    }

    public String getPb() {
        return getS("pb");
    }

    public long KayitEkle(String carkod, String adi, String eposta, String telefon, String pb) {
        ContentValues veriler = new ContentValues();
        veriler.put("carkod", carkod);
        veriler.put("adi", adi);
        veriler.put("eposta", eposta);
        veriler.put("telefon", telefon);
        veriler.put("pb", pb);
        return ins(veriler);
    }

    public boolean KayitDuzelt(long id, String carkod, String adi, String eposta, String telefon, String pb) {
        ContentValues veriler = new ContentValues();
        veriler.put("carkod", carkod);
        veriler.put("adi", adi);
        veriler.put("eposta", eposta);
        veriler.put("telefon", telefon);
        veriler.put("pb", pb);
        return upd(veriler, id);
    }

    public boolean BakiyeDuzelt(long id, String bakiye) {
        ContentValues veriler = new ContentValues();
        veriler.put("bakiye", bakiye);
        return upd(veriler, id);
    }

    public boolean getById(long kartid) {
        query("CariHesap Kartları (id ile)", null, String.valueOf(this.idColumn) + " = " + kartid, null);
        moveToFirst();
        return getCount() == 1;
    }

    public boolean getByCarKod(String carkod) {
        query("CariHesap Kartları (carikod ile)", null, "carkod ='" + carkod + "'", null);
        moveToFirst();
        return getCount() == 1;
    }

    public void getByFilter(String filtre) {
        query("CariHesap Kartları (Filtreli)", null, "adi like '%" + filtre + "%'", "carkod");
    }

    public void getAll() {
        query("CariHesap Kartları", null, null, "carkod");
    }

    public int getHrkCount() {
        OdmCariKartHrk islem = new OdmCariKartHrk(this.vContext);
        int ret = islem.getHrkCount(getCarKod());
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
                list.add(String.valueOf(getCarKod()) + "=>" + getBakiye());
            } while (moveToNext());
        }
        closecr();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.vContext, R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter) dataAdapter);
    }

    public List<String> getList() {
        List<String> list = new ArrayList<>();
        getAll();
        if (moveToFirst()) {
            do {
                list.add(getCarKod());
            } while (moveToNext());
        }
        closecr();
        return list;
    }
}
