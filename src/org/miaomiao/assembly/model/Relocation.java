package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;

public class Relocation extends BaseDataModel {

    private int offset;
    private int segment;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        this.offset = reader.readUnsignedShort();
        this.segment = reader.readUnsignedShort();
    }

}
