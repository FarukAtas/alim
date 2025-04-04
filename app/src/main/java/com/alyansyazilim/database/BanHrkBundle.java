package com.alyansyazilim.database;

import android.content.Context;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class BanHrkBundle {
    private Context con;
    private List<Item> list = new ArrayList();
    private long srcid;
    private int srcmdl;

    public class Item {
        public String ack;
        public String alck;
        public String bankod;
        public String borc;
        public long srcid;
        public int srcmdl;
        public String zaman;

        public Item() {
        }
    }

    public BanHrkBundle(Context con, int srcmdl, long srcid) {
        this.srcmdl = srcmdl;
        this.srcid = srcid;
        this.con = con;
    }

    public List<Item> getList() {
        return this.list;
    }

    public boolean add(int srcmdl, long srcid, String zaman, String bankod, String ack, String borc, String alck) {
        Item item = new Item();
        item.srcmdl = srcmdl;
        item.srcid = srcid;
        item.zaman = zaman;
        item.bankod = bankod;
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

    private void BanKartLink(Item item, int EkleCikar) {
        BigDecimal bk;
        OdmBankaKartlar k = new OdmBankaKartlar(this.con);
        if (k.getByBanKod(item.bankod)) {
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
        K.loge("banka_kart bulunamadı: '" + item.bankod + "'");
    }

    public void combine() {
        OdmBankaKartHrk hrk = new OdmBankaKartHrk(this.con);
        Item oldItem = new Item();
        hrk.getBySrcMdlId(this.srcmdl, this.srcid);
        if (hrk.moveToFirst()) {
            do {
                oldItem.bankod = hrk.getBanKod();
                oldItem.borc = hrk.getBorc();
                oldItem.alck = hrk.getAlck();
                BanKartLink(oldItem, -1);
            } while (hrk.moveToNext());
        }
        hrk.deleteBySrcMdlId(this.srcmdl, this.srcid);
        for (Item item : this.list) {
            hrk.KayitEkle(item.srcmdl, item.srcid, item.zaman, item.bankod, item.ack, item.borc, item.alck);
            BanKartLink(item, 1);
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
