package com.edianjucai.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings({ "unchecked", "null" })
public class XMLReaderUtil {

    private static final String fileName = "edianjucai.xml";
    private static Map<String, String> xmlMap = new HashMap<String, String>();

    static {
        InputStream stream = null;
        try {
            stream = XMLReaderUtil.class.getClassLoader().getResourceAsStream(fileName);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();

            List<Element> results = root.elements();
            for (Element element : results) {
                xmlMap.put(element.attributeValue("id"), element.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream == null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String getSql(String key) {
        return xmlMap.get(key);
    }
}
