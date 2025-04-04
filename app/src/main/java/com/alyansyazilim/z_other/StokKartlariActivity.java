package com.alyansyazilim.z_other;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.alyansyazilim.a_islem.KasaIslemTahsil;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class StokKartlariActivity extends Activity {
    private static final String[] fields = {K.idColumn, "stkkod", "adi", "kalan"};
    private CursorAdapter dataSource;
    private EditText edBul;
    private OdmStokKartlar k;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stok_kartlari);
        this.k = new OdmStokKartlar(this);
        this.edBul = (EditText) findViewById(R.id.edFindTxt);
        Cursor data = this.k.Kayitlar();
        this.dataSource = new SimpleCursorAdapter(this, R.layout.layout_stok_kartlari_row, data, fields, new int[]{0, R.id.first, R.id.last, R.id.bky});
        ListView view = (ListView) findViewById(R.id.listView1);
        view.setHeaderDividersEnabled(true);
        view.addHeaderView(getLayoutInflater().inflate(R.layout.layout_stok_kartlari_row, (ViewGroup) null));
        view.setAdapter((ListAdapter) this.dataSource);
        view.requestFocus();
        view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() { // from class: com.alyansyazilim.z_other.StokKartlariActivity.1
            @Override // android.view.View.OnCreateContextMenuListener
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (v.getId() == R.id.listView1) {
                    menu.setHeaderTitle("İşlem seç");
                    String[] menuItems = StokKartlariActivity.this.getResources().getStringArray(R.array.menu);
                    for (int i = 0; i < menuItems.length; i++) {
                        menu.add(0, i, i, menuItems[i]);
                    }
                }
            }
        });
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.z_other.StokKartlariActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                v.showContextMenu();
            }
        });
        setButtons(this.edBul);
    }

    private void setButtons(final EditText edBul) {
        Button btnEkle = (Button) findViewById(R.id.button1);
        Button btnBul = (Button) findViewById(R.id.button2);
        btnEkle.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.z_other.StokKartlariActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Ekle düğmesine basıldı ..", 0).show();
            }
        });
        btnBul.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.z_other.StokKartlariActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Bul düğmesine basıldı ..'" + edBul.getText().toString() + "'", 0).show();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem item) {
        K.loge("onContextItemSelected begin");
        startActivity(new Intent(this, (Class<?>) KasaIslemTahsil.class));
        return true;
    }
}
