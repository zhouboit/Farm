package com.jonbore.common.utils;

import com.jonbore.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties properties = new Properties();

    public static void init() {
        try {
            File config = new File(PropertiesUtil.class.getResource("/configs").toString().substring(6));
            File[] configs = config.listFiles();
            for (File file : configs) {
                if (file.getName().endsWith(".properties")) {
                    FileInputStream in = new FileInputStream(file);
                    properties.load(in);
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据key值从系统初始化的配置中获取配置值
     *
     * @param key
     * @return String
     */
    public static String getProperties(String key) {
        if (StringUtils.isNotBlank(key)) {
            String value = properties.getProperty(key);
            if (StringUtils.isBlank(value))
                throw new RuntimeException(key + "的属性值为空或未配置");
            return value;
        }
        return null;
    }

}
