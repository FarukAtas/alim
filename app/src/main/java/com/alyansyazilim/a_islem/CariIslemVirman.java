package com.alyansyazilim.a_islem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import com.alyansyazilim.a_islemler.CariHesapIslemlerCRUD;
import com.alyansyazilim.database.OdmCariIslem;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.C2C2C;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class CariIslemVirman extends Activity {
    private C2C2C c2c;
    private String carkod;
    private String carkod_bakiye;
    private EditText edAck;
    private TextView edKhAdi;
    private Button edKhk;
    private Button edSaat;
    private TextView edSonuc;
    private Button edTarih;
    private EditText edTutar;
    private TableRow hgtrow;
    private long islemid;
    private TextView lbCarAd;
    private TextView lbCarKod;
    private TextView lbFromPB;
    private TextView lbTutar;
    private int mdl;
    protected int op;
    private String FromPB = "";
    private String ToPB = "";
    TextWatcher tw = new TextWatcher() { // from class: com.alyansyazilim.a_islem.CariIslemVirman.1
        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            CariIslemVirman.this.calcHGT();
        }
    };
    DialogInterface.OnClickListener khk_brw_ocl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemVirman.2
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (selectedPosition > -1) {
                String kod = ((AlertDialog) dialog).getListView().getItemAtPosition(selectedPosition).toString();
                OdmCariKartlar khk = new OdmCariKartlar(CariIslemVirman.this);
                khk.getByCarKod(kod);
                CariIslemVirman.this.ToPB = khk.getPb();
                CariIslemVirman.this.c2c.setToPB(CariIslemVirman.this.ToPB);
                CariIslemVirman.this.c2c.ToKur = CariIslemVirman.this.c2c.getKur(CariIslemVirman.this.c2c.getToPB());
                CariIslemVirman.this.edKhk.setText(kod);
                CariIslemVirman.this.edKhAdi.setText(String.valueOf(khk.getCarAd()) + " (" + CariIslemVirman.this.ToPB + ")");
                CariIslemVirman.this.calcHGT();
                CariIslemVirman.this.edAck.requestFocus();
                CariIslemVirman.this.hgtrow.setVisibility(CariIslemVirman.this.c2c.getHGTvisibility());
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_islem_virman);
        FN.initAutoComplete(this, R.id.edAciklama, "carislem");
        setButtons();
        this.c2c = new C2C2C(this);
        this.op = 1;
        Bundle extras = getIntent().getExtras();
        OdmCariKartlar kart = new OdmCariKartlar(this);
        this.mdl = extras.getInt("mdl", K.MDL_CARVIRMAN);
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.islemid = extras.getLong("islemid");
            opEditLoad();
            kart.getByCarKod(this.carkod);
        } else {
            kart.getById(extras.getLong("carkartid"));
            FN.getTarihSaat(this, this.edTarih, this.edSaat);
            this.carkod_bakiye = K.Str2BigDecimal(kart.getBakiye(), 0).abs().toString();
            this.FromPB = kart.getPb();
            this.c2c.setFromPB(kart.getPb());
            this.c2c.FromKur = this.c2c.getKur(this.c2c.getFromPB());
        }
        this.lbFromPB.setText(this.FromPB);
        this.lbCarKod.setText(kart.getCarKod());
        this.lbCarAd.setText(String.valueOf(kart.getCarAd()) + " (" + this.FromPB + ")");
        setEvents();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calcHGT() {
        this.c2c.ManualResult = false;
        String snc = this.c2c.calcBL(this.edTutar);
        if (!K.isNOE(snc)) {
            snc = String.valueOf(snc) + " " + this.ToPB;
        }
        this.edSonuc.setText(snc);
    }

    private void setEvents() {
        this.edTutar.addTextChangedListener(this.tw);
        this.lbTutar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemVirman.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariIslemVirman.this.edTutar.beginBatchEdit();
                CariIslemVirman.this.edTutar.setText(CariIslemVirman.this.carkod_bakiye);
                CariIslemVirman.this.edTutar.endBatchEdit();
            }
        });
        this.edSonuc.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemVirman.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariIslemVirman.this.c2c.setTutar(CariIslemVirman.this.edTutar);
                CariIslemVirman.this.c2c.showDialog(CariIslemVirman.this, CariIslemVirman.this.edSonuc);
            }
        });
    }

    private void setButtons() {
        this.hgtrow = (TableRow) findViewById(R.id.trHGT);
        this.hgtrow.setVisibility(8);
        this.edKhk = (Button) findViewById(R.id.edKrsKod);
        this.edKhAdi = (TextView) findViewById(R.id.lbKrsAd);
        this.edSonuc = (TextView) findViewById(R.id.edSonuc);
        this.lbCarKod = (TextView) findViewById(R.id.lbCarKod);
        this.lbCarAd = (TextView) findViewById(R.id.lbCarAd);
        this.lbTutar = (TextView) findViewById(R.id.lbTutar);
        this.lbFromPB = (TextView) findViewById(R.id.lbFromPB);
        this.edAck = (EditText) findViewById(R.id.edAciklama);
        this.edTutar = (EditText) findViewById(R.id.edTutar);
        this.edTarih = (Button) findViewById(R.id.edTarih);
        this.edSaat = (Button) findViewById(R.id.edSaat);
        this.edKhk.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemVirman.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                FN.luCarKart(CariIslemVirman.this, CariIslemVirman.this.edKhk, CariIslemVirman.this.edTutar, CariIslemVirman.this.khk_brw_ocl);
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemVirman.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariIslemVirman.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemVirman.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (CariIslemVirman.this.op) {
                    case 1:
                        snc = CariIslemVirman.this.opInsertExecute();
                        break;
                    case 2:
                        snc = CariIslemVirman.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okislem");
                    CariIslemVirman.this.setResult(-1, intent);
                    CariIslemVirman.this.finish();
                }
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
        CariHesapIslemlerCRUD.doVirmanSaveLinks(this, rowid, this.mdl, K.TarihSaatSql(this.edTarih, this.edSaat), this.edTutar.getText().toString(), this.lbCarKod.getText().toString(), this.c2c.Sonuc.toString(), this.lbCarAd.getText().toString(), this.edAck.getText().toString(), this.edKhk.getText().toString(), 21);
    }

    protected boolean opInsertExecute() {
        OdmCariIslem k = new OdmCariIslem(this);
        boolean readyForSave = true;
        if (1 != 0 && K.isNOE(this.edTutar)) {
            K.Error(this, "Tutar boş olamaz.");
            this.edTutar.requestFocus();
            readyForSave = false;
        }
        if (readyForSave && K.isNOE(this.edKhk)) {
            K.Error(this, "Karşı Hesap Kodu boş olamaz.");
            readyForSave = false;
        }
        String khk = this.edKhk.getText().toString();
        if (readyForSave) {
            OdmCariKartlar kart = new OdmCariKartlar(this);
            if (!kart.getByCarKod(khk)) {
                K.Error(this, "Cari Hesap kartı bulunamadı. Hesap kodunu konrtol edin.");
                readyForSave = false;
            }
        }
        if (readyForSave) {
            long rowid = k.KayitEkle(this.mdl, this.lbCarKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 21, this.edKhk.getText().toString(), this.edTarih.getText().toString(), this.edSaat.getText().toString(), this.c2c.toJson());
            doSaveLinks(rowid);
        }
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmCariIslem k = new OdmCariIslem(this);
        boolean result = k.KayitDuzelt(this.islemid, this.lbCarKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 21, this.edKhk.getText().toString(), this.edTarih.getText().toString(), this.edSaat.getText().toString(), this.c2c.toJson());
        if (result) {
            doSaveLinks(this.islemid);
        }
        return result;
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    protected void opEditLoad() {
        OdmCariIslem k = new OdmCariIslem(this);
        OdmCariKartlar chk = new OdmCariKartlar(this);
        OdmCariKartlar kchk = new OdmCariKartlar(this);
        if (k.getById(this.islemid)) {
            chk.getByCarKod(k.getCarKod());
            kchk.getByCarKod(k.getKarsiHesapKodu());
            this.edKhAdi.setText(String.valueOf(kchk.getCarAd()) + " (" + kchk.getPb() + ")");
            this.FromPB = chk.getPb();
            this.ToPB = kchk.getPb();
            this.carkod = k.getCarKod();
            this.edAck.setText(k.getAck());
            this.edTutar.setText(k.getTutar());
            this.edTutar.requestFocus();
            this.edKhk.setText(k.getKarsiHesapKodu());
            this.edTarih.setText(k.getZamanT());
            this.edSaat.setText(k.getZamanS());
            this.c2c.fromJson(k.getMeta());
            if (this.c2c.version == 0) {
                K.loge("version == 0");
                this.c2c.setFromPB(this.FromPB);
                this.c2c.setToPB(this.ToPB);
                this.c2c.calcBL(this.edTutar);
            }
            this.edSonuc.setText(String.valueOf(this.c2c.getSonucFmt()) + " " + this.ToPB);
            this.hgtrow.setVisibility(this.c2c.getHGTvisibility());
            this.carkod_bakiye = this.edTutar.getText().toString();
            return;
        }
        finish();
    }
}
