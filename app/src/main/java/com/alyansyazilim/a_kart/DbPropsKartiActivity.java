package com.alyansyazilim.a_kart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.alyansyazilim.database.OdmDbInfo;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class DbPropsKartiActivity extends Activity {
    private EditText edAd;
    private EditText edNewPass1;
    private EditText edNewPass2;
    private CheckBox edPassReq;
    private EditText edPosta;
    private CheckBox edShowPass;
    private EditText edTel;
    private long kartid;
    private LinearLayout vPass;
    private ScrollView vScroll;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dbprops);
        this.kartid = 1L;
        setButtons();
        opEditLoad();
    }

    private void setButtons() {
        this.edAd = (EditText) findViewById(R.id.edAdi);
        this.edPosta = (EditText) findViewById(R.id.edPosta);
        this.edTel = (EditText) findViewById(R.id.edTel);
        this.edNewPass1 = (EditText) findViewById(R.id.edNewPass1);
        this.edNewPass2 = (EditText) findViewById(R.id.edNewPass2);
        this.vPass = (LinearLayout) findViewById(R.id.vPassw);
        this.vScroll = (ScrollView) findViewById(R.id.scrollView1);
        this.edPassReq = (CheckBox) findViewById(R.id.edPassReq);
        this.edPassReq.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.DbPropsKartiActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DbPropsKartiActivity.this.vPass.setVisibility(DbPropsKartiActivity.this.edPassReq.isChecked() ? 0 : 8);
                DbPropsKartiActivity.this.vScroll.post(new Runnable() { // from class: com.alyansyazilim.a_kart.DbPropsKartiActivity.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DbPropsKartiActivity.this.vScroll.scrollTo(0, DbPropsKartiActivity.this.edPassReq.isChecked() ? DbPropsKartiActivity.this.vScroll.getBottom() : 0);
                        if (DbPropsKartiActivity.this.edPassReq.isChecked()) {
                            DbPropsKartiActivity.this.edNewPass1.requestFocus();
                        }
                    }
                });
            }
        });
        this.edShowPass = (CheckBox) findViewById(R.id.edShowPass);
        this.edShowPass.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.DbPropsKartiActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (DbPropsKartiActivity.this.edShowPass.isChecked()) {
                    DbPropsKartiActivity.this.edNewPass1.setInputType(145);
                    DbPropsKartiActivity.this.edNewPass2.setInputType(145);
                } else {
                    DbPropsKartiActivity.this.edNewPass1.setInputType(129);
                    DbPropsKartiActivity.this.edNewPass2.setInputType(129);
                }
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.DbPropsKartiActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                DbPropsKartiActivity.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.DbPropsKartiActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = DbPropsKartiActivity.this.opEditExecute();
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okkart");
                    DbPropsKartiActivity.this.setResult(-1, intent);
                    DbPropsKartiActivity.this.finish();
                }
            }
        });
    }

    protected boolean opEditExecute() {
        OdmDbInfo k = new OdmDbInfo(this);
        boolean readyForSave = true;
        if (1 != 0 && this.edPassReq.isChecked() && K.isNOE(this.edNewPass1)) {
            K.Error(this, "Şifre boş olamaz. Şifresiz kullanım için 'Girişte şifre kullanılsın' işaretini kaldırın.");
            readyForSave = false;
        }
        if (readyForSave && this.edNewPass1.getText().toString().compareTo(this.edNewPass2.getText().toString()) != 0) {
            K.Error(this, "Şifreler birbirinden farklı.");
            readyForSave = false;
        }
        if (readyForSave) {
            return k.KayitDuzelt(this.kartid, this.edAd, this.edPosta, this.edTel, this.edPassReq, this.edNewPass1);
        }
        return readyForSave;
    }

    protected void opEditLoad() {
        OdmDbInfo k = new OdmDbInfo(this);
        if (k.getById_(this.kartid)) {
            this.edAd.setText(k.getAdi());
            this.edAd.requestFocus();
            this.edPosta.setText(k.getEposta());
            this.edTel.setText(k.getTelefon());
            this.edPassReq.setChecked(k.getPassReq());
            this.edNewPass1.setText(k.getPassw());
            this.edNewPass2.setText(k.getPassw());
            this.vPass.setVisibility(this.edPassReq.isChecked() ? 0 : 8);
            k.closecr();
            return;
        }
        k.closecr();
        finish();
    }
}
