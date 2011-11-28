package org.miaomiao.assembly.model;

import org.jetbrains.annotations.Nullable;
import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;
import org.miaomiao.util.Logger;

import java.io.IOException;

public class SectionHeader extends BaseDataModel {

    Logger logger;

    /**
     * Name (8-byte ASCII string): Represents the name of the section. Section names start with a dot (for instance, .reloc). If the section name contains exactly eight characters, the null ter- minator is omitted. If the section name has fewer than eight characters, the array Name is padded with null characters. Image files cannot have section names with more than eight characters. In object files, however, section names can be longer. (Imagine a long-winded file generator emitting a section named .myownsectionnobodyelsecouldevergrok.) In this case, the name is placed in the string table, and the field contains the slash (/) character in the first byte, followed by an ASCII string containing a decimal representation of the respective offset in the string table.
     */
    private byte[] nameBytes;
    /**
     * union {
     * DWORD   PhysicalAddress;
     * DWORD   VirtualSize;
     * } Misc
     * PhysicalAddress/VirtualSize (4-byte unsigned integer): In image files, this field holds the actual (unaligned) size in bytes of the code or data in this section.
     */
    private long physicalAddress;
    /**
     * VirtualAddress (4-byte unsigned integer): Despite its name, this field holds the RVA of the beginning of the section.
     */
    private long virtualAddress;
    /**
     * SizeOfRawData (4-byte unsigned integer): In an image file, this field holds the size in bytes of the initialized data on disk, rounded up to a multiple of the FileAlignment value speci- fied in the PE header. If SizeOfRawData is less than VirtualSize, the rest of the section is padded with null bytes when laid out in memory.
     */
    private long sizeOfRawData;
    /**
     * PointerToRawData (4-byte unsigned integer): This field holds a file pointer to the section’s first page. In image files, this value should be a multiple of the FileAlignment value speci- fied in the PE header.
     */
    private long pointerToRawData;
    /**
     * PointerToRelocations (4-byte unsigned integer): This is a file pointer to the beginning of relo- cation entries for the section. In image files, this field is not used and should be set to 0.
     */
    private long pointerToRelocations;
    /**
     * PointerToLinenumbers (4-byte unsigned integer): This field holds a file pointer to the beginning of line-number entries for the section. In managed PE files, the COFF line numbers are stripped, and this field must be set to 0.
     */
    private long pointerToLinenumbers;
    /**
     * NumberOfRelocations (2-byte unsigned integer): In managed image files, this field should be set to 0.
     */
    private int numberOfRelocations;
    /**
     * NumberOfLinenumbers (2-byte unsigned integer): In managed image files, this field should be set to 0.
     */
    private int numberOfLinenumbers;
    /**
     * Characteristics (4-byte unsigned integer): This field specifies the characteristics of an image file and holds a combination of binary flags
     */
    private long characteristics;

    /**
     * TLS descriptor table index is scaled.
     */
    public static final int Flag_SCALE_INDEX = 0x00000001;
    /**
     * SwingUtilities2.Section contains executable code. In IL assembler–generated PE files, only the .text section carries this flag.
     */
    public static final int Flag_CNT_CODE = 0x00000020;
    /**
     * SwingUtilities2.Section contains initialized data.
     */
    public static final int Flag_CNT_INITIALIZED_DATA = 0x00000040;
    /**
     * SwingUtilities2.Section contains uninitialized data.
     */
    public static final int Flag_CNT_UNINITIALIZED_DATA = 0x00000080;
    /**
     * SwingUtilities2.Section contains comments or some other type of auxiliary information.
     */
    public static final int Flag_LNK_INFO = 0x00000200;
    /**
     * Reset speculative exception handling bits in the translation lookaside buffer (TLB) entries for this section.
     */
    public static final int Flag_NO_DEFER_SPEC_EXC = 0x00004000;
    /**
     * SwingUtilities2.Section contains extended relocations.
     */
    public static final int Flag_LNK_NRELOC_OVFL = 0x01000000;
    /**
     * SwingUtilities2.Section can be discarded as needed.
     */
    public static final int Flag_MEM_DISCARDABLE = 0x02000000;
    /**
     * SwingUtilities2.Section cannot be cached.
     */
    public static final int Flag_MEM_NOT_CACHED = 0x04000000;
    /**
     * SwingUtilities2.Section cannot be paged.
     */
    public static final int Flag_MEM_NOT_PAGED = 0x08000000;
    /**
     * SwingUtilities2.Section can be shared in memory.
     */
    public static final int Flag_MEM_SHARED = 0x10000000;
    /**
     * SwingUtilities2.Section can be executed as code. In IL assembler–generated PE files, only the .text section carries this flag.
     */
    public static final int Flag_MEM_EXECUTE = 0x20000000;
    /**
     * SwingUtilities2.Section can be read.
     */
    public static final int Flag_MEM_READ = 0x40000000;
    /**
     * SwingUtilities2.Section can be written to. In PE files generated by IL assembler, only the .sdata and .tls sections carry this flag.
     */
    public static final int Flag_MEM_WRITE = 0x80000000;

    private SectionData data;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        if (nameBytes == null) {
            nameBytes = new byte[8];
        }
        reader.readFully(this.nameBytes);
        this.physicalAddress = reader.readUnsignedInt();
        this.virtualAddress = reader.readUnsignedInt();
        this.sizeOfRawData = reader.readUnsignedInt();
        this.pointerToRawData = reader.readUnsignedInt();
        this.pointerToRelocations = reader.readUnsignedInt();
        this.pointerToLinenumbers = reader.readUnsignedInt();
        this.numberOfRelocations = reader.readUnsignedShort();
        this.numberOfLinenumbers = reader.readUnsignedShort();
        this.characteristics = reader.readUnsignedInt();
    }

    public void parseData(InputStreamReader reader) throws IOException, LoadException {
        String name = this.getName();
        Class<? extends SectionData> klass = SectionHeader.getDataClassByName(name);
        if(klass==null) {
            logger.warn("don't support section header %s",name);
            return;
        }
        if(this.data==null || !klass.equals(this.data.getClass())) {
            try {
                this.data=klass.newInstance();
            } catch (Throwable t) {
                logger.error(t);
                return;
            }
        }
        this.data.parse(reader);
    }

    public byte[] getNameBytes() {
        return nameBytes;
    }

    public String getName() {
        int index = 0;
        while (index < nameBytes.length && nameBytes[index] != 0) {
            index++;
        }
        return new String(nameBytes, 0, index);
    }

    public long getPhysicalAddress() {
        return physicalAddress;
    }

    public long getVirtualSize() {
        return getPhysicalAddress();
    }

    public long getVirtualAddress() {
        return virtualAddress;
    }

    public long getSizeOfRawData() {
        return sizeOfRawData;
    }

    public long getPointerToRawData() {
        return pointerToRawData;
    }

    public long getPointerToRelocations() {
        return pointerToRelocations;
    }

    public long getPointerToLinenumbers() {
        return pointerToLinenumbers;
    }

    public int getNumberOfRelocations() {
        return numberOfRelocations;
    }

    public int getNumberOfLinenumbers() {
        return numberOfLinenumbers;
    }

    public long getCharacteristics() {
        return characteristics;
    }

    public SectionData getData() {
        return data;
    }

    public static @Nullable Class<? extends SectionData> getDataClassByName(String name) {
        if(".text".equals(name)) {
            return TextSectionData.class;
        } else if(".sdata".equals(name)) {
            return SDataSectionData.class;
        } else if(".reloc".equals(name)) {
            return RelocSectionData.class;
        } else if(".rsrc".equals(name)) {
            return RsrcSectionData.class;
        } else if(".tls".equals(name)) {
            return TlsSectionData.class;
        } else {
            return null;
        }
    }

}
