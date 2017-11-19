package com.jonbore.util;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;

public class ZipArchiveUtil {

    private static final Integer BUFF_SIZE = 1024;

    /**
     * 压缩文件
     *
     * @param sourceFileDir     要压缩的文件路径
     * @param targetZipFilePath 压缩之后文件路径(含文件名) \***\***\*.zip
     * @param encoding          编码格式 UTF-8/GBK
     */
    public static void zip(String sourceFileDir, String targetZipFilePath, String encoding) {
        try {
            OutputStream out = null;
            BufferedOutputStream bos = null;
            ZipArchiveOutputStream zaos = null;
            File zipFile = new File(targetZipFilePath);
            if (!zipFile.getParentFile().exists()) {
                zipFile.getParentFile().mkdirs();
            }
            zipFile.delete();
            try {
                out = new FileOutputStream(zipFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bos = new BufferedOutputStream(out);
            zaos = new ZipArchiveOutputStream(bos);
            zaos.setEncoding(encoding);
            zip(zaos, sourceFileDir, "");
            zaos.flush();
            zaos.close();
            bos.flush();
            bos.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件
     *
     * @param zaos          压缩之后文件的输出流
     * @param sourceFileDir 要压缩的文件路径
     * @param relativePath  压缩文件内部文件相对与根目录的路径
     */
    public static void zip(ZipArchiveOutputStream zaos, String sourceFileDir, String relativePath) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(baos);
            File dir = new File(sourceFileDir);
            File[] files = dir.listFiles();
            if (files == null || files.length < 1) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    zip(zaos, files[i].getAbsolutePath(), relativePath
                            + files[i].getName() + File.separator);
                } else {
                    zaos.putArchiveEntry(new ZipArchiveEntry(relativePath
                            + files[i].getName()));
                    FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
                    IOUtils.copy(in, zaos);
                    zaos.closeArchiveEntry();
                    in.close();
                }
            }
            bos.flush();
            bos.close();
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解压文件
     *
     * @param sourceZipFilePath zip压缩文件所在路径
     * @param unZipToDir        解压后文件存放目录
     * @param decoding          解压文件编码
     */
    public static void unZip(String sourceZipFilePath, String unZipToDir, String decoding) {

        try {
            File zipFile = new File(sourceZipFilePath);
            if (!zipFile.exists()) {
                return;
            }
            InputStream in = new FileInputStream(zipFile);
            BufferedInputStream bis = new BufferedInputStream(in, BUFF_SIZE);
            ZipArchiveInputStream zais = new ZipArchiveInputStream(bis, decoding);
            // 如果 unZipToDir 为 null, 空字符串, 或者全是空格, 则解压到压缩文件所在目录
            if (StringUtils.isBlank(unZipToDir)) {
                unZipToDir = zipFile.getParentFile().getAbsolutePath();
            }
            unZipToDir = unZipToDir.endsWith(File.separator) ? unZipToDir : unZipToDir + File.separator;
            ZipArchiveEntry entry = null;
            while ((entry = zais.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(unZipToDir, entry.getName());
                    directory.getParentFile().mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        File file = new File(unZipToDir, entry.getName());
                        file.getParentFile().mkdirs();
                        OutputStream out = new FileOutputStream(file);
                        os = new BufferedOutputStream(out, BUFF_SIZE);
                        IOUtils.copy(zais, os);
                    } finally {
                        IOUtils.closeQuietly(os);

                    }
                }
            }
            IOUtils.closeQuietly(zais);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解压文件
     *
     * @param in         zip压缩文件输入流
     * @param unZipToDir 解压后文件存放目录
     * @param decoding   解压文件编码
     */
    public static void unZip(InputStream in, String unZipToDir, String decoding) {

        try {
            BufferedInputStream bis = new BufferedInputStream(in, BUFF_SIZE);
            ZipArchiveInputStream zais = new ZipArchiveInputStream(bis, decoding);
            unZipToDir = unZipToDir.endsWith(File.separator) ? unZipToDir : unZipToDir + File.separator;
            ZipArchiveEntry entry = null;
            while ((entry = zais.getNextZipEntry()) != null) {
                if (entry.isDirectory()) {
                    File directory = new File(unZipToDir, entry.getName());
                    directory.getParentFile().mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        File file = new File(unZipToDir, entry.getName());
                        file.getParentFile().mkdirs();
                        OutputStream out = new FileOutputStream(file);
                        os = new BufferedOutputStream(out, BUFF_SIZE);
                        IOUtils.copy(zais, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
            IOUtils.closeQuietly(zais);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void Test() {
//        ZipArchiveUtil.zip("F:\\20171118174017072", "F:\\20171118174017072.zip", "GBK");
        ZipArchiveUtil.unZip("F:\\20171118174017072.zip", "F:\\unzip", "GBK");
    }
}
