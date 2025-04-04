package com.alyansyazilim.a_kartlar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.alyansyazilim.a_extre.BankaExtre;
import com.alyansyazilim.a_islem.BankaIslemVirman;
import com.alyansyazilim.a_islem.BankaIslemYatan;
import com.alyansyazilim.a_kart.BankaKartiActivity;
import com.alyansyazilim.database.OdmBankaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class BankaKartlariActivity extends Activity {
    private static final String[] fields = {K.idColumn, "bankod1", "adi", "bakiye", "pb"};
    private EditText edBul;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
    private ListView view;

    /* JADX INFO: Access modifiers changed from: private */
    public void doLoadView() {
        String filtre = this.edBul.getText().toString();
        OdmBankaKartlar k = new OdmBankaKartlar(this);
        if (filtre == null || filtre.length() == 0) {
            k.getAll();
        } else {
            k.getByFilter(filtre);
        }
        this.mylist.clear();
        try {
            if (k.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("id", k.getID());
                    map.put("bankod", k.getBanKod());
                    map.put("adi", k.getBanAd());
                    map.put("pb", k.getPb());
                    map.put("bakiye", k.getBakiyeF());
                    this.mylist.add(map);
                } while (k.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleAdapter mSchedule = new SimpleAdapter(this, this.mylist, R.layout.activity_banka_kartlari_row, fields, new int[]{0, R.id.first, R.id.last, R.id.bky, R.id.pb});
        this.view.setAdapter((ListAdapter) mSchedule);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banka_kartlari);
        this.edBul = (EditText) findViewById(R.id.edFindTxt);
        this.view = (ListView) findViewById(R.id.listView1);
        doLoadView();
        this.view.setFocusable(true);
        this.view.setClickable(true);
        this.view.requestFocus();
        this.view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() { // from class: com.alyansyazilim.a_kartlar.BankaKartlariActivity.1
            @Override // android.view.View.OnCreateContextMenuListener
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (v.getId() == R.id.listView1) {
                    menu.setHeaderTitle("İşlem seç");
                    String[] menuItems = BankaKartlariActivity.this.getResources().getStringArray(R.array.popup_bankakart);
                    for (int i = 0; i < menuItems.length; i++) {
                        menu.add(0, i, i, menuItems[i]);
                    }
                }
            }
        });
        this.view.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.a_kartlar.BankaKartlariActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                v.showContextMenu();
            }
        });
        setButtons(this.edBul);
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == -1) {
                    String string = data.getStringExtra("result");
                    if (string.equals("okkart")) {
                        Toast.makeText(getApplicationContext(), "Kart kayıt yapıldı ", 0).show();
                    }
                    if (string.equals("okextre")) {
                        Toast.makeText(getApplicationContext(), "Liste güncellendi ", 0).show();
                    }
                    if (string.equals("okislem")) {
                        Toast.makeText(getApplicationContext(), "işlem kayıt yapıldı ", 0).show();
                    }
                    doLoadView();
                    break;
                }
                break;
        }
    }

    private void setButtons(final EditText edBul) {
        Button btnEkle = (Button) findViewById(R.id.button1);
        btnEkle.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.BankaKartlariActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaKartlariActivity.this.doKartCRUD(1, -1L);
            }
        });
        Button btnBul = (Button) findViewById(R.id.button2);
        btnBul.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.BankaKartlariActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaKartlariActivity.this.doLoadView();
                String bul = edBul.getText().toString();
                Toast.makeText(v.getContext(), String.valueOf(BankaKartlariActivity.this.mylist.size()) + " kart bulundu ..'" + bul + "'", 0).show();
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.BankaKartlariActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaKartlariActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HashMap<String, String> map = this.mylist.get(info.position);
        long id = Long.parseLong(map.get("id"));
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.YatirilanStr))) {
            Intent intent = new Intent(this, (Class<?>) BankaIslemYatan.class);
            doYeniIslem(id, intent, K.MDL_BANYATAN);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.CekilenStr))) {
            Intent intent2 = new Intent(this, (Class<?>) BankaIslemYatan.class);
            doYeniIslem(id, intent2, K.MDL_BANCEKILEN);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.VirmanStr))) {
            Intent intent3 = new Intent(this, (Class<?>) BankaIslemVirman.class);
            doYeniIslem(id, intent3, K.MDL_BANVIRMAN);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.HesapExtresiStr))) {
            doExtre(id);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.DegistirStr))) {
            doKartCRUD(2, id);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.SilStr))) {
            chkKartSil(id);
            return true;
        }
        return true;
    }

    private void doExtre(long kartid) {
        Intent intent = new Intent(this, (Class<?>) BankaExtre.class);
        Bundle extras = new Bundle();
        extras.putLong("bankartid", kartid);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doKartCRUD(int islem, long kartid) {
        Intent intent = new Intent(this, (Class<?>) BankaKartiActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("islem", islem);
        extras.putLong("bankartid", kartid);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
    }

    private void doYeniIslem(long kartid, Intent intent, int mdl) {
        Bundle extras = new Bundle();
        extras.putInt("mdl", mdl);
        extras.putInt("islem", 1);
        extras.putLong("bankartid", kartid);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
    }

    public void chkKartSil(final long id) {
        OdmBankaKartlar kart = new OdmBankaKartlar(this);
        kart.getById(id);
        if (kart.getHrkCount() > 0) {
            K.Error(this, "Bu hesap ile işlem yapıldığı için silenemez.");
        } else {
            DialogInterface.OnClickListener dcl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.BankaKartlariActivity.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case -1:
                            BankaKartlariActivity.this.doKartSil(id);
                            BankaKartlariActivity.this.doLoadView();
                            break;
                    }
                }
            };
            K.YesNo(this, "Kayıt silinecek mi?", dcl);
        }
    }

    public void doKartSil(long id) {
        OdmBankaKartlar kart = new OdmBankaKartlar(this);
        kart.deleteById(id);
    }
}
