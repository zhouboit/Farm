package com.jonbore.utils.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * 借用 http://www.cnblogs.com/xifenglou/p/6233689.html
 * 转换有BOM为无BOM
 *
 * @author bo.zhou1
 * @date 2108/1/19
 */
public class UnicodeInputStream extends InputStream {
    private PushbackInputStream pushbackInputStream;
    private boolean isInitialization = false;
    private String defaultEnc;
    private String encoding;

    private static final int BOM_SIZE = 4;

    public UnicodeInputStream() {
    }

    public UnicodeInputStream(InputStream in, String defaultEnc) {
        this.pushbackInputStream = new PushbackInputStream(in, BOM_SIZE);
        this.defaultEnc = defaultEnc;
    }

    public String getEncoding() {
        if (!isInitialization) {
            try {
                init();
            } catch (IOException ex) {
                IllegalStateException ise = new IllegalStateException("Init method failed.");
                ise.initCause(ise);
                throw ise;
            }
        }
        return encoding;
    }

    protected void init() throws IOException {
        if (isInitialization) {
            return;
        }
        byte bom[] = new byte[BOM_SIZE];
        int n, unread;
        n = this.pushbackInputStream.read(bom, 0, bom.length);

        if ((bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00) &&
                (bom[2] == (byte) 0xFE) && (bom[3] == (byte) 0xFF)) {
            encoding = "UTF-32BE";
            unread = n - 4;
        } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE) &&
                (bom[2] == (byte) 0x00) && (bom[3] == (byte) 0x00)) {
            encoding = "UTF-32LE";
            unread = n - 4;
        } else if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB) &&
                (bom[2] == (byte) 0xBF)) {
            encoding = "UTF-8";
            unread = n - 3;
        } else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
            encoding = "UTF-16BE";
            unread = n - 2;
        } else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
            encoding = "UTF-16LE";
            unread = n - 2;
        } else {
            encoding = defaultEnc;
            unread = n;
        }

        if (unread > 0) {
            this.pushbackInputStream.unread(bom, (n - unread), unread);
        }

        isInitialization = true;
    }

    @Override
    public void close() throws IOException {
        isInitialization = true;
        this.pushbackInputStream.close();
    }

    @Override
    public int read() throws IOException {
        isInitialization = true;
        return this.pushbackInputStream.read();
    }
}
