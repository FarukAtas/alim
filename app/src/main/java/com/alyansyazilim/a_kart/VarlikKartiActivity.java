package com.alyansyazilim.a_kart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.alyansyazilim.database.OdmVarlikKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class VarlikKartiActivity extends Activity {
    private EditText edAd;
    private EditText edKod;
    private Spinner edPb;
    private Button edTarih;
    private Spinner edTur;
    private EditText edTutar;
    private int op;
    private long vrlkartid;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varlik_karti);
        this.vrlkartid = 0L;
        this.op = 1;
        setButtons();
        Bundle extras = getIntent().getExtras();
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.vrlkartid = extras.getLong("vrlkartid");
            opEditLoad();
        } else {
            this.edTur.setSelection(0);
            this.edPb.setSelection(0);
            FN.getTarihSaat(getParent(), this.edTarih, null);
        }
    }

    private void setButtons() {
        this.edKod = (EditText) findViewById(R.id.edKod);
        this.edAd = (EditText) findViewById(R.id.edAd);
        this.edTutar = (EditText) findViewById(R.id.edTutar);
        this.edPb = (Spinner) findViewById(R.id.edPb);
        this.edTur = (Spinner) findViewById(R.id.edTur);
        this.edTarih = (Button) findViewById(R.id.edTarih);
        FN.loadPbCB(this, this.edPb);
        FN.loadVarlikTurCB(this, this.edTur);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.VarlikKartiActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                VarlikKartiActivity.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.VarlikKartiActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (VarlikKartiActivity.this.op) {
                    case 1:
                        snc = VarlikKartiActivity.this.opInsertExecute();
                        break;
                    case 2:
                        snc = VarlikKartiActivity.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okkart");
                    VarlikKartiActivity.this.setResult(-1, intent);
                    VarlikKartiActivity.this.finish();
                }
            }
        });
    }

    public void btTarihChClick(View v) {
        FN.onDateChange(this, this.edTarih);
    }

    protected boolean opInsertExecute() {
        OdmVarlikKartlar k = new OdmVarlikKartlar(this);
        boolean readyForSave = true;
        if (1 != 0 && k.getByKod(this.edKod.getText().toString())) {
            K.Error(this, "Bu kod daha önce kayıt yapılmış. Farklı bir kod ile kayıt yapın.");
            readyForSave = false;
        }
        if (readyForSave && K.isNOE(this.edKod)) {
            K.Error(this, "Hesap Kodu boş olamaz.");
            readyForSave = false;
        }
        if (readyForSave) {
            k.KayitEkle(this.edKod.getText().toString(), String.valueOf(this.edTur.getSelectedItemId()), this.edAd.getText().toString(), this.edTutar.getText().toString(), this.edPb.getSelectedItem().toString(), "99", null, this.edTarih.getText().toString());
        }
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmVarlikKartlar k = new OdmVarlikKartlar(this);
        return k.KayitDuzelt(this.vrlkartid, this.edKod.getText().toString(), String.valueOf(this.edTur.getSelectedItemId()), this.edAd.getText().toString(), this.edTutar.getText().toString(), this.edPb.getSelectedItem().toString(), "99", null, this.edTarih.getText().toString());
    }

    protected void opEditLoad() {
        OdmVarlikKartlar k = new OdmVarlikKartlar(this);
        if (k.getById(this.vrlkartid)) {
            String stTur = K.VarTur2Str(k.getTuru());
            int siTur = 0;
            int i = 0;
            while (true) {
                if (i >= this.edTur.getCount()) {
                    break;
                }
                if (this.edTur.getItemAtPosition(i).toString().compareTo(stTur) != 0) {
                    i++;
                } else {
                    siTur = i;
                    break;
                }
            }
            int siPb = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= this.edPb.getCount()) {
                    break;
                }
                if (!this.edPb.getItemAtPosition(i2).toString().equals(k.getPb())) {
                    i2++;
                } else {
                    siPb = i2;
                    break;
                }
            }
            this.edKod.setText(k.getKod());
            this.edKod.requestFocus();
            this.edAd.setText(k.getAd());
            this.edTutar.setText(k.getTutar());
            this.edPb.setSelection(siPb);
            this.edTur.setSelection(siTur);
            this.edTarih.setText(k.getZamanT());
            return;
        }
        finish();
    }
}
