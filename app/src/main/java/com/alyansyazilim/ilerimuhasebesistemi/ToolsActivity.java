package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/* loaded from: classes.dex */
public class ToolsActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tools);
        setButtons();
    }

    private void setButtons() {
        Button btDovizKurlari = (Button) findViewById(R.id.btDovizKurlari);
        btDovizKurlari.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.ToolsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ToolsActivity.this.startActivity(new Intent(ToolsActivity.this, (Class<?>) DovizKurlariActivity.class));
            }
        });
        Button btSystemInfo = (Button) findViewById(R.id.btSystemInfo);
        btSystemInfo.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.ToolsActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ToolsActivity.this.startActivity(new Intent(ToolsActivity.this, (Class<?>) SystemInfo.class));
            }
        });
        Button btMTV = (Button) findViewById(R.id.btMtv);
        btMTV.setEnabled(false);
        btMTV.setVisibility(8);
        Button btOrtalamaVade = (Button) findViewById(R.id.btOrtalamaVade);
        btOrtalamaVade.setEnabled(false);
        btOrtalamaVade.setVisibility(8);
        Button btKrediTaksit = (Button) findViewById(R.id.btKrediTaksit);
        btKrediTaksit.setEnabled(false);
        btKrediTaksit.setVisibility(8);
    }

    public void btDovizKurlariClick(View v) {
    }

    public void btMainMenuClick(View v) {
        finish();
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings, menu);
        return true;
    }
}
