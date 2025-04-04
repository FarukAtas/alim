package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.alyansyazilim.database.OdmDbInfo;

/* loaded from: classes.dex */
public class ServerActivity extends Activity {
    public static final String EXTRA_STOP_SERVER = "stop_server";
    private static final int INSTALL_KEYCHAIN_CODE = 1;
    public static final String SP_KEY_MCHNM = "com.alyansyazilim.ilerimuhasebesistemi.mchnm";
    public static final String SP_KEY_ROOT = "com.alyansyazilim.ilerimuhasebesistemi";
    public static final String SP_KEY_SRVIP = "com.alyansyazilim.ilerimuhasebesistemi.srvip";
    public static final String TAG = "alim";
    private Button btConnect;
    private Button btDisConnect;
    private Button btTestConnect;
    private EditText edID;
    private EditText edSrvIP;
    private SharedPreferences prefs;
    private Button serverButton;

    public void enableButtons() {
        this.edID.setEnabled(K.DbType == 0 && K.SrvType == 0);
        this.edSrvIP.setEnabled(K.DbType == 0 && K.SrvType == 0);
        this.serverButton.setEnabled(K.DbType == 0);
        this.btConnect.setEnabled(K.DbType == 0 && K.SrvType == 0);
        this.btTestConnect.setEnabled(K.DbType == 0 && K.SrvType == 0);
        this.btDisConnect.setEnabled(K.DbType == 1 && K.SrvType == 0);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.prefs = getSharedPreferences(SP_KEY_ROOT, 0);
        String spMcnNm = this.prefs.getString(SP_KEY_MCHNM, Build.MODEL);
        String spSrvIP = this.prefs.getString(SP_KEY_SRVIP, "");
        setContentView(R.layout.activity_server_c);
        TextView certTv = (TextView) findViewById(R.id.lblIpAdress);
        certTv.setText("Bu cihazın IP adresi : " + Utils.getIPAddress(true));
        new Thread(new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.ServerActivity.1
            @Override // java.lang.Runnable
            public void run() {
            }
        }).start();
        this.edID = (EditText) findViewById(R.id.edID);
        this.edID.setText(spMcnNm);
        this.edSrvIP = (EditText) findViewById(R.id.edSrvIP);
        this.edSrvIP.setText(spSrvIP);
        this.serverButton = (Button) findViewById(R.id.server_button);
        this.serverButton.setText(K.SrvType == 0 ? R.string.server_start : R.string.server_stop);
        this.serverButton.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.ServerActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ServerActivity.this.prefs.edit().putString(ServerActivity.SP_KEY_MCHNM, ServerActivity.this.edID.getText().toString()).commit();
                if (ServerActivity.this.serverButton.getText().equals(ServerActivity.this.getResources().getString(R.string.server_start))) {
                    ServerActivity.this.serverButton.setText(R.string.server_stop);
                    K.SrvType = 1;
                    K.ConnectID = ServerActivity.this.edID.getText().toString();
                    ServerActivity.this.enableButtons();
                    ServerActivity.this.startServer();
                    return;
                }
                ServerActivity.this.serverButton.setText(R.string.server_start);
                K.SrvType = 0;
                ServerActivity.this.enableButtons();
                ServerActivity.this.stopServer();
            }
        });
        this.serverButton.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(this.edID.getWindowToken(), 0);
        this.btConnect = (Button) findViewById(R.id.btConnect);
        this.btConnect.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.ServerActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ServerActivity.this.prefs.edit().putString(ServerActivity.SP_KEY_SRVIP, ServerActivity.this.edSrvIP.getText().toString()).commit();
                if (K.isNOE(ServerActivity.this.edSrvIP.getText().toString())) {
                    ServerActivity.this.connectSerrver("85.100.114.121");
                } else {
                    ServerActivity.this.connectSerrver(ServerActivity.this.edSrvIP.getText().toString());
                }
            }
        });
        this.btTestConnect = (Button) findViewById(R.id.btTestSrvConnect);
        this.btTestConnect.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.ServerActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ServerActivity.this.connectSerrver("85.100.114.121");
            }
        });
        this.btDisConnect = (Button) findViewById(R.id.btDisConnect);
        this.btDisConnect.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.ServerActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                K.DbType = 0;
                ServerActivity.this.enableButtons();
                OdmDbInfo.setGlobalHash(ServerActivity.this);
            }
        });
        TextView faceb = (TextView) findViewById(R.id.lblFace);
        faceb.setClickable(true);
        faceb.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.ServerActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.facebook.com/Alim.Sosyal.Paylasim.Sayfasi"));
                ServerActivity.this.startActivity(browserIntent);
            }
        });
        enableButtons();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectSerrver(String ipaddr) {
        K.DbType = 1;
        K.Server = ipaddr;
        K.ConnectID = this.edID.getText().toString();
        enableButtons();
        OdmDbInfo dbi = new OdmDbInfo(this);
        dbi.notRaiseNullAVEKcr = true;
        if (dbi.getRow()) {
            OdmDbInfo.setGlobalHash(this);
            finish();
        } else {
            K.Error(this, "Bağlantı yapılamadı. internet bağlantınızı ve IP adresini kontrol ediniz.");
            K.DbType = 0;
            enableButtons();
        }
        dbi.closecr();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        K.loge("In onNewIntent()");
        super.onNewIntent(intent);
        boolean isStopServer = intent.getBooleanExtra(EXTRA_STOP_SERVER, false);
        if (isStopServer) {
            this.serverButton.setText(R.string.server_start);
            stopServer();
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            switch (resultCode) {
                case -1:
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startServer() {
        Intent secureWebServerIntent = new Intent(this, (Class<?>) ServerService.class);
        startService(secureWebServerIntent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopServer() {
        Intent secureWebServerIntent = new Intent(this, (Class<?>) ServerService.class);
        stopService(secureWebServerIntent);
    }
}
