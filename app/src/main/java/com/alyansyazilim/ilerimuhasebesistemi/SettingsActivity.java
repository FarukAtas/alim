package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.alyansyazilim.a_kart.DbPropsKartiActivity;
import com.alyansyazilim.a_kartlar.ParaBirimKartlariActivity;
import com.alyansyazilim.database.Veritabani;
import com.alyansyazilim.z_other.ListViewDemoActivity;

/* loaded from: classes.dex */
public class SettingsActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setButtons();
    }

    private void setButtons() {
        Button btnMainMenu = (Button) findViewById(R.id.btMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SettingsActivity.this.finish();
            }
        });
        Button btEmptyDB = (Button) findViewById(R.id.btEmptyDB);
        btEmptyDB.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (K.DbType == 0) {
                    SettingsActivity.this.chkEmptyDB();
                } else {
                    K.Error(SettingsActivity.this, "Sunucuda bu işlem yapılamaz.");
                }
            }
        });
        Button btSampleDB = (Button) findViewById(R.id.btSampleDB);
        btSampleDB.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (K.DbType == 0) {
                    SettingsActivity.this.chkSampleDB();
                } else {
                    K.Error(SettingsActivity.this, "Sunucuda bu işlem yapılamaz.");
                }
            }
        });
        Button btParaBirimleri = (Button) findViewById(R.id.btParaBirimleri);
        btParaBirimleri.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, (Class<?>) ParaBirimKartlariActivity.class));
            }
        });
        Button btTest1 = (Button) findViewById(R.id.btTest1);
        btTest1.setVisibility(8);
        btTest1.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, (Class<?>) ListViewDemoActivity.class));
            }
        });
        Button btHesapSahibi = (Button) findViewById(R.id.btHesapSahibi);
        btHesapSahibi.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (K.DbType == 0) {
                    SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, (Class<?>) DbPropsKartiActivity.class));
                } else {
                    K.Error(SettingsActivity.this, "Sunucu Bilgileri Değiştirilemez");
                }
            }
        });
        Button btBckRst = (Button) findViewById(R.id.btBckRst);
        btBckRst.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (K.DbType == 0) {
                    SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, (Class<?>) BackupRestoreActivity.class));
                } else {
                    K.Error(SettingsActivity.this, "Sunucu Bilgileri Değiştirilemez");
                }
            }
        });
        Button btTest2 = (Button) findViewById(R.id.btTest2);
        btTest2.setVisibility(8);
        btTest2.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
            }
        });
    }

    public void chkSampleDB() {
        DialogInterface.OnClickListener dcl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DialogInterface.OnClickListener dcl2 = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.9.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog2, int which2) {
                        switch (which2) {
                            case -3:
                                K.toast(SettingsActivity.this, "Örnek veri oluştuma İPTAL edildi");
                                break;
                            case -2:
                                Veritabani.doSampleDB(SettingsActivity.this);
                                break;
                            case -1:
                                Veritabani.doEmptyDB(SettingsActivity.this);
                                Veritabani.doSampleDB(SettingsActivity.this);
                                break;
                        }
                    }
                };
                K.YesNoCancel(SettingsActivity.this, "Örnek verilerle karışmaması için Daha önce girilen kayıtları silmek istermisiniz? Silinen kayıtlar geri alınamaz !", dcl2);
            }
        };
        K.YesNo(this, "Örnek veriler tamamen programın kullanımı açıklamayak içindir. Gerçek kayıtlarla hiçbir ilgisi yoktur. Devam etmek istiyormusunuz?", dcl, null);
    }

    public void chkEmptyDB() {
        DialogInterface.OnClickListener dcl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SettingsActivity.10
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case -1:
                        Veritabani.doEmptyDB(SettingsActivity.this);
                        break;
                }
            }
        };
        K.YesNo(this, "Tüm kayıtlar silinecek ve geri alınamayacak. Bu işlemi yapmak istediğinize emin misiniz?", dcl, null);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_settings, menu);
        return true;
    }
}
