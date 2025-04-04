package com.alyansyazilim.a_islemler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.alyansyazilim.a_islem.KasaIslemTahsil;
import com.alyansyazilim.a_islem.KasaIslemVirman;
import com.alyansyazilim.database.KasHrkBundle;
import com.alyansyazilim.database.OdmKasaIslem;
import com.alyansyazilim.database.OdmKasaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.C2C2C;
import com.alyansyazilim.ilerimuhasebesistemi.K;

/* loaded from: classes.dex */
public class KasaIslemlerCRUD {
    public static void doIslemEdit(Activity con, int islem, int mdl, long islemid) {
        Intent intent;
        switch (mdl) {
            case K.MDL_KASTAHSIL /* 65793 */:
                intent = new Intent(con, (Class<?>) KasaIslemTahsil.class);
                break;
            case K.MDL_KASODEME /* 65794 */:
                intent = new Intent(con, (Class<?>) KasaIslemTahsil.class);
                break;
            case K.MDL_KASVIRMAN /* 65795 */:
                intent = new Intent(con, (Class<?>) KasaIslemVirman.class);
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
        KasHrkBundle b = new KasHrkBundle(con, mdl, islemid);
        b.combine();
        OdmKasaIslem h = new OdmKasaIslem(con);
        h.deleteById(islemid);
    }

    public static boolean doTahsilatSaveLinks(Context con, long rowid, int mdl, String zaman, String edTutar, String lbKasKod, String edAck) {
        KasHrkBundle b = new KasHrkBundle(con, mdl, rowid);
        String bt = "0";
        String at = "0";
        if (mdl == 65793) {
            bt = edTutar;
        } else {
            at = edTutar;
        }
        b.add(mdl, rowid, zaman, lbKasKod, edAck, bt, at);
        b.combine();
        return true;
    }

    public static boolean doVirmanSaveLinks(Context con, long rowid, int mdl, String zaman, String edTutar, String lbKasKod, String edSonuc, String lbCarAd, String edAck, String edKhk, int edKht) {
        KasHrkBundle c = new KasHrkBundle(con, mdl, rowid);
        c.add(mdl, rowid, zaman, lbKasKod, edAck, "0", edTutar);
        c.add(mdl, rowid, zaman, edKhk, edAck, edSonuc, "0");
        c.combine();
        return true;
    }

    public static void doRelink(Context con) {
        String sonuc;
        OdmKasaIslem islem = new OdmKasaIslem(con);
        OdmKasaKartlar kart = new OdmKasaKartlar(con);
        islem.getAll();
        if (islem.moveToFirst()) {
            do {
                kart.getByKasKod(islem.getKasKod());
                switch (islem.getIkod()) {
                    case K.MDL_KASTAHSIL /* 65793 */:
                    case K.MDL_KASODEME /* 65794 */:
                        doTahsilatSaveLinks(con, Long.valueOf(islem.getID()).longValue(), islem.getIkod(), islem.getZaman(), islem.getTutar(), islem.getKasKod(), islem.getAck());
                        break;
                    case K.MDL_KASVIRMAN /* 65795 */:
                        C2C2C c2c = new C2C2C(con);
                        c2c.fromJson(islem.getMeta());
                        if (c2c.version == 0) {
                            sonuc = islem.getTutar();
                        } else {
                            sonuc = c2c.Sonuc.toString();
                        }
                        doVirmanSaveLinks(con, Long.valueOf(islem.getID()).longValue(), islem.getIkod(), islem.getZaman(), islem.getTutar(), islem.getKasKod(), sonuc, kart.getKasAd(), islem.getAck(), islem.getKarsiHesapKodu(), islem.getKarsiHesapTuru());
                        break;
                }
            } while (islem.moveToNext());
        }
    }
}
