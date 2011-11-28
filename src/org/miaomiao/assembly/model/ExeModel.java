package org.miaomiao.assembly.model;

import org.miaomiao.assembly.*;
import org.miaomiao.loader.InputStreamReader;
import org.miaomiao.util.Logger;

import java.io.IOException;

/**
 * data model for a executable file (.exe or .dll)
 */
public class ExeModel extends Assembly {

    private Logger logger=Logger.getLogger(this.getClass());

    /**
     * 00h  old style exe header  32 bytes
     */
    private OldStyleExeHeader oldHeader;
    /**
     * 20 bytes
     */
    private CoffHeader coffHeader;
    /**
     * 224 bytes
     */
    private PEHeader peHeader;
    /**
     * The NumberOfSections field of the COFF header defines the number of entries in the section header table. The section header indexing in the table is one-based, with the order of the sections defined by the linker. The sections follow one another contiguously in the order defined by the section header table, with (as you already know) starting RVAs aligned by the value of the SectionAlignment field of the PE header.
     * A section header is a 40-byte structure
     */
    private SectionHeader[] sectionHeaders;
    /**
     * InputStream start position
     */
    private int basePosition;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        if (this.oldHeader == null) {
            this.oldHeader = new OldStyleExeHeader();
        }
        int basePosition=this.basePosition=reader.getPosition();
        //ms-dos header 64 bytes
        //old style dos header 32 bytes
        this.oldHeader.parse(reader);
        //20h--3ch reserved  all zero
        reader.byPass(0x1c);
        //0x3c offset to segment exe header
        final long dataOffset = reader.readUnsignedInt();
        //assert (offset == 0x80);
        //ms dos stub  64bytes
        //This program cannot be run in DOS mode.
        reader.byPass(0x40);
        int nowPosition=reader.getPosition();
        logger.debug("now position %d",nowPosition);
        long skip=basePosition+dataOffset-nowPosition;
        if(skip>0) {
            reader.byPass(skip);
        }
        //The PE signature that usually (but not necessarily) immediately follows the MS-DOS stub is a 4-byte item
        //PE Signature (4 Bytes)
        //The signature contains the char- acters P and E, followed by 2 null bytes.
        long flag=reader.readUnsignedInt();
        assert(flag==0x4550);
        if(coffHeader==null) {
            coffHeader=new CoffHeader();
        }
        coffHeader.logger=this.logger;
        coffHeader.parse(reader);
        //The size of the PE header is not fixed. It depends on the number of data directories defined in the header and is specified in the SizeOfOptionalHeader field of the COFF header.
        if(peHeader==null) {
            peHeader=new PEHeader();
        }
        peHeader.logger=this.logger;
        peHeader.parse(reader);
        //The table of section headers must immediately follow the PE header.
        int size=this.coffHeader.getNumberOfSections();
        if(sectionHeaders==null || sectionHeaders.length!=size) {
            sectionHeaders=new SectionHeader[size];
        }
        for(int i=0;i<size;i++) {
            if(sectionHeaders[i]==null) {
                sectionHeaders[i]=new SectionHeader(this);
            }
            sectionHeaders[i].logger=this.logger;
            sectionHeaders[i].parse(reader);
        }
        logger.debug("after parse section headers,it's at %d",reader.getPosition());
        for (SectionHeader sectionHeader : sectionHeaders) {
            sectionHeader.parseData(reader);
        }
    }

    public OldStyleExeHeader getOldHeader() {
        return oldHeader;
    }

    public CoffHeader getCoffHeader() {
        return coffHeader;
    }

    public PEHeader getPeHeader() {
        return peHeader;
    }

    public SectionHeader[] getSectionHeaders() {
        return sectionHeaders;
    }

    public int getBasePosition() {
        return basePosition;
    }

}
