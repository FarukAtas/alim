package com.alyansyazilim.a_islem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.alyansyazilim.a_islemler.BankaIslemlerCRUD;
import com.alyansyazilim.database.OdmBankaIslem;
import com.alyansyazilim.database.OdmBankaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class BankaIslemYatan extends Activity {
    private String bankod;
    private TextView edAck;
    private Button edSaat;
    private Button edTarih;
    private TextView edTutar;
    private long islemid;
    private TextView lbBanAd;
    private TextView lbBanKod;
    private int mdl;
    protected int op;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banka_islem_yatan);
        FN.initAutoComplete(this, R.id.edAciklama, "banislem");
        setButtons();
        this.op = 1;
        Bundle extras = getIntent().getExtras();
        OdmBankaKartlar kart = new OdmBankaKartlar(this);
        this.mdl = extras.getInt("mdl", K.MDL_BANYATAN);
        if (this.mdl == 66050) {
            setTitle(getString(R.string.title_activity_banka_cekilen));
        }
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.islemid = extras.getLong("islemid");
            opEditLoad();
            kart.getByBanKod(this.bankod);
        } else {
            kart.getById(extras.getLong("bankartid"));
            FN.getTarihSaat(this, this.edTarih, this.edSaat);
        }
        this.lbBanKod.setText(kart.getBanKod());
        this.lbBanAd.setText(kart.getBanAd());
    }

    private void setButtons() {
        this.lbBanKod = (TextView) findViewById(R.id.lbBanKod);
        this.lbBanAd = (TextView) findViewById(R.id.lbBanAd);
        this.edAck = (EditText) findViewById(R.id.edAciklama);
        this.edTutar = (EditText) findViewById(R.id.edTutar);
        this.edTarih = (Button) findViewById(R.id.edTarih);
        this.edSaat = (Button) findViewById(R.id.edSaat);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.BankaIslemYatan.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaIslemYatan.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.BankaIslemYatan.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                switch (BankaIslemYatan.this.op) {
                    case 1:
                        BankaIslemYatan.this.opInsertExecute();
                        break;
                    case 2:
                        BankaIslemYatan.this.opEditExecute();
                        break;
                }
                Intent intent = new Intent();
                intent.putExtra("result", "ok");
                BankaIslemYatan.this.setResult(-1, intent);
                BankaIslemYatan.this.finish();
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
        BankaIslemlerCRUD.doYatanSaveLinks(this, rowid, this.mdl, K.TarihSaatSql(this.edTarih, this.edSaat), this.edTutar.getText().toString(), this.lbBanKod.getText().toString(), this.edAck.getText().toString());
    }

    protected void opInsertExecute() {
        OdmBankaIslem k = new OdmBankaIslem(this);
        long rowid = k.KayitEkle(this.mdl, this.lbBanKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 0, null, this.edTarih.getText().toString(), this.edSaat.getText().toString(), null);
        doSaveLinks(rowid);
    }

    protected void opEditExecute() {
        OdmBankaIslem k = new OdmBankaIslem(this);
        k.KayitDuzelt(this.islemid, this.lbBanKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 0, null, this.edTarih.getText().toString(), this.edSaat.getText().toString(), null);
        doSaveLinks(this.islemid);
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    protected void opEditLoad() {
        OdmBankaIslem k = new OdmBankaIslem(this);
        if (k.getById(this.islemid)) {
            this.bankod = k.getBanKod();
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
