package com.alyansyazilim.database;

import android.content.ContentValues;
import android.content.Context;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.OdmBase;

/* loaded from: classes.dex */
public class OdmBankaKartHrk extends OdmBase {
    public OdmBankaKartHrk(Context con) {
        super(con, "banhrk");
    }

    public long getSrcId() {
        return getI("srcid");
    }

    public int getSrcMdl() {
        return getI("srcmdl");
    }

    public String getBanKod() {
        return getS("bankod");
    }

    public String getAck() {
        return getS("ack");
    }

    public String getBorc() {
        return getD("borc");
    }

    public String getAlck() {
        return getD("alck");
    }

    public String getBorcF() {
        return K.ParaYaz(getBorc());
    }

    public String getAlckF() {
        return K.ParaYaz(getAlck());
    }

    public String getZaman() {
        return getS("zaman");
    }

    public long KayitEkle(int srcmdl, long srcid, String zaman, String bankod, String ack, String borc, String alck) {
        ContentValues veriler = new ContentValues();
        veriler.put("srcmdl", Integer.valueOf(srcmdl));
        veriler.put("srcid", Long.valueOf(srcid));
        veriler.put("zaman", zaman);
        veriler.put("bankod", bankod);
        veriler.put("ack", ack);
        veriler.put("borc", borc);
        veriler.put("alck", alck);
        return ins(veriler);
    }

    public void getAll(String ABanKod) {
        query("Banka Hareketleri", null, "bankod='" + ABanKod + "'", "zaman");
    }

    public void getBySrcMdlId(int srcmdl, long srcid) {
        query("Banka Hareketleri", null, "srcmdl=" + srcmdl + " and srcid=" + srcid, "zaman");
    }

    public long deleteBySrcMdlId(int srcmdl, long srcid) {
        return del("srcmdl=" + srcmdl + " and srcid=" + srcid);
    }

    public int getHrkCount(String banKod) {
        query("Banka Hareket Sayısı", new String[]{"count(*) as say"}, "bankod='" + banKod + "'", null);
        moveToFirst();
        int ret = getI("say");
        closecr();
        return ret;
    }
}
