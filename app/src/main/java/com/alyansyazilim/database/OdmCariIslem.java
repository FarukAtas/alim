package com.alyansyazilim.database;

import android.content.ContentValues;
import android.content.Context;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.OdmBase;

/* loaded from: classes.dex */
public class OdmCariIslem extends OdmBase {
    public OdmCariIslem(Context con) {
        super(con, "carislem");
    }

    public int getIkod() {
        return getI("ikod");
    }

    public String getCarKod() {
        return getS("carkod");
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

    public long KayitEkle(int ikod, String carkod, String ack, String tutar, int kht, String khk, String tarih, String saat, String meta) {
        ContentValues veriler = new ContentValues();
        veriler.put("zaman", K.TarihSaatSql(tarih, saat));
        veriler.put("ikod", Integer.valueOf(ikod));
        veriler.put("carkod", carkod);
        veriler.put("ack", ack);
        veriler.put("tutar", tutar);
        veriler.put("kht", Integer.valueOf(kht));
        veriler.put("khk", khk);
        veriler.put("meta", meta);
        return ins(veriler);
    }

    public boolean KayitDuzelt(long id, String carkod, String ack, String tutar, int kht, String khk, String tarih, String saat, String meta) {
        ContentValues veriler = new ContentValues();
        veriler.put("zaman", K.TarihSaatSql(tarih, saat));
        veriler.put("carkod", carkod);
        veriler.put("ack", ack);
        veriler.put("tutar", tutar);
        veriler.put("kht", Integer.valueOf(kht));
        veriler.put("khk", khk);
        veriler.put("meta", meta);
        return upd(veriler, id);
    }

    public void getAll() {
        query("CariHesap İşlemleri", null, null, "zaman desc");
    }

    public boolean getById(long islemid) {
        query("CariHesap İşlemleri (id ile)", null, String.valueOf(this.idColumn) + " = " + islemid, null);
        moveToFirst();
        return getCount() == 1;
    }

    public String getTopUsedKod(int kht) {
        String ret;
        queryLong("CariHesap İşlemleri (topusedcod)", new String[]{"khk", "count(*)"}, "khk <>'' and kht=" + kht, "khk", "2 desc", 0, 0);
        moveToFirst();
        if (getCount() > 0) {
            ret = getKarsiHesapKodu();
        } else {
            OdmParaBirimKartlar pb = new OdmParaBirimKartlar(this.vContext);
            ret = pb.getYerelPBKod();
            pb.closecr();
        }
        closecr();
        return ret;
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
