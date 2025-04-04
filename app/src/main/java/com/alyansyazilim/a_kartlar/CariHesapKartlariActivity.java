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
import com.alyansyazilim.a_extre.CariExtre;
import com.alyansyazilim.a_islem.CariIslemBorclandir;
import com.alyansyazilim.a_islem.CariIslemTahsilat;
import com.alyansyazilim.a_islem.CariIslemVirman;
import com.alyansyazilim.a_kart.CariHesapKartiActivity;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class CariHesapKartlariActivity extends Activity {
    private static final String[] fields = {K.idColumn, "carkod", "adi", "bakiye", "telefon", "pb"};
    private EditText edBul;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
    private ListView view;

    private void toMap(OdmCariKartlar k) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", k.getID());
        map.put("carkod", k.getCarKod());
        map.put("adi", k.getCarAd());
        map.put("telefon", K.concat(k.getTelefon(), k.getEposta(), "/"));
        map.put("bakiye", k.getBakiyeF());
        map.put("pb", k.getPb());
        this.mylist.add(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doLoadView() {
        String filtre = this.edBul.getText().toString();
        OdmCariKartlar k = new OdmCariKartlar(this);
        if (filtre == null || filtre.length() == 0) {
            k.getAll();
        } else {
            k.getByFilter(filtre);
        }
        this.mylist.clear();
        if (k.moveToFirst()) {
            do {
                toMap(k);
            } while (k.moveToNext());
        }
        k.closecr();
        SimpleAdapter mSchedule = new SimpleAdapter(this, this.mylist, R.layout.activity_cari_hesapkartlari_row, fields, new int[]{0, R.id.first, R.id.last, R.id.bky, R.id.tlf, R.id.pb});
        this.view.setAdapter((ListAdapter) mSchedule);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_hesapkartlari);
        this.edBul = (EditText) findViewById(R.id.edFindTxt);
        this.view = (ListView) findViewById(R.id.listView1);
        doLoadView();
        this.view.setFocusable(true);
        this.view.setClickable(true);
        this.view.requestFocus();
        this.view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() { // from class: com.alyansyazilim.a_kartlar.CariHesapKartlariActivity.1
            @Override // android.view.View.OnCreateContextMenuListener
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (v.getId() == R.id.listView1) {
                    menu.setHeaderTitle("İşlem seç");
                    String[] menuItems = CariHesapKartlariActivity.this.getResources().getStringArray(R.array.popup_carkart);
                    for (int i = 0; i < menuItems.length; i++) {
                        menu.add(0, i, i, menuItems[i]);
                    }
                }
            }
        });
        this.view.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.a_kartlar.CariHesapKartlariActivity.2
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
        btnEkle.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.CariHesapKartlariActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariHesapKartlariActivity.this.doKartCRUD(1, -1L);
            }
        });
        Button btnBul = (Button) findViewById(R.id.button2);
        btnBul.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.CariHesapKartlariActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariHesapKartlariActivity.this.doLoadView();
                String bul = edBul.getText().toString();
                Toast.makeText(v.getContext(), String.valueOf(CariHesapKartlariActivity.this.mylist.size()) + " kart bulundu ..'" + bul + "'", 0).show();
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.CariHesapKartlariActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariHesapKartlariActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HashMap<String, String> map = this.mylist.get(info.position);
        long id = Long.parseLong(map.get("id"));
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.TahsilatStr))) {
            Intent intent = new Intent(this, (Class<?>) CariIslemTahsilat.class);
            doYeniIslem(id, intent, K.MDL_CARTAHSIL);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.OdemeStr))) {
            Intent intent2 = new Intent(this, (Class<?>) CariIslemTahsilat.class);
            doYeniIslem(id, intent2, K.MDL_CARODEME);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.BorclandirStr))) {
            Intent intent3 = new Intent(this, (Class<?>) CariIslemBorclandir.class);
            doYeniIslem(id, intent3, K.MDL_CARBORCD);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.AlacaklandirStr))) {
            Intent intent4 = new Intent(this, (Class<?>) CariIslemBorclandir.class);
            doYeniIslem(id, intent4, K.MDL_CARALCKD);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.VirmanStr))) {
            Intent intent5 = new Intent(this, (Class<?>) CariIslemVirman.class);
            doYeniIslem(id, intent5, K.MDL_CARVIRMAN);
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

    private void doExtre(long carkartid) {
        Intent intent = new Intent(this, (Class<?>) CariExtre.class);
        Bundle extras = new Bundle();
        extras.putLong("carkartid", carkartid);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doKartCRUD(int islem, long carkartid) {
        Intent intent = new Intent(this, (Class<?>) CariHesapKartiActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("islem", islem);
        extras.putLong("carkartid", carkartid);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
    }

    private void doYeniIslem(long carkartid, Intent intent, int mdl) {
        Bundle extras = new Bundle();
        extras.putInt("mdl", mdl);
        extras.putInt("islem", 1);
        extras.putLong("carkartid", carkartid);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
    }

    public void chkKartSil(final long id) {
        OdmCariKartlar kart = new OdmCariKartlar(this);
        kart.getById(id);
        if (kart.getHrkCount() > 0) {
            K.Error(this, "Bu hesap ile işlem yapıldığı için silenemez.");
            return;
        }
        kart.closecr();
        DialogInterface.OnClickListener dcl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.CariHesapKartlariActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case -1:
                        CariHesapKartlariActivity.this.doKartSil(id);
                        CariHesapKartlariActivity.this.doLoadView();
                        break;
                }
            }
        };
        K.YesNo(this, "Kayıt silinecek mi?", dcl);
    }

    public void doKartSil(long id) {
        OdmCariKartlar kart = new OdmCariKartlar(this);
        kart.deleteById(id);
    }
}
