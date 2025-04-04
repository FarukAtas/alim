package com.alyansyazilim.ilerimuhasebesistemi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;
import com.alyansyazilim.a_islemler.BankaIslemlerCRUD;
import com.alyansyazilim.a_islemler.CariHesapIslemlerCRUD;
import com.alyansyazilim.a_islemler.KasaIslemlerCRUD;
import com.embarcadero.javaandroid.DBXDefaultFormatter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
/* loaded from: classes.dex */
public class K {
    public static final int BANTHS_KHT_BANKA = 21;
    public static final int CARTHS_KHT_BANKA = 1;
    public static final int CARTHS_KHT_CARI = 21;
    public static final int CARTHS_KHT_KASA = 0;
    public static String ConnectID = null;
    public static int DbType = 0;
    public static final int ICE_CREAM_SANDWICH_MR1 = 15;
    public static final int KASTHS_KHT_KASA = 21;
    public static final int MDL_BANCEKILEN = 66050;
    public static final int MDL_BANVIRMAN = 66051;
    public static final int MDL_BANYATAN = 66049;
    public static final int MDL_CARALCKD = 65539;
    public static final int MDL_CARBORCD = 65538;
    public static final int MDL_CARODEME = 65540;
    public static final int MDL_CARTAHSIL = 65537;
    public static final int MDL_CARVIRMAN = 65541;
    public static final int MDL_KASODEME = 65794;
    public static final int MDL_KASTAHSIL = 65793;
    public static final int MDL_KASVIRMAN = 65795;
    public static final int MDL_NONE = 0;
    public static final int MDL_VAR_ARAC = 0;
    public static final int MDL_VAR_HISSE = 2;
    public static final int MDL_VAR_TASINMAZ = 1;
    public static String MchNm = null;
    public static final int OP_DELETE = 3;
    public static final int OP_EDIT = 2;
    public static final int OP_INSERT = 1;
    public static final int SHOW_SUBACTIVITY = 1;
    public static String Server = null;
    public static int SrvIdx = 0;
    public static int SrvType = 0;
    public static final String TBL_CARKART = "carkart";
    public static final String TBL_DBINFO = "dbinfo";
    public static final String TBL_DVZKART = "doviz";
    public static final int WSS_BACKUPNEED = 8;
    public static final int WSS_ERROR = 128;
    public static final int WSS_MESSAGE = 32;
    public static final int WSS_NORMAL = 1;
    public static final int WSS_SDF = 64;
    public static final int WSS_UNREGISTERED = 2;
    public static final int WSS_UPGRADENEED = 4;
    public static String idColumn;
    public static String vAdi;
    public static TextView vAdiTxt;
    public static String vAndroidID;
    public static Context vAppContext = null;
    public static String vHash;
    public static String vMainScreenEur;
    public static String vMainScreenUsd;
    public static String vVersionName;

    public K() {
        loge(vAppContext.toString());
    }

    public static void setAppContext(Context con) {
        vAppContext = con;
    }

    public static Context getAppContext() {
        return vAppContext;
    }

    public static void doIslemEdit(Activity act, int srcmdl, long srcid) {
        switch (srcmdl) {
            case MDL_CARTAHSIL /* 65537 */:
            case MDL_CARBORCD /* 65538 */:
            case MDL_CARALCKD /* 65539 */:
            case MDL_CARODEME /* 65540 */:
            case MDL_CARVIRMAN /* 65541 */:
                CariHesapIslemlerCRUD.doIslemEdit(act, 2, srcmdl, srcid);
                break;
            case MDL_KASTAHSIL /* 65793 */:
            case MDL_KASODEME /* 65794 */:
            case MDL_KASVIRMAN /* 65795 */:
                KasaIslemlerCRUD.doIslemEdit(act, 2, srcmdl, srcid);
                break;
            case MDL_BANYATAN /* 66049 */:
            case MDL_BANCEKILEN /* 66050 */:
            case MDL_BANVIRMAN /* 66051 */:
                BankaIslemlerCRUD.doIslemEdit(act, 2, srcmdl, srcid);
                break;
        }
    }

    public static void doIslemSil(Activity act, int srcmdl, long srcid) {
        switch (srcmdl) {
            case MDL_CARTAHSIL /* 65537 */:
            case MDL_CARBORCD /* 65538 */:
            case MDL_CARALCKD /* 65539 */:
            case MDL_CARODEME /* 65540 */:
            case MDL_CARVIRMAN /* 65541 */:
                CariHesapIslemlerCRUD.doIslemSil(act, srcmdl, srcid);
                break;
            case MDL_KASTAHSIL /* 65793 */:
            case MDL_KASODEME /* 65794 */:
            case MDL_KASVIRMAN /* 65795 */:
                KasaIslemlerCRUD.doIslemSil(act, srcmdl, srcid);
                break;
            case MDL_BANYATAN /* 66049 */:
            case MDL_BANCEKILEN /* 66050 */:
            case MDL_BANVIRMAN /* 66051 */:
                BankaIslemlerCRUD.doIslemSil(act, srcmdl, srcid);
                break;
        }
    }

    public static String Mdl2Str(int mdl) {
        Context con = vAppContext;
        switch (mdl) {
            case MDL_CARTAHSIL /* 65537 */:
                return con.getString(R.string.TahsilatStr);
            case MDL_CARBORCD /* 65538 */:
                return con.getString(R.string.BorclandirStr);
            case MDL_CARALCKD /* 65539 */:
                return con.getString(R.string.AlacaklandirStr);
            case MDL_CARODEME /* 65540 */:
                return con.getString(R.string.OdemeStr);
            case MDL_CARVIRMAN /* 65541 */:
                return con.getString(R.string.VirmanStr);
            case MDL_KASTAHSIL /* 65793 */:
                return con.getString(R.string.TahsilatStr);
            case MDL_KASODEME /* 65794 */:
                return con.getString(R.string.OdemeStr);
            case MDL_KASVIRMAN /* 65795 */:
                return con.getString(R.string.DegisimStr);
            case MDL_BANYATAN /* 66049 */:
                return con.getString(R.string.YatirilanStr);
            case MDL_BANCEKILEN /* 66050 */:
                return con.getString(R.string.CekilenStr);
            case MDL_BANVIRMAN /* 66051 */:
                return con.getString(R.string.VirmanStr);
            default:
                return "****";
        }
    }

    public static String VarTur2Str(int mdl) {
        switch (mdl) {
            case 0:
                return "Araç";
            case 1:
                return "Gayrimenkul";
            case 2:
                return "Hisse";
            default:
                return "****";
        }
    }

    public static String Now_2(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
    }

    public static String Now_2() {
        return Now_2(new Date());
    }

    public static String DateSql(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String TarihSaatSql(String tarih, String saat) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(tarihstr2Date(tarih));
        Calendar calendar2 = Calendar.getInstance();
        if (saat == null) {
            calendar2.setTime(saatstr2Date("00:00:00"));
        } else {
            calendar2.setTime(saatstr2Date(saat));
        }
        calendar1.set(11, calendar2.get(11));
        calendar1.set(12, calendar2.get(12));
        return DateSql(calendar1.getTime());
    }

    public static String TarihSaatSql(TextView tarih, TextView saat) {
        return TarihSaatSql(tarih.getText().toString(), saat.getText().toString());
    }

    public static String NowSql() {
        return DateSql(new Date());
    }

    public static Date str2Date(String ts, String fmt) {
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        try {
            Date date = format.parse(ts);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date ts2Date(String ts) {
        return str2Date(ts, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date saatstr2Date(String ts) {
        return str2Date(ts, DBXDefaultFormatter.TIMEFORMAT_WO_MS);
    }

    public static Date tarihstr2Date(String ts) {
        return str2Date(ts, "dd.MM.yyyy");
    }

    public static String SqlTSmd(String ts) {
        if (ts == null) {
            return null;
        }
        return ts.length() > 9 ? String.valueOf(ts.substring(8, 10)) + "/" + ts.substring(5, 7) : "";
    }

    public static String SqlTStrT(String ts) {
        if (ts == null || ts.length() == 0) {
            return null;
        }
        return ts.length() > 9 ? String.valueOf(ts.substring(8, 10)) + "." + ts.substring(5, 7) + "." + ts.substring(0, 4) : "";
    }

    public static String SqlTStrS(String ts) {
        if (ts == null || ts.length() == 0) {
            return null;
        }
        return ts.length() > 18 ? ts.substring(11, 19) : "";
    }

    public static String ParaYazKur(Double AVal) {
        if (AVal.doubleValue() == 0.0d) {
            return null;
        }
        return String.format("%,5.4f", AVal);
    }

    public static String ParaYaz(Double AVal) {
        return AVal.doubleValue() == 0.0d ? "" : String.format("%,9.2f", AVal);
    }

    public static String ParaYaz(BigDecimal bk) {
        return ParaYaz(Double.valueOf(bk.doubleValue()));
    }

    public static String ParaYaz(String ts) {
        if (isNOE(ts)) {
            return null;
        }
        return ParaYaz(new BigDecimal(ts));
    }

    public static BigDecimal Str2BigDecimal(String str, int defaultvalue) {
        BigDecimal ret = new BigDecimal(defaultvalue);
        if (!isNOE(str)) {
            try {
                return new BigDecimal(str);
            } catch (Exception e) {
                return ret;
            }
        }
        return ret;
    }

    public static String BigDecimalFmt(BigDecimal bk) {
        Double AVal = Double.valueOf(bk.doubleValue());
        return AVal.doubleValue() == 0.0d ? "" : String.format("%,2.2f", AVal);
    }

    public static String i2s(long intval) {
        return new StringBuilder().append(intval).toString();
    }

    public static void YesNo(Context context, String msg, DialogInterface.OnClickListener oclPositive, DialogInterface.OnClickListener oclNegative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Dikkat");
        builder.setMessage(msg);
        builder.setPositiveButton("Evet", oclPositive);
        builder.setNegativeButton("Hayır", oclNegative);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    public static void YesNo(Context context, String msg, DialogInterface.OnClickListener ocl) {
        YesNo(context, msg, ocl, ocl);
    }

    public static void YesNoCancel(Context context, String msg, DialogInterface.OnClickListener ocl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Dikkat");
        builder.setMessage(msg);
        builder.setPositiveButton("Evet", ocl);
        builder.setNegativeButton("Hayır", ocl);
        builder.setNeutralButton("İptal", ocl);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    public static void MsgBox(Context context, String msg, String title, int iconid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.K.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setIcon(iconid);
        builder.show();
    }

    public static void Warn(Context context, String msg) {
        MsgBox(context, msg, "Dikkat", android.R.drawable.ic_dialog_alert);
    }

    public static void Info(Context context, String msg) {
        MsgBox(context, msg, "Bilgi", android.R.drawable.ic_dialog_info);
    }

    public static void Error(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Hata");
        builder.setMessage(msg);
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.K.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    public static boolean isNOE(String str) {
        return str == null || str.length() == 0;
    }

    public static String concat(String str1, String str2, String sep) {
        if (isNOE(str1) && isNOE(str2)) {
            return "";
        }
        if (isNOE(str1) || isNOE(str2)) {
            return isNOE(str1) ? str2 : str1;
        }
        return String.valueOf(str1) + sep + str2;
    }

    public static void loge(String msg) {
    }

    public static boolean isNOE(TextView edit) {
        return isNOE(edit.getText().toString());
    }

    public static String getAndroidID() {
        return vAndroidID;
    }

    public static void setAndroidID(String android_id) {
        vAndroidID = android_id;
    }

    public static String getVersionName() {
        return vVersionName;
    }

    public static void setVersionName(String versionName) {
        vVersionName = versionName;
    }

    public static void toast(Context con, String msg) {
        Toast.makeText(con, msg, 1).show();
    }
}
