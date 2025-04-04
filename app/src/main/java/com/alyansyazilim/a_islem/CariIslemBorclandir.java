package com.alyansyazilim.a_islem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.alyansyazilim.a_islemler.CariHesapIslemlerCRUD;
import com.alyansyazilim.database.OdmCariIslem;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class CariIslemBorclandir extends Activity {
    private String carkod;
    private EditText edAck;
    private Button edSaat;
    private Button edTarih;
    private EditText edTutar;
    private long islemid;
    private TextView lbCarAd;
    private TextView lbCarKod;
    private int mdl;
    protected int op;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_islem_borclandir);
        FN.initAutoComplete(this, R.id.edAciklama, "carislem");
        setButtons();
        this.op = 1;
        Bundle extras = getIntent().getExtras();
        OdmCariKartlar kart = new OdmCariKartlar(this);
        this.mdl = extras.getInt("mdl", K.MDL_CARBORCD);
        if (this.mdl == 65539) {
            setTitle(getString(R.string.title_activity_cari_alacaklandir));
        }
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.islemid = extras.getLong("islemid");
            opEditLoad();
            kart.getByCarKod(this.carkod);
        } else {
            kart.getById(extras.getLong("carkartid"));
            FN.getTarihSaat(this, this.edTarih, this.edSaat);
        }
        this.lbCarKod.setText(kart.getCarKod());
        this.lbCarAd.setText(String.valueOf(kart.getCarAd()) + " (" + kart.getPb() + ")");
        kart.closecr();
    }

    private void setButtons() {
        this.lbCarKod = (TextView) findViewById(R.id.lbCarKod);
        this.lbCarAd = (TextView) findViewById(R.id.lbCarAd);
        this.edAck = (EditText) findViewById(R.id.edAciklama);
        this.edTutar = (EditText) findViewById(R.id.edTutar);
        this.edTarih = (Button) findViewById(R.id.edTarih);
        this.edSaat = (Button) findViewById(R.id.edSaat);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemBorclandir.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariIslemBorclandir.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemBorclandir.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                switch (CariIslemBorclandir.this.op) {
                    case 1:
                        CariIslemBorclandir.this.opInsertExecute();
                        break;
                    case 2:
                        CariIslemBorclandir.this.opEditExecute();
                        break;
                }
                Intent intent = new Intent();
                intent.putExtra("result", "ok");
                CariIslemBorclandir.this.setResult(-1, intent);
                CariIslemBorclandir.this.finish();
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
        CariHesapIslemlerCRUD.doBorclandirSaveLinks(this, rowid, this.mdl, K.TarihSaatSql(this.edTarih, this.edSaat), this.edTutar.getText().toString(), this.lbCarKod.getText().toString(), this.edAck.getText().toString());
    }

    protected void opInsertExecute() {
        OdmCariIslem k = new OdmCariIslem(this);
        long rowid = k.KayitEkle(this.mdl, this.lbCarKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 0, null, this.edTarih.getText().toString(), this.edSaat.getText().toString(), null);
        doSaveLinks(rowid);
    }

    protected void opEditExecute() {
        OdmCariIslem k = new OdmCariIslem(this);
        k.KayitDuzelt(this.islemid, this.lbCarKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 0, null, this.edTarih.getText().toString(), this.edSaat.getText().toString(), null);
        doSaveLinks(this.islemid);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    protected void opEditLoad() {
        OdmCariIslem k = new OdmCariIslem(this);
        if (k.getById(this.islemid)) {
            this.carkod = k.getCarKod();
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
