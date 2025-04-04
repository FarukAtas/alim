package com.alyansyazilim.a_kart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.alyansyazilim.database.OdmParaBirimKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class ParaBirimKartiActivity extends Activity {
    private EditText edAck;
    private EditText edKod;
    private CheckBox edYerel;
    private int op;
    private long pbkartid;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_karti);
        this.pbkartid = 0L;
        this.op = 1;
        setButtons();
        Bundle extras = getIntent().getExtras();
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.pbkartid = extras.getLong("pbkartid");
            opEditLoad();
        }
    }

    private void setButtons() {
        this.edKod = (EditText) findViewById(R.id.edKod);
        this.edAck = (EditText) findViewById(R.id.edAck);
        this.edYerel = (CheckBox) findViewById(R.id.edYerelPB);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.ParaBirimKartiActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                ParaBirimKartiActivity.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_kart.ParaBirimKartiActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (ParaBirimKartiActivity.this.op) {
                    case 1:
                        snc = ParaBirimKartiActivity.this.opInsertExecute();
                        break;
                    case 2:
                        snc = ParaBirimKartiActivity.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okkart");
                    ParaBirimKartiActivity.this.setResult(-1, intent);
                    ParaBirimKartiActivity.this.finish();
                }
            }
        });
    }

    protected boolean opInsertExecute() {
        OdmParaBirimKartlar k = new OdmParaBirimKartlar(this);
        boolean readyForSave = true;
        if (1 != 0 && K.isNOE(this.edKod)) {
            K.Error(this, "Hesap Kodu boş olamaz.");
            readyForSave = false;
        }
        if (readyForSave && k.getByKod(this.edKod.getText().toString())) {
            K.Error(this, "Bu kod daha önce kayıt yapılmış. Farklı bir kod ile kayıt yapın.");
            readyForSave = false;
        }
        if (readyForSave) {
            k.KayitEkle(this.edKod.getText().toString(), this.edAck.getText().toString(), this.edYerel.isChecked());
        }
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmParaBirimKartlar k = new OdmParaBirimKartlar(this);
        return k.KayitDuzelt(this.pbkartid, this.edKod.getText().toString(), this.edAck.getText().toString(), this.edYerel.isChecked());
    }

    protected void opEditLoad() {
        OdmParaBirimKartlar k = new OdmParaBirimKartlar(this);
        if (k.getById(this.pbkartid)) {
            this.edKod.setText(k.getKod());
            this.edKod.setEnabled(false);
            this.edAck.setText(k.getAck());
            this.edAck.requestFocus();
            this.edYerel.setChecked(k.getYerelPB());
            return;
        }
        finish();
    }
}
