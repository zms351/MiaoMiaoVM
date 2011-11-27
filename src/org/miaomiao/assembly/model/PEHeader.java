package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;
import java.util.logging.Logger;

public class PEHeader extends BaseDataModel {

    Logger logger;

    /**
     * 2 1
     * Linker major version number. The VC++ linker sets this field to 8; the pure-IL file generator employed by other compilers does the same. In earlier versions, this field was set to 7 and 6, respectively.
     */
    private int majorLinkerVersion;
    /**
     * 3 1
     * Linker minor version number.
     */
    private int minorLinkerVersion;
    /**
     * 4 4
     * Size of the code section (.text) or the sum of all code sections if multiple code sections exist. The IL assembler always emits a single code section.
     */
    private long sizeOfCode;
    /**
     * 8 4
     * Size of the initialized data section (held in the field SizeOfRawData of the respective section header) or the sum of all such sections. The initialized data is defined as specific values, stored in the disk image file.
     */
    private long sizeOfInitializedData;
    /**
     * 12 4
     * Size of the uninitialized data section (.bss) or the sum of all such sections. This data is not part of the disk file and does not have specific values, but the OS loader commits memory space for this data when the file is loaded.
     */
    private long sizeOfUninitializedData;
    /**
     * 16 4
     * RVA of the entry point function. For unmanaged DLLs, this can be 0. For managed PE files, this value always points to the common language runtime invocation stub.
     */
    private long addressOfEntryPoint;
    /**
     * 20 4
     * RVA of the beginning of the file’s code section(s).
     */
    private long baseOfCode;
    /**
     * 24/-  4/-
     * RVA of the beginning of the file’s data section(s). This entry doesn’t exist in the 64-bit Optional header.
     */
    private long baseOfData;
    /**
     * 28/24  4/8
     * Image’s preferred starting virtual address; must be aligned on the 64KB boundary (0x10000). In ILAsm, this field can be specified explicitly by the directive .imagebase <integer value> and/or the command-line option /BASE=<integer value>. The command-line option takes precedence over the directive.
     */
    private long imageBase;
    /**
     * 32 4
     * Alignment of sections when loaded in memory. This setting must be greater than or equal to the value of the FileAlignment field. The default is the memory page size.
     */
    private long sectionAlignment;
    /**
     * 36 4
     * Alignment of sections in the disk image file. The value should be a power of 2, from 512 to 64,000 0x200 (to 0x10000). If SectionAlignment is set to less than the memory page size, FileAlignment must match SectionAlignment. In ILAsm, this field can be specified explicitly by the directive .file alignment <integer value> and/or the command-line option /ALIGNMENT=<integer value>.The command-line option takes precedence over the directive.
     */
    private long fileAlignment;
    /**
     * 40 2
     * Major version number of the required operating system.
     */
    private int majorOperatingSystemVersion;
    /**
     * 42 2
     * Minor version number of the required operating system.
     */
    private int minorOperatingSystemVersion;
    /**
     * 44 2
     * Major version number of the application.
     */
    private int majorImageVersion;
    /**
     * 46 2
     * Minor version number of the application.
     */
    private int minorImageVersion;
    /**
     * 48 2
     * Major version number of the subsystem.
     */
    private int majorSubsystemVersion;
    /**
     * 50 2
     * Minor version number of the subsystem.
     */
    private int minorSubsystemVersion;
    /**
     * 52 4
     * Reserved.
     */
    private long win32VersionValue;
    /**
     * 56 4
     * Size of the image file (in bytes), including all headers. This field must be set to a multiple of the SectionAlignment value.
     */
    private long sizeOfImage;
    /**
     * 60 4
     * Sum of the sizes of the MS-DOS header and stub, the COFF header, the PE header, and the section headers, rounded up to a multiple of the FileAlignment value.
     */
    private long sizeOfHeaders;
    /**
     * 64 4
     * Checksum of the disk image file.
     */
    private long checkSum;
    /**
     * 68 2
     * User interface subsystem required to run this image file. 
     */
    private int subsystem;
    /**
     * 70 2
     * In managed files of v1.0, always set to 0. In managed files of v1.1 and later, always set to 0x400: no unmanaged Windows structural exception handling.
     */
    private int dllCharacteristics;
    /**
     * 72 4/8
     * Size of virtual memory to reserve for the initial thread’s stack. Only the SizeOfStackCommit field is committed; the rest is available in one-page increments. The default is 1MB for 32-bit images and 4MB for 64-bit images. In ILAsm, this field can be specified explicitly by the directive .stackreserve <integer value> and/or the command-line option /STACK=<integer value>. The command-line option takes precedence over the directive.
     */
    private long sizeOfStackReserve;
    /**
     * 76/80 4/8
     * Size of virtual memory initially committed for the initial thread’s stack. The default is one page (4KB) for 32-bit images and 16KB for 64-bit images.
     */
    private long sizeOfStackCommit;
    /**
     * 80/88 4/8
     * Size of virtual memory to reserve for the initial process heap. Only the SizeOfHeapCommit field is committed; the rest is available in one-page increments. The default is 1MB for both 32-bit and 64bit images.
     */
    private long sizeOfHeapReserve;
    /**
     * 84/96 4/8
     * Size of virtual memory initially committed for the process heap. The default is 4KB (one operating system memory page) for 32-bit images and 2KB for 64-bit images.
     */
    private long sizeOfHeapCommit;
    /**
     * 88/104 4
     * Obsolete, set to 0.
     */
    private long loaderFlags;
    /**
     * 92/108 4
     * Number of entries in the DataDirectory array; at least 16. Although it is theoretically possible to emit more than 16 data directories, all existing managed compilers emit exactly 16 data directories, with the 16th (last) data directory never used (reserved).
     */
    private long numberOfRvaAndSizes;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        //magic 0 2
        //“Magic number” identifying the state of the image file. Acceptable values are 0x010B for a 32-bit PE file, 0x020B for a 64- bit PE file, and 0x107 for a ROM image file. Managed PE files must have this field set to 0x010B or 0x020B (version 2.0 and later only, for 64-bit images).
        long startPosition=reader.getPosition();
        int magic = reader.readUnsignedShort();
        if (magic == 0x010b) {
            //32-bit pe file
        } else if (magic == 0x020b) {
            //64-bit pe file
        } else if (magic == 0x0107) {
            //rom image file
        } else {
            throw new RuntimeException("pe header magic unknown");
        }
        boolean bit64=magic==0x020b;
        this.majorLinkerVersion=reader.read();
        this.minorLinkerVersion=reader.read();
        this.sizeOfCode=reader.readUnsignedInt();
        this.sizeOfInitializedData=reader.readUnsignedInt();
        this.sizeOfUninitializedData=reader.readUnsignedInt();
        this.addressOfEntryPoint=reader.readUnsignedInt();
        this.baseOfCode=reader.readUnsignedInt();
        if(bit64) {
            this.imageBase=reader.readUnsignedLong();
        } else {
            this.baseOfData=reader.readUnsignedInt();
            this.imageBase=reader.readUnsignedInt();
        }
        this.sectionAlignment=reader.readUnsignedInt();
        this.fileAlignment=reader.readUnsignedInt();
        this.majorOperatingSystemVersion=reader.readUnsignedShort();
        this.minorOperatingSystemVersion=reader.readUnsignedShort();
        this.majorImageVersion=reader.readUnsignedShort();
        this.minorImageVersion=reader.readUnsignedShort();
        this.majorSubsystemVersion=reader.readUnsignedShort();
        this.minorSubsystemVersion=reader.readUnsignedShort();
        this.win32VersionValue=reader.readUnsignedInt();
        this.sizeOfImage=reader.readUnsignedInt();
        this.sizeOfHeaders=reader.readUnsignedInt();
        this.checkSum=reader.readUnsignedInt();
        this.subsystem=reader.readUnsignedShort();
        this.dllCharacteristics=reader.readUnsignedShort();
        if(bit64) {
            this.sizeOfStackReserve=reader.readUnsignedLong();
            this.sizeOfStackCommit=reader.readUnsignedLong();
            this.sizeOfHeapReserve=reader.readUnsignedLong();
            this.sizeOfHeapCommit=reader.readUnsignedLong();
        } else {
            this.sizeOfStackReserve=reader.readUnsignedInt();
            this.sizeOfStackCommit=reader.readUnsignedInt();
            this.sizeOfHeapReserve=reader.readUnsignedInt();
            this.sizeOfHeapCommit=reader.readUnsignedInt();
        }
        this.loaderFlags=reader.readUnsignedInt();
        this.numberOfRvaAndSizes=reader.readUnsignedInt();
        logger.info("read header "+(reader.getPosition()-startPosition)+" bytes");
    }

    public int getMajorLinkerVersion() {
        return majorLinkerVersion;
    }

    public int getMinorLinkerVersion() {
        return minorLinkerVersion;
    }

    public long getSizeOfCode() {
        return sizeOfCode;
    }

    public long getSizeOfInitializedData() {
        return sizeOfInitializedData;
    }

    public long getSizeOfUninitializedData() {
        return sizeOfUninitializedData;
    }

    public long getAddressOfEntryPoint() {
        return addressOfEntryPoint;
    }

    public long getBaseOfCode() {
        return baseOfCode;
    }

    public long getBaseOfData() {
        return baseOfData;
    }

    public long getImageBase() {
        return imageBase;
    }

    public long getSectionAlignment() {
        return sectionAlignment;
    }

    public long getFileAlignment() {
        return fileAlignment;
    }

    public int getMajorOperatingSystemVersion() {
        return majorOperatingSystemVersion;
    }

    public int getMinorOperatingSystemVersion() {
        return minorOperatingSystemVersion;
    }

    public int getMajorImageVersion() {
        return majorImageVersion;
    }

    public int getMinorImageVersion() {
        return minorImageVersion;
    }

    public int getMajorSubsystemVersion() {
        return majorSubsystemVersion;
    }

    public int getMinorSubsystemVersion() {
        return minorSubsystemVersion;
    }

    public long getWin32VersionValue() {
        return win32VersionValue;
    }

    public long getSizeOfImage() {
        return sizeOfImage;
    }

    public long getSizeOfHeaders() {
        return sizeOfHeaders;
    }

    public long getCheckSum() {
        return checkSum;
    }

    public int getSubsystem() {
        return subsystem;
    }

    public int getDllCharacteristics() {
        return dllCharacteristics;
    }

    public long getSizeOfStackReserve() {
        return sizeOfStackReserve;
    }

    public long getSizeOfStackCommit() {
        return sizeOfStackCommit;
    }

    public long getSizeOfHeapReserve() {
        return sizeOfHeapReserve;
    }

    public long getSizeOfHeapCommit() {
        return sizeOfHeapCommit;
    }

    public long getLoaderFlags() {
        return loaderFlags;
    }

    public long getNumberOfRvaAndSizes() {
        return numberOfRvaAndSizes;
    }

}
