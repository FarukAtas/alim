package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.alyansyazilim.a_islemler.BankaIslemlerActivity;
import com.alyansyazilim.a_kartlar.BankaKartlariActivity;

/* loaded from: classes.dex */
public class BankaActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_banka);
        setButtons();
    }

    private void setButtons() {
        Button btnKart = (Button) findViewById(R.id.btKartlar);
        btnKart.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BankaActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaActivity.this.startActivity(new Intent(BankaActivity.this, (Class<?>) BankaKartlariActivity.class));
            }
        });
        Button btnIslem = (Button) findViewById(R.id.btIslemler);
        btnIslem.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BankaActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaActivity.this.startActivity(new Intent(BankaActivity.this, (Class<?>) BankaIslemlerActivity.class));
            }
        });
        Button btnMainMenu = (Button) findViewById(R.id.btMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BankaActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_banka, menu);
        return true;
    }
}
