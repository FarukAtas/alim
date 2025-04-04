package com.alyansyazilim.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/* loaded from: classes.dex */
public class AciklamaACDBAdapter {
    private final Context mCtx;
    private SQLiteDatabase mDb;
    private Veritabani mDbHelper;
    private String tblnm;

    public AciklamaACDBAdapter(Context ctx, String tblnm) {
        this.mCtx = ctx;
        this.tblnm = tblnm;
    }

    protected void finalize() throws Throwable {
        this.mDbHelper.close();
        super.finalize();
    }

    public AciklamaACDBAdapter open() throws SQLException {
        this.mDbHelper = new Veritabani(this.mCtx);
        this.mDb = this.mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (this.mDb.isOpen()) {
            this.mDb.close();
        }
        this.mDbHelper.close();
    }

    public Cursor getStationCursor(String args) {
        String sqlQuery = String.valueOf(" SELECT distinct 0 as _id, ack ") + " FROM " + this.tblnm;
        String sqlQuery2 = String.valueOf(String.valueOf(sqlQuery) + " WHERE ack LIKE '%" + args.replace("'", "''") + "%' ") + " ORDER BY ack";
        if (this.mDb == null) {
            open();
        }
        if (this.mDb == null) {
            return null;
        }
        Cursor result = this.mDb.rawQuery(sqlQuery2, null);
        return result;
    }
}
