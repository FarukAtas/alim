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
import com.alyansyazilim.a_islemler.KasaIslemlerCRUD;
import com.alyansyazilim.database.OdmKasaIslem;
import com.alyansyazilim.database.OdmKasaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.C2C2C;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class KasaIslemVirman extends Activity {
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
    private String kaskod;
    private String kaskod_bakiye;
    private TextView lbFromPB;
    private TextView lbKasAd;
    private TextView lbKasKod;
    private TextView lbTutar;
    private int mdl;
    protected int op;
    private String FromPB = "";
    private String ToPB = "";
    View.OnKeyListener okl = new View.OnKeyListener() { // from class: com.alyansyazilim.a_islem.KasaIslemVirman.1
        @Override // android.view.View.OnKeyListener
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            KasaIslemVirman.this.calcHGT();
            return false;
        }
    };
    TextWatcher tw = new TextWatcher() { // from class: com.alyansyazilim.a_islem.KasaIslemVirman.2
        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            KasaIslemVirman.this.calcHGT();
        }
    };
    DialogInterface.OnClickListener khk_brw_ocl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_islem.KasaIslemVirman.3
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (selectedPosition > -1) {
                String kod = ((AlertDialog) dialog).getListView().getItemAtPosition(selectedPosition).toString();
                OdmKasaKartlar khk = new OdmKasaKartlar(KasaIslemVirman.this);
                khk.getByKasKod(kod);
                KasaIslemVirman.this.ToPB = khk.getPb();
                KasaIslemVirman.this.c2c.setToPB(KasaIslemVirman.this.ToPB);
                KasaIslemVirman.this.c2c.ToKur = KasaIslemVirman.this.c2c.getKur(KasaIslemVirman.this.c2c.getToPB());
                KasaIslemVirman.this.edKhk.setText(kod);
                KasaIslemVirman.this.edKhAdi.setText(String.valueOf(khk.getKasAd()) + " (" + KasaIslemVirman.this.ToPB + ")");
                KasaIslemVirman.this.calcHGT();
                KasaIslemVirman.this.edAck.requestFocus();
                KasaIslemVirman.this.hgtrow.setVisibility(KasaIslemVirman.this.c2c.getHGTvisibility());
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa_islem_virman);
        FN.initAutoComplete(this, R.id.edAciklama, "kasislem");
        setButtons();
        this.c2c = new C2C2C(this);
        this.op = 1;
        Bundle extras = getIntent().getExtras();
        OdmKasaKartlar kart = new OdmKasaKartlar(this);
        this.mdl = extras.getInt("mdl", K.MDL_KASVIRMAN);
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.islemid = extras.getLong("islemid");
            opEditLoad();
            kart.getByKasKod(this.kaskod);
        } else {
            kart.getById(extras.getLong("kaskartid"));
            FN.getTarihSaat(this, this.edTarih, this.edSaat);
            this.kaskod_bakiye = K.Str2BigDecimal(kart.getBakiye(), 0).abs().toString();
            this.FromPB = kart.getPb();
            this.c2c.setFromPB(kart.getPb());
            this.c2c.FromKur = this.c2c.getKur(this.c2c.getFromPB());
        }
        this.lbFromPB.setText(this.FromPB);
        this.lbKasKod.setText(kart.getKasKod());
        this.lbKasAd.setText(String.valueOf(kart.getKasAd()) + " (" + this.FromPB + ")");
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
        this.lbTutar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.KasaIslemVirman.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaIslemVirman.this.edTutar.beginBatchEdit();
                KasaIslemVirman.this.edTutar.setText(KasaIslemVirman.this.kaskod_bakiye);
                KasaIslemVirman.this.edTutar.endBatchEdit();
            }
        });
        this.edSonuc.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.KasaIslemVirman.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaIslemVirman.this.c2c.setTutar(KasaIslemVirman.this.edTutar);
                KasaIslemVirman.this.c2c.showDialog(KasaIslemVirman.this, KasaIslemVirman.this.edSonuc);
            }
        });
    }

    private void setButtons() {
        this.hgtrow = (TableRow) findViewById(R.id.trHGT);
        this.hgtrow.setVisibility(8);
        this.edKhk = (Button) findViewById(R.id.edKrsKod);
        this.edKhAdi = (TextView) findViewById(R.id.lbKrsAd);
        this.edSonuc = (TextView) findViewById(R.id.edSonuc);
        this.lbKasKod = (TextView) findViewById(R.id.lbCarKod);
        this.lbKasAd = (TextView) findViewById(R.id.lbCarAd);
        this.lbTutar = (TextView) findViewById(R.id.lbTutar);
        this.lbFromPB = (TextView) findViewById(R.id.lbFromPB);
        this.edAck = (EditText) findViewById(R.id.edAciklama);
        this.edTutar = (EditText) findViewById(R.id.edTutar);
        this.edTarih = (Button) findViewById(R.id.edTarih);
        this.edSaat = (Button) findViewById(R.id.edSaat);
        this.edKhk.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.KasaIslemVirman.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                FN.luKasKart(KasaIslemVirman.this, KasaIslemVirman.this.edKhk, KasaIslemVirman.this.edTutar, KasaIslemVirman.this.khk_brw_ocl);
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.KasaIslemVirman.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                KasaIslemVirman.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.KasaIslemVirman.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (KasaIslemVirman.this.op) {
                    case 1:
                        snc = KasaIslemVirman.this.opInsertExecute();
                        break;
                    case 2:
                        snc = KasaIslemVirman.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okislem");
                    KasaIslemVirman.this.setResult(-1, intent);
                    KasaIslemVirman.this.finish();
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
        KasaIslemlerCRUD.doVirmanSaveLinks(this, rowid, this.mdl, K.TarihSaatSql(this.edTarih, this.edSaat), this.edTutar.getText().toString(), this.lbKasKod.getText().toString(), this.c2c.Sonuc.toString(), this.lbKasAd.getText().toString(), this.edAck.getText().toString(), this.edKhk.getText().toString(), 21);
    }

    protected boolean opInsertExecute() {
        OdmKasaIslem k = new OdmKasaIslem(this);
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
            OdmKasaKartlar kart = new OdmKasaKartlar(this);
            if (!kart.getByKasKod(khk)) {
                K.Error(this, "Kasa kartı bulunamadı. Hesap kodunu konrtol edin.");
                readyForSave = false;
            }
        }
        if (readyForSave) {
            long rowid = k.KayitEkle(this.mdl, this.lbKasKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 21, this.edKhk.getText().toString(), this.edTarih.getText().toString(), this.edSaat.getText().toString(), this.c2c.toJson());
            doSaveLinks(rowid);
        }
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmKasaIslem k = new OdmKasaIslem(this);
        boolean result = k.KayitDuzelt(this.islemid, this.lbKasKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), 21, this.edKhk.getText().toString(), this.edTarih.getText().toString(), this.edSaat.getText().toString(), this.c2c.toJson());
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
        OdmKasaIslem k = new OdmKasaIslem(this);
        OdmKasaKartlar chk = new OdmKasaKartlar(this);
        OdmKasaKartlar kchk = new OdmKasaKartlar(this);
        if (k.getById(this.islemid)) {
            chk.getByKasKod(k.getKasKod());
            kchk.getByKasKod(k.getKarsiHesapKodu());
            this.edKhAdi.setText(String.valueOf(kchk.getKasAd()) + " (" + kchk.getPb() + ")");
            this.FromPB = chk.getPb();
            this.ToPB = kchk.getPb();
            this.kaskod = k.getKasKod();
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
            this.kaskod_bakiye = this.edTutar.getText().toString();
            return;
        }
        finish();
    }
}
