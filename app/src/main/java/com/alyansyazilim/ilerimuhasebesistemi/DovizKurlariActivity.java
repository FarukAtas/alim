package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.alyansyazilim.database.OdmDovizKartlar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* loaded from: classes.dex */
public class DovizKurlariActivity extends Activity {
    static final String KEY_ALIS = "ALIS";
    static final String KEY_ITEM = "DOVIZ";
    static final String KEY_NAME = "ADI";
    static final String KEY_SATIS = "SATIS";
    static final String URLa = "http://www.altinkaynak.com/main_xml/altin.xml";
    static final String URLd = "http://xml.altinkaynak.com/doviz.xml";
    static final String URLd1 = "http://www.altinkaynak.com/main_xml/doviz.xml";
    private Button btSave;
    private Handler handler;
    private TextView lbTarih;
    private ListView lv;
    private ArrayList<HashMap<String, String>> menuItems = new ArrayList<>();
    private ProgressDialog progress;
    private String tstxt;
    private String tstxtSql;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dovizkur_kartlari);
        this.handler = new Handler();
        setButtons();
        this.lv = (ListView) findViewById(R.id.listView1);
        this.lbTarih = (TextView) findViewById(R.id.lbTarihSaat);
        this.lbTarih.setText("Bilgiler yükleniyor...");
        ((TextView) findViewById(R.id.lbKaynak)).setText("Kaynak: Altınkaynak");
        doLoadList();
    }

    public static void doGetMainDovizKur(final Activity act, final Handler handler, final TextView lbUsd, final TextView lbEuro) {
        Runnable runnable = new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.DovizKurlariActivity.1
            private String usdtxt = "0.0000";
            private String eurtxt = "0.0000";

            @Override // java.lang.Runnable
            public void run() {
                try {
                    XMLParser parser = new XMLParser(act);
                    WebServices.getWebServiceStatus(act, handler, "Main");
                    Document doc = parser.getDomElement_akaynak(DovizKurlariActivity.URLd1);
                    if (doc != null) {
                        NodeList nl = doc.getElementsByTagName(DovizKurlariActivity.KEY_ITEM);
                        this.usdtxt = parser.getValue((Element) nl.item(1), DovizKurlariActivity.KEY_SATIS);
                        this.eurtxt = parser.getValue((Element) nl.item(2), DovizKurlariActivity.KEY_SATIS);
                        K.vMainScreenUsd = this.usdtxt;
                        K.vMainScreenEur = this.eurtxt;
                    }
                    Handler handler2 = handler;
                    final TextView textView = lbUsd;
                    final TextView textView2 = lbEuro;
                    handler2.post(new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.DovizKurlariActivity.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            textView.setText("1 USD = " + AnonymousClass1.this.usdtxt + " TL");
                            textView2.setText("1 EURO = " + AnonymousClass1.this.eurtxt + " TL");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Handler handler3 = handler;
                    final Activity activity = act;
                    handler3.post(new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.DovizKurlariActivity.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            Toast.makeText(activity, "Güncel kur bilgileri alınamadı", 1).show();
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSaveList() {
        OdmDovizKartlar kart = new OdmDovizKartlar(this);
        Iterator<HashMap<String, String>> it = this.menuItems.iterator();
        while (it.hasNext()) {
            HashMap<String, String> item = it.next();
            kart.KayitEkle(item.get(KEY_NAME), this.tstxtSql, item.get(KEY_ALIS), item.get(KEY_SATIS));
        }
        K.toast(this, "kayıt yapıldı.");
        this.btSave.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doLoadList() {
        this.menuItems = new ArrayList<>();
        this.progress = ProgressDialog.show(this, "Döviz Kurları", "Lütfen Bekleyin...", true);
        Thread get = new Thread() { // from class: com.alyansyazilim.ilerimuhasebesistemi.DovizKurlariActivity.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                XMLParser parser = new XMLParser(DovizKurlariActivity.this);
                WebServices.getWebServiceStatus(DovizKurlariActivity.this, DovizKurlariActivity.this.handler, "OnlineDovizKur");
                Document docA = parser.getDomElement_akaynak(DovizKurlariActivity.URLa);
                if (docA != null) {
                    NodeList nl = docA.getElementsByTagName(DovizKurlariActivity.KEY_ITEM);
                    HashMap<String, String> map = new HashMap<>();
                    Element e = (Element) nl.item(1);
                    map.put(DovizKurlariActivity.KEY_NAME, "ALT");
                    map.put(DovizKurlariActivity.KEY_ALIS, parser.getValue(e, DovizKurlariActivity.KEY_ALIS));
                    map.put(DovizKurlariActivity.KEY_SATIS, parser.getValue(e, DovizKurlariActivity.KEY_SATIS));
                    DovizKurlariActivity.this.menuItems.add(map);
                }
                Document doc = parser.getDomElement_akaynak(DovizKurlariActivity.URLd);
                if (doc != null) {
                    NodeList nl2 = doc.getElementsByTagName(DovizKurlariActivity.KEY_ITEM);
                    DovizKurlariActivity.this.tstxt = parser.getValue((Element) nl2.item(0), DovizKurlariActivity.KEY_ALIS);
                    DovizKurlariActivity.this.tstxtSql = K.DateSql(K.str2Date(DovizKurlariActivity.this.tstxt, "dd.MM.yyyy HH:mm:ss"));
                    for (int i = 1; i < nl2.getLength(); i++) {
                        HashMap<String, String> map2 = new HashMap<>();
                        Element e2 = (Element) nl2.item(i);
                        map2.put(DovizKurlariActivity.KEY_NAME, parser.getValue(e2, DovizKurlariActivity.KEY_NAME));
                        map2.put(DovizKurlariActivity.KEY_ALIS, parser.getValue(e2, DovizKurlariActivity.KEY_ALIS));
                        map2.put(DovizKurlariActivity.KEY_SATIS, parser.getValue(e2, DovizKurlariActivity.KEY_SATIS));
                        DovizKurlariActivity.this.menuItems.add(map2);
                    }
                }
                DovizKurlariActivity.this.handler.post(new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.DovizKurlariActivity.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ListAdapter adapter = new SimpleAdapter(DovizKurlariActivity.this, DovizKurlariActivity.this.menuItems, R.layout.activity_dovizkur_kartlari_row, new String[]{DovizKurlariActivity.KEY_NAME, DovizKurlariActivity.KEY_ALIS, DovizKurlariActivity.KEY_SATIS}, new int[]{R.id.first, R.id.last, R.id.bky});
                        DovizKurlariActivity.this.lv.setAdapter(adapter);
                        DovizKurlariActivity.this.lbTarih.setText(DovizKurlariActivity.this.tstxt);
                        DovizKurlariActivity.this.progress.dismiss();
                        DovizKurlariActivity.this.setSaveBtnEnable();
                    }
                });
            }
        };
        get.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSaveBtnEnable() {
        if (K.isNOE(this.tstxt)) {
            this.btSave.setEnabled(false);
            return;
        }
        OdmDovizKartlar kart = new OdmDovizKartlar(this);
        kart.getByZaman(this.tstxtSql);
        if (kart.getCount() == 0) {
            this.btSave.setEnabled(true);
        } else {
            K.toast(this, "Bu kurlar daha önce kayıt edilmiş.");
        }
    }

    private void setButtons() {
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.DovizKurlariActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DovizKurlariActivity.this.finish();
            }
        });
        Button btRefresh = (Button) findViewById(R.id.btRefresh);
        btRefresh.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.DovizKurlariActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DovizKurlariActivity.this.doLoadList();
            }
        });
        this.btSave = (Button) findViewById(R.id.btSave);
        this.btSave.setEnabled(false);
        this.btSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.DovizKurlariActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DovizKurlariActivity.this.doSaveList();
            }
        });
    }
}
