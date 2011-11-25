package org.miaomiao.loader;

import java.io.*;

public class MemoryInputSource implements InputSource {

    private byte[] data;
    private int offset;
    private int len;

    public MemoryInputSource(byte[] data) {
        this(data, 0, data.length);
    }

    public MemoryInputSource(byte[] data, int offset, int len) {
        this.data = data;
        this.offset = offset;
        this.len = len;
    }

    @Override
    public InputStream openSource() throws IOException {
        return new ByteArrayInputStream(this.getData(), this.getOffset(), this.getLen());
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

}
