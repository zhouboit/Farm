package com.jonbore.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * @author bo.zhou1
 * @date 2018/1/5.
 */
public class JarRunUtil {
    private static String CLAZZ = "com.mes.agent.main.JarTest";
    private static String DIR = "D:\\tools\\repository\\com\\mes\\MES-Agent\\1.0\\MES-Agent-1.0.jar";
    private static String CLASSDIR = "E:\\hirain\\IOV-MES\\MES-Agent\\target\\classes";

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        File jarDir = new File(CLASSDIR);
        URL url = jarDir.toURI().toURL();
        ClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class clazz = classLoader.loadClass(CLAZZ);
        Object object = clazz.newInstance();
        Method method = clazz.getMethod("getEnv");
        Map<String, String> result = (Map<String, String>) method.invoke(object);
        result.forEach((k, v) -> {
            System.out.println(Thread.currentThread().getContextClassLoader().getClass().toString() + k + " : " + v);
        });
    }
}
