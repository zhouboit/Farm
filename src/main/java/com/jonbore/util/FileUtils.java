package com.jonbore.util;

import org.junit.Test;

import java.io.*;

public class FileUtils {

    /**
     * 将字符串写入制定路径下的文件
     *
     * @param content
     * @param filePath
     * @return boolean 成功true 失败false
     * @throws Exception
     */
    public static boolean writeToFile(String content, String filePath) throws Exception {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        OutputStream outputStream = new FileOutputStream(filePath);
        try {
            outputStream.write(content.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public void Test() throws Exception {
        FileUtils.writeToFile("123456dddddddddddddddd789", "F:/170211/writeTest.txt");
    }
}
