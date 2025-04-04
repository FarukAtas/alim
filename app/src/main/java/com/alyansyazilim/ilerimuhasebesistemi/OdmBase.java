package com.alyansyazilim.ilerimuhasebesistemi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.alyansyazilim.database.Veritabani;
import com.embarcadero.javaandroid.DBXException;
import com.embarcadero.javaandroid.DSProxy;
import com.embarcadero.javaandroid.DSRESTConnection;
import com.embarcadero.javaandroid.TDataSet;
import com.embarcadero.javaandroid.TJSONArray;
import com.embarcadero.javaandroid.TJSONObject;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class OdmBase {
    private DSRESTConnection AVEKcn;
    protected TDataSet AVEKcr;
    private DSProxy.TServerMethods1 AVEKsm;
    protected String TBLNM;
    private Cursor cr;
    private SQLiteDatabase db;
    protected String idColumn;
    public boolean notRaiseNullAVEKcr;
    protected Context vContext;
    protected String vKodColumn;
    private Veritabani vt;

    private DSRESTConnection getConnection() {
        DSRESTConnection conn = new DSRESTConnection();
        conn.setHost(K.Server);
        conn.setPort(8080);
        conn.setUserName(K.ConnectID);
        conn.setPassword("admin");
        return conn;
    }

    private String upper(String str) {
        return str.toUpperCase(Locale.US);
    }

    public OdmBase(Context con, String tblnm, String sKodColumn) {
        switch (K.DbType) {
            case 0:
                this.vt = new Veritabani(con);
                this.idColumn = "_id";
                K.idColumn = this.idColumn;
                break;
            case 1:
                this.AVEKcn = getConnection();
                this.AVEKsm = new DSProxy.TServerMethods1(this.AVEKcn);
                this.idColumn = "ID";
                K.idColumn = this.idColumn;
                break;
        }
        this.vContext = con;
        this.TBLNM = tblnm;
        this.vKodColumn = sKodColumn;
    }

    public OdmBase(Context con, String tblnm) {
        this(con, tblnm, null);
    }

    public Cursor getCr() {
        return this.cr;
    }

    public TDataSet DSqry(String msg, String q) {
        try {
            return this.AVEKsm.QryExec(msg, q);
        } catch (DBXException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void queryLong(String msg, String[] columns, String selection, String groupBy, String orderBy, Integer limitA, Integer limitB) {
        String lmt = "";
        switch (K.DbType) {
            case 0:
                if (limitA.intValue() != 0) {
                    lmt = " LIMIT " + limitA;
                    if (limitB.intValue() != 0) {
                        lmt = String.valueOf(lmt) + ", " + limitB;
                        break;
                    }
                }
                break;
            case 1:
                if (limitA.intValue() != 0) {
                    lmt = " FIRST " + limitA;
                    if (limitB.intValue() != 0) {
                        lmt = String.valueOf(lmt) + " SKIP " + limitB;
                        break;
                    }
                }
                break;
        }
        switch (K.DbType) {
            case 0:
                closecr();
                this.db = this.vt.getReadableDatabase();
                if (!K.isNOE(lmt)) {
                    orderBy = String.valueOf(orderBy) + lmt;
                }
                this.cr = this.db.query(this.TBLNM, columns, selection, null, groupBy, null, orderBy);
                K.loge(String.valueOf(msg) + " - " + orderBy);
                K.loge(String.valueOf(msg) + " - " + Server.Cursor2JSON(this.cr));
                break;
            case 1:
                String q = SQLiteQueryBuilder.buildQueryString(false, this.TBLNM, columns, selection, groupBy, null, orderBy, null);
                if (!K.isNOE(lmt)) {
                    q = String.valueOf(q.substring(0, 6)) + lmt + q.substring(6);
                }
                K.loge(String.valueOf(msg) + " - " + q);
                this.AVEKcr = DSqry(msg, q);
                if (this.AVEKcr == null) {
                    if (!this.notRaiseNullAVEKcr) {
                        K.Error(this.vContext, String.valueOf(msg) + " verisi alınamadı");
                    }
                    K.loge(String.valueOf(msg) + " verisi Cursor NULL oldu !!!!");
                    break;
                }
                break;
        }
    }

    public void query(String msg, String[] columns, String selection, String orderBy) {
        queryLong(msg, columns, selection, null, orderBy, 0, 0);
    }

    public long ins(ContentValues val) {
        long rowId = 0;
        switch (K.DbType) {
            case 0:
                closecr();
                this.db = this.vt.getWritableDatabase();
                long rowId2 = this.db.insertOrThrow(this.TBLNM, null, val);
                closecr();
                return rowId2;
            case 1:
                StringBuilder cn = new StringBuilder();
                StringBuilder vl = new StringBuilder();
                try {
                    rowId = this.AVEKsm.GetIDG();
                    val.put(this.idColumn, Long.valueOf(rowId));
                    int i = 0;
                    for (Map.Entry<String, Object> colName : val.valueSet()) {
                        cn.append(i > 0 ? "," : "");
                        cn.append(colName.getKey());
                        vl.append(i > 0 ? "," : "");
                        vl.append("'" + val.getAsString(colName.getKey()) + "'");
                        i++;
                    }
                    String sql = "INSERT INTO " + this.TBLNM + " (" + cn.toString() + ") VALUES (" + vl.toString() + ")";
                    K.loge(sql);
                    this.AVEKsm.SqlExec(String.valueOf(this.TBLNM) + " ekle ", sql);
                } catch (DBXException e) {
                    e.printStackTrace();
                }
                return rowId;
            default:
                return 0L;
        }
    }

    public boolean upd(ContentValues val, long ID) {
        long nora = 0;
        switch (K.DbType) {
            case 0:
                closecr();
                this.db = this.vt.getWritableDatabase();
                long nora2 = this.db.update(this.TBLNM, val, String.valueOf(this.idColumn) + "=?", new String[]{K.i2s(ID)});
                closecr();
                return nora2 == 1;
            case 1:
                StringBuilder sql = new StringBuilder();
                sql.append("UPDATE " + this.TBLNM + " SET ");
                int i = 0;
                for (Map.Entry<String, Object> colName : val.valueSet()) {
                    sql.append(i > 0 ? "," : "");
                    sql.append(colName.getKey());
                    sql.append("='" + val.getAsString(colName.getKey()) + "'");
                    i++;
                }
                sql.append(" WHERE id=" + ID);
                K.loge(sql.toString());
                try {
                    nora = this.AVEKsm.SqlExec(String.valueOf(this.TBLNM) + " değiştir id:" + ID, sql.toString());
                } catch (DBXException e) {
                    e.printStackTrace();
                }
                return nora == 1;
            default:
                return false;
        }
    }

    public long del(String where) {
        long rownumber = 0;
        switch (K.DbType) {
            case 0:
                closecr();
                this.db = this.vt.getReadableDatabase();
                long rownumber2 = this.db.delete(this.TBLNM, where, null);
                closecr();
                return rownumber2;
            case 1:
                String sql = "DELETE FROM " + this.TBLNM + " WHERE " + where;
                K.loge(sql);
                try {
                    rownumber = this.AVEKsm.SqlExec(String.valueOf(this.TBLNM) + " sil ", sql);
                } catch (DBXException e) {
                    e.printStackTrace();
                }
                return rownumber;
            default:
                return 0L;
        }
    }

    public long delById(long ID) {
        return del(String.valueOf(this.idColumn) + "=" + K.i2s(ID));
    }

    protected String getS(String colname) {
        switch (K.DbType) {
            case 0:
                return this.cr.getString(this.cr.getColumnIndex(colname));
            case 1:
                try {
                    if (this.AVEKcr != null) {
                        return this.AVEKcr.getValue(upper(colname)).GetAsString();
                    }
                    return null;
                } catch (DBXException e) {
                    e.printStackTrace();
                    return null;
                }
            default:
                return null;
        }
    }

    protected boolean getB(String colname) {
        switch (K.DbType) {
            case 0:
                return this.cr.getInt(this.cr.getColumnIndex(colname)) != 0;
            case 1:
                try {
                    if (this.AVEKcr == null) {
                        return false;
                    }
                    return this.AVEKcr.getValue(upper(colname)).GetAsInt16() != 0;
                } catch (DBXException e) {
                    e.printStackTrace();
                    break;
                }
        }
        return false;
    }

    protected int getI(String colname) {
        switch (K.DbType) {
            case 0:
                return this.cr.getInt(this.cr.getColumnIndex(colname));
            case 1:
                try {
                    if (this.AVEKcr != null) {
                        return this.AVEKcr.getValue(upper(colname)).GetAsInt32();
                    }
                    return 0;
                } catch (DBXException e) {
                    e.printStackTrace();
                    return 0;
                }
            default:
                return 0;
        }
    }

    protected String getIs(String colname) {
        switch (K.DbType) {
            case 0:
                return this.cr.getString(this.cr.getColumnIndex(colname));
            case 1:
                try {
                    if (this.AVEKcr != null) {
                        return Integer.toString(this.AVEKcr.getValue(upper(colname)).GetAsInt32());
                    }
                    return null;
                } catch (DBXException e) {
                    e.printStackTrace();
                    return null;
                }
            default:
                return null;
        }
    }

    protected int getI16(String colname) {
        switch (K.DbType) {
            case 0:
                return this.cr.getInt(this.cr.getColumnIndex(colname));
            case 1:
                try {
                    if (this.AVEKcr != null) {
                        return this.AVEKcr.getValue(upper(colname)).GetAsInt16();
                    }
                    return 0;
                } catch (DBXException e) {
                    e.printStackTrace();
                    return 0;
                }
            default:
                return 0;
        }
    }

    protected String getI16s(String colname) {
        switch (K.DbType) {
            case 0:
                return this.cr.getString(this.cr.getColumnIndex(colname));
            case 1:
                try {
                    if (this.AVEKcr != null) {
                        return Integer.toString(this.AVEKcr.getValue(upper(colname)).GetAsInt16());
                    }
                    return null;
                } catch (DBXException e) {
                    e.printStackTrace();
                    return null;
                }
            default:
                return null;
        }
    }

    public String getID() {
        return getIs(this.idColumn);
    }

    protected String getD(String colname) {
        switch (K.DbType) {
            case 0:
                int idx = this.cr.getColumnIndex(colname);
                return Double.toString(this.cr.getDouble(idx));
            case 1:
                try {
                    if (this.AVEKcr != null) {
                        return Double.toString(this.AVEKcr.getValue(upper(colname)).GetAsCurrency());
                    }
                    return null;
                } catch (DBXException e) {
                    e.printStackTrace();
                    return null;
                }
            default:
                return null;
        }
    }

    public boolean moveToFirst() {
        switch (K.DbType) {
            case 0:
                if (this.cr != null) {
                    break;
                }
                break;
            case 1:
                if (this.AVEKcr != null) {
                    this.AVEKcr.reset();
                    break;
                }
                break;
        }
        return false;
    }

    public boolean moveToNext() {
        switch (K.DbType) {
            case 0:
                return this.cr.moveToNext();
            case 1:
                if (this.AVEKcr != null) {
                    return this.AVEKcr.next();
                }
                return false;
            default:
                return false;
        }
    }

    public int getCount() {
        switch (K.DbType) {
            case 0:
                return this.cr.getCount();
            case 1:
                if (this.AVEKcr == null) {
                    return 0;
                }
                long n = 0;
                try {
                    TJSONObject j0 = this.AVEKcr.asJSONObject();
                    TJSONArray j1 = j0.getJSONArray("table");
                    if (j1.size() > 0) {
                        String nm = j1.getAsJsonArray(0).getString(0);
                        n = j0.getJSONArray(nm).size();
                    } else {
                        n = 0;
                    }
                } catch (DBXException e) {
                    e.printStackTrace();
                }
                return (int) n;
            default:
                return 0;
        }
    }

    public void closecr() {
        if (this.cr != null && !this.cr.isClosed()) {
            this.cr.close();
        }
        if (this.db != null && this.db.isOpen()) {
            this.db.close();
        }
    }

    public synchronized void finalize() throws Throwable {
        closecr();
        if (this.vt != null) {
            this.vt.close();
        }
        if (this.AVEKcn != null) {
            this.AVEKcn.CloseSession();
        }
        super.finalize();
    }
}
