package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import com.alyansyazilim.database.AciklamaACAdapter;
import com.alyansyazilim.database.AciklamaACDBAdapter;
import com.alyansyazilim.database.OdmBankaKartlar;
import com.alyansyazilim.database.OdmCariKartlar;
import com.alyansyazilim.database.OdmKasaKartlar;
import com.alyansyazilim.database.OdmParaBirimKartlar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* loaded from: classes.dex */
public class FN {
    public static void initAutoComplete(Activity con, int res, String src) {
        final AutoCompleteTextView item_filter = (AutoCompleteTextView) con.findViewById(res);
        AciklamaACDBAdapter mDBAdabter = new AciklamaACDBAdapter(con, src);
        Cursor mItemCursor = mDBAdabter.getStationCursor("");
        con.startManagingCursor(mItemCursor);
        AciklamaACAdapter mCursorAdapter = new AciklamaACAdapter(con, mItemCursor, src);
        item_filter.setAdapter(mCursorAdapter);
        item_filter.setThreshold(1);
        item_filter.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.FN.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                String capital = cursor.getString(cursor.getColumnIndexOrThrow("ack"));
                item_filter.setText(capital);
            }
        });
    }

    public static void luDialog(List<String> list, Activity con, final TextView edit, final TextView nextitem, String emptyErrText, DialogInterface.OnClickListener ocl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        int selecteditem = -1;
        String selectedVal = edit.getText().toString();
        if (list.size() == 0) {
            K.Error(con, emptyErrText);
            return;
        }
        final String[] itemList = new String[list.size()];
        for (int i = 0; i < itemList.length; i++) {
            itemList[i] = list.get(i);
            if (itemList[i].equals(selectedVal)) {
                selecteditem = i;
            }
        }
        if (selecteditem == -1) {
            selecteditem = 0;
        }
        builder.setTitle("Hesap seçin");
        builder.setSingleChoiceItems(itemList, selecteditem, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.FN.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        if (ocl == null) {
            builder.setPositiveButton(R.string.SecStr, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.FN.3
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                    if (selectedPosition > -1) {
                        edit.setText(itemList[selectedPosition]);
                        if (nextitem != null) {
                            nextitem.requestFocus();
                        }
                    }
                    K.loge("select button pressed");
                }
            });
        } else {
            builder.setPositiveButton(R.string.SecStr, ocl);
        }
        builder.setNegativeButton(R.string.VazgecStr, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.FN.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.show();
    }

    public static void luCarKart(Activity con, TextView edit, TextView next, DialogInterface.OnClickListener ocl) {
        OdmCariKartlar b = new OdmCariKartlar(con);
        luDialog(b.getList(), con, edit, next, "Tanımlı cari hesap kartı bulunamadı. Önce hesap ekleyin.", ocl);
    }

    public static void luKasKart(Activity con, TextView edit, TextView next, DialogInterface.OnClickListener ocl) {
        OdmKasaKartlar b = new OdmKasaKartlar(con);
        luDialog(b.getList(), con, edit, next, "Tanımlı kasa kartı bulunamadı. Önce hesap ekleyin.", ocl);
    }

    public static void luBanKart(Activity con, TextView edit, TextView next, DialogInterface.OnClickListener ocl) {
        OdmBankaKartlar b = new OdmBankaKartlar(con);
        luDialog(b.getList(), con, edit, next, "Tanımlı banka kartı bulunamadı. Önce hesap ekleyin.", ocl);
    }

    public static void getTarihSaat(Activity con, TextView tarih, TextView saat) {
        String ts = K.NowSql();
        if (tarih != null) {
            tarih.setText(K.SqlTStrT(ts));
        }
        if (saat != null) {
            saat.setText(K.SqlTStrS(ts));
        }
    }

    public static void onDateChange(Activity con, final TextView v) {
        Date date = K.tarihstr2Date(v.getText().toString());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(1);
        int month = c.get(2);
        int day = c.get(5);
        DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.FN.5
            @Override // android.app.DatePickerDialog.OnDateSetListener
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                v.setText(String.format("%02d.%02d.%04d", Integer.valueOf(selectedDay), Integer.valueOf(selectedMonth + 1), Integer.valueOf(selectedYear)));
            }
        };
        new DatePickerDialog(con, datePickerListener, year, month, day).show();
    }

    public static void onTimeChange(Activity con, final TextView v) {
        Date date = K.saatstr2Date(v.getText().toString());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(11);
        int minute = c.get(12);
        TimePickerDialog.OnTimeSetListener datePickerListener = new TimePickerDialog.OnTimeSetListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.FN.6
            @Override // android.app.TimePickerDialog.OnTimeSetListener
            public void onTimeSet(TimePicker view, int hourOfDay, int minute2) {
                v.setText(String.format("%02d:%02d:00", Integer.valueOf(hourOfDay), Integer.valueOf(minute2)));
            }
        };
        new TimePickerDialog(con, datePickerListener, hour, minute, true).show();
    }

    public static void loadPbCB(Activity activity, Spinner spinner) {
        OdmParaBirimKartlar pb = new OdmParaBirimKartlar(activity);
        pb.addItemsOnSpinner2(spinner);
        pb.closecr();
    }

    public static void loadVarlikTurCB(Activity activity, Spinner spinner) {
        List<String> list = new ArrayList<>();
        list.add(K.VarTur2Str(0));
        list.add(K.VarTur2Str(1));
        list.add(K.VarTur2Str(2));
        ArrayAdapter<String> aAdpt = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, list);
        aAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) aAdpt);
    }
}
