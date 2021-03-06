package org.apache.commons.codecc.net;

import android.support.v4.media.TransportMediator;
import java.io.ByteArrayOutputStream;
import java.util.BitSet;
import org.apache.commons.codecc.BinaryDecoder;
import org.apache.commons.codecc.BinaryEncoder;
import org.apache.commons.codecc.DecoderException;
import org.apache.commons.codecc.EncoderException;
import org.apache.commons.codecc.StringDecoder;
import org.apache.commons.codecc.StringEncoder;
import org.apache.commons.codecc.binary.StringUtils;

public class QuotedPrintableCodec implements BinaryDecoder, BinaryEncoder, StringDecoder, StringEncoder {
    private static final byte ESCAPE_CHAR = (byte) 61;
    private static final BitSet PRINTABLE_CHARS = new BitSet(256);
    private static final byte SPACE = (byte) 32;
    private static final byte TAB = (byte) 9;
    private final String charset;

    static {
        int i;
        for (i = 33; i <= 60; i++) {
            PRINTABLE_CHARS.set(i);
        }
        for (i = 62; i <= TransportMediator.KEYCODE_MEDIA_PLAY; i++) {
            PRINTABLE_CHARS.set(i);
        }
        PRINTABLE_CHARS.set(9);
        PRINTABLE_CHARS.set(32);
    }

    public QuotedPrintableCodec() {
        this("UTF-8");
    }

    public QuotedPrintableCodec(String str) {
        this.charset = str;
    }

    private static final void encodeQuotedPrintable(int i, ByteArrayOutputStream byteArrayOutputStream) {
        byteArrayOutputStream.write(61);
        char toUpperCase = Character.toUpperCase(Character.forDigit((i >> 4) & 15, 16));
        char toUpperCase2 = Character.toUpperCase(Character.forDigit(i & 15, 16));
        byteArrayOutputStream.write(toUpperCase);
        byteArrayOutputStream.write(toUpperCase2);
    }

    public static final byte[] encodeQuotedPrintable(BitSet bitSet, byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bitSet == null) {
            bitSet = PRINTABLE_CHARS;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i : bArr) {
            int i2;
            if (i2 < 0) {
                i2 += 256;
            }
            if (bitSet.get(i2)) {
                byteArrayOutputStream.write(i2);
            } else {
                encodeQuotedPrintable(i2, byteArrayOutputStream);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static final byte[] decodeQuotedPrintable(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (b == ESCAPE_CHAR) {
                i++;
                try {
                    int digit16 = Utils.digit16(bArr[i]);
                    i++;
                    byteArrayOutputStream.write((char) ((digit16 << 4) + Utils.digit16(bArr[i])));
                } catch (Throwable e) {
                    throw new DecoderException("Invalid quoted-printable encoding", e);
                }
            }
            byteArrayOutputStream.write(b);
            i++;
        }
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] encode(byte[] bArr) {
        return encodeQuotedPrintable(PRINTABLE_CHARS, bArr);
    }

    public byte[] decode(byte[] bArr) {
        return decodeQuotedPrintable(bArr);
    }

    public String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            return encode(str, getDefaultCharset());
        } catch (Throwable e) {
            throw new EncoderException(e.getMessage(), e);
        }
    }

    public String decode(String str, String str2) {
        if (str == null) {
            return null;
        }
        return new String(decode(StringUtils.getBytesUsAscii(str)), str2);
    }

    public String decode(String str) {
        if (str == null) {
            return null;
        }
        try {
            return decode(str, getDefaultCharset());
        } catch (Throwable e) {
            throw new DecoderException(e.getMessage(), e);
        }
    }

    public Object encode(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be quoted-printable encoded");
    }

    public Object decode(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be quoted-printable decoded");
    }

    public String getDefaultCharset() {
        return this.charset;
    }

    public String encode(String str, String str2) {
        if (str == null) {
            return null;
        }
        return StringUtils.newStringUsAscii(encode(str.getBytes(str2)));
    }
}
