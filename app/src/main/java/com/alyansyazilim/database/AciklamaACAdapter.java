package com.alyansyazilim.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.alyansyazilim.ilerimuhasebesistemi.R;

/* loaded from: classes.dex */
public class AciklamaACAdapter extends CursorAdapter {
    private AciklamaACDBAdapter dbAdapter;

    public AciklamaACAdapter(Context context, Cursor c, String tblnm) {
        super(context, c);
        this.dbAdapter = null;
        this.dbAdapter = new AciklamaACDBAdapter(context, tblnm);
        this.dbAdapter.open();
    }

    @Override // android.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        String item = createItem(cursor);
        ((TextView) view).setText(item);
    }

    @Override // android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        TextView view = (TextView) inflater.inflate(R.layout.simple_list_item_1a, parent, false);
        String item = createItem(cursor);
        view.setText(item);
        return view;
    }

    @Override // android.widget.CursorAdapter
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        if (getFilterQueryProvider() != null) {
            return getFilterQueryProvider().runQuery(constraint);
        }
        String args = "";
        if (constraint != null) {
            args = constraint.toString();
        }
        Cursor currentCursor = this.dbAdapter.getStationCursor(args);
        return currentCursor;
    }

    private String createItem(Cursor cursor) {
        String item = cursor.getString(1);
        return item;
    }

    public void close() {
        this.dbAdapter.close();
    }
}
