package com.alyansyazilim.a_kart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class CariHesapKartiActivity extends Activity {
    private long carkartid;
    private EditText edCarAd;
    private EditText edCarKod;
    private Spinner edPb;
    private EditText edPosta;
    private EditText edTel;
    private int op;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_hesapkarti);
        this.carkartid = 0L;
        this.op = 1;
        setButtons();
        Bundle extras = getIntent().getExtras();
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.carkartid = extras.getLong("carkartid");
            opEditLoad();
            return;
        }
        this.edPb.setSelection(0);
    }

    private void setButtons() {
        this.edCarKod = (EditText) findViewById(R.id.edCarKod);
        this.edCarAd = (EditText) findViewById(R.id.edCarAd);
        this.edPosta = (EditText) findViewById(R.id.edPosta);
        this.edTel = (EditText) findViewById(R.id.edTel);
        this.edPb = (Spinner) findViewById(R.id.edPb);
        FN.loadPbCB(this, this.edPb);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.CariHesapKartiActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariHesapKartiActivity.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.CariHesapKartiActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (CariHesapKartiActivity.this.op) {
                    case 1:
                        snc = CariHesapKartiActivity.this.opInsertExecute();
                        break;
                    case 2:
                        snc = CariHesapKartiActivity.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okkart");
                    CariHesapKartiActivity.this.setResult(-1, intent);
                    CariHesapKartiActivity.this.finish();
                }
            }
        });
    }

    protected boolean opInsertExecute() {
        OdmCariKartlar k = new OdmCariKartlar(this);
        boolean readyForSave = true;
        if (1 != 0 && k.getByCarKod(this.edCarKod.getText().toString())) {
            K.Error(this, "Bu kod daha önce kayıt yapılmış. Farklı bir kod ile kayıt yapın.");
            readyForSave = false;
        }
        if (readyForSave && K.isNOE(this.edCarKod)) {
            K.Error(this, "Hesap Kodu boş olamaz.");
            readyForSave = false;
        }
        if (readyForSave) {
            k.KayitEkle(this.edCarKod.getText().toString(), this.edCarAd.getText().toString(), this.edPosta.getText().toString(), this.edTel.getText().toString(), this.edPb.getSelectedItem().toString());
        }
        k.closecr();
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmCariKartlar k = new OdmCariKartlar(this);
        boolean readyForSave = true;
        if (k.getByCarKod(this.edCarKod.getText().toString()) && !Long.valueOf(k.getID()).equals(Long.valueOf(this.carkartid))) {
            K.Error(this, "Bu kod başka bir hesap için kullanılmış. Farklı bir kod ile kayıt yapın.");
            readyForSave = false;
        }
        if (readyForSave) {
            readyForSave = k.KayitDuzelt(this.carkartid, this.edCarKod.getText().toString(), this.edCarAd.getText().toString(), this.edPosta.getText().toString(), this.edTel.getText().toString(), this.edPb.getSelectedItem().toString());
        }
        k.closecr();
        return readyForSave;
    }

    protected void opEditLoad() {
        OdmCariKartlar k = new OdmCariKartlar(this);
        if (k.getById(this.carkartid)) {
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
            this.edCarKod.setText(k.getCarKod());
            if (allowKodCh) {
                this.edCarKod.requestFocus();
            } else {
                this.edCarKod.setEnabled(false);
            }
            this.edCarAd.setText(k.getCarAd());
            if (!allowKodCh) {
                this.edCarAd.requestFocus();
            }
            this.edPosta.setText(k.getEposta());
            this.edTel.setText(k.getTelefon());
            this.edPb.setSelection(si);
            this.edPb.setEnabled(allowKodCh);
            return;
        }
        finish();
    }
}
