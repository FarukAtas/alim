package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.alyansyazilim.database.OdmDbInfo;
import com.alyansyazilim.database.Veritabani;
import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/* loaded from: classes.dex */
public class BackupRestoreActivity extends Activity {
    public static final String BCKDIR = "/alimydk/";
    private static final String[] fields = {K.idColumn, "carkod", "adi", "bakiye", "telefon", "pb"};
    private Button btFolderX;
    private Button btnSave;
    private String folder;
    private int gFolderType;
    private ListView view;
    ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
    View.OnClickListener btFolderOCL = new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BackupRestoreActivity.1
        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (BackupRestoreActivity.this.gFolderType == 0) {
                BackupRestoreActivity.this.setbtFolder(1);
            } else {
                BackupRestoreActivity.this.setbtFolder(0);
            }
            BackupRestoreActivity.this.doLoadView();
        }
    };

    public static boolean createDirIfNotExists(File file) {
        if (file.exists() || file.mkdirs()) {
            return true;
        }
        return false;
    }

    void setbtFolder(int foldertype) {
        if (foldertype == 0) {
            this.gFolderType = 0;
            this.folder = Environment.getExternalStorageDirectory() + BCKDIR;
            this.btFolderX.setText("İndirilenlere Git");
        }
        if (foldertype == 1) {
            this.gFolderType = 1;
            this.folder = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()) + "/";
            this.btFolderX.setText("Hafıza Kartına Git");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doLoadView() {
        this.btnSave.setEnabled(false);
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            File f = new File(this.folder);
            if (createDirIfNotExists(f)) {
                File[] files = f.listFiles();
                if (files == null) {
                    K.toast(this, "dosya listesi bulunamadı.");
                    View empty = findViewById(R.layout.empty);
                    this.view.setEmptyView(empty);
                    return;
                }
                this.mylist.clear();
                for (int i = 0; i < files.length; i++) {
                    HashMap<String, String> map = new HashMap<>();
                    String[] tokens = files[i].getName().split("\\.(?=[^\\.]+$)");
                    if (tokens.length > 1 && tokens[1].compareTo("db_cyrpt") == 0) {
                        map.put("id", new StringBuilder().append(i).toString());
                        map.put("carkod", tokens[0]);
                        map.put("bakiye", new StringBuilder().append(files[i].length() / 1024).toString());
                        map.put("pb", "KB");
                        map.put("path", files[i].getAbsolutePath());
                        map.put("filename", tokens[0]);
                        this.mylist.add(map);
                    }
                }
                SimpleAdapter mSchedule = new SimpleAdapter(this, this.mylist, R.layout.activity_cari_hesapkartlari_row, fields, new int[]{0, R.id.first, R.id.last, R.id.bky, R.id.tlf, R.id.pb});
                this.view.setAdapter((ListAdapter) mSchedule);
                this.btnSave.setEnabled(true);
                return;
            }
            K.toast(this, "yedekleme için yeni klasör oluşturulamadı." + f.getPath());
            return;
        }
        K.toast(this, "SD kart bulunamadı.");
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backuprestore);
        setButtons();
        this.view = (ListView) findViewById(R.id.listView1);
        setbtFolder(0);
        doLoadView();
        this.view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BackupRestoreActivity.2
            @Override // android.view.View.OnCreateContextMenuListener
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (v.getId() == R.id.listView1) {
                    menu.setHeaderTitle("İşlem seç");
                    String[] menuItems = BackupRestoreActivity.this.getResources().getStringArray(R.array.popup_backuprestore);
                    for (int i = 0; i < menuItems.length; i++) {
                        menu.add(0, i, i, menuItems[i]);
                    }
                }
            }
        });
        this.view.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BackupRestoreActivity.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                v.showContextMenu();
            }
        });
    }

    private void setButtons() {
        this.btFolderX = (Button) findViewById(R.id.btFolder);
        this.btFolderX.setOnClickListener(this.btFolderOCL);
        Button btnBack = (Button) findViewById(R.id.btBack);
        btnBack.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BackupRestoreActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                BackupRestoreActivity.this.finish();
            }
        });
        this.btnSave = (Button) findViewById(R.id.btSave);
        this.btnSave.setOnClickListener(new View.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BackupRestoreActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (BackupRestoreActivity.this.doBackup()) {
                    BackupRestoreActivity.this.doLoadView();
                }
            }
        });
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        HashMap<String, String> map = this.mylist.get(info.position);
        final String path = map.get("path");
        String fname = map.get("filename");
        String title = item.getTitle().toString();
        if (title.equalsIgnoreCase(getString(R.string.GeriYukleStr))) {
            DialogInterface.OnClickListener dcl = new DialogInterface.OnClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.BackupRestoreActivity.6
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case -1:
                            BackupRestoreActivity.this.doRestore(path);
                            break;
                    }
                }
            };
            K.YesNo(this, String.valueOf(fname) + " dosyası Geriyükleme yapılacak mı?", dcl);
        }
        if (title.equalsIgnoreCase(getString(R.string.EpostaStr))) {
            doEmail(path);
        }
        if (title.equalsIgnoreCase(getString(R.string.SilStr))) {
            doDelete(path);
            return true;
        }
        return true;
    }

    private boolean doEmail(String path) {
        try {
            OdmDbInfo dbi = new OdmDbInfo(this);
            dbi.getRow();
            Intent emailIntent = new Intent("android.intent.action.SEND");
            emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{dbi.getEposta()});
            emailIntent.putExtra("android.intent.extra.SUBJECT", "alim veritabanı yedeği");
            String emailText = "Merhaba " + dbi.getAdi() + ",\r\n";
            String emailText2 = String.valueOf(emailText) + "alim yedek dosyası ektedir.";
            emailIntent.setType("text/plain");
            emailIntent.putExtra("android.intent.extra.TEXT", emailText2);
            emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + path));
            Intent client = Intent.createChooser(emailIntent, "e-posta:");
            startActivityForResult(client, 1);
        } catch (Throwable tt) {
            Toast.makeText(this, "Request failed: " + tt.toString(), 1).show();
            tt.printStackTrace();
        }
        return true;
    }

    private boolean doDelete(String path) {
        File file = new File(path);
        boolean deleted = file.delete();
        doLoadView();
        return deleted;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doRestore(String path) {
        K.toast(this, "geri yükleme yapılacak. " + path + " => " + Veritabani.getDbFileName(this));
        try {
            String currentDBPath = Veritabani.getDbFileName(this);
            try {
                RandomAccessFile f = new RandomAccessFile(path, "r");
                byte[] bytes = new byte[(int) f.length()];
                f.readFully(bytes);
                f.close();
                byte[] d = new AlimMcrypt2().decrypt2(bytes);
                RandomAccessFile o = new RandomAccessFile(currentDBPath, "rw");
                o.write(d);
                o.close();
                new Veritabani(this).openRawDataBase().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } catch (Exception e2) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doBackup() {
        try {
            setbtFolder(0);
            File sd = Environment.getExternalStorageDirectory();
            K.loge("sd canWrite?");
            if (sd.canWrite()) {
                K.loge("sd canWrite true");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
                Date now = new Date();
                String fileName = "alimydk_" + formatter.format(now) + ".db_cyrpt";
                String currentDBPath = Veritabani.getDbFileName(this);
                String backupDBPath = Environment.getExternalStorageDirectory() + BCKDIR + fileName;
                File currentDB = new File(currentDBPath);
                File backupDB = new File(backupDBPath);
                if (currentDB.exists()) {
                    K.loge("current db exists true");
                    try {
                        RandomAccessFile f = new RandomAccessFile(currentDB, "r");
                        byte[] bytes = new byte[(int) f.length()];
                        f.readFully(bytes);
                        f.close();
                        byte[] d = new AlimMcrypt2().encrypt2(bytes);
                        RandomAccessFile o = new RandomAccessFile(backupDB, "rw");
                        o.write(d);
                        o.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    K.toast(this, "veritabanı bulunamadı.");
                }
            }
            return true;
        } catch (Exception e2) {
            return false;
        }
    }
}
