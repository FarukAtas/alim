package com.alyansyazilim.a_extre;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.alyansyazilim.database.OdmDbInfo;
import com.alyansyazilim.database.OdmKasaKartHrk;
import com.alyansyazilim.database.OdmKasaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class KasaExtre extends Activity {
    private String eposta;
    private String kaskod;
    private TextView lbKasKod;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
    private ListView view;

    public void doLoadView() {
        BigDecimal bt = new BigDecimal(0);
        BigDecimal at = new BigDecimal(0);
        OdmKasaKartHrk k = new OdmKasaKartHrk(this);
        k.getAll(this.kaskod);
        this.mylist.clear();
        if (k.moveToFirst()) {
            do {
                bt = bt.add(new BigDecimal(k.getBorc()));
                at = at.add(new BigDecimal(k.getAlck()));
                HashMap<String, String> map = new HashMap<>();
                map.put("srcid", K.i2s(k.getSrcId()));
                map.put("srcmdl", K.i2s(k.getSrcMdl()));
                map.put("tarih", K.SqlTSmd(k.getZaman()));
                map.put("tarihL", K.SqlTStrT(k.getZaman()));
                map.put("ack", k.getAck());
                map.put("borc", k.getBorcF());
                map.put("alck", k.getAlckF());
                map.put("baky", K.ParaYaz(bt.subtract(at)));
                this.mylist.add(map);
            } while (k.moveToNext());
        }
        Collections.reverse(this.mylist);
        SimpleAdapter mSchedule = new SimpleAdapter(this, this.mylist, R.layout.activity_kasa_extre_row, new String[]{"tarih", "ack", "borc", "alck", "baky"}, new int[]{R.id.tvTarih, R.id.tvAck, R.id.tvBorc, R.id.tvAlck, R.id.tvBky});
        this.view.setAdapter((ListAdapter) mSchedule);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa_extre);
        setButtons();
        OdmKasaKartlar ck = new OdmKasaKartlar(this);
        ck.getById(getIntent().getExtras().getLong("kaskartid"));
        this.kaskod = ck.getKasKod();
        this.lbKasKod.setText(String.valueOf(ck.getKasKod()) + " - " + ck.getKasAd() + " (" + ck.getPb() + ")");
        ck.closecr();
        this.view = (ListView) findViewById(R.id.lvExtre);
        doLoadView();
        this.view.setFocusable(true);
        this.view.setClickable(true);
        this.view.requestFocus();
        this.view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() { // from class: com.alyansyazilim.a_extre.KasaExtre.1
            @Override // android.view.View.OnCreateContextMenuListener
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (v.getId() == R.id.lvExtre) {
                    menu.setHeaderTitle("İşlem seç");
                    String[] menuItems = KasaExtre.this.getResources().getStringArray(R.array.popup_kasaextre);
                    for (int i = 0; i < menuItems.length; i++) {
                        menu.add(0, i, i, menuItems[i]);
                    }
                }
            }
        });
        this.view.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.a_extre.KasaExtre.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                v.showContextMenu();
            }
        });
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    Toast.makeText(getApplicationContext(), "işlem kayıt yapıldı ", 0).show();
                    doLoadView();
                    doSetParentRefreshRequired();
                    break;
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSetParentRefreshRequired() {
        Intent intent = new Intent();
        intent.putExtra("result", "okextre");
        setResult(-1, intent);
    }

    private void setButtons() {
        this.lbKasKod = (TextView) findViewById(R.id.lbKasKod);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_extre.KasaExtre.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaExtre.this.finish();
            }
        });
        Button btnMail = (Button) findViewById(R.id.btMail);
        btnMail.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_extre.KasaExtre.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaExtre.this.btMailClick(v);
            }
        });
    }

    public void btMailClick(View v) {
        email_text();
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
        OdmKasaKartlar k = new OdmKasaKartlar(this);
        k.getByKasKod(this.kaskod);
        String strFile = String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + "/temp";
        File file = new File(strFile);
        if (!file.exists()) {
            file.mkdirs();
        }
        String strFile2 = String.valueOf(strFile) + "/report.html";
        createFile(strFile2);
        try {
            Intent emailIntent = new Intent("android.intent.action.SEND");
            String emailtext = String.valueOf(k.getKasAd()) + " Kasa Defteri,";
            String emailtext2 = String.valueOf(String.valueOf(String.valueOf(emailtext) + "<hr/>") + "<table><tr><td>aas</td><td>asdasd</td></tr><tr><td>2aas</td><td>2asdasd</td></tr></table>") + "<hr/>";
            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{""});
            emailIntent.putExtra("android.intent.extra.SUBJECT", "Kasa Defteri");
            emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + strFile2));
            emailIntent.putExtra("android.intent.extra.TEXT", Html.fromHtml(emailtext2));
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (Throwable tt) {
            Toast.makeText(this, "Request failed: " + tt.toString(), 1).show();
        }
    }

    public void email_text() {
        OdmDbInfo dbi = new OdmDbInfo(this);
        dbi.getRow();
        this.eposta = dbi.getEposta();
        dbi.closecr();
        OdmKasaKartlar k = new OdmKasaKartlar(this);
        k.getByKasKod(this.kaskod);
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{this.eposta});
        emailIntent.putExtra("android.intent.extra.SUBJECT", "Kasa Defteri");
        String emailText = String.valueOf(k.getKasAd()) + " Kasa Defteri,\r\nkasa defteriniz aşağıda listelenmiştir.\r\n+----------+----------+----------+----------+----------------------------------------+\r\n!  Tarih   !  Giriş   !  Çıkış   !   Kalan  !  Açıklama                              !\r\n+----------+----------+----------+----------+----------------------------------------+\r\n";
        Iterator<HashMap<String, String>> it = this.mylist.iterator();
        while (it.hasNext()) {
            HashMap<String, String> map = it.next();
            emailText = String.valueOf(String.valueOf(emailText) + String.format("!%-10s!%10s!%10s!%10s!%-40s!", eTrim(map, "tarihL"), eTrim(map, "borc"), eTrim(map, "alck"), eTrim(map, "baky"), eTrim(map, "ack"))) + "\r\n";
        }
        String emailText2 = String.valueOf(String.valueOf(emailText) + "+----------+----------+----------+----------+----------------------------------------+\r\nliste sonu.yeni işlemler en üsttedir.\r\n") + "Not: Listeyi düzgün görüntülemek için, metni not defterine kopyalayın.\r\n";
        emailIntent.setType("text/plain");
        emailIntent.putExtra("android.intent.extra.TEXT", emailText2);
        Intent client = Intent.createChooser(emailIntent, "e-posta:");
        startActivityForResult(client, 1);
        k.closecr();
    }

    public void email_html() {
        OdmKasaKartlar k = new OdmKasaKartlar(this);
        k.getByKasKod(this.kaskod);
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{this.eposta});
        emailIntent.putExtra("android.intent.extra.SUBJECT", "Kasa Defteri");
        String emailText = "<html><body><p>" + k.getKasAd() + " Kasa Defteri,</p><p>kasa defteriniz aşağıda listelenmiştir.</p><table border=1><tr><td>Tarih</td><td>Borç</td><td>Alacak</td><td>Bakiye</td><td>Açıklama</td></tr>";
        Iterator<HashMap<String, String>> it = this.mylist.iterator();
        while (it.hasNext()) {
            HashMap<String, String> map = it.next();
            emailText = String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(emailText) + "<tr>") + "<td>" + eTrim1(map, "tarih") + "</td>") + "<td>" + eTrim1(map, "borc") + "</td>") + "<td>" + eTrim1(map, "alck") + "</td>") + "<td>" + eTrim1(map, "baky") + "</td>") + "<td>" + eTrim1(map, "ack") + "</td>") + "</tr>";
        }
        String emailText2 = String.valueOf(emailText) + "</table>liste sonu.<br></body></html>";
        emailIntent.setType("text/plain");
        emailIntent.putExtra("android.intent.extra.TEXT", emailText2);
        Intent client = Intent.createChooser(emailIntent, "e-posta:");
        startActivityForResult(client, 1);
    }

    private String eTrim(HashMap<String, String> p, String w) {
        if (p.get(w) != null) {
            String ret = p.get(w).toString();
            return ret;
        }
        return "";
    }

    private String eTrim1(HashMap<String, String> p, String w) {
        if (p.get(w) != null) {
            String ret = p.get(w).toString();
            return ret;
        }
        return "&nbsp;";
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HashMap<String, String> map = this.mylist.get(info.position);
        final int srcmdl = Integer.parseInt(map.get("srcmdl"));
        final long srcid = Long.parseLong(map.get("srcid"));
        if (item.getTitle().toString().equals(getString(R.string.DegistirStr))) {
            K.doIslemEdit(this, srcmdl, srcid);
        }
        if (item.getTitle().toString().equals(getString(R.string.SilStr))) {
            DialogInterface.OnClickListener dcl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_extre.KasaExtre.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case -1:
                            KasaExtre.this.doIslemSil(srcmdl, srcid);
                            KasaExtre.this.doLoadView();
                            KasaExtre.this.doSetParentRefreshRequired();
                            break;
                    }
                }
            };
            K.YesNo(this, "Kayıt silinecek mi?", dcl);
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doIslemSil(int mdl, long id) {
        K.doIslemSil(this, mdl, id);
    }
}
