package com.alyansyazilim.a_islemler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.alyansyazilim.a_islem.CariIslemBorclandir;
import com.alyansyazilim.a_islem.CariIslemTahsilat;
import com.alyansyazilim.a_islem.CariIslemVirman;
import com.alyansyazilim.database.BanHrkBundle;
import com.alyansyazilim.database.CarHrkBundle;
import com.alyansyazilim.database.KasHrkBundle;
import com.alyansyazilim.database.OdmCariIslem;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.C2C2C;
import com.alyansyazilim.ilerimuhasebesistemi.K;

/* loaded from: classes.dex */
public class CariHesapIslemlerCRUD {
    public static void doIslemEdit(Activity con, int islem, int mdl, long islemid) {
        Intent intent;
        switch (mdl) {
            case K.MDL_CARTAHSIL /* 65537 */:
                intent = new Intent(con, (Class<?>) CariIslemTahsilat.class);
                break;
            case K.MDL_CARBORCD /* 65538 */:
                intent = new Intent(con, (Class<?>) CariIslemBorclandir.class);
                break;
            case K.MDL_CARALCKD /* 65539 */:
                intent = new Intent(con, (Class<?>) CariIslemBorclandir.class);
                break;
            case K.MDL_CARODEME /* 65540 */:
                intent = new Intent(con, (Class<?>) CariIslemTahsilat.class);
                break;
            case K.MDL_CARVIRMAN /* 65541 */:
                intent = new Intent(con, (Class<?>) CariIslemVirman.class);
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
        CarHrkBundle c = new CarHrkBundle(con, mdl, islemid);
        c.combine();
        KasHrkBundle k = new KasHrkBundle(con, mdl, islemid);
        k.combine();
        BanHrkBundle b = new BanHrkBundle(con, mdl, islemid);
        b.combine();
        OdmCariIslem h = new OdmCariIslem(con);
        h.deleteById(islemid);
    }

    public static boolean doTahsilatSaveLinks(Context con, long rowid, int mdl, String zaman, String edTutar, String lbCarKod, String edSonuc, String lbCarAd, String edAck, String edKhk, int edKht) {
        CarHrkBundle c = new CarHrkBundle(con, mdl, rowid);
        KasHrkBundle k = new KasHrkBundle(con, mdl, rowid);
        BanHrkBundle b = new BanHrkBundle(con, mdl, rowid);
        String bt = "0";
        String at = "0";
        if (mdl == 65537) {
            at = edSonuc;
        } else {
            bt = edSonuc;
        }
        c.add(mdl, rowid, zaman, lbCarKod, edAck, bt, at);
        c.combine();
        if (mdl == 65537) {
            at = edTutar;
        } else {
            bt = edTutar;
        }
        if (edKht == 0) {
            k.add(mdl, rowid, zaman, edKhk, String.valueOf(lbCarAd) + " - " + edAck, at, bt);
        }
        k.combine();
        if (edKht == 1) {
            b.add(mdl, rowid, zaman, edKhk, String.valueOf(lbCarAd) + " - " + edAck, at, bt);
        }
        b.combine();
        return true;
    }

    public static boolean doBorclandirSaveLinks(Context con, long rowid, int mdl, String zaman, String edTutar, String lbCarKod, String edAck) {
        CarHrkBundle b = new CarHrkBundle(con, mdl, rowid);
        String bt = "0";
        String at = "0";
        if (mdl == 65538) {
            bt = edTutar;
        } else {
            at = edTutar;
        }
        b.add(mdl, rowid, zaman, lbCarKod, edAck, bt, at);
        b.combine();
        return true;
    }

    public static boolean doVirmanSaveLinks(Context con, long rowid, int mdl, String zaman, String edTutar, String lbCarKod, String edSonuc, String lbCarAd, String edAck, String edKhk, int edKht) {
        CarHrkBundle c = new CarHrkBundle(con, mdl, rowid);
        c.add(mdl, rowid, zaman, lbCarKod, edAck, "0", edTutar);
        c.add(mdl, rowid, zaman, edKhk, edAck, edSonuc, "0");
        c.combine();
        return true;
    }

    public static void doRelink(Context con) {
        String sonuc;
        String sonuc2;
        OdmCariIslem islem = new OdmCariIslem(con);
        OdmCariKartlar kart = new OdmCariKartlar(con);
        islem.getAll();
        if (islem.moveToFirst()) {
            do {
                kart.getByCarKod(islem.getCarKod());
                switch (islem.getIkod()) {
                    case K.MDL_CARTAHSIL /* 65537 */:
                    case K.MDL_CARODEME /* 65540 */:
                        C2C2C c2c = new C2C2C(con);
                        c2c.fromJson(islem.getMeta());
                        if (c2c.version == 0) {
                            sonuc2 = islem.getTutar();
                        } else {
                            sonuc2 = c2c.Sonuc.toString();
                        }
                        doTahsilatSaveLinks(con, Long.valueOf(islem.getID()).longValue(), islem.getIkod(), islem.getZaman(), islem.getTutar(), islem.getCarKod(), sonuc2, kart.getCarAd(), islem.getAck(), islem.getKarsiHesapKodu(), islem.getKarsiHesapTuru());
                        break;
                    case K.MDL_CARBORCD /* 65538 */:
                    case K.MDL_CARALCKD /* 65539 */:
                        doBorclandirSaveLinks(con, Long.valueOf(islem.getID()).longValue(), islem.getIkod(), islem.getZaman(), islem.getTutar(), islem.getCarKod(), islem.getAck());
                        break;
                    case K.MDL_CARVIRMAN /* 65541 */:
                        C2C2C c2c2 = new C2C2C(con);
                        c2c2.fromJson(islem.getMeta());
                        if (c2c2.version == 0) {
                            sonuc = islem.getTutar();
                        } else {
                            sonuc = c2c2.Sonuc.toString();
                        }
                        doVirmanSaveLinks(con, Long.valueOf(islem.getID()).longValue(), islem.getIkod(), islem.getZaman(), islem.getTutar(), islem.getCarKod(), sonuc, kart.getCarAd(), islem.getAck(), islem.getKarsiHesapKodu(), islem.getKarsiHesapTuru());
                        break;
                }
            } while (islem.moveToNext());
        }
    }
}
