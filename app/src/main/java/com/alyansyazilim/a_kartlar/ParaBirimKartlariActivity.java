package com.alyansyazilim.a_kartlar;

import android.app.Activity;
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
import com.alyansyazilim.a_kart.ParaBirimKartiActivity;
import com.alyansyazilim.database.OdmParaBirimKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class ParaBirimKartlariActivity extends Activity {
    private static final String[] fields = {K.idColumn, "pbkod", "ack", "bakiye", "telefon"};
    private EditText edBul;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
    private ListView view;

    /* JADX INFO: Access modifiers changed from: private */
    public void doLoadView() {
        OdmParaBirimKartlar k = new OdmParaBirimKartlar(this);
        String filtre = this.edBul.getText().toString();
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
                    map.put("pbkod", k.getKod());
                    map.put("ack", k.getAck());
                    map.put("bakiye", k.getYerelPB() ? "Yerel Para Birimi" : "");
                    this.mylist.add(map);
                } while (k.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleAdapter mSchedule = new SimpleAdapter(this, this.mylist, R.layout.activity_pb_kartlari_row, fields, new int[]{0, R.id.first, R.id.last, R.id.bky, R.id.tlf});
        this.view.setAdapter((ListAdapter) mSchedule);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_kartlari);
        this.edBul = (EditText) findViewById(R.id.edFindTxt);
        this.view = (ListView) findViewById(R.id.listView1);
        doLoadView();
        this.view.setFocusable(true);
        this.view.setClickable(true);
        this.view.requestFocus();
        this.view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() { // from class: com.alyansyazilim.a_kartlar.ParaBirimKartlariActivity.1
            @Override // android.view.View.OnCreateContextMenuListener
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (v.getId() == R.id.listView1) {
                    menu.setHeaderTitle("İşlem seç");
                    String[] menuItems = ParaBirimKartlariActivity.this.getResources().getStringArray(R.array.popup_pbkart);
                    for (int i = 0; i < menuItems.length; i++) {
                        menu.add(0, i, i, menuItems[i]);
                    }
                }
            }
        });
        this.view.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.a_kartlar.ParaBirimKartlariActivity.2
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
        btnEkle.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.ParaBirimKartlariActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ParaBirimKartlariActivity.this.doKartCRUD(1, -1L);
            }
        });
        Button btnBul = (Button) findViewById(R.id.button2);
        btnBul.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.ParaBirimKartlariActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ParaBirimKartlariActivity.this.doLoadView();
                String bul = edBul.getText().toString();
                Toast.makeText(v.getContext(), String.valueOf(ParaBirimKartlariActivity.this.mylist.size()) + " kart bulundu ..'" + bul + "'", 0).show();
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kartlar.ParaBirimKartlariActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ParaBirimKartlariActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HashMap<String, String> map = this.mylist.get(info.position);
        long id = Long.parseLong(map.get("id"));
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.DegistirStr))) {
            doKartCRUD(2, id);
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doKartCRUD(int islem, long kartid) {
        Intent intent = new Intent(this, (Class<?>) ParaBirimKartiActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("islem", islem);
        extras.putLong("pbkartid", kartid);
        intent.putExtras(extras);
        startActivityForResult(intent, 1);
    }
}
