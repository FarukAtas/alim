package com.alyansyazilim.z_other;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.alyansyazilim.ilerimuhasebesistemi.K;
import com.alyansyazilim.ilerimuhasebesistemi.R;
import com.embarcadero.javaandroid.DBXException;
import com.embarcadero.javaandroid.DSProxy;
import com.embarcadero.javaandroid.DSRESTConnection;
import com.embarcadero.javaandroid.TDataSet;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class ListViewDemoActivity extends Activity {
    private ArrayList<String> COUNTRIES;
    private String[] Countries;

    private DSRESTConnection getConnection1() {
        DSRESTConnection conn = new DSRESTConnection();
        conn.setHost("192.168.10.24");
        conn.setPort(8080);
        conn.setUserName("admin");
        conn.setPassword("admin");
        return conn;
    }

    protected void setOrientation() {
        int current = getRequestedOrientation();
        if (current != 1) {
            setRequestedOrientation(1);
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setOrientation();
        if (newConfig.orientation == 2) {
            Toast.makeText(this, "landscape", 0).show();
        } else if (newConfig.orientation == 1) {
            Toast.makeText(this, "portrait", 0).show();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_lvd);
        this.Countries = getResources().getStringArray(R.array.countries);
        Arrays.sort(this.Countries);
        ListView list = (ListView) findViewById(R.id.list);
        DSRESTConnection conn = getConnection1();
        DSProxy.TServerMethods1 sm = new DSProxy.TServerMethods1(conn);
        try {
            TDataSet reader = sm.QryExec("kasa kartları", "select * from carkart order by carkod");
            this.COUNTRIES = new ArrayList<>();
            K.loge("dsnap_before while");
            while (reader.next()) {
                K.loge("dsnap_" + reader.getValue("ADI").GetAsString());
                this.COUNTRIES.add(reader.getValue("ADI").GetAsString());
            }
            K.loge("dsnap_after while");
            list.setAdapter((ListAdapter) new ArrayAdapter(this, android.R.layout.simple_list_item_1, this.COUNTRIES));
        } catch (DBXException e) {
            e.printStackTrace();
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.z_other.ListViewDemoActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                v.showContextMenu();
            }
        });
    }

    @Override // android.app.Activity, android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(this.COUNTRIES.get(info.position));
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(0, i, i, menuItems[i]);
            }
        }
    }

    @Override // android.app.Activity
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];
        String listItemName = this.COUNTRIES.get(info.position);
        TextView text = (TextView) findViewById(R.id.footer);
        text.setText(String.format("Selected %s for item %s", menuItemName, listItemName));
        return true;
    }
}
