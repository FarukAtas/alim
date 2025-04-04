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
public class OdmDovizKartlar extends OdmBase {
    public OdmDovizKartlar(Context con) {
        super(con, K.TBL_DVZKART);
    }

    public String getKod() {
        return getS("pb");
    }

    public String getAlis() {
        return getD("alis");
    }

    public String getSatis() {
        return getD("satis");
    }

    public String getAlisF() {
        return K.ParaYaz(getAlis());
    }

    public String getZaman() {
        return getS("zaman");
    }

    public long KayitEkle(String kod, String zaman, String alis, String satis) {
        ContentValues veriler = new ContentValues();
        veriler.put("pb", kod);
        veriler.put("zaman", zaman);
        veriler.put("alis", alis);
        veriler.put("satis", satis);
        return ins(veriler);
    }

    public boolean KayitDuzelt(long id, String kod, String zaman, String alis, String satis) {
        ContentValues veriler = new ContentValues();
        veriler.put("pb", kod);
        veriler.put("zaman", zaman);
        veriler.put("alis", alis);
        veriler.put("satis", satis);
        return upd(veriler, id);
    }

    public boolean getById(long kartid) {
        query("Döviz Kartları (id ile)", null, String.valueOf(this.idColumn) + " = " + kartid, null);
        moveToFirst();
        return getCount() == 1;
    }

    public void getByKod(String kod) {
        query("Döviz Kartları (kod ile)", null, "pb ='" + kod + "'", "zaman desc");
    }

    public boolean getByKodZaman(String kod, String zaman) {
        queryLong("Döviz Kartları (kod ve zaman ile)", null, "pb='" + kod + "' and zaman <='" + zaman + "'", null, "zaman desc", 1, 0);
        moveToFirst();
        return getCount() == 1;
    }

    public void getByZaman(String zaman) {
        query("Döviz Kartları (zaman ile)", null, "zaman ='" + zaman + "'", "pb");
    }

    public void getAll() {
        query("Döviz Kartları", null, null, "zaman,pb");
    }

    public long deleteById(long islemid) {
        return delById(islemid);
    }

    public void addItemsOnSpinner2(Spinner spinner2) {
        List<String> list = new ArrayList<>();
        getAll();
        if (moveToFirst()) {
            do {
                list.add(String.valueOf(getKod()) + "=>" + getAlisF());
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
        closecr();
        return list;
    }
}
