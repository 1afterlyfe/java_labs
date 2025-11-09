package filters;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class ShiftDecryptReader extends FilterReader {
    private final int shift;

    public ShiftDecryptReader(Reader in, char key) {
        super(in);
        this.shift = key;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c == -1) return -1;
        return (char) (c - shift);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int read = super.read(cbuf, off, len);
        if (read == -1) return -1;
        for (int i = off; i < off + read; i++) {
            cbuf[i] = (char) (cbuf[i] - shift);
        }
        return read;
    }
}

