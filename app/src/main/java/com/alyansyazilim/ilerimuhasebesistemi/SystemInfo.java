package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.database.Veritabani;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

/* loaded from: classes.dex */
public class SystemInfo extends Activity {
    private String carkod;
    private String eposta;
    private StringBuilder lbErr;
    private StringBuilder lbRp;
    private TextView lbRp1;
    private String vTarih;

    private void doTitle() {
        this.lbRp1.setText((CharSequence) null);
        this.lbRp1.append("Sistem Bilgisi\n");
        this.lbRp1.append("Tarih  : " + this.vTarih + "\n");
        this.lbRp1.append("---------------------------------\n");
        this.lbRp1.append(this.lbRp);
        if (this.lbErr.length() > 0) {
            K.loge(this.lbErr.toString());
        }
    }

    private void doSwInfo() {
        this.lbRp.append("Klasör Bilgileri\n");
        this.lbRp.append("---------------------------------\n");
        this.lbRp.append("ExternalStorageDirectory:" + Environment.getExternalStorageDirectory().getAbsolutePath() + "\n");
        this.lbRp.append("DownloadsDirectory:" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "\n");
        this.lbRp.append("FilesDir:" + getFilesDir() + "\n");
        this.lbRp.append("DatabasePathDir:" + Veritabani.getDbFileName(this) + "\n");
        this.lbRp.append("CacheDir:" + getCacheDir() + "\n");
        this.lbRp.append("ExternalCacheDir:" + getExternalCacheDir() + "\n");
        this.lbRp.append("ExternalFilesDir:" + getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "\n");
        this.lbRp.append("\n");
    }

    private void doHwInfo() {
        this.lbRp.append("Android İşletim Sistemi\n");
        this.lbRp.append("---------------------------------\n");
        this.lbRp.append(RegSrv.getDeviceTxt());
        this.lbRp.append("\n");
    }

    public void doReport() {
        this.lbRp1.setTypeface(Typeface.MONOSPACE);
        Date date = new Date();
        this.vTarih = K.Now_2(date);
        doHwInfo();
        doSwInfo();
        doTitle();
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapor_geneldurum);
        setButtons();
        this.lbRp1 = (TextView) findViewById(R.id.lbRp);
        this.lbRp = new StringBuilder();
        this.lbErr = new StringBuilder();
        doReport();
    }

    private void setButtons() {
        Button btInc = (Button) findViewById(R.id.btIncrease);
        btInc.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SystemInfo.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                float ts = SystemInfo.this.lbRp1.getTextSize();
                SystemInfo.this.lbRp1.setTextSize((1.0f + ts) / SystemInfo.this.getResources().getDisplayMetrics().density);
                SystemInfo.this.lbRp1.getTextSize();
            }
        });
        Button btDec = (Button) findViewById(R.id.btDecrease);
        btDec.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SystemInfo.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                float ts = SystemInfo.this.lbRp1.getTextSize();
                SystemInfo.this.lbRp1.setTextSize((ts - 1.0f) / SystemInfo.this.getResources().getDisplayMetrics().density);
                SystemInfo.this.lbRp1.getTextSize();
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SystemInfo.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SystemInfo.this.finish();
            }
        });
        Button btnMail = (Button) findViewById(R.id.btMail);
        btnMail.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.SystemInfo.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SystemInfo.this.email_text();
            }
        });
    }

    private void createFile(String strFile) {
        try {
            String data = String.valueOf("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/><html><body style='tab-interval: .5in'><div class='Section1'><div>") + "<p class='MsoNormal' dir='LTR' style='text-align: left; unicode-bidi: embed'><span>";
            FileOutputStream fos = new FileOutputStream(strFile);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(String.valueOf(String.valueOf(String.valueOf(String.valueOf(data) + "Email data<br />") + "bla bla bla<br />") + "Footer<br />") + "</span></p></div></body></html>");
            out.flush();
            out.close();
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(), 1).show();
        }
    }

    public void email_attach(View v) {
        OdmCariKartlar k = new OdmCariKartlar(this);
        k.getByCarKod(this.carkod);
        String strFile = String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + "/temp";
        File file = new File(strFile);
        if (!file.exists()) {
            file.mkdirs();
        }
        String strFile2 = String.valueOf(strFile) + "/report.html";
        createFile(strFile2);
        try {
            Intent emailIntent = new Intent("android.intent.action.SEND");
            String address = k.getEposta();
            String emailtext = "Merhaba " + k.getCarAd() + ",";
            String emailtext2 = String.valueOf(String.valueOf(String.valueOf(emailtext) + "<hr/>") + "<table><tr><td>aas</td><td>asdasd</td></tr><tr><td>2aas</td><td>2asdasd</td></tr></table>") + "<hr/>";
            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{address});
            emailIntent.putExtra("android.intent.extra.SUBJECT", "Hesap extresi");
            emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + strFile2));
            emailIntent.putExtra("android.intent.extra.TEXT", Html.fromHtml(emailtext2));
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (Throwable tt) {
            Toast.makeText(this, "Request failed: " + tt.toString(), 1).show();
        }
    }

    public void email_text() {
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{"alyansyazilim@gmail.com"});
        emailIntent.putExtra("android.intent.extra.SUBJECT", "Sistem Bilgisi");
        String emailText = this.lbRp1.getText().toString();
        emailIntent.setType("text/plain");
        emailIntent.putExtra("android.intent.extra.TEXT", emailText);
        Intent client = Intent.createChooser(emailIntent, "e-posta:");
        startActivityForResult(client, 1);
    }

    public void email_html() {
        OdmCariKartlar k = new OdmCariKartlar(this);
        k.getByCarKod(this.carkod);
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{this.eposta});
        emailIntent.putExtra("android.intent.extra.SUBJECT", "Hesap Extreniz");
        String emailText = String.valueOf("<html><body><p>Merhaba </p>") + "liste sonu.<br></body></html>";
        emailIntent.setType("text/plain");
        emailIntent.putExtra("android.intent.extra.TEXT", emailText);
        Intent client = Intent.createChooser(emailIntent, "e-posta:");
        startActivityForResult(client, 1);
    }
}
