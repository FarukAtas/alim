package com.alyansyazilim.a_islem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.alyansyazilim.a_islemler.KasaIslemlerCRUD;
import com.alyansyazilim.database.OdmKasaIslem;
import com.alyansyazilim.database.OdmKasaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class KasaIslemTahsil extends Activity {
    private TextView edAck;
    private Button edSaat;
    private Button edTarih;
    private TextView edTutar;
    private long islemid;
    private String kaskod;
    private TextView lbKasAd;
    private TextView lbKasKod;
    private int mdl;
    protected int op;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa_islem_tahsil);
        FN.initAutoComplete(this, R.id.edAciklama, "kasislem");
        setButtons();
        this.op = 1;
        Bundle extras = getIntent().getExtras();
        OdmKasaKartlar kart = new OdmKasaKartlar(this);
        this.mdl = extras.getInt("mdl", K.MDL_KASTAHSIL);
        if (this.mdl == 65794) {
            setTitle(getString(R.string.title_activity_kasa_odeme));
        }
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.islemid = extras.getLong("islemid");
            opEditLoad();
            kart.getByKasKod(this.kaskod);
        } else {
            kart.getById(extras.getLong("kaskartid"));
            FN.getTarihSaat(this, this.edTarih, this.edSaat);
        }
        this.lbKasKod.setText(kart.getKasKod());
        this.lbKasAd.setText(kart.getKasAd());
    }

    private void setButtons() {
        this.lbKasKod = (TextView) findViewById(R.id.lbKasKod);
        this.lbKasAd = (TextView) findViewById(R.id.lbKasAd);
        this.edAck = (EditText) findViewById(R.id.edAciklama);
        this.edTutar = (EditText) findViewById(R.id.edTutar);
        this.edTarih = (Button) findViewById(R.id.edTarih);
        this.edSaat = (Button) findViewById(R.id.edSaat);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.KasaIslemTahsil.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaIslemTahsil.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.KasaIslemTahsil.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                switch (KasaIslemTahsil.this.op) {
                    case 1:
                        KasaIslemTahsil.this.opInsertExecute();
                        break;
                    case 2:
                        KasaIslemTahsil.this.opEditExecute();
                        break;
                }
                Intent intent = new Intent();
                intent.putExtra("result", "ok");
                KasaIslemTahsil.this.setResult(-1, intent);
                KasaIslemTahsil.this.finish();
            }
        });
    }

    public void btTarihChClick(View v) {
        FN.onDateChange(this, this.edTarih);
    }

    public void btSaatChClick(View v) {
        FN.onTimeChange(this, this.edSaat);
    }

    private void doSaveLinks(long rowid) {
        KasaIslemlerCRUD.doTahsilatSaveLinks(this, rowid, this.mdl, K.TarihSaatSql(this.edTarih, this.edSaat), this.edTutar.getText().toString(), this.lbKasKod.getText().toString(), this.edAck.getText().toString());
    }

    protected void opInsertExecute() {
        OdmKasaIslem k = new OdmKasaIslem(this);
        long rowid = k.KayitEkle(this.mdl, this.lbKasKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 0, null, this.edTarih.getText().toString(), this.edSaat.getText().toString(), null);
        doSaveLinks(rowid);
    }

    protected void opEditExecute() {
        OdmKasaIslem k = new OdmKasaIslem(this);
        k.KayitDuzelt(this.islemid, this.lbKasKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 0, null, this.edTarih.getText().toString(), this.edSaat.getText().toString(), null);
        doSaveLinks(this.islemid);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    protected void opEditLoad() {
        OdmKasaIslem k = new OdmKasaIslem(this);
        if (k.getById(this.islemid)) {
            this.kaskod = k.getKasKod();
            this.edAck.setText(k.getAck());
            this.edTutar.setText(k.getTutar());
            this.edTutar.requestFocus();
            this.edTarih.setText(k.getZamanT());
            this.edSaat.setText(k.getZamanS());
            return;
        }
        finish();
    }
}
