package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alyansyazilim.database.OdmBankaKartlar;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.database.OdmDovizKartlar;
import com.alyansyazilim.database.OdmKasaKartlar;
import com.alyansyazilim.database.OdmParaBirimKartlar;
import com.alyansyazilim.database.OdmVarlikKartlar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/* loaded from: classes.dex */
public class RaporGenelDurum extends Activity {
    private String carkod;
    private String eposta;
    private BigDecimal genelToplam;
    private StringBuilder lbErr;
    private StringBuilder lbRp;
    private TextView lbRp1;
    private Item[] pblist;
    private int pblistcount;
    private String vTarih;
    private String vTarihSql;

    public class Item {
        BigDecimal a;
        BigDecimal b;
        BigDecimal kur;
        String kurtarih;
        String pbkod;
        int siralama;
        BigDecimal sonuc;

        public Item(String pbkod, BigDecimal b, BigDecimal a) {
            this.pbkod = pbkod;
            this.b = b;
            this.a = a;
            int idx = RaporGenelDurum.this.getPbIdx(pbkod);
            if (idx > -1) {
                this.siralama = RaporGenelDurum.this.pblist[idx].siralama;
                this.kur = RaporGenelDurum.this.pblist[idx].kur;
            } else {
                this.kur = new BigDecimal(0);
                RaporGenelDurum.this.lbErr.append(pbkod);
            }
        }

        public Item(RaporGenelDurum raporGenelDurum, String pbkod, String b, String a) {
            this(pbkod, new BigDecimal(b), new BigDecimal(a));
        }

        public Item(String pbkod, int sira, BigDecimal kur, String kt) {
            this.pbkod = pbkod;
            this.b = new BigDecimal("0");
            this.a = new BigDecimal("0");
            this.siralama = sira;
            this.kur = kur;
            this.kurtarih = kt;
        }
    }

    private void doTitle() {
        this.lbRp1.setText((CharSequence) null);
        this.lbRp1.append("GENEL DURUM RAPORU ( TL )\n");
        this.lbRp1.append("Tarih  : " + this.vTarih + "\n");
        this.lbRp1.append("----------------------------------\n");
        this.lbRp1.append("Genel Toplam  : " + K.ParaYaz(this.genelToplam) + " TL\n");
        BigDecimal kur = this.pblist[getPbIdx("USD")].kur;
        this.lbRp1.append("USD Karşılığı : " + K.ParaYaz(Double.valueOf(this.genelToplam.doubleValue() / kur.doubleValue())) + "\n");
        BigDecimal kur2 = this.pblist[getPbIdx("EUR")].kur;
        this.lbRp1.append("EUR Karşılığı : " + K.ParaYaz(Double.valueOf(this.genelToplam.doubleValue() / kur2.doubleValue())) + "\n");
        BigDecimal kur3 = this.pblist[getPbIdx("ALT")].kur;
        this.lbRp1.append("ALT Karşılığı : " + K.ParaYaz(Double.valueOf(this.genelToplam.doubleValue() / kur3.doubleValue())) + "\n");
        this.lbRp1.append("---------------------------------\n");
        this.lbRp1.append(this.lbRp);
        if (this.lbErr.length() > 0) {
            K.loge(this.lbErr.toString());
        }
    }

    private void doVarliklar() {
        OdmVarlikKartlar kart = new OdmVarlikKartlar(this);
        kart.getAll();
        List<Item> list = new ArrayList<>();
        if (kart.moveToFirst()) {
            do {
                addorreplace(list, kart.getPb(), kart.getTutar());
            } while (kart.moveToNext());
            if (list.size() > 0) {
                this.lbRp.append("\n");
                this.lbRp.append("VARLIKLAR\n");
                this.lbRp.append("---------\n");
                doPrintList(list);
            }
        }
        kart.closecr();
    }

    private void doCari() {
        OdmCariKartlar kart = new OdmCariKartlar(this);
        kart.getAll();
        List<Item> list = new ArrayList<>();
        if (kart.moveToFirst()) {
            do {
                addorreplace(list, kart.getPb(), kart.getBakiye());
            } while (kart.moveToNext());
            if (list.size() > 0) {
                this.lbRp.append("\n");
                this.lbRp.append("CARİ HESAPLAR\n");
                this.lbRp.append("-------------\n");
                doPrintList(list);
            }
        }
        kart.closecr();
    }

    private void doKasa() {
        OdmKasaKartlar kart = new OdmKasaKartlar(this);
        kart.getAll();
        List<Item> list = new ArrayList<>();
        if (kart.moveToFirst()) {
            do {
                addorreplace(list, kart.getPb(), kart.getBakiye());
            } while (kart.moveToNext());
            if (list.size() > 0) {
                this.lbRp.append("\n");
                this.lbRp.append("KASALAR\n");
                this.lbRp.append("-------\n");
                doPrintList(list);
            }
        }
        kart.closecr();
    }

    private void doBanka() {
        OdmBankaKartlar kart = new OdmBankaKartlar(this);
        kart.getAll();
        List<Item> list = new ArrayList<>();
        if (kart.moveToFirst()) {
            do {
                addorreplace(list, kart.getPb(), kart.getBakiye());
            } while (kart.moveToNext());
            if (list.size() > 0) {
                this.lbRp.append("\n");
                this.lbRp.append("BANKA HESAPLARI\n");
                this.lbRp.append("---------------\n");
                doPrintList(list);
            }
        }
        kart.closecr();
    }

    private void addorreplace(List<Item> list, String pbkod, String tutar) {
        BigDecimal a;
        BigDecimal t = new BigDecimal(tutar);
        BigDecimal b = new BigDecimal(0);
        int s = t.compareTo(b);
        if (s != 0) {
            if (s > 0) {
                b = t;
                a = new BigDecimal(0);
            } else {
                a = t.negate();
            }
            for (int i = 0; i < list.size(); i++) {
                Item tmp = list.get(i);
                if (tmp.pbkod.equals(pbkod)) {
                    tmp.b = tmp.b.add(b);
                    tmp.a = tmp.a.add(a);
                    return;
                }
            }
            list.add(new Item(pbkod, b, a));
        }
    }

    protected void sortlist(List<Item> list) {
        Collections.sort(list, new Comparator<Item>() { // from class: com.alyansyazilim.ilerimuhasebesistemi.RaporGenelDurum.1
            @Override // java.util.Comparator
            public int compare(Item s1, Item s2) {
                return s1.siralama - s2.siralama;
            }
        });
        calclist(list);
    }

    private void calclist(List<Item> list) {
        for (Item item : list) {
            item.sonuc = item.b.subtract(item.a).multiply(item.kur);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPbIdx(String pbkod) {
        for (int i = 0; i < this.pblistcount; i++) {
            if (this.pblist[i].pbkod.equals(pbkod)) {
                return i;
            }
        }
        return -1;
    }

    private void doLoadPb2array() {
        BigDecimal kur;
        OdmParaBirimKartlar kart = new OdmParaBirimKartlar(this);
        OdmDovizKartlar dvz = new OdmDovizKartlar(this);
        kart.getAll();
        this.pblistcount = kart.getCount();
        this.pblist = new Item[this.pblistcount];
        if (kart.moveToFirst()) {
            int i = 0;
            do {
                String pb = kart.getKod();
                String kt = "--";
                if (kart.getYerelPB()) {
                    kur = new BigDecimal(1);
                } else if (dvz.getByKodZaman(pb, this.vTarihSql)) {
                    kur = new BigDecimal(dvz.getSatis());
                    kt = dvz.getZaman();
                } else {
                    kur = new BigDecimal("0");
                }
                this.pblist[i] = new Item(pb, kart.getSira(), kur, kt);
                i++;
            } while (kart.moveToNext());
        }
        kart.closecr();
        dvz.closecr();
        kart.closecr();
    }

    private void doPrintList(List<Item> list) {
        sortlist(list);
        this.lbRp.append("  P.B.    (+)         (-)     TL DEĞER \n");
        this.lbRp.append("  ---- ---------- ---------- ----------\n");
        for (Item item : list) {
            this.lbRp.append(String.format("  %-4.4s:%10s-%10s=%10s\n", item.pbkod, K.ParaYaz(item.b), K.ParaYaz(item.a), K.ParaYaz(item.sonuc)));
            this.genelToplam = this.genelToplam.add(item.sonuc);
        }
    }

    private void doPrintKurInfo() {
        this.lbRp.append("\n");
        this.lbRp.append("Raporda kullanılan Kurlar:\n");
        this.lbRp.append("--------------------------\n");
        for (int i = 0; i < this.pblistcount; i++) {
            this.lbRp.append(String.format("  %-4.4s : %9s", this.pblist[i].pbkod, K.ParaYazKur(Double.valueOf(this.pblist[i].kur.doubleValue()))));
            this.lbRp.append(" " + K.SqlTStrT(this.pblist[i].kurtarih) + " " + K.SqlTStrS(this.pblist[i].kurtarih));
            this.lbRp.append("\n");
        }
    }

    public void doReport() {
        this.lbRp1.setTypeface(Typeface.MONOSPACE);
        Date date = new Date();
        this.vTarih = K.Now_2(date);
        this.vTarihSql = K.DateSql(date);
        this.genelToplam = new BigDecimal(0);
        doLoadPb2array();
        doKasa();
        doBanka();
        doCari();
        doVarliklar();
        doPrintKurInfo();
        doTitle();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapor_geneldurum);
        setButtons();
        this.lbRp1 = (TextView) findViewById(R.id.lbRp);
        this.lbRp = new StringBuilder();
        this.lbErr = new StringBuilder();
        doReport();
    }

    private void setButtons() {
        Button btInc = (Button) findViewById(R.id.btIncrease);
        btInc.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.RaporGenelDurum.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                float ts = RaporGenelDurum.this.lbRp1.getTextSize();
                RaporGenelDurum.this.lbRp1.setTextSize((1.0f + ts) / RaporGenelDurum.this.getResources().getDisplayMetrics().density);
                RaporGenelDurum.this.lbRp1.getTextSize();
            }
        });
        Button btDec = (Button) findViewById(R.id.btDecrease);
        btDec.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.RaporGenelDurum.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                float ts = RaporGenelDurum.this.lbRp1.getTextSize();
                RaporGenelDurum.this.lbRp1.setTextSize((ts - 1.0f) / RaporGenelDurum.this.getResources().getDisplayMetrics().density);
                RaporGenelDurum.this.lbRp1.getTextSize();
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.RaporGenelDurum.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                RaporGenelDurum.this.finish();
            }
        });
        Button btnMail = (Button) findViewById(R.id.btMail);
        btnMail.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.RaporGenelDurum.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                RaporGenelDurum.this.email_text();
            }
        });
    }

    private void createFile(String strFile) {
        try {
            String data = String.valueOf("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><html><body style='tab-interval: .5in'><div class='Section1'><div>") + "<p class='MsoNormal' dir='LTR' style='text-align: left; unicode-bidi: embed'><span>";
            FileOutputStream fos = new FileOutputStream(strFile);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(String.valueOf(String.valueOf(String.valueOf(String.valueOf(data) + "Email data<br />") + "bla bla bla<br />") + "Footer<br />") + "</span></p></div></body></html>");
            out.flush();
            out.close();
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(), 1).show();
        }
    }

    public void email_attach(View v) {
        OdmCariKartlar k = new OdmCariKartlar(this);
        k.getByCarKod(this.carkod);
        String strFile = String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + "/temp";
        File file = new File(strFile);
        if (!file.exists()) {
            file.mkdirs();
        }
        String strFile2 = String.valueOf(strFile) + "/report.html";
        createFile(strFile2);
        try {
            Intent emailIntent = new Intent("android.intent.action.SEND");
            String address = k.getEposta();
            String emailtext = "Merhaba " + k.getCarAd() + ",";
            String emailtext2 = String.valueOf(String.valueOf(String.valueOf(emailtext) + "<hr/>") + "<table><tr><td>aas</td><td>asdasd</td></tr><tr><td>2aas</td><td>2asdasd</td></tr></table>") + "<hr/>";
            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{address});
            emailIntent.putExtra("android.intent.extra.SUBJECT", "Hesap extresi");
            emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + strFile2));
            emailIntent.putExtra("android.intent.extra.TEXT", Html.fromHtml(emailtext2));
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (Throwable tt) {
            Toast.makeText(this, "Request failed: " + tt.toString(), 1).show();
        }
    }

    public void email_text() {
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.putExtra("android.intent.extra.SUBJECT", String.valueOf(this.vTarih) + " tarihli Genel Durum Raporu");
        String emailText = this.lbRp1.getText().toString();
        String emailText2 = String.valueOf(emailText) + "Not: Listeyi düzgün görüntülemek için, metni not defterine kopyalayın.\r\n";
        emailIntent.setType("text/plain");
        emailIntent.putExtra("android.intent.extra.TEXT", emailText2);
        Intent client = Intent.createChooser(emailIntent, "e-posta:");
        startActivityForResult(client, 1);
    }

    public void email_html() {
        OdmCariKartlar k = new OdmCariKartlar(this);
        k.getByCarKod(this.carkod);
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{this.eposta});
        emailIntent.putExtra("android.intent.extra.SUBJECT", "Hesap Extreniz");
        String emailText = String.valueOf("<html><body><p>Merhaba </p>") + "liste sonu.<br></body></html>";
        emailIntent.setType("text/plain");
        emailIntent.putExtra("android.intent.extra.TEXT", emailText);
        Intent client = Intent.createChooser(emailIntent, "e-posta:");
        startActivityForResult(client, 1);
    }
}
