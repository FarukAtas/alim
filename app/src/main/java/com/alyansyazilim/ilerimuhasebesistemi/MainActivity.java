package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alyansyazilim.a_kart.DbPropsKartiActivity;
import com.alyansyazilim.a_kartlar.BankaKartlariActivity;
import com.alyansyazilim.a_kartlar.CariHesapKartlariActivity;
import com.alyansyazilim.a_kartlar.KasaKartlariActivity;
import com.alyansyazilim.a_kartlar.VarlikKartlariActivity;
import com.alyansyazilim.database.OdmDbInfo;
import com.alyansyazilim.database.Veritabani;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class MainActivity extends Activity {
    private TextView edLoginPass;
    private Handler handler;
    private TextView lbEuro;
    private TextView lbPassErr;
    private TextView lbUsd;
    private boolean loggedin = false;
    private View.OnClickListener mGoListener = new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService("input_method");
            imm.hideSoftInputFromWindow(MainActivity.this.edLoginPass.getWindowToken(), 0);
            OdmDbInfo k = new OdmDbInfo(MainActivity.this);
            boolean pweq = k.passwordEqual(MainActivity.this.edLoginPass);
            k.closecr();
            try {
                k.finalize();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            if (!pweq) {
                MainActivity.this.lbPassErr.setText("Şifre hatalı.");
                MainActivity.this.vRecover.setVisibility(0);
            } else {
                MainActivity.this.goMainMenu();
            }
        }
    };
    private View.OnClickListener mRecoverListener = new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.2
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            RecoverMyPassword(MainActivity.this);
        }

        private void RecoverMyPassword(Context mainActivity) {
            int wss = WebServices.getWebServiceStatus(mainActivity, MainActivity.this.handler, "Main");
            if (!WebServices.statusEqual(wss, 1)) {
                MainActivity.this.lbPassErr.setText("internet bağlantısı bulunamadı.");
                return;
            }
            OdmDbInfo dbi = new OdmDbInfo(mainActivity);
            dbi.getRow();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("dbid", dbi.getDbId());
                jsonObject.put("username", dbi.getAdi());
                jsonObject.put("email", dbi.getEposta());
                jsonObject.put("pass", dbi.getPassw());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String ret = WebServices.doWebService(mainActivity, "RecoverPassword", jsonObject.toString());
            if (ret.compareTo("OK") == 0) {
                MainActivity.this.lbPassErr.setText("şifreniz '" + dbi.getEposta() + "' adresine eposta ile gönderildi. gelen kutunuzu veya spam klasörünü kontrol edin. adresiniz ile ilgili sorun var ise, destek@alyansyazilim.com adresine eposta ile başvurun.");
            } else {
                MainActivity.this.lbPassErr.setText("eposta gönderilemedi");
            }
            MainActivity.this.vRecover.setVisibility(8);
        }
    };
    private LinearLayout vRecover;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String versionName = "yok";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        K.setAndroidID(Settings.Secure.getString(getContentResolver(), "android_id"));
        K.setVersionName(versionName);
        setTitle(((Object) getTitle()) + " v: " + K.vVersionName);
        K.setAppContext(getApplicationContext());
        K.vMainScreenUsd = "0.0000";
        K.vMainScreenEur = "0.0000";
        this.handler = new Handler();
        Veritabani vt = new Veritabani(this);
        if (vt.passwordRequired()) {
            goLogin();
        } else {
            goMainMenu();
        }
    }

    private void goLogin() {
        setContentView(R.layout.layout_main_login);
        this.edLoginPass = (TextView) findViewById(R.id.edLoginPass);
        this.vRecover = (LinearLayout) findViewById(R.id.vRecover);
        this.vRecover.setVisibility(8);
        this.lbPassErr = (TextView) findViewById(R.id.lbPassErr);
        this.lbPassErr.setText("");
        Button goButton = (Button) findViewById(R.id.btLogin);
        goButton.setOnClickListener(this.mGoListener);
        Button btRecover = (Button) findViewById(R.id.btRecover);
        btRecover.setOnClickListener(this.mRecoverListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goMainMenu() {
        this.loggedin = true;
        setContentView(R.layout.layout_main1);
        setupButons();
        this.lbUsd.setText("1 USD = 0.0000 TL");
        this.lbEuro.setText("1 USD = 0.0000 TL");
        K.vAdiTxt = (TextView) findViewById(R.id.lbMainScreenVers);
        K.vAdiTxt.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (K.DbType == 0) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) DbPropsKartiActivity.class));
                } else {
                    K.Error(MainActivity.this, "Sunucu Bilgileri Değiştirilemez");
                }
            }
        });
        OdmDbInfo.setGlobalHash(this);
        DovizKurlariActivity.doGetMainDovizKur(this, this.handler, this.lbUsd, this.lbEuro);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        DialogInterface.OnClickListener ocl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        };
        if (this.loggedin) {
            K.YesNo(this, "Çıkmak istediğinize emin misiniz?", ocl, null);
        } else {
            finish();
        }
    }

    private void setupButons() {
        this.lbUsd = (TextView) findViewById(R.id.lbUsd);
        this.lbEuro = (TextView) findViewById(R.id.lbEuro);
        Button mnCarKartlar = (Button) findViewById(R.id.btCariKartlar);
        Button mnKasKartlar = (Button) findViewById(R.id.btKasaKartlar);
        Button mnBanKartlar = (Button) findViewById(R.id.btBankaKartlar);
        Button mnVrlKartlar = (Button) findViewById(R.id.btVarlikKartlar);
        Button btClient = (Button) findViewById(R.id.btConnect);
        btClient.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) ServerActivity.class));
            }
        });
        Button mnSettings = (Button) findViewById(R.id.btAyarlar);
        mnSettings.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.doMenuSelect(R.id.menu_settings);
            }
        });
        Button mnAraclar = (Button) findViewById(R.id.btAraclar);
        mnAraclar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) ToolsActivity.class));
            }
        });
        Button btGenelRaporlar = (Button) findViewById(R.id.btGenelRaporlar);
        btGenelRaporlar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) RaporGenelDurum.class));
            }
        });
        mnCarKartlar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) CariHesapKartlariActivity.class));
            }
        });
        mnKasKartlar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) KasaKartlariActivity.class));
            }
        });
        mnVrlKartlar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) VarlikKartlariActivity.class));
            }
        });
        mnBanKartlar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) BankaKartlariActivity.class));
            }
        });
        View.OnClickListener dvzOCL = new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.MainActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, (Class<?>) DovizKurlariActivity.class));
            }
        };
        this.lbUsd.setOnClickListener(dvzOCL);
        this.lbEuro.setOnClickListener(dvzOCL);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        return doMenuSelect(item.getItemId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doMenuSelect(int i) {
        switch (i) {
            case R.id.menu_settings /* 2131230891 */:
                if (this.loggedin) {
                    startActivity(new Intent(this, (Class<?>) SettingsActivity.class));
                    return true;
                }
                K.toast(this, "Önce şifreyi girmelisiniz.");
                return true;
            case R.id.menu_Detay /* 2131230892 */:
            default:
                return false;
            case R.id.menu_quit /* 2131230893 */:
                System.exit(0);
                return true;
        }
    }
}
