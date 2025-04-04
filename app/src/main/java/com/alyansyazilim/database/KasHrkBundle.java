package com.alyansyazilim.database;

import android.content.Context;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class KasHrkBundle {
    private Context con;
    private List<Item> list = new ArrayList();
    private long srcid;
    private int srcmdl;

    public class Item {
        public String ack;
        public String alck;
        public String borc;
        public String kaskod;
        public long srcid;
        public int srcmdl;
        public String zaman;

        public Item() {
        }
    }

    public KasHrkBundle(Context con, int srcmdl, long srcid) {
        this.srcmdl = srcmdl;
        this.srcid = srcid;
        this.con = con;
    }

    public List<Item> getList() {
        return this.list;
    }

    public boolean add(int srcmdl, long srcid, String zaman, String kaskod, String ack, String borc, String alck) {
        Item item = new Item();
        item.srcmdl = srcmdl;
        item.srcid = srcid;
        item.zaman = zaman;
        item.kaskod = kaskod;
        item.ack = ack;
        if (K.isNOE(borc)) {
            borc = "0";
        }
        item.borc = borc;
        if (K.isNOE(alck)) {
            alck = "0";
        }
        item.alck = alck;
        this.list.add(item);
        return true;
    }

    private void KasKartLink(Item item, int EkleCikar) {
        BigDecimal bk;
        OdmKasaKartlar k = new OdmKasaKartlar(this.con);
        if (k.getByKasKod(item.kaskod)) {
            BigDecimal b = new BigDecimal(item.borc);
            BigDecimal a = new BigDecimal(item.alck);
            BigDecimal bk2 = new BigDecimal(k.getBakiye());
            if (EkleCikar == 1) {
                bk = bk2.add(b).subtract(a);
            } else {
                bk = bk2.add(a).subtract(b);
            }
            k.BakiyeDuzelt(Long.valueOf(k.getID()).longValue(), bk.toString());
            return;
        }
        K.loge("kasa_kart bulunamadı: '" + item.kaskod + "'");
    }

    public void combine() {
        OdmKasaKartHrk hrk = new OdmKasaKartHrk(this.con);
        Item oldItem = new Item();
        hrk.getBySrcMdlId(this.srcmdl, this.srcid);
        if (hrk.moveToFirst()) {
            do {
                oldItem.kaskod = hrk.getKasKod();
                oldItem.borc = hrk.getBorc();
                oldItem.alck = hrk.getAlck();
                KasKartLink(oldItem, -1);
            } while (hrk.moveToNext());
        }
        hrk.deleteBySrcMdlId(this.srcmdl, this.srcid);
        for (Item item : this.list) {
            hrk.KayitEkle(item.srcmdl, item.srcid, item.zaman, item.kaskod, item.ack, item.borc, item.alck);
            KasKartLink(item, 1);
        }
    }

    public int getSrcmdl() {
        return this.srcmdl;
    }

    public void setSrcmdl(int srcmdl) {
        this.srcmdl = srcmdl;
    }

    public long getSrcid() {
        return this.srcid;
    }

    public void setSrcid(int srcid) {
        this.srcid = srcid;
    }
}
