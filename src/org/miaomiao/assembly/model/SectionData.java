package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;

public abstract class SectionData extends BaseDataModel {

    protected SectionHeader header;
    protected int basePosition;

    public int getBasePosition() {
        return basePosition;
    }

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
        this.basePosition=reader.getPosition();
    }
    
    protected void jumpToEntry(ImageDataDirectory entry,InputStreamReader reader,long vBase) throws IOException {
        long jump=entry.getVirtualAddress() - vBase - (reader.getPosition() - this.getBasePosition());
        logger.debug("jump to entry by pass %d",jump);
        reader.byPass(jump);
    }

}
