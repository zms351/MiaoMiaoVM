package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;

public class CoffHeader extends BaseDataModel {

    /**
     * 0 2
     * Number identifying the type of target machine.
     */
    private int machine;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        this.machine=reader.readUnsignedShort();
    }

}
