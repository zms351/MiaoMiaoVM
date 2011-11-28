package org.miaomiao.loader;

import java.io.*;

public class InputStreamReader extends InputStream implements Closeable {

    private int position;
    private PushbackInputStream input;

    public static final int BufferSize = 1024;

    public InputStreamReader(InputStream input, int position) {
        this.input = new PushbackInputStream(new BufferedInputStream(input, BufferSize), BufferSize);
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
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int num = input.read(b, off, len);
        this.position += len;
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
        long ch1 = this.readUnsignedShort();
        long ch2 = this.readUnsignedShort();
        return (ch2 << 16) + ch1;
    }

    public long readUnsignedInt_BE() throws IOException {
        long ch1 = this.readUnsignedShort_BE();
        long ch2 = this.readUnsignedShort_BE();
        return (ch1 << 16) + ch2;
    }

    public long readUnsignedLong() throws IOException {
        long ch1 = this.readUnsignedInt();
        long ch2 = this.readUnsignedInt();
        return (ch2 << 32) + ch1;
    }

    public final void readFully(byte b[]) throws IOException {
        readFully(b, 0, b.length);
    }

    public final void readFully(byte b[], int off, int len) throws IOException {
        if (len < 0) {
            throw new IndexOutOfBoundsException();
        }
        int n = 0;
        while (n < len) {
            int count = input.read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
        this.position += n;
    }

    public void byPass(long n) throws IOException {
        long r=super.skip(n);
        assert(r==n);
    }

}
