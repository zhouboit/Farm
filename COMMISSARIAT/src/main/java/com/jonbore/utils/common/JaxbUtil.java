package com.jonbore.utils.common;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * Created by xiuyou.xu on 2017/8/29.
 */
public class JaxbUtil {
    /**
     * 从字符串中反序列化得到java bean
     *
     * @param klass
     * @param xml
     * @param <T>
     * @return
     */
    public static <T> T unmarshal(Class<T> klass, String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(klass);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 序列化java bean到xml
     *
     * @param klass
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String marshal(Class<T> klass, Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(klass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化xml
     *
     * @param xml
     * @return
     */
    public static String format(String xml) {
        try {
            SAXReader reader = new SAXReader();
            StringReader in = new StringReader(xml);
            Document doc = reader.read(in);
            OutputFormat formater = OutputFormat.createPrettyPrint();
            formater.setSuppressDeclaration(true);
            StringWriter out = new StringWriter();
            XMLWriter writer = new XMLWriter(out, formater);
            writer.write(doc);
            writer.close();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取xml读取流 InputStreamReader 有无BOM都可使用
     *
     * @param inputStream xml文件流
     * @return 返回 xml文件内容字符串
     */
    public static String readXMLContent(InputStream inputStream) {
        UnicodeInputStream unicodeInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            String encode = null;
            unicodeInputStream = new UnicodeInputStream(inputStream, encode);
            encode = unicodeInputStream.getEncoding();
            if (encode == null) {
                inputStreamReader = new InputStreamReader(unicodeInputStream);
            } else {
                inputStreamReader = new InputStreamReader(unicodeInputStream, encode);
            }
            String line = "";
            StringBuilder xml = new StringBuilder();
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                xml.append(line);
            }
            return xml.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (unicodeInputStream != null){
                    unicodeInputStream.close();
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
