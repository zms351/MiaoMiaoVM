package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;
import org.miaomiao.util.Logger;

import java.io.IOException;

public abstract class SectionData extends BaseDataModel {

    Logger logger=Logger.getLogger(this.getClass());

    protected SectionHeader header;

    public SectionHeader getHeader() {
        return header;
    }

    public void setHeader(SectionHeader header) {
        this.header = header;
    }

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        logger.warn("you should override this method");
    }

    protected void jumpToStart(InputStreamReader reader) throws IOException {
        SectionHeader header=this.getHeader();
        long jump=header.getPointerToRawData()-(reader.getPosition()-header.getParent().getBasePosition());
        logger.debug("%s jump to start by %d",getHeader().getName(),jump);
        reader.byPass(jump);
    }

}
