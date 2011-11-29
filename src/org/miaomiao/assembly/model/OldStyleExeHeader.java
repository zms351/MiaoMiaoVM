package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;

/**
 * http://www.delorie.com/djgpp/doc/exe/
 */
public class OldStyleExeHeader extends BaseDataModel {

    /**
     * 02-03	The number of bytes in the last block of the program that are actually used. If this value is zero, that means the entire last block is used (i.e. the effective value is 512).
     * bytes_in_last_block
     */
    private int lastBlockSize;
    /**
     * 04-05	Number of blocks in the file that are part of the EXE file. If [02-03] is non-zero, only that much of the last block is used.
     * blocks_in_file
     */
    private int numberOfBlocks;
    /**
     * 06-07	Number of relocation entries stored after the header. May be zero.
     * num_relocs
     */
    private int numberOfRelocs;
    /**
     * 08-09	Number of paragraphs in the header. The program's data begins just after the header, and this field can be used to calculate the appropriate file offset. The header includes the relocation entries. Note that some OSs and/or programs may fail if the header is not a multiple of 512 bytes.
     * header_paragraphs
     */
    private int numberOfHeaderParagraphs;
    /**
     * 0A-0B	Number of paragraphs of additional memory that the program will need. This is the equivalent of the BSS size in a Unix program. The program can't be loaded if there isn't at least this much memory available to it.
     * min_extra_paragraphs
     */
    private int minExtraParagraphs;
    /**
     * 0C-0D	Maximum number of paragraphs of additional memory. Normally, the OS reserves all the remaining conventional memory for your program, but you can limit it with this field.
     * max_extra_paragraphs
     */
    private int maxExtraParagraphs;
    /**
     * 0E-0F	Relative value of the stack segment. This value is added to the segment the program was loaded at, and the result is used to initialize the SS register.
     */
    private int ss;
    /**
     * 10-11	Initial value of the SP register.
     */
    private int sp;
    /**
     * 12-13	Word checksum. If set properly, the 16-bit sum of all words in the file should be zero. Usually, this isn't filled in.
     */
    private int checksum;
    /**
     * 14-15	Initial value of the IP register.
     */
    private int ip;
    /**
     * 16-17	Initial value of the CS register, relative to the segment the program was loaded at.
     */
    private int cs;
    /**
     * 18-19	Offset of the first relocation item in the file.
     */
    private int relocTableOffset;
    /**
     * 1A-1B	Overlay number. Normally zero, meaning that it's the main program.
     */
    private int overlayNumber;
    /**
     * 2
     */
    private int oemIdentifier;
    /**
     * 2
     */
    private int oemInfo;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        //00-01	0x4d, 0x5a. This is the "magic number" of an EXE file. The first byte of the file is 0x4d and the second is 0x5a.
        int flag = reader.readUnsignedShort_BE();
        assert (flag == 0x4d5a);
        this.lastBlockSize = reader.readUnsignedShort();
        this.numberOfBlocks = reader.readUnsignedShort();
        this.numberOfRelocs = reader.readUnsignedShort();
        this.numberOfHeaderParagraphs = reader.readUnsignedShort();
        this.minExtraParagraphs = reader.readUnsignedShort();
        this.maxExtraParagraphs = reader.readUnsignedShort();
        this.ss = reader.readUnsignedShort();
        this.sp = reader.readUnsignedShort();
        this.checksum = reader.readUnsignedShort();
        this.ip = reader.readUnsignedShort();
        this.cs = reader.readUnsignedShort();
        this.relocTableOffset = reader.readUnsignedShort();
        this.overlayNumber = reader.readUnsignedShort();
        this.oemIdentifier=reader.readUnsignedShort();
        this.oemInfo=reader.readUnsignedShort();
    }

    public int getOemIdentifier() {
        return oemIdentifier;
    }

    public int getOemInfo() {
        return oemInfo;
    }

    public int getLastBlockSize() {
        return lastBlockSize;
    }

    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public int getNumberOfRelocs() {
        return numberOfRelocs;
    }

    public int getNumberOfHeaderParagraphs() {
        return numberOfHeaderParagraphs;
    }

    public int getMinExtraParagraphs() {
        return minExtraParagraphs;
    }

    public int getMaxExtraParagraphs() {
        return maxExtraParagraphs;
    }

    public int getSs() {
        return ss;
    }

    public int getSp() {
        return sp;
    }

    public int getChecksum() {
        return checksum;
    }

    public int getIp() {
        return ip;
    }

    public int getCs() {
        return cs;
    }

    public int getRelocTableOffset() {
        return relocTableOffset;
    }

    public int getOverlayNumber() {
        return overlayNumber;
    }

}
