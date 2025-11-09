package filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class ShiftEncryptWriter extends FilterWriter {
    private final int shift;

    public ShiftEncryptWriter(Writer out, char key) {
        super(out);
        this.shift = key;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + shift);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++) {
            cbuf[i] = (char) (cbuf[i] + shift);
        }
        super.write(cbuf, off, len);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        char[] buf = str.substring(off, off + len).toCharArray();
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (char) (buf[i] + shift);
        }
        super.write(buf, 0, buf.length);
    }
}
