package com.alyansyazilim.ilerimuhasebesistemi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class YorumActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
        JSONObject j = new JSONObject();
        try {
            j.put("action", "getComments");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        K.loge(j.toString());
        JSONObject json = getJSONfromURL("http://www.alyansyazilim.com/alim/yorumsvc.php", j);
        try {
            JSONArray earthquakes = json.getJSONArray("rows");
            for (int i = 0; i < earthquakes.length() - 1; i++) {
                HashMap<String, String> map = new HashMap<>();
                JSONObject e = earthquakes.getJSONObject(i);
                map.put("id", String.valueOf(i));
                map.put("name", e.getString("ipaddr"));
                map.put("magnitude", e.getString("yorum"));
                mylist.add(map);
            }
        } catch (JSONException e2) {
            K.loge("Error parsing data " + e2.toString());
        }
        ListAdapter adapter = new SimpleAdapter(this, mylist, R.layout.activity_cari_hesapkartlari_row, new String[]{"name", "magnitude"}, new int[]{R.id.first, R.id.last});
        final ListView lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.alyansyazilim.ilerimuhasebesistemi.YorumActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> o = (HashMap) lv.getItemAtPosition(position);
                Toast.makeText(YorumActivity.this, "ID '" + o.get("id") + "' was clicked.", 0).show();
            }
        });
    }

    public static JSONObject getJSONfromURL(String url, JSONObject j) {
        InputStream is = null;
        String result = "";
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("APIKey", "ultraQuote"));
            nameValuePairs.add(new BasicNameValuePair("command", j.toString()));
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e) {
            K.loge("Error in http connection " + e.toString());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(String.valueOf(line) + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e2) {
            K.loge("Error converting result " + e2.toString());
        }
        K.loge(result);
        try {
            JSONObject jArray = new JSONObject(result);
            return jArray;
        } catch (JSONException e3) {
            K.loge("Error parsing data " + e3.toString());
            return null;
        }
    }
}
