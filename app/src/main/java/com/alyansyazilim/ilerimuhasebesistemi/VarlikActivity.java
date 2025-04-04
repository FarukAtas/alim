package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.alyansyazilim.a_kartlar.VarlikKartlariActivity;

/* loaded from: classes.dex */
public class VarlikActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_varlik);
        setButtons();
    }

    private void setButtons() {
        Button btnKart = (Button) findViewById(R.id.btKartlar);
        btnKart.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.VarlikActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                VarlikActivity.this.startActivity(new Intent(VarlikActivity.this, (Class<?>) VarlikKartlariActivity.class));
            }
        });
        Button btnMainMenu = (Button) findViewById(R.id.btMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.VarlikActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                VarlikActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
