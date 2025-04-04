package com.alyansyazilim.z_other;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class StokActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stok);
        setButtons();
    }

    private void setButtons() {
        Button btnMainMenu = (Button) findViewById(R.id.btMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.z_other.StokActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                StokActivity.this.finish();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stok, menu);
        return true;
    }
}
