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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import com.alyansyazilim.a_islemler.CariHesapIslemlerCRUD;
import com.alyansyazilim.database.OdmBankaKartlar;
import com.alyansyazilim.database.OdmCariIslem;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.database.OdmKasaKartlar;
import com.alyansyazilim.ilerimuhasebesistemi.C2C2C;
import com.alyansyazilim.ilerimuhasebesistemi.FN;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class CariIslemTahsilat extends Activity {
    private String[] Yontemler;
    private C2C2C c2c;
    private String carkod;
    private String carkod_bakiye;
    private EditText edAck;
    private Button edKhk;
    private Spinner edKht;
    private Button edSaat;
    private Button edSonuc;
    private Button edTarih;
    private EditText edTutar;
    private TableRow hgtrow;
    private long islemid;
    private TextView lbCarAd;
    private TextView lbCarKod;
    private TextView lbFromPB;
    private int mdl;
    protected int op;
    private String FromPB = "";
    private String ToPB = "";
    TextWatcher tw = new TextWatcher() { // from class: com.alyansyazilim.a_islem.CariIslemTahsilat.1
        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            CariIslemTahsilat.this.calcHGT();
        }
    };
    DialogInterface.OnClickListener khk_brw_ocl_k = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemTahsilat.2
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (selectedPosition > -1) {
                String kod = ((AlertDialog) dialog).getListView().getItemAtPosition(selectedPosition).toString();
                CariIslemTahsilat.this.khk_setText_k(kod);
            }
        }
    };
    DialogInterface.OnClickListener khk_brw_ocl_b = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemTahsilat.3
        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            if (selectedPosition > -1) {
                String kod = ((AlertDialog) dialog).getListView().getItemAtPosition(selectedPosition).toString();
                OdmBankaKartlar khk = new OdmBankaKartlar(CariIslemTahsilat.this);
                khk.getByBanKod(kod);
                CariIslemTahsilat.this.FromPB = khk.getPb();
                CariIslemTahsilat.this.c2c.setFromPB(CariIslemTahsilat.this.FromPB);
                CariIslemTahsilat.this.c2c.FromKur = CariIslemTahsilat.this.c2c.getKur(CariIslemTahsilat.this.c2c.getFromPB());
                CariIslemTahsilat.this.lbFromPB_setText(kod);
                CariIslemTahsilat.this.edKhk.setText(kod);
                CariIslemTahsilat.this.calcHGT();
                CariIslemTahsilat.this.edTutar.requestFocus();
                CariIslemTahsilat.this.hgtrow.setVisibility(CariIslemTahsilat.this.c2c.getHGTvisibility());
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_islem_tahsilat);
        FN.initAutoComplete(this, R.id.edAciklama, "carislem");
        setButtons();
        this.c2c = new C2C2C(this);
        this.Yontemler = getResources().getStringArray(R.array.toyontemi);
        ArrayAdapter<String> data = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.Yontemler);
        data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.edKht.setAdapter((SpinnerAdapter) data);
        this.op = 1;
        Bundle extras = getIntent().getExtras();
        OdmCariKartlar kart = new OdmCariKartlar(this);
        this.mdl = extras.getInt("mdl", K.MDL_CARTAHSIL);
        if (this.mdl == 65540) {
            setTitle(getString(R.string.title_activity_cari_odeme));
        }
        if (extras.getInt("islem") == 2) {
            this.op = 2;
            this.islemid = extras.getLong("islemid");
            opEditLoad();
            kart.getByCarKod(this.carkod);
        } else {
            kart.getById(extras.getLong("carkartid"));
            FN.getTarihSaat(this, this.edTarih, this.edSaat);
            this.carkod_bakiye = K.Str2BigDecimal(kart.getBakiye(), 0).abs().toString();
            this.ToPB = kart.getPb();
            this.c2c.setToPB(kart.getPb());
            this.c2c.ToKur = this.c2c.getKur(this.c2c.getToPB());
            khk_setText_k(new OdmCariIslem(this).getTopUsedKod(0));
        }
        this.lbCarKod.setText(kart.getCarKod());
        this.lbCarAd.setText(String.valueOf(kart.getCarAd()) + " (" + this.ToPB + ")");
        setEvents();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lbFromPB_setText(String kod) {
        this.lbFromPB.setText(this.FromPB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void khk_setText_k(String kod) {
        if (!K.isNOE(kod)) {
            OdmKasaKartlar khk = new OdmKasaKartlar(this);
            if (khk.getByKasKod(kod)) {
                this.FromPB = khk.getPb();
            } else {
                this.FromPB = "";
            }
            this.c2c.setFromPB(this.FromPB);
            this.c2c.FromKur = this.c2c.getKur(this.c2c.getFromPB());
            lbFromPB_setText(kod);
            this.edKhk.setText(kod);
            calcHGT();
            this.edTutar.requestFocus();
            this.hgtrow.setVisibility(this.c2c.getHGTvisibility());
        }
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
        TextView lbTutar = (TextView) findViewById(R.id.lbTutar);
        lbTutar.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemTahsilat.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariIslemTahsilat.this.edTutar.beginBatchEdit();
                CariIslemTahsilat.this.edTutar.setText(CariIslemTahsilat.this.carkod_bakiye);
                CariIslemTahsilat.this.edTutar.endBatchEdit();
            }
        });
        this.edSonuc.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemTahsilat.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariIslemTahsilat.this.c2c.setTutar(CariIslemTahsilat.this.edTutar);
                CariIslemTahsilat.this.c2c.showDialog(CariIslemTahsilat.this, CariIslemTahsilat.this.edSonuc);
            }
        });
    }

    private void setButtons() {
        this.hgtrow = (TableRow) findViewById(R.id.trHGT);
        this.hgtrow.setVisibility(8);
        this.edKht = (Spinner) findViewById(R.id.edYontem);
        this.edKhk = (Button) findViewById(R.id.edKrsKod);
        this.edSonuc = (Button) findViewById(R.id.edSonuc);
        this.lbCarKod = (TextView) findViewById(R.id.lbCarKod);
        this.lbCarAd = (TextView) findViewById(R.id.lbCarAd);
        this.lbFromPB = (TextView) findViewById(R.id.lbFromPB);
        this.edAck = (EditText) findViewById(R.id.edAciklama);
        this.edTutar = (EditText) findViewById(R.id.edTutar);
        this.edTarih = (Button) findViewById(R.id.edTarih);
        this.edSaat = (Button) findViewById(R.id.edSaat);
        this.edKhk.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemTahsilat.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                switch (CariIslemTahsilat.this.edKht.getSelectedItemPosition()) {
                    case 0:
                        FN.luKasKart(CariIslemTahsilat.this, CariIslemTahsilat.this.edKhk, CariIslemTahsilat.this.edTutar, CariIslemTahsilat.this.khk_brw_ocl_k);
                        break;
                    case 1:
                        FN.luBanKart(CariIslemTahsilat.this, CariIslemTahsilat.this.edKhk, CariIslemTahsilat.this.edTutar, CariIslemTahsilat.this.khk_brw_ocl_b);
                        break;
                }
            }
        });
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemTahsilat.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                CariIslemTahsilat.this.finish();
            }
        });
        Button btnSave = (Button) findViewById(R.id.btSave);
        btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.a_islem.CariIslemTahsilat.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                boolean snc = false;
                switch (CariIslemTahsilat.this.op) {
                    case 1:
                        snc = CariIslemTahsilat.this.opInsertExecute();
                        break;
                    case 2:
                        snc = CariIslemTahsilat.this.opEditExecute();
                        break;
                }
                if (snc) {
                    Intent intent = new Intent();
                    intent.putExtra("result", "okislem");
                    CariIslemTahsilat.this.setResult(-1, intent);
                    CariIslemTahsilat.this.finish();
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
        CariHesapIslemlerCRUD.doTahsilatSaveLinks(this, rowid, this.mdl, K.TarihSaatSql(this.edTarih, this.edSaat), this.edTutar.getText().toString(), this.lbCarKod.getText().toString(), this.c2c.Sonuc.toString(), this.lbCarAd.getText().toString(), this.edAck.getText().toString(), this.edKhk.getText().toString(), this.edKht.getSelectedItemPosition());
    }

    protected boolean opInsertExecute() {
        OdmCariIslem k = new OdmCariIslem(this);
        boolean readyForSave = true;
        if (1 != 0 && K.isNOE(this.edKhk)) {
            K.Error(this, "Hesap Kodu boş olamaz.");
            readyForSave = false;
        }
        if (readyForSave && K.isNOE(this.edTutar)) {
            K.Error(this, "Tutar boş olamaz.");
            readyForSave = false;
        }
        int kht = this.edKht.getSelectedItemPosition();
        String khk = this.edKhk.getText().toString();
        if (readyForSave && kht == 0) {
            OdmKasaKartlar kart = new OdmKasaKartlar(this);
            if (!kart.getByKasKod(khk)) {
                K.Error(this, "Kasa kartı bulunamadı. Hesap kodunu konrtol edin.");
                readyForSave = false;
            }
        }
        if (readyForSave && kht == 1) {
            OdmBankaKartlar kart2 = new OdmBankaKartlar(this);
            if (!kart2.getByBanKod(khk)) {
                K.Error(this, "Banka kartı bulunamadı. Hesap kodunu konrtol edin.");
                readyForSave = false;
            }
        }
        if (readyForSave) {
            long rowid = k.KayitEkle(this.mdl, this.lbCarKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), this.edKht.getSelectedItemPosition(), this.edKhk.getText().toString(), this.edTarih.getText().toString(), this.edSaat.getText().toString(), this.c2c.toJson());
            doSaveLinks(rowid);
        }
        return readyForSave;
    }

    protected boolean opEditExecute() {
        OdmCariIslem k = new OdmCariIslem(this);
        boolean result = k.KayitDuzelt(this.islemid, this.lbCarKod.getText().toString(), this.edAck.getText().toString(), this.edTutar.getText().toString(), this.edKht.getSelectedItemPosition(), this.edKhk.getText().toString(), this.edTarih.getText().toString(), this.edSaat.getText().toString(), this.c2c.toJson());
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
        if (k.getById(this.islemid)) {
            int khtid = k.getKarsiHesapTuru();
            chk.getByCarKod(k.getCarKod());
            this.ToPB = chk.getPb();
            switch (khtid) {
                case 0:
                    OdmKasaKartlar khkk = new OdmKasaKartlar(this);
                    khkk.getByKasKod(k.getKarsiHesapKodu());
                    this.FromPB = khkk.getPb();
                    break;
                case 1:
                    OdmBankaKartlar khkb = new OdmBankaKartlar(this);
                    khkb.getByBanKod(k.getKarsiHesapKodu());
                    this.FromPB = khkb.getPb();
                    break;
            }
            this.carkod = k.getCarKod();
            this.edAck.setText(k.getAck());
            this.edTutar.setText(k.getTutar());
            this.edTutar.requestFocus();
            this.edKht.setSelection(khtid);
            this.edKhk.setText(k.getKarsiHesapKodu());
            this.edTarih.setText(k.getZamanT());
            this.edSaat.setText(k.getZamanS());
            this.c2c.fromJson(k.getMeta());
            if (this.c2c.version == 0) {
                this.c2c.setFromPB(this.FromPB);
                this.c2c.setToPB(this.ToPB);
                this.c2c.calcBL(this.edTutar);
            }
            this.edSonuc.setText(String.valueOf(this.c2c.getSonucFmt()) + " " + this.ToPB);
            lbFromPB_setText(this.edKhk.getText().toString());
            this.hgtrow.setVisibility(this.c2c.getHGTvisibility());
            this.carkod_bakiye = this.edTutar.getText().toString();
            return;
        }
        finish();
    }
}
