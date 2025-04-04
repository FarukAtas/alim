package com.alyansyazilim.a_islemler;

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
import com.alyansyazilim.database.OdmCariIslem;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class CariHesapIslemlerActivity extends Activity {
    private static final String[] fields = {K.idColumn, "zaman", "carkod", "tutar", "ack"};
    private EditText edBul;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
    private ListView view;

    /* JADX INFO: Access modifiers changed from: private */
    public void doLoadView() {
        OdmCariIslem islem = new OdmCariIslem(this);
        OdmCariKartlar kart = new OdmCariKartlar(this);
        this.mylist.clear();
        islem.getAll();
        if (islem.moveToFirst()) {
            do {
                kart.getByCarKod(islem.getCarKod());
                HashMap<String, String> map = new HashMap<>();
                map.put("id", islem.getID());
                map.put("ikod", K.i2s(islem.getIkod()));
                map.put("zaman", K.SqlTSmd(islem.getZaman()));
                map.put("carkod", kart.getCarAd());
                map.put("tutar", islem.getTutarF());
                map.put("ack", K.Mdl2Str(islem.getIkod()));
                this.mylist.add(map);
            } while (islem.moveToNext());
        }
        SimpleAdapter mSchedule = new SimpleAdapter(this, this.mylist, R.layout.activity_cari_islemler_row, fields, new int[]{0, R.id.first, R.id.last, R.id.bky, R.id.tlf});
        this.view.setAdapter((ListAdapter) mSchedule);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_islemler);
        this.edBul = (EditText) findViewById(R.id.edFindTxt);
        this.view = (ListView) findViewById(R.id.listView1);
        doLoadView();
        this.view.setFocusable(true);
        this.view.setClickable(true);
        this.view.requestFocus();
        this.view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() { // from class: com.alyansyazilim.a_islemler.CariHesapIslemlerActivity.1
            @Override // android.view.View.OnCreateContextMenuListener
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (v.getId() == R.id.listView1) {
                    menu.setHeaderTitle("İşlem seç");
                    String[] menuItems = CariHesapIslemlerActivity.this.getResources().getStringArray(R.array.popup_carislem);
                    for (int i = 0; i < menuItems.length; i++) {
                        menu.add(0, i, i, menuItems[i]);
                    }
                }
            }
        });
        this.view.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.a_islemler.CariHesapIslemlerActivity.2
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
                    Toast.makeText(getApplicationContext(), "işlem Kayıt yapıldı ", 0).show();
                    doLoadView();
                    break;
                }
                break;
        }
    }

    private void setButtons(final EditText edBul) {
        Button btnBul = (Button) findViewById(R.id.button2);
        btnBul.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islemler.CariHesapIslemlerActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariHesapIslemlerActivity.this.doLoadView();
                Toast.makeText(v.getContext(), "Bul düğmesine basıldı ..'" + edBul.getText().toString() + "'", 0).show();
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islemler.CariHesapIslemlerActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariHesapIslemlerActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HashMap<String, String> map = this.mylist.get(info.position);
        final int mdl = Integer.parseInt(map.get("ikod"));
        final long id = Long.parseLong(map.get("id"));
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.DegistirStr))) {
            K.doIslemEdit(this, mdl, id);
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.SilStr))) {
            DialogInterface.OnClickListener dcl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_islemler.CariHesapIslemlerActivity.5
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case -1:
                            CariHesapIslemlerActivity.this.doIslemSil(mdl, id);
                            CariHesapIslemlerActivity.this.doLoadView();
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
