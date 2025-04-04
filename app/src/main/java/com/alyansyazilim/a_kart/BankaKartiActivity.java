package com.alyansyazilim.a_kart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.alyansyazilim.database.OdmBankaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class BankaKartiActivity extends Activity {
    private long bankartid;
    private EditText edBanAd;
    private EditText edBanKod;
    private Spinner edPb;
    private int op;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banka_karti);
        this.bankartid = 0L;
        this.op = 1;
        setButtons();
        Bundle extras = getIntent().getExtras();
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.bankartid = extras.getLong("bankartid");
            opEditLoad();
            return;
        }
        this.edPb.setSelection(0);
    }

    private void setButtons() {
        this.edBanKod = (EditText) findViewById(R.id.edBanKod);
        this.edBanAd = (EditText) findViewById(R.id.edBanAd);
        this.edPb = (Spinner) findViewById(R.id.edPb);
        FN.loadPbCB(this, this.edPb);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.BankaKartiActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaKartiActivity.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.BankaKartiActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (BankaKartiActivity.this.op) {
                    case 1:
                        snc = BankaKartiActivity.this.opInsertExecute();
                        break;
                    case 2:
                        snc = BankaKartiActivity.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okkart");
                    BankaKartiActivity.this.setResult(-1, intent);
                    BankaKartiActivity.this.finish();
                }
            }
        });
    }

    protected boolean opInsertExecute() {
        OdmBankaKartlar k = new OdmBankaKartlar(this);
        boolean readyForSave = true;
        if (1 != 0 && k.getByBanKod(this.edBanKod.getText().toString())) {
            K.Error(this, "Bu kod daha önce kayıt yapılmış. Farklı bir kod ile kayıt yapın.");
            readyForSave = false;
        }
        if (readyForSave && K.isNOE(this.edBanKod)) {
            K.Error(this, "Hesap Kodu boş olamaz.");
            readyForSave = false;
        }
        if (readyForSave) {
            k.KayitEkle(this.edBanKod.getText().toString(), this.edBanAd.getText().toString(), this.edPb.getSelectedItem().toString());
        }
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmBankaKartlar k = new OdmBankaKartlar(this);
        boolean readyForSave = true;
        if (k.getByBanKod(this.edBanKod.getText().toString()) && !Long.valueOf(k.getID()).equals(Long.valueOf(this.bankartid))) {
            K.Error(this, "Bu kod başka bir hesap için kullanılmış. Farklı bir kod ile kayıt yapın.");
            readyForSave = false;
        }
        if (readyForSave) {
            return k.KayitDuzelt(this.bankartid, this.edBanKod.getText().toString(), this.edBanAd.getText().toString(), this.edPb.getSelectedItem().toString());
        }
        return readyForSave;
    }

    protected void opEditLoad() {
        OdmBankaKartlar k = new OdmBankaKartlar(this);
        if (k.getById(this.bankartid)) {
            int si = 0;
            int i = 0;
            while (true) {
                if (i >= this.edPb.getCount()) {
                    break;
                }
                if (!this.edPb.getItemAtPosition(i).toString().equals(k.getPb())) {
                    i++;
                } else {
                    si = i;
                    break;
                }
            }
            boolean allowKodCh = k.getHrkCount() == 0;
            this.edBanKod.setText(k.getBanKod());
            if (allowKodCh) {
                this.edBanKod.requestFocus();
            } else {
                this.edBanKod.setEnabled(false);
            }
            this.edBanAd.setText(k.getBanAd());
            if (!allowKodCh) {
                this.edBanAd.requestFocus();
            }
            this.edPb.setSelection(si);
            this.edPb.setEnabled(allowKodCh);
            return;
        }
        finish();
    }
}
