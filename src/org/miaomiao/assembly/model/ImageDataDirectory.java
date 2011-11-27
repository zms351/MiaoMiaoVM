package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;

public class ImageDataDirectory extends BaseDataModel {

    private long virtualAddress;
    private long size;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        this.virtualAddress=reader.readUnsignedInt();
        this.size=reader.readUnsignedInt();
    }

    public long getVirtualAddress() {
        return virtualAddress;
    }

    public long getSize() {
        return size;
    }

}
