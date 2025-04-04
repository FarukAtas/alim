package com.alyansyazilim.ilerimuhasebesistemi;

import android.content.Context;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* loaded from: classes.dex */
public class XMLParser {
    private Context vContext;
    public WebServices ws;

    public XMLParser(Context con) {
        this.vContext = con;
        this.ws = new WebServices(this.vContext);
    }

    public Document getDomElement_akaynak(String Aurl) {
        Document document = null;
        try {
            URL url = new URL(Aurl);
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            document = dBuilder.parse(new InputSource(url.openStream()));
            document.getDocumentElement().normalize();
            return document;
        } catch (MalformedURLException e) {
            return document;
        } catch (IOException e2) {
            return document;
        } catch (ParserConfigurationException e3) {
            return document;
        } catch (SAXException e4) {
            return document;
        }
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

    public final String getElementValue(Node elem) {
        if (elem != null && elem.hasChildNodes()) {
            for (Node child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getNodeType() == 3) {
                    return child.getNodeValue();
                }
            }
        }
        return "";
    }
}
