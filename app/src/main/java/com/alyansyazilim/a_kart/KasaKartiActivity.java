package com.alyansyazilim.a_kart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.alyansyazilim.database.OdmKasaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class KasaKartiActivity extends Activity {
    private EditText edKasAd;
    private EditText edKasKod;
    private Spinner edPb;
    private EditText edSira;
    private long kaskartid;
    private int op;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa_karti);
        this.kaskartid = 0L;
        this.op = 1;
        setButtons();
        Bundle extras = getIntent().getExtras();
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.kaskartid = extras.getLong("kaskartid");
            opEditLoad();
        } else {
            this.edSira.setText("99");
            this.edPb.setSelection(0);
        }
    }

    private void setButtons() {
        this.edKasKod = (EditText) findViewById(R.id.edKasKod);
        this.edKasAd = (EditText) findViewById(R.id.edKasAd);
        this.edPb = (Spinner) findViewById(R.id.edPb);
        this.edSira = (EditText) findViewById(R.id.edSira);
        FN.loadPbCB(this, this.edPb);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.KasaKartiActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaKartiActivity.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.KasaKartiActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (KasaKartiActivity.this.op) {
                    case 1:
                        snc = KasaKartiActivity.this.opInsertExecute();
                        break;
                    case 2:
                        snc = KasaKartiActivity.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okkart");
                    KasaKartiActivity.this.setResult(-1, intent);
                    KasaKartiActivity.this.finish();
                }
            }
        });
    }

    protected boolean opInsertExecute() {
        OdmKasaKartlar k = new OdmKasaKartlar(this);
        boolean readyForSave = true;
        if (1 != 0 && k.getByKasKod(this.edKasKod.getText().toString())) {
            K.Error(this, "Bu kod daha önce kayıt yapılmış. Farklı bir kod ile kayıt yapın.");
            readyForSave = false;
        }
        if (readyForSave && K.isNOE(this.edKasKod)) {
            K.Error(this, "Hesap Kodu boş olamaz.");
            readyForSave = false;
        }
        if (readyForSave) {
            k.KayitEkle(this.edKasKod.getText().toString(), this.edKasAd.getText().toString(), this.edPb.getSelectedItem().toString(), this.edSira.getText().toString());
        }
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmKasaKartlar k = new OdmKasaKartlar(this);
        boolean readyForSave = true;
        if (k.getByKasKod(this.edKasKod.getText().toString()) && !Long.valueOf(k.getID()).equals(Long.valueOf(this.kaskartid))) {
            K.Error(this, "Bu kod başka bir hesap için kullanılmış. Farklı bir kod ile kayıt yapın.");
            readyForSave = false;
        }
        if (readyForSave) {
            return k.KayitDuzelt(this.kaskartid, this.edKasKod.getText().toString(), this.edKasAd.getText().toString(), this.edPb.getSelectedItem().toString(), this.edSira.getText().toString());
        }
        return readyForSave;
    }

    protected void opEditLoad() {
        OdmKasaKartlar k = new OdmKasaKartlar(this);
        if (k.getById(this.kaskartid)) {
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
            this.edKasKod.setText(k.getKasKod());
            if (allowKodCh) {
                this.edKasKod.requestFocus();
            } else {
                this.edKasKod.setEnabled(false);
            }
            this.edKasAd.setText(k.getKasAd());
            if (!allowKodCh) {
                this.edKasAd.requestFocus();
            }
            this.edPb.setSelection(si);
            this.edSira.setText(k.getSira());
            this.edPb.setEnabled(allowKodCh);
            return;
        }
        finish();
    }
}
