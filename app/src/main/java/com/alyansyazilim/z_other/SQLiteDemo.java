package com.alyansyazilim.z_other;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.alyansyazilim.database.Veritabani;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class SQLiteDemo extends ListActivity {
    private static final int DIALOG_ID = 100;
    private static final String[] fields = {"_id", "carkod", "adi", "bakiye"};
    private CursorAdapter dataSource;
    private SQLiteDatabase database;
    private View entryView;
    private EditText firstNameEditor;
    private EditText lastNameEditor;
    private Integer updateid;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Veritabani helper = new Veritabani(this);
        this.database = helper.getWritableDatabase();
        Cursor data = this.database.query(K.TBL_CARKART, fields, null, null, null, null, null);
        this.dataSource = new SimpleCursorAdapter(this, R.layout.activity_cari_hesapkartlari_row, data, fields, new int[]{0, R.id.first, R.id.last, R.id.bky});
        ListView view = getListView();
        view.setHeaderDividersEnabled(true);
        view.addHeaderView(getLayoutInflater().inflate(R.layout.activity_cari_hesapkartlari_row, (ViewGroup) null));
        setListAdapter(this.dataSource);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.z_other.SQLiteDemo.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                v.showContextMenu();
            }
        });
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, DIALOG_ID, 1, R.string.addItem);
        return true;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId() == DIALOG_ID) {
            showDialog(DIALOG_ID);
            return true;
        }
        return true;
    }

    @Override // android.app.Activity
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        this.entryView = getLayoutInflater().inflate(R.layout.entry, (ViewGroup) null);
        builder.setView(this.entryView);
        this.firstNameEditor = (EditText) this.entryView.findViewById(R.id.firstName);
        this.lastNameEditor = (EditText) this.entryView.findViewById(R.id.lastName);
        builder.setTitle(R.string.addDialogTitle);
        builder.setPositiveButton(R.string.addItem, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.z_other.SQLiteDemo.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ContentValues values = new ContentValues();
                values.put("carkod", SQLiteDemo.this.firstNameEditor.getText().toString());
                values.put("adi", SQLiteDemo.this.lastNameEditor.getText().toString());
                SQLiteDemo.this.database.insert(K.TBL_CARKART, null, values);
                SQLiteDemo.this.dataSource.getCursor().requery();
            }
        });
        builder.setNegativeButton(R.string.cancelItem, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.z_other.SQLiteDemo.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    protected Dialog onCreateDialogEdit(int id, String kodu, String adi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        this.entryView = getLayoutInflater().inflate(R.layout.entry, (ViewGroup) null);
        builder.setView(this.entryView);
        this.firstNameEditor = (EditText) this.entryView.findViewById(R.id.firstName);
        this.lastNameEditor = (EditText) this.entryView.findViewById(R.id.lastName);
        this.updateid = Integer.valueOf(id);
        this.firstNameEditor.setText(kodu);
        this.lastNameEditor.setText(adi);
        builder.setTitle(R.string.addDialogTitle);
        builder.setPositiveButton(R.string.addItem, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.z_other.SQLiteDemo.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ContentValues values = new ContentValues();
                values.put("carkod", SQLiteDemo.this.firstNameEditor.getText().toString());
                values.put("adi", SQLiteDemo.this.lastNameEditor.getText().toString());
                SQLiteDemo.this.database.update(K.TBL_CARKART, values, "_id=" + String.format("%d", SQLiteDemo.this.updateid), null);
                SQLiteDemo.this.dataSource.getCursor().requery();
            }
        });
        builder.setNegativeButton(R.string.cancelItem, new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.z_other.SQLiteDemo.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
