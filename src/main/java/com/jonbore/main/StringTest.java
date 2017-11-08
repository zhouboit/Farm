package com.jonbore.main;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by bo.zhou1 on 2017/11/8.
 */
public class StringTest {

    public static void main(String [] args) throws IOException {
        File file = new File("e:\\queqiaoxian");
        FileInputStream fileInputStream = new FileInputStream(file);
        String result = IOUtils.toString(fileInputStream, "UTF-8");
//        System.out.println(result.length());
//        String subStr = result.substring(result.lastIndexOf("。“柔情似水”，")+4);
        System.out.println(result.replaceAll("\\s", "").length());
    }
}
