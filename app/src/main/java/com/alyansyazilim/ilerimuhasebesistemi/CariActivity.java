package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.alyansyazilim.a_islemler.CariHesapIslemlerActivity;
import com.alyansyazilim.a_kartlar.CariHesapKartlariActivity;

/* loaded from: classes.dex */
public class CariActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cari);
        setButtons();
    }

    private void setButtons() {
        Button btnKart = (Button) findViewById(R.id.btKartlar);
        btnKart.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.CariActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariActivity.this.startActivity(new Intent(CariActivity.this, (Class<?>) CariHesapKartlariActivity.class));
            }
        });
        Button btnIslem = (Button) findViewById(R.id.btIslemler);
        btnIslem.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.CariActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariActivity.this.startActivity(new Intent(CariActivity.this, (Class<?>) CariHesapIslemlerActivity.class));
            }
        });
        Button btnMainMenu = (Button) findViewById(R.id.btMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.CariActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
