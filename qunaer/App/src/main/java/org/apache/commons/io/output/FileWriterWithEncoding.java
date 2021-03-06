package org.apache.commons.io.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class FileWriterWithEncoding extends Writer {
    private final Writer out;

    public FileWriterWithEncoding(String str, String str2) {
        this(new File(str), str2, false);
    }

    public FileWriterWithEncoding(String str, String str2, boolean z) {
        this(new File(str), str2, z);
    }

    public FileWriterWithEncoding(String str, Charset charset) {
        this(new File(str), charset, false);
    }

    public FileWriterWithEncoding(String str, Charset charset, boolean z) {
        this(new File(str), charset, z);
    }

    public FileWriterWithEncoding(String str, CharsetEncoder charsetEncoder) {
        this(new File(str), charsetEncoder, false);
    }

    public FileWriterWithEncoding(String str, CharsetEncoder charsetEncoder, boolean z) {
        this(new File(str), charsetEncoder, z);
    }

    public FileWriterWithEncoding(File file, String str) {
        this(file, str, false);
    }

    public FileWriterWithEncoding(File file, String str, boolean z) {
        this.out = initWriter(file, str, z);
    }

    public FileWriterWithEncoding(File file, Charset charset) {
        this(file, charset, false);
    }

    public FileWriterWithEncoding(File file, Charset charset, boolean z) {
        this.out = initWriter(file, charset, z);
    }

    public FileWriterWithEncoding(File file, CharsetEncoder charsetEncoder) {
        this(file, charsetEncoder, false);
    }

    public FileWriterWithEncoding(File file, CharsetEncoder charsetEncoder, boolean z) {
        this.out = initWriter(file, charsetEncoder, z);
    }

    private static Writer initWriter(File file, Object obj, boolean z) {
        OutputStream fileOutputStream;
        IOException e;
        RuntimeException e2;
        if (file == null) {
            throw new NullPointerException("File is missing");
        } else if (obj == null) {
            throw new NullPointerException("Encoding is missing");
        } else {
            boolean exists = file.exists();
            try {
                fileOutputStream = new FileOutputStream(file, z);
                try {
                    if (obj instanceof Charset) {
                        return new OutputStreamWriter(fileOutputStream, (Charset) obj);
                    }
                    if (obj instanceof CharsetEncoder) {
                        return new OutputStreamWriter(fileOutputStream, (CharsetEncoder) obj);
                    }
                    return new OutputStreamWriter(fileOutputStream, (String) obj);
                } catch (IOException e3) {
                    e = e3;
                    IOUtils.closeQuietly(null);
                    IOUtils.closeQuietly(fileOutputStream);
                    if (!exists) {
                        FileUtils.deleteQuietly(file);
                    }
                    throw e;
                } catch (RuntimeException e4) {
                    e2 = e4;
                    IOUtils.closeQuietly(null);
                    IOUtils.closeQuietly(fileOutputStream);
                    if (!exists) {
                        FileUtils.deleteQuietly(file);
                    }
                    throw e2;
                }
            } catch (IOException e5) {
                e = e5;
                fileOutputStream = null;
                IOUtils.closeQuietly(null);
                IOUtils.closeQuietly(fileOutputStream);
                if (exists) {
                    FileUtils.deleteQuietly(file);
                }
                throw e;
            } catch (RuntimeException e6) {
                e2 = e6;
                fileOutputStream = null;
                IOUtils.closeQuietly(null);
                IOUtils.closeQuietly(fileOutputStream);
                if (exists) {
                    FileUtils.deleteQuietly(file);
                }
                throw e2;
            }
        }
    }

    public void write(int i) {
        this.out.write(i);
    }

    public void write(char[] cArr) {
        this.out.write(cArr);
    }

    public void write(char[] cArr, int i, int i2) {
        this.out.write(cArr, i, i2);
    }

    public void write(String str) {
        this.out.write(str);
    }

    public void write(String str, int i, int i2) {
        this.out.write(str, i, i2);
    }

    public void flush() {
        this.out.flush();
    }

    public void close() {
        this.out.close();
    }
}
