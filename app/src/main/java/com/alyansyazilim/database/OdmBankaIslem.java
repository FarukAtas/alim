package com.alyansyazilim.database;

import android.content.ContentValues;
import android.content.Context;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.OdmBase;

/* loaded from: classes.dex */
public class OdmBankaIslem extends OdmBase {
    public OdmBankaIslem(Context con) {
        super(con, "banislem");
    }

    public int getIkod() {
        return getI("ikod");
    }

    public String getBanKod() {
        return getS("bankod");
    }

    public String getAck() {
        return getS("ack");
    }

    public int getKarsiHesapTuru() {
        return getI("kht");
    }

    public String getKarsiHesapKodu() {
        return getS("khk");
    }

    public String getMeta() {
        return getS("meta");
    }

    public String getTutar() {
        return getD("tutar");
    }

    public String getTutarF() {
        return K.ParaYaz(getTutar());
    }

    public String getZaman() {
        return getS("zaman");
    }

    public long KayitEkle(int ikod, String bankod, String ack, String tutar, int kht, String khk, String tarih, String saat, String meta) {
        ContentValues veriler = new ContentValues();
        veriler.put("zaman", K.TarihSaatSql(tarih, saat));
        veriler.put("ikod", Integer.valueOf(ikod));
        veriler.put("bankod", bankod);
        veriler.put("ack", ack);
        veriler.put("tutar", tutar);
        veriler.put("kht", Integer.valueOf(kht));
        veriler.put("khk", khk);
        veriler.put("meta", meta);
        return ins(veriler);
    }

    public boolean KayitDuzelt(long id, String bankod, String ack, String tutar, int kht, String khk, String tarih, String saat, String meta) {
        ContentValues veriler = new ContentValues();
        veriler.put("zaman", K.TarihSaatSql(tarih, saat));
        veriler.put("bankod", bankod);
        veriler.put("ack", ack);
        veriler.put("tutar", tutar);
        veriler.put("kht", Integer.valueOf(kht));
        veriler.put("khk", khk);
        veriler.put("meta", meta);
        return upd(veriler, id);
    }

    public void getAll() {
        query("Banka İşlemleri", null, null, "zaman desc");
    }

    public boolean getById(long islemid) {
        query("Kasa İşlemleri (id ile)", null, String.valueOf(this.idColumn) + " = " + islemid, null);
        moveToFirst();
        return getCount() == 1;
    }

    public long deleteById(long islemid) {
        return delById(islemid);
    }

    public String getZamanS() {
        return K.SqlTStrS(getZaman());
    }

    public String getZamanT() {
        return K.SqlTStrT(getZaman());
    }
}
