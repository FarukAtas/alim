package com.alyansyazilim.a_islemler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.alyansyazilim.a_islem.BankaIslemVirman;
import com.alyansyazilim.a_islem.BankaIslemYatan;
import com.alyansyazilim.database.BanHrkBundle;
import com.alyansyazilim.database.OdmBankaIslem;
import com.alyansyazilim.database.OdmBankaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.C2C2C;
import com.alyansyazilim.ilerimuhasebesistemi.K;

/* loaded from: classes.dex */
public class BankaIslemlerCRUD {
    public static void doIslemEdit(Activity con, int islem, int mdl, long islemid) {
        Intent intent;
        switch (mdl) {
            case K.MDL_BANYATAN /* 66049 */:
                intent = new Intent(con, (Class<?>) BankaIslemYatan.class);
                break;
            case K.MDL_BANCEKILEN /* 66050 */:
                intent = new Intent(con, (Class<?>) BankaIslemYatan.class);
                break;
            case K.MDL_BANVIRMAN /* 66051 */:
                intent = new Intent(con, (Class<?>) BankaIslemVirman.class);
                break;
            default:
                intent = null;
                break;
        }
        if (intent != null) {
            Bundle extras = new Bundle();
            extras.putInt("mdl", mdl);
            extras.putInt("islem", islem);
            extras.putLong("islemid", islemid);
            intent.putExtras(extras);
            con.startActivityForResult(intent, 1);
        }
    }

    public static void doIslemSil(Context con, int mdl, long islemid) {
        BanHrkBundle b = new BanHrkBundle(con, mdl, islemid);
        b.combine();
        OdmBankaIslem h = new OdmBankaIslem(con);
        h.deleteById(islemid);
    }

    public static boolean doYatanSaveLinks(Context con, long rowid, int mdl, String zaman, String edTutar, String lbBanKod, String edAck) {
        BanHrkBundle b = new BanHrkBundle(con, mdl, rowid);
        String bt = "0";
        String at = "0";
        if (mdl == 66049) {
            bt = edTutar;
        } else {
            at = edTutar;
        }
        b.add(mdl, rowid, zaman, lbBanKod, edAck, bt, at);
        b.combine();
        return true;
    }

    public static boolean doVirmanSaveLinks(Context con, long rowid, int mdl, String zaman, String edTutar, String lbBanKod, String edSonuc, String lbCarAd, String edAck, String edKhk, int edKht) {
        BanHrkBundle c = new BanHrkBundle(con, mdl, rowid);
        c.add(mdl, rowid, zaman, lbBanKod, edAck, "0", edTutar);
        c.add(mdl, rowid, zaman, edKhk, edAck, edSonuc, "0");
        c.combine();
        return true;
    }

    public static void doRelink(Context con) {
        String sonuc;
        OdmBankaIslem islem = new OdmBankaIslem(con);
        OdmBankaKartlar kart = new OdmBankaKartlar(con);
        islem.getAll();
        if (islem.moveToFirst()) {
            do {
                kart.getByBanKod(islem.getBanKod());
                switch (islem.getIkod()) {
                    case K.MDL_BANYATAN /* 66049 */:
                    case K.MDL_BANCEKILEN /* 66050 */:
                        doYatanSaveLinks(con, Long.valueOf(islem.getID()).longValue(), islem.getIkod(), islem.getZaman(), islem.getTutar(), islem.getBanKod(), islem.getAck());
                        break;
                    case K.MDL_BANVIRMAN /* 66051 */:
                        C2C2C c2c = new C2C2C(con);
                        c2c.fromJson(islem.getMeta());
                        if (c2c.version == 0) {
                            sonuc = islem.getTutar();
                        } else {
                            sonuc = c2c.Sonuc.toString();
                        }
                        doVirmanSaveLinks(con, Long.valueOf(islem.getID()).longValue(), islem.getIkod(), islem.getZaman(), islem.getTutar(), islem.getBanKod(), sonuc, kart.getBanAd(), islem.getAck(), islem.getKarsiHesapKodu(), islem.getKarsiHesapTuru());
                        break;
                }
            } while (islem.moveToNext());
        }
    }
}
