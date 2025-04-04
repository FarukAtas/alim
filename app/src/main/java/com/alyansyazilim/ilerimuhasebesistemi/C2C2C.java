package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.alyansyazilim.database.OdmDovizKartlar;
import java.math.BigDecimal;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class C2C2C {
    public BigDecimal FromKur;
    private String FromPB;
    public boolean ManualResult;
    public BigDecimal Sonuc;
    public BigDecimal ToKur;
    private String ToPB;
    public BigDecimal Tutar;
    public String YerelPB;
    public BigDecimal YpbSonuc;
    private Button btFromKurCl;
    private Button btToKurCl;
    private int c2c_Method;
    private EditText edFromKur;
    private CheckBox edManual;
    private EditText edSonuc;
    private EditText edToKur;
    private EditText edTutar;
    private EditText edYpbSonuc;
    private BigDecimal internalSonuc;
    private TextView lbFark;
    private TextView lbFromKur;
    private TextView lbFromPB;
    private TextView lbToKur;
    private TextView lbToPB;
    private TextView lbYerelPB;
    private TextView lbYpbSonuc;
    private String oldValues;
    TextWatcher tw_kur;
    TextWatcher tw_sonuc;
    private Context vContext;
    public int version;

    public String toJson() {
        JSONObject jObjectData = new JSONObject();
        try {
            jObjectData.put("v", this.version);
            jObjectData.put("m", this.c2c_Method);
            jObjectData.put("k", this.Tutar.toString());
            jObjectData.put("f", this.FromPB);
            jObjectData.put("fk", this.FromKur.toString());
            jObjectData.put("ys", this.YpbSonuc.toString());
            jObjectData.put("y", this.YerelPB);
            jObjectData.put("tk", this.ToKur.toString());
            jObjectData.put("t", this.ToPB);
            jObjectData.put("s", this.Sonuc.toString());
            jObjectData.put("mr", this.ManualResult);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObjectData.toString();
    }

    public void fromJson(String meta) {
        JSONObject jObj = null;
        try {
            JSONObject jObj2 = new JSONObject(meta);
            jObj = jObj2;
        } catch (JSONException e) {
            K.loge("JSON ParserError parsing data " + e.toString());
        }
        this.version = jObj.optInt("v");
        if (this.version == 0) {
            SetDefaults();
        }
        if (this.version == 1) {
            this.c2c_Method = jObj.optInt("m");
            this.Tutar = Str2BD0(jObj.optString("k"));
            this.FromPB = jObj.optString("f");
            this.FromKur = Str2BD1(jObj.optString("fk"));
            this.YpbSonuc = Str2BD0(jObj.optString("ys"));
            this.YerelPB = jObj.optString("y");
            this.ToKur = Str2BD1(jObj.optString("tk"));
            this.ToPB = jObj.optString("t");
            this.Sonuc = Str2BD0(jObj.optString("s"));
            this.ManualResult = jObj.optBoolean("mr");
        }
    }

    private void SetDefaults() {
        this.c2c_Method = 0;
        this.Tutar = new BigDecimal(0);
        this.FromPB = "TL";
        this.FromKur = new BigDecimal(1);
        this.YerelPB = "TL";
        this.YpbSonuc = new BigDecimal(0);
        this.ToPB = "TL";
        this.ToKur = new BigDecimal(1);
        this.Sonuc = new BigDecimal(0);
    }

    public C2C2C(Context con) {
        this.tw_kur = new TextWatcher() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.1
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                C2C2C.this.calcUI();
            }
        };
        this.tw_sonuc = new TextWatcher() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.2
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                C2C2C.this.lbFarkDisplay();
            }
        };
        this.version = 1;
        this.vContext = con;
        SetDefaults();
    }

    public C2C2C(Context con, String json) {
        this.tw_kur = new TextWatcher() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.1
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                C2C2C.this.calcUI();
            }
        };
        this.tw_sonuc = new TextWatcher() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.2
            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                C2C2C.this.lbFarkDisplay();
            }
        };
        this.vContext = con;
        fromJson(json);
    }

    public BigDecimal getTutar() {
        return this.Tutar;
    }

    public void setTutar(TextView tutar) {
        this.Tutar = Str2BD0(tutar);
    }

    public boolean isDiferent() {
        return this.c2c_Method == 0;
    }

    public int getHGTvisibility() {
        return this.c2c_Method == 0 ? 8 : 0;
    }

    public String getFromPB() {
        return this.FromPB;
    }

    public void setFromPB(String fromPB) {
        this.FromPB = fromPB;
        calcC2CMethod();
    }

    public String getToPB() {
        return this.ToPB;
    }

    public void setToPB(String toPB) {
        this.ToPB = toPB;
        calcC2CMethod();
    }

    public void calcC2CMethod() {
        this.c2c_Method = 0;
        this.c2c_Method = (this.FromPB.compareTo(this.YerelPB) != 0 ? 1 : 0) + this.c2c_Method;
        this.c2c_Method = (this.YerelPB.compareTo(this.ToPB) != 0 ? 2 : 0) + this.c2c_Method;
        this.c2c_Method = this.FromPB.compareTo(this.ToPB) != 0 ? this.c2c_Method : 0;
    }

    public BigDecimal getKur(String PB) {
        if (PB.compareTo("TL") == 0) {
            return new BigDecimal(1);
        }
        OdmDovizKartlar d = new OdmDovizKartlar(this.vContext);
        d.getByKod(PB);
        if (d.moveToFirst()) {
            return new BigDecimal(d.getSatis());
        }
        return new BigDecimal(0);
    }

    public static BigDecimal Str2BD1(String ts) {
        return K.Str2BigDecimal(ts, 1);
    }

    public static BigDecimal Str2BD1(TextView tv) {
        return K.Str2BigDecimal(tv.getText().toString(), 1);
    }

    public static BigDecimal Str2BD0(String ts) {
        return K.Str2BigDecimal(ts, 0);
    }

    public static BigDecimal Str2BD0(TextView tv) {
        return K.Str2BigDecimal(tv.getText().toString(), 0);
    }

    private BigDecimal calc(String tutar, String fromKur, String toKur) {
        BigDecimal vFromKur = Str2BD0(fromKur);
        BigDecimal vToKur = Str2BD0(toKur);
        this.Tutar = Str2BD0(tutar);
        switch (this.c2c_Method) {
            case 0:
                this.internalSonuc = this.Tutar;
                break;
            case 1:
                this.internalSonuc = this.Tutar.multiply(vFromKur);
                break;
            case 2:
                if (vToKur.doubleValue() == 0.0d) {
                    this.internalSonuc = new BigDecimal(0);
                    break;
                } else {
                    this.internalSonuc = this.Tutar.divide(vToKur, 2, 3);
                    break;
                }
            case 3:
                this.YpbSonuc = this.Tutar.multiply(vFromKur);
                if (vToKur.doubleValue() == 0.0d) {
                    this.internalSonuc = new BigDecimal(0);
                    break;
                } else {
                    this.internalSonuc = this.YpbSonuc.divide(vToKur, 2, 3);
                    break;
                }
        }
        return this.internalSonuc;
    }

    private BigDecimal calc(TextView tutar, TextView fromKur, TextView toKur) {
        return calc(tutar.getText().toString(), fromKur.getText().toString(), toKur.getText().toString());
    }

    private BigDecimal calc(TextView tutar, BigDecimal fromKur, BigDecimal toKur) {
        return calc(tutar.getText().toString(), fromKur.toString(), toKur.toString());
    }

    public String getSonucFmt() {
        return K.BigDecimalFmt(this.Sonuc);
    }

    public String calcBL(TextView tutar) {
        this.Sonuc = calc(tutar, this.FromKur, this.ToKur).setScale(2, 3);
        return getSonucFmt();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void calcUI() {
        BigDecimal tmp = calc(this.edTutar, this.edFromKur, this.edToKur);
        this.Sonuc = tmp.setScale(2, 3);
        this.edSonuc.setText(this.Sonuc.toString());
        this.edYpbSonuc.setText(K.BigDecimalFmt(this.YpbSonuc.setScale(2, 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lbFarkDisplay() {
        BigDecimal ints = this.internalSonuc.setScale(2, 3);
        BigDecimal fark = Str2BD0(this.edSonuc).subtract(ints);
        int yon = fark.compareTo(Str2BD0("0"));
        if (this.ManualResult) {
            if (yon == 0) {
                this.lbFark.setText("");
                return;
            } else if (yon > 0) {
                this.lbFark.setText("Hesaplan " + K.BigDecimalFmt(ints) + " sonuçtan " + fark.toString() + " fazla");
                return;
            } else {
                this.lbFark.setText("Hesaplan " + K.BigDecimalFmt(ints) + " sonuçtan " + fark.negate().toString() + " eksik");
                return;
            }
        }
        this.lbFark.setText("");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveToClass() {
        this.version = 1;
        this.FromKur = Str2BD0(this.edFromKur);
        this.ToKur = Str2BD0(this.edToKur);
        if (this.ManualResult) {
            this.Sonuc = Str2BD0(this.edSonuc);
        }
    }

    private void loadFromClass() {
        this.edManual.setChecked(this.ManualResult);
        this.edTutar.setText(this.Tutar.toString());
        this.edFromKur.setText(this.FromKur.toString());
        this.edYpbSonuc.setText(this.YpbSonuc.toString());
        this.edToKur.setText(this.ToKur.toString());
        this.edSonuc.setText(this.Sonuc.toString());
        this.lbFromPB.setText(getFromPB());
        this.lbYerelPB.setText(this.YerelPB);
        this.lbToPB.setText(getToPB());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUI() {
        this.ManualResult = this.edManual.isChecked();
        this.edTutar.setEnabled(false);
        this.edYpbSonuc.setEnabled(false);
        this.edFromKur.setEnabled(!this.ManualResult);
        this.btFromKurCl.setEnabled(!this.ManualResult);
        this.edToKur.setEnabled(!this.ManualResult);
        this.btToKurCl.setEnabled(!this.ManualResult);
        this.edSonuc.setEnabled(this.ManualResult);
        if ((this.c2c_Method & 1) == 1) {
            this.edFromKur.setVisibility(0);
            this.lbFromKur.setVisibility(0);
            this.btFromKurCl.setVisibility(0);
        } else {
            this.edFromKur.setVisibility(8);
            this.lbFromKur.setVisibility(8);
            this.btFromKurCl.setVisibility(8);
        }
        if (this.c2c_Method == 3) {
            this.lbYpbSonuc.setVisibility(0);
            this.edYpbSonuc.setVisibility(0);
            this.lbYerelPB.setVisibility(0);
        } else {
            this.lbYpbSonuc.setVisibility(8);
            this.edYpbSonuc.setVisibility(8);
            this.lbYerelPB.setVisibility(8);
        }
        if ((this.c2c_Method & 2) == 2) {
            this.edToKur.setVisibility(0);
            this.lbToKur.setVisibility(0);
            this.btToKurCl.setVisibility(0);
        } else {
            this.edToKur.setVisibility(8);
            this.lbToKur.setVisibility(8);
            this.btToKurCl.setVisibility(8);
        }
        if (this.ManualResult) {
            this.edSonuc.requestFocus();
            this.edSonuc.addTextChangedListener(this.tw_sonuc);
            this.edFromKur.removeTextChangedListener(this.tw_kur);
            this.edToKur.removeTextChangedListener(this.tw_kur);
            calc(this.edTutar, this.edFromKur, this.edToKur);
        } else {
            if (this.edFromKur.getVisibility() == 0) {
                this.edFromKur.requestFocus();
            } else {
                this.edToKur.requestFocus();
            }
            this.edFromKur.addTextChangedListener(this.tw_kur);
            this.edToKur.addTextChangedListener(this.tw_kur);
            this.edSonuc.removeTextChangedListener(this.tw_sonuc);
            calcUI();
        }
        lbFarkDisplay();
    }

    public void showDialog(Activity con, final TextView sonucFld) {
        this.oldValues = toJson();
        LayoutInflater factory = LayoutInflater.from(con);
        View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, (ViewGroup) null);
        this.edTutar = (EditText) textEntryView.findViewById(R.id.edTutar);
        this.lbFromPB = (TextView) textEntryView.findViewById(R.id.lbFromPB);
        this.lbFromKur = (TextView) textEntryView.findViewById(R.id.lbFromKur);
        this.edFromKur = (EditText) textEntryView.findViewById(R.id.edFromKur);
        this.btFromKurCl = (Button) textEntryView.findViewById(R.id.btFromKurCl);
        this.lbYpbSonuc = (TextView) textEntryView.findViewById(R.id.lbAraSonuc);
        this.edYpbSonuc = (EditText) textEntryView.findViewById(R.id.edAraSonuc);
        this.lbYerelPB = (TextView) textEntryView.findViewById(R.id.lbYerelPB);
        this.lbToKur = (TextView) textEntryView.findViewById(R.id.lbToKur);
        this.edToKur = (EditText) textEntryView.findViewById(R.id.edToKur);
        this.btToKurCl = (Button) textEntryView.findViewById(R.id.btToKurCl);
        this.lbFark = (TextView) textEntryView.findViewById(R.id.lbFark);
        this.edSonuc = (EditText) textEntryView.findViewById(R.id.edSonuc);
        this.lbToPB = (TextView) textEntryView.findViewById(R.id.lbToPB);
        this.edManual = (CheckBox) textEntryView.findViewById(R.id.edManualInput);
        this.edSonuc.removeTextChangedListener(this.tw_sonuc);
        loadFromClass();
        calcC2CMethod();
        setUI();
        this.btFromKurCl.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                C2C2C.this.edFromKur.setText("");
                C2C2C.this.edFromKur.requestFocus();
            }
        });
        this.btToKurCl.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                C2C2C.this.edToKur.setText("");
                C2C2C.this.edToKur.requestFocus();
            }
        });
        this.edManual.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                C2C2C.this.setUI();
            }
        });
        AlertDialog ad = new AlertDialog.Builder(con).setTitle(String.valueOf(this.Tutar.toString()) + " " + getFromPB() + " -> " + getToPB()).setView(textEntryView).setPositiveButton(R.string.TamamStr, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                if (sonucFld != null) {
                    sonucFld.setText(String.valueOf(K.BigDecimalFmt(C2C2C.Str2BD0(C2C2C.this.edSonuc))) + " " + C2C2C.this.ToPB);
                }
                C2C2C.this.saveToClass();
            }
        }).setNegativeButton(R.string.IptalStr, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.C2C2C.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
                C2C2C.this.fromJson(C2C2C.this.oldValues);
            }
        }).create();
        ad.show();
    }
}
