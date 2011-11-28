package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;

public class RVAAndSize extends BaseDataModel {

    private long address;
    private long size;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        this.address=reader.readUnsignedInt();
        this.size=reader.readUnsignedInt();
    }

    public long getAddress() {
        return address;
    }

    public long getSize() {
        return size;
    }

}
