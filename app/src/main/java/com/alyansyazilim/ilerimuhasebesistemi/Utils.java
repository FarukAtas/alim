package com.alyansyazilim.ilerimuhasebesistemi;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import org.apache.http.conn.util.InetAddressUtils;

/* loaded from: classes.dex */
public class Utils {
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for (byte b : bytes) {
            int intVal = b & 255;
            if (intVal < 16) {
                sbuf.append("0");
            }
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    public static byte[] getUTF8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    public static String loadFileAsString(String filename) throws IOException {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), 1024);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            byte[] bytes = new byte[1024];
            boolean isUTF8 = false;
            int count = 0;
            while (true) {
                int read = is.read(bytes);
                if (read == -1) {
                    break;
                }
                if (count == 0 && bytes[0] == -17 && bytes[1] == -69 && bytes[2] == -65) {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3);
                } else {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
    }

    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName == null || !intf.getName().equalsIgnoreCase(interfaceName)) {
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4) {
                                return sAddr;
                            }
                        } else if (!isIPv4) {
                            int delim = sAddr.indexOf(37);
                            return delim >= 0 ? sAddr.substring(0, delim) : sAddr;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }
}
