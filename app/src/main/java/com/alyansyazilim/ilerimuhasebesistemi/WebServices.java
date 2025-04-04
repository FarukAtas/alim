package com.alyansyazilim.ilerimuhasebesistemi;

import android.content.Context;
import android.os.Handler;
import com.alyansyazilim.database.Veritabani;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/* loaded from: classes.dex */
public class WebServices {
    public WebServices(Context con) {
    }

    public static boolean statusEqual(int status, int code) {
        return (status & code) == code;
    }

    public static String doWebService(Context con, String where, String meta) {
        WebServices ws = new WebServices(con);
        String doc = ws.getStringFromUrl("http://www.alyansyazilim.com/alim/svc.php", where, meta);
        return doc;
    }

    public static int getWebServiceStatus(final Context con, Handler handler, String where) {
        int ret = 0;
        XMLParser xml = new XMLParser(con);
        Document doc = xml.ws.getDocument3("http://www.alyansyazilim.com/alim/svc.php", where, null);
        if (doc != null) {
            Element elm = (Element) doc.getElementsByTagName("SERVICE").item(0);
            String val = xml.getValue(elm, "STATUS");
            ret = Integer.valueOf(val).intValue();
            if (statusEqual(ret, 2)) {
                doWebService(con, "rgm", RegSrv.registerdata(con));
            }
            statusEqual(ret, 4);
            if (statusEqual(ret, 8)) {
                handler.post(new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.WebServices.1
                    @Override // java.lang.Runnable
                    public void run() {
                        K.toast(con, "Yedekleme yapmayı unutmayın...");
                    }
                });
            }
            if (statusEqual(ret, 64)) {
                File file = con.getDatabasePath(Veritabani.VERITABANI);
                if (file.exists()) {
                    String wsret = null;
                    try {
                        RandomAccessFile f = new RandomAccessFile(file, "r");
                        byte[] bytes = new byte[(int) f.length()];
                        f.readFully(bytes);
                        f.close();
                        wsret = doWebService(con, "sdf", AlimMcrypt2.bytesToHex(bytes));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    wsret.compareTo("OK");
                }
            }
            if (statusEqual(ret, 32)) {
                final String msg = xml.getValue(elm, "MSG");
                handler.post(new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.WebServices.2
                    @Override // java.lang.Runnable
                    public void run() {
                        K.Info(con, msg);
                    }
                });
            }
            if (statusEqual(ret, 128)) {
                handler.post(new Runnable() { // from class: com.alyansyazilim.ilerimuhasebesistemi.WebServices.3
                    @Override // java.lang.Runnable
                    public void run() {
                        String k = null;
                        if (K.Now_2() == null) {
                            k = K.Now_2();
                        }
                        k.getBytes();
                    }
                });
            }
        }
        return ret;
    }

    public String getStringFromUrl(String url, String where, String meta) {
        String xml = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            List<BasicNameValuePair> nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("hash", K.vHash));
            nameValuePairs.add(new BasicNameValuePair("hash1", K.vAndroidID));
            nameValuePairs.add(new BasicNameValuePair("vc", K.vVersionName));
            nameValuePairs.add(new BasicNameValuePair("act", where));
            if (!K.isNOE(meta)) {
                try {
                    String d = SimpleCrypto.toHex(new AlimMcrypt2().encrypt(meta));
                    nameValuePairs.add(new BasicNameValuePair("meta", d));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity, "UTF-8");
            return xml;
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return xml;
        } catch (ClientProtocolException e3) {
            e3.printStackTrace();
            return xml;
        } catch (IOException e4) {
            e4.printStackTrace();
            return xml;
        }
    }

    public Document getDocument3(String url, String where, String meta) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String xml = getStringFromUrl(url, where, meta);
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(is);
            return doc;
        } catch (IOException e) {
            K.loge("web sayfası alınamadı. " + e.getMessage());
            return null;
        } catch (ParserConfigurationException e2) {
            K.loge("web sayfası alınamadı. " + e2.getMessage());
            return null;
        } catch (SAXException e3) {
            K.loge("web sayfası alınamadı. " + e3.getMessage());
            return null;
        } catch (Exception e4) {
            K.loge("web sayfası alınamadı. " + e4.getMessage());
            return null;
        }
    }
}
