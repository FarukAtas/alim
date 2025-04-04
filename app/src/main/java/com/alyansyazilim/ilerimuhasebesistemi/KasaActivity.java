package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.alyansyazilim.a_islemler.KasaIslemlerActivity;
import com.alyansyazilim.a_kartlar.KasaKartlariActivity;

/* loaded from: classes.dex */
public class KasaActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_kasa);
        setButtons();
    }

    private void setButtons() {
        Button btnKart = (Button) findViewById(R.id.btKasaKart);
        btnKart.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.KasaActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaActivity.this.startActivity(new Intent(KasaActivity.this, (Class<?>) KasaKartlariActivity.class));
            }
        });
        Button btIslemler = (Button) findViewById(R.id.btIslemler);
        btIslemler.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.KasaActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaActivity.this.startActivity(new Intent(KasaActivity.this, (Class<?>) KasaIslemlerActivity.class));
            }
        });
        Button btnMainMenu = (Button) findViewById(R.id.btMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.KasaActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
