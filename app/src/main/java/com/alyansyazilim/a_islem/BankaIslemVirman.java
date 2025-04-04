package com.alyansyazilim.a_islem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import com.alyansyazilim.a_islemler.BankaIslemlerCRUD;
import com.alyansyazilim.database.OdmBankaIslem;
import com.alyansyazilim.database.OdmBankaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.C2C2C;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class BankaIslemVirman extends Activity {
    private String bankod;
    private String bankod_bakiye;
    private C2C2C c2c;
    private EditText edAck;
    private TextView edKhAdi;
    private Button edKhk;
    private Button edSaat;
    private TextView edSonuc;
    private Button edTarih;
    private EditText edTutar;
    private TableRow hgtrow;
    private long islemid;
    private TextView lbBanAd;
    private TextView lbBanKod;
    private TextView lbFromPB;
    private TextView lbTutar;
    private int mdl;
    protected int op;
    private String FromPB = "";
    private String ToPB = "";
    View.OnKeyListener okl = new View.OnKeyListener() { // from class: com.alyansyazilim.a_islem.BankaIslemVirman.1
        @Override // android.view.View.OnKeyListener
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            BankaIslemVirman.this.calcHGT();
            return false;
        }
    };
    TextWatcher tw = new TextWatcher() { // from class: com.alyansyazilim.a_islem.BankaIslemVirman.2
        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            BankaIslemVirman.this.calcHGT();
        }
    };
    DialogInterface.OnClickListener khk_brw_ocl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_islem.BankaIslemVirman.3
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (selectedPosition > -1) {
                String kod = ((AlertDialog) dialog).getListView().getItemAtPosition(selectedPosition).toString();
                OdmBankaKartlar khk = new OdmBankaKartlar(BankaIslemVirman.this);
                khk.getByBanKod(kod);
                BankaIslemVirman.this.ToPB = khk.getPb();
                BankaIslemVirman.this.c2c.setToPB(BankaIslemVirman.this.ToPB);
                BankaIslemVirman.this.c2c.ToKur = BankaIslemVirman.this.c2c.getKur(BankaIslemVirman.this.c2c.getToPB());
                BankaIslemVirman.this.edKhk.setText(kod);
                BankaIslemVirman.this.edKhAdi.setText(String.valueOf(khk.getBanAd()) + " (" + BankaIslemVirman.this.ToPB + ")");
                BankaIslemVirman.this.calcHGT();
                BankaIslemVirman.this.edAck.requestFocus();
                BankaIslemVirman.this.hgtrow.setVisibility(BankaIslemVirman.this.c2c.getHGTvisibility());
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banka_islem_virman);
        FN.initAutoComplete(this, R.id.edAciklama, "banislem");
        setButtons();
        this.c2c = new C2C2C(this);
        this.op = 1;
        Bundle extras = getIntent().getExtras();
        OdmBankaKartlar kart = new OdmBankaKartlar(this);
        this.mdl = extras.getInt("mdl", K.MDL_BANVIRMAN);
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.islemid = extras.getLong("islemid");
            opEditLoad();
            kart.getByBanKod(this.bankod);
        } else {
            kart.getById(extras.getLong("bankartid"));
            FN.getTarihSaat(this, this.edTarih, this.edSaat);
            this.bankod_bakiye = K.Str2BigDecimal(kart.getBakiye(), 0).abs().toString();
            this.FromPB = kart.getPb();
            this.c2c.setFromPB(kart.getPb());
            this.c2c.FromKur = this.c2c.getKur(this.c2c.getFromPB());
        }
        this.lbFromPB.setText(this.FromPB);
        this.lbBanKod.setText(kart.getBanKod());
        this.lbBanAd.setText(String.valueOf(kart.getBanAd()) + " (" + this.FromPB + ")");
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
        this.lbTutar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.BankaIslemVirman.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaIslemVirman.this.edTutar.beginBatchEdit();
                BankaIslemVirman.this.edTutar.setText(BankaIslemVirman.this.bankod_bakiye);
                BankaIslemVirman.this.edTutar.endBatchEdit();
            }
        });
        this.edSonuc.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.BankaIslemVirman.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaIslemVirman.this.c2c.setTutar(BankaIslemVirman.this.edTutar);
                BankaIslemVirman.this.c2c.showDialog(BankaIslemVirman.this, BankaIslemVirman.this.edSonuc);
            }
        });
    }

    private void setButtons() {
        this.hgtrow = (TableRow) findViewById(R.id.trHGT);
        this.hgtrow.setVisibility(8);
        this.edKhk = (Button) findViewById(R.id.edKrsKod);
        this.edKhAdi = (TextView) findViewById(R.id.lbKrsAd);
        this.edSonuc = (TextView) findViewById(R.id.edSonuc);
        this.lbBanKod = (TextView) findViewById(R.id.lbCarKod);
        this.lbBanAd = (TextView) findViewById(R.id.lbCarAd);
        this.lbTutar = (TextView) findViewById(R.id.lbTutar);
        this.lbFromPB = (TextView) findViewById(R.id.lbFromPB);
        this.edAck = (EditText) findViewById(R.id.edAciklama);
        this.edTutar = (EditText) findViewById(R.id.edTutar);
        this.edTarih = (Button) findViewById(R.id.edTarih);
        this.edSaat = (Button) findViewById(R.id.edSaat);
        this.edKhk.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.BankaIslemVirman.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                FN.luBanKart(BankaIslemVirman.this, BankaIslemVirman.this.edKhk, BankaIslemVirman.this.edTutar, BankaIslemVirman.this.khk_brw_ocl);
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.BankaIslemVirman.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BankaIslemVirman.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.BankaIslemVirman.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (BankaIslemVirman.this.op) {
                    case 1:
                        snc = BankaIslemVirman.this.opInsertExecute();
                        break;
                    case 2:
                        snc = BankaIslemVirman.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okislem");
                    BankaIslemVirman.this.setResult(-1, intent);
                    BankaIslemVirman.this.finish();
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
        BankaIslemlerCRUD.doVirmanSaveLinks(this, rowid, this.mdl, K.TarihSaatSql(this.edTarih, this.edSaat), this.edTutar.getText().toString(), this.lbBanKod.getText().toString(), this.c2c.Sonuc.toString(), this.lbBanAd.getText().toString(), this.edAck.getText().toString(), this.edKhk.getText().toString(), 21);
    }

    protected boolean opInsertExecute() {
        OdmBankaIslem k = new OdmBankaIslem(this);
        boolean readyForSave = true;
        if (1 != 0 && K.isNOE(this.edKhk)) {
            K.Error(this, "Giriş Yapılan Hesap Kodu boş olamaz.");
            readyForSave = false;
        }
        if (readyForSave && K.isNOE(this.edTutar)) {
            K.Error(this, "Tutar boş olamaz.");
            readyForSave = false;
        }
        String khk = this.edKhk.getText().toString();
        if (readyForSave) {
            OdmBankaKartlar kart = new OdmBankaKartlar(this);
            if (!kart.getByBanKod(khk)) {
                K.Error(this, "Banka kartı bulunamadı. Hesap kodunu konrtol edin.");
                readyForSave = false;
            }
        }
        if (readyForSave) {
            long rowid = k.KayitEkle(this.mdl, this.lbBanKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 21, this.edKhk.getText().toString(), this.edTarih.getText().toString(), this.edSaat.getText().toString(), this.c2c.toJson());
            doSaveLinks(rowid);
        }
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmBankaIslem k = new OdmBankaIslem(this);
        boolean result = k.KayitDuzelt(this.islemid, this.lbBanKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 21, this.edKhk.getText().toString(), this.edTarih.getText().toString(), this.edSaat.getText().toString(), this.c2c.toJson());
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
        OdmBankaIslem k = new OdmBankaIslem(this);
        OdmBankaKartlar chk = new OdmBankaKartlar(this);
        OdmBankaKartlar kchk = new OdmBankaKartlar(this);
        if (k.getById(this.islemid)) {
            chk.getByBanKod(k.getBanKod());
            kchk.getByBanKod(k.getKarsiHesapKodu());
            this.edKhAdi.setText(String.valueOf(kchk.getBanAd()) + " (" + kchk.getPb() + ")");
            this.FromPB = chk.getPb();
            this.ToPB = kchk.getPb();
            this.bankod = k.getBanKod();
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
            this.bankod_bakiye = this.edTutar.getText().toString();
            return;
        }
        finish();
    }
}
