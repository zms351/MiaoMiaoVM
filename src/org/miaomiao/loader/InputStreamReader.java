package org.miaomiao.loader;

import java.io.*;

public class InputStreamReader extends InputStream implements Closeable {

    private int position;
    private PushbackInputStream input;

    public static final int PushbackSize = 1024 * 5;

    public InputStreamReader(InputStream input, int position) {
        this.input = new PushbackInputStream(input, PushbackSize);
        this.position = position;
    }

    public InputStreamReader(InputStream input) {
        this(input, 0);
    }

    public InputStreamReader(InputSource source) throws IOException {
        this(source.openSource());
    }

    /**
     * read a byte
     *
     * @return 0--256
     * @throws IOException for eof
     */
    @Override
    public int read() throws IOException {
        int n = input.read();
        if (n < 0) {
            throw new EOFException();
        }
        this.position++;
        return n;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public void close() throws IOException {
        this.input.close();
    }

    /**
     * close the stream when finalized
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b,0,b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int num=input.read(b,off,len);
        this.position+=len;
        return num;
    }

    /**
     * little endian
     * read unsigned short
     *
     * @return int for unsigned
     * @throws IOException
     */
    public int readUnsignedShort() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        return (ch2 << 8) + ch1;
    }

    public int readUnsignedShort_BE() throws IOException {
        int ch1 = this.read();
        int ch2 = this.read();
        return (ch1 << 8) + ch2;
    }

    public long readUnsignedInt() throws IOException {
        long ch1 = this.read();
        int ch2 = this.read();
        int ch3 = this.read();
        long ch4 = this.read();
        return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
    }

    public long readUnsignedInt_BE() throws IOException {
        long ch1 = this.read();
        int ch2 = this.read();
        int ch3 = this.read();
        long ch4 = this.read();
        return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
    }

}
