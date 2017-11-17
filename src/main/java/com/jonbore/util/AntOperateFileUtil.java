package com.jonbore.util;

import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.*;
import java.util.Enumeration;

/**
 * Created by bo.zhou1 on 2017/11/17.
 */
public class AntOperateFileUtil {

        private static Logger logger = Logger.getLogger(AntOperateFileUtil.class);

        /**
         * 压缩文件
         * @param zipFilePath 压缩的文件完整名称(目录+文件名)
         * @param srcPathName 需要被压缩的文件或文件夹
         * @author leo
         * @since 3.5.6_2016年12月22日
         */
        public static void compressFiles(String zipFilePath, String srcPathName) {
            File zipFile = new File(zipFilePath);
            File srcdir = new File(srcPathName);
            if (!srcdir.exists()){
                throw new RuntimeException(srcPathName + "不存在！");
            }
            Project prj = new Project();
            FileSet fileSet = new FileSet();
            fileSet.setProject(prj);
            if(srcdir.isDirectory()) { //是目录
                fileSet.setDir(srcdir);
                fileSet.setIncludes("*.csv"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
                //fileSet.setExcludes(...); //排除哪些文件或文件夹
            } else {
                fileSet.setFile(srcdir);
            }
            Zip zip = new Zip();
            zip.setProject(prj);
            zip.setDestFile(zipFile);
            zip.setEncoding("gbk"); //以gbk编码进行压缩，注意windows是默认以gbk编码进行压缩的
            zip.addFileset(fileSet);
            zip.execute();
            logger.debug("---compress files success---");
        }

        /**
         * 解压文件到指定目录
         * @param zipFilePath 目标文件
         * @param fileSavePath 解压目录
         * @author isDelete 是否删除目标文件
         */
        @SuppressWarnings("unchecked")
        public static void unZipFiles(String zipFilePath, String fileSavePath, boolean isDelete) throws Exception{
            try {
                File f = new File(zipFilePath);
                if ((!f.exists()) && (f.length() <= 0)) {
                    throw new RuntimeException("要解压的文件不存在!");
                }
                //一定要加上编码，之前解压另外一个文件，没有加上编码导致不能解压
                ZipFile zipFile = new ZipFile(f, "gbk");
                String strPath, gbkPath, strtemp;
                strPath = fileSavePath;// 输出的绝对位置
                Enumeration<ZipEntry> e = zipFile.getEntries();
                while (e.hasMoreElements()) {
                    org.apache.tools.zip.ZipEntry zipEnt = e.nextElement();
                    gbkPath = zipEnt.getName();
                    strtemp = strPath + File.separator + gbkPath;
                    if (zipEnt.isDirectory()) { //目录
                        File dir = new File(strtemp);
                        if(!dir.exists()){
                            dir.mkdirs();
                        }
                        continue;
                    } else {
                        // 读写文件
                        InputStream is = zipFile.getInputStream(zipEnt);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        // 建目录
                        String strsubdir = gbkPath;
                        for (int i = 0; i < strsubdir.length(); i++) {
                            if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
                                String temp = strPath + File.separator
                                        + strsubdir.substring(0, i);
                                File subdir = new File(temp);
                                if (!subdir.exists())
                                    subdir.mkdir();
                            }
                        }
                        FileOutputStream fos = new FileOutputStream(strtemp);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        int len;
                        byte[] buff = new byte[1024];
                        while ((len = bis.read(buff)) != -1) {
                            bos.write(buff, 0, len);
                        }
                        bos.close();
                        fos.close();
                    }
                }
                zipFile.close();
            } catch (Exception e) {
                logger.error("解压文件出现异常：", e);
                throw e;
            }
            /**
             * 文件不能删除的原因：
             * 1.看看是否被别的进程引用，手工删除试试(删除不了就是被别的进程占用)
             2.file是文件夹 并且不为空，有别的文件夹或文件，
             3.极有可能有可能自己前面没有关闭此文件的流(我遇到的情况)
             */
            if (isDelete) {
                boolean flag = new File(zipFilePath).delete();
                logger.debug("删除源文件结果: " + flag);
            }
            logger.debug("compress files success");
        }

        public static void main(String[] args) throws Exception {
//      unZipFiles("C:\\Users\\Administrator\\Desktop\\alipay\\optask.zip", "E:\\", false);
//      unZipFiles("E:\\exda.zip", "E:\\", true);
//      compressFiles("E:\\exda.zip", "E:\\");
        }

    }
