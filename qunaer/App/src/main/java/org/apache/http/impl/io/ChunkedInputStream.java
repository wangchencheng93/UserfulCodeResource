package org.apache.http.impl.io;

import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.io.SessionInputBuffer;

@Deprecated
public class ChunkedInputStream extends InputStream {
    public ChunkedInputStream(SessionInputBuffer sessionInputBuffer) {
        throw new RuntimeException("Stub!");
    }

    public int read() {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] bArr, int i, int i2) {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] bArr) {
        throw new RuntimeException("Stub!");
    }

    public void close() {
        throw new RuntimeException("Stub!");
    }

    public Header[] getFooters() {
        throw new RuntimeException("Stub!");
    }
}
