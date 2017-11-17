package com.jonbore.main;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by bo.zhou1 on 2017/11/8.
 */
public class StringTest {

    public static void main(String [] args) throws IOException {
        File file = new File("C:\\Users\\bo.zhou1.HIRAIN\\Desktop\\MES 5.0-开发平台 后30页代码.docx");
        FileInputStream fileInputStream = new FileInputStream(file);
        String result = IOUtils.toString(fileInputStream);
//        System.out.println(result.length());
//        String subStr = result.substring(result.lastIndexOf("。“柔情似水”，")+4);
        System.out.println("0000000075204X|545245003|A00|00|26249509|单体式车载终端|北京经纬恒润科技有限公司|TDS3P".length());

        System.out.println("werwer|dfsfd@sdfsfd".replace("|","@"));
        System.out.println("werwer|dfsfd@sdfsfd".split("\\|")[0]);
    }
}
