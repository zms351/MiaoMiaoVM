package org.miaomiao.assembly.model;

import org.miaomiao.assembly.*;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * data model for a executable file (.exe or .dll)
 */
public class ExeModel extends Assembly {

    private Logger logger=Logger.getLogger(this.getClass().getName());

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

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        if (this.oldHeader == null) {
            this.oldHeader = new OldStyleExeHeader();
        }
        int basePosition=reader.getPosition();
        //ms-dos header 64 bytes
        //old style dos header 32 bytes
        this.oldHeader.parse(reader);
        //20h--3ch reserved  all zero
        long count=reader.skip(0x1c);
        assert(count==0x1c);
        //0x3c offset to segment exe header
        final long dataOffset = reader.readUnsignedInt();
        //assert (offset == 0x80);
        //ms dos stub  64bytes
        //This program cannot be run in DOS mode.
        count=reader.skip(0x40);
        assert(count==0x40);
        int nowPosition=reader.getPosition();
        logger.finest(String.valueOf(nowPosition));
        long skip=basePosition+dataOffset-nowPosition;
        if(skip>0) {
            count=reader.skip(skip);
            assert(count==skip);
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
    }

}
