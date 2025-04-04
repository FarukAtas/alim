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
public class OdmVarlikKartlar extends OdmBase {
    public OdmVarlikKartlar(Context con) {
        super(con, "varlik", "varkod");
    }

    public String getKod() {
        return getS("varkod");
    }

    public int getTuru() {
        return getI("turu");
    }

    public String getAd() {
        return getS("adi");
    }

    public String getTarih() {
        return getS("zaman");
    }

    public String getTutar() {
        return getD("tutar");
    }

    public String getTutarF() {
        return K.ParaYaz(getTutar());
    }

    public String getPb() {
        return getS("pb");
    }

    public String getZaman() {
        return getS("zaman");
    }

    private void setContentValues(ContentValues veriler, String kod, String turu, String adi, String tutar, String pb, String sira, String meta, String tarih) {
        veriler.put("varkod", kod);
        veriler.put("turu", turu);
        veriler.put("adi", adi);
        veriler.put("pb", pb);
        veriler.put("tutar", tutar);
        veriler.put("meta", meta);
        veriler.put("sira", sira);
        veriler.put("zaman", K.TarihSaatSql(tarih, (String) null));
    }

    public long KayitEkle(String kod, String turu, String adi, String tutar, String pb, String sira, String meta, String tarih) {
        ContentValues veriler = new ContentValues();
        setContentValues(veriler, kod, turu, adi, tutar, pb, sira, meta, tarih);
        return ins(veriler);
    }

    public boolean KayitDuzelt(long id, String kod, String turu, String adi, String tutar, String pb, String sira, String meta, String tarih) {
        ContentValues veriler = new ContentValues();
        setContentValues(veriler, kod, turu, adi, tutar, pb, sira, meta, tarih);
        return upd(veriler, id);
    }

    public boolean getById(long kartid) {
        query("Varlık Kartları (id ile)", null, String.valueOf(this.idColumn) + " = " + kartid, null);
        moveToFirst();
        return getCount() == 1;
    }

    public boolean getByKod(String kod) {
        query("Varlık Kartları (kod ile)", null, "varkod ='" + kod + "'", null);
        moveToFirst();
        return getCount() == 1;
    }

    public void getByFilter(String filtre) {
        query("Varlık Kartları (Filtreli)", null, "adi like '%" + filtre + "%'", "sira");
    }

    public void getAll() {
        query("Varlık Kartları", null, null, "sira, varkod");
    }

    public long deleteById(long islemid) {
        return delById(islemid);
    }

    public void addItemsOnSpinner2(Spinner spinner2) {
        List<String> list = new ArrayList<>();
        getAll();
        if (moveToFirst()) {
            do {
                list.add(getKod());
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
                list.add(getKod());
            } while (moveToNext());
        }
        return list;
    }

    public String getZamanT() {
        return K.SqlTStrT(getZaman());
    }
}
