package com.alyansyazilim.ilerimuhasebesistemi;

import android.content.Context;
import android.os.Build;
import com.alyansyazilim.database.OdmDbInfo;
import com.alyansyazilim.database.Veritabani;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class RegSrv {
    private static final String TIMESTAMPFILENAME = "alim.txt";
    private static String sID = null;

    public static String getHash(Context con) {
        OdmDbInfo dbi = new OdmDbInfo(con);
        dbi.getRow();
        String txt = String.valueOf(K.vAndroidID) + "_" + getAppFirsRunTimeStamp(con) + getFakeImei() + "_" + dbi.getDbId() + "_" + dbi.getZaman() + "_" + dbi.getAdi() + "_" + dbi.getEposta();
        dbi.closecr();
        String hash = AlimMcrypt2.str2md5(txt);
        return hash;
    }

    public static String getDbAdi(Context con) {
        OdmDbInfo dbi = new OdmDbInfo(con);
        dbi.getRow();
        String txt = dbi.getAdi();
        dbi.closecr();
        return txt;
    }

    public static String getStatistics(Context con) {
        return "/";
    }

    public static synchronized String getAppFirsRunTimeStamp(Context context) {
        String str;
        synchronized (RegSrv.class) {
            if (sID == null) {
                File timestampfile = new File(context.getFilesDir(), TIMESTAMPFILENAME);
                try {
                    if (!timestampfile.exists()) {
                        writeInstallationFile(timestampfile, context);
                    }
                    sID = readInstallationFile(timestampfile);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            str = sID;
        }
        return str;
    }

    private static String readInstallationFile(File file) throws IOException {
        RandomAccessFile f = new RandomAccessFile(file, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    private static String getFakeImei() {
        return "_35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.DISPLAY.length() % 10) + (Build.HOST.length() % 10) + (Build.ID.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10) + (Build.TAGS.length() % 10) + (Build.TYPE.length() % 10) + (Build.USER.length() % 10) + (Build.CPU_ABI2.length() % 10) + (Build.BOOTLOADER.length() % 10) + (Build.FINGERPRINT.length() % 10) + (Build.RADIO.length() % 10);
    }

    private static void writeInstallationFile(File installation, Context context) throws IOException {
        FileOutputStream out = new FileOutputStream(installation);
        String id = K.Now_2();
        out.write(id.getBytes());
        out.close();
    }

    public static JSONObject getDeviceJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("board", Build.BOARD);
            jsonObject.put("bootloader", Build.BOOTLOADER);
            jsonObject.put("brand", Build.BRAND);
            jsonObject.put("cpu_abi", Build.CPU_ABI);
            jsonObject.put("cpu_abi2", Build.CPU_ABI2);
            jsonObject.put("device", Build.DEVICE);
            jsonObject.put("display", Build.DISPLAY);
            jsonObject.put("fingerprint", Build.FINGERPRINT);
            jsonObject.put("hardware", Build.HARDWARE);
            jsonObject.put("host", Build.HOST);
            jsonObject.put("id", Build.ID);
            jsonObject.put("manufacturer", Build.MANUFACTURER);
            jsonObject.put("model", Build.MODEL);
            jsonObject.put("product", Build.PRODUCT);
            jsonObject.put("radio", Build.RADIO);
            jsonObject.put("tags", Build.TAGS);
            jsonObject.put("time", Build.TIME);
            jsonObject.put("type", Build.TYPE);
            jsonObject.put("unknown", "unknown");
            jsonObject.put("user", Build.USER);
            jsonObject.put("version.codename", Build.VERSION.CODENAME);
            jsonObject.put("version.incremental", Build.VERSION.INCREMENTAL);
            jsonObject.put("version.release", Build.VERSION.RELEASE);
            jsonObject.put("version.sdk", Build.VERSION.SDK);
            jsonObject.put("version.sdk_int", Build.VERSION.SDK_INT);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String getDeviceTxt() {
        StringBuilder buf = new StringBuilder();
        buf.append("board :" + Build.BOARD + "\n");
        buf.append("bootloader :" + Build.BOOTLOADER + "\n");
        buf.append("brand :" + Build.BRAND + "\n");
        buf.append("cpu_abi :" + Build.CPU_ABI + "\n");
        buf.append("cpu_abi2 :" + Build.CPU_ABI2 + "\n");
        buf.append("device :" + Build.DEVICE + "\n");
        buf.append("display :" + Build.DISPLAY + "\n");
        buf.append("fingerprint :" + Build.FINGERPRINT + "\n");
        buf.append("hardware :" + Build.HARDWARE + "\n");
        buf.append("host :" + Build.HOST + "\n");
        buf.append("id :" + Build.ID + "\n");
        buf.append("manufacturer :" + Build.MANUFACTURER + "\n");
        buf.append("model :" + Build.MODEL + "\n");
        buf.append("product :" + Build.PRODUCT + "\n");
        buf.append("radio :" + Build.RADIO + "\n");
        buf.append("tags :" + Build.TAGS + "\n");
        buf.append("time :" + Build.TIME + "\n");
        buf.append("type :" + Build.TYPE + "\n");
        buf.append("unknown :unknown\n");
        buf.append("user :" + Build.USER + "\n");
        buf.append("version.codename :" + Build.VERSION.CODENAME + "\n");
        buf.append("version.incremental :" + Build.VERSION.INCREMENTAL + "\n");
        buf.append("version.release :" + Build.VERSION.RELEASE + "\n");
        buf.append("version.sdk :" + Build.VERSION.SDK + "\n");
        buf.append("version.sdk_int :" + Build.VERSION.SDK_INT + "\n");
        return buf.toString();
    }

    public static String getDeviceID_1(Context con) {
        String m_szLongID = String.valueOf(K.vAndroidID) + getAppFirsRunTimeStamp(con);
        String m_szUniqueID = AlimMcrypt2.str2md5(m_szLongID);
        return m_szUniqueID;
    }

    public static JSONObject getDatabaseJson(Context con) {
        Veritabani vt = new Veritabani(con);
        return vt.dbinfoJson();
    }

    public static String registerdata(Context con) {
        JSONObject total = new JSONObject();
        try {
            total.put("d", getDeviceJson());
            total.put("ver", 1);
            total.put("f", getAppFirsRunTimeStamp(con));
            total.put("a", K.vAndroidID);
            total.put("u", getDatabaseJson(con));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return total.toString();
    }

    public static void test_php() {
        JSONObject total = new JSONObject();
        try {
            total.put("ver", 1);
            total.put("device", getDeviceJson());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        String d = total.toString();
        K.loge("org:" + d.length());
        K.loge("org:" + d);
        try {
            d = SimpleCrypto.toHex(new AlimMcrypt2().encrypt(d));
        } catch (Exception e) {
            e.printStackTrace();
        }
        K.loge("enc:" + d.length());
        K.loge("enc:" + d);
        try {
            String dd = new String(new AlimMcrypt2().decrypt(d), "UTF-8");
            K.loge("dec:" + dd.length());
            K.loge("dec:" + dd);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        InputStream is = null;
        String result = "";
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<>(1);
            nameValuePairs.add(new BasicNameValuePair("msg", d));
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.alyansyazilim.com/alim/msg.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch (Exception e3) {
            K.loge("Error in http connection " + e3.toString());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    sb.append(String.valueOf(line) + "\n");
                }
            }
            is.close();
            result = sb.toString();
        } catch (Exception e4) {
            K.loge("Error converting result " + e4.toString());
        }
        K.loge("php:" + result.length());
        K.loge("php:" + result);
    }
}
