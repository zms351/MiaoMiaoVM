package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;
import java.util.Date;

public class CoffHeader extends BaseDataModel {

    

    /**
     * 0 2
     * Number identifying the type of target machine.
     */
    private int machine;

    public static final int MachineI386 = 0x014c;
    public static final int MachineArm = 0x01c0;
    public static final int MachineIA64 = 0x0200;
    public static final int MachineAMD64 = 0x8664;

    /**
     * 2 2
     * Number of entries in the section table, which immediately follows the headers.
     */
    private int numberOfSections;
    /**
     * 4 4
     * Time and date of file creation.
     */
    private long timeDateStamp;
    private long pointerToSymbolTable;
    private long numberOfSymbols;
    /**
     * 16 2
     * Size of the PE header. This field is specific to PE files; it is set to 0 in COFF files.
     */
    private int sizeOfOptionalHeader;
    /**
     * 18 2
     * Flags indicating the attributes of the file.
     */
    private int characteristics;

    /**
     * Image file only. This flag indicates that the file contains no base relocations and must be loaded at its preferred base address. In the case of base address conflict, the OS loader reports an error. This flag should not be set for managed PE files.
     */
    public static final int Flag_RELOCS_STRIPPED = 0x0001;
    /**
     * Flag indicates that the file is an image file (EXE or DLL). This flag should be set for managed PE files. If it is not set, this generally indicates a linker error.
     */
    public static final int Flag_EXECUTABLE_IMAGE = 0x0002;

    /**
     * COFF line numbers have been removed. This flag should be set for managed PE files because they do not use the debug information embedded in the PE file itself. Instead, the debug information is saved in accompanying program database (PDB) files.
     */
    public static final int Flag_LINE_NUMS_STRIPPED = 0x0004;
    /**
     * COFF symbol table entries for local symbols have been removed. This flag should be set for managed PE files, for the reason given in the preceding entry.
     */
    public static final int Flag_LOCAL_SYMS_STRIPPED = 0x0008;
    /**
     * Aggressively trim the working set. This flag should not be set for pure-IL managed PE files.
     */
    public static final int Flag_AGGRESIVE_WS_TRIM = 0x0010;
    /**
     * Application can handle addresses beyond the 2GB range. This flag should not be set for pure-IL managed PE files of versions 1.0 and 1.1 but can be set for v2.0 files.
     */
    public static final int Flag_LARGE_ADDRESS_AWARE = 0x0020;
    /**
     * Little endian. This flag should not be set for pure-IL managed PE files.
     */
    public static final int Flag_BYTES_REVERSED_LO = 0x0080;
    /**
     * Machine is based on 32-bit architecture. This flag is usually set by the current versions of code generators producing managed PE files. Version 2.0 and newer, however, can produce 64-bit specific images, which don’t have this flag set.
     */
    public static final int Flag_32BIT_MACHINE = 0x0100;
    /**
     * Debug information has been removed from the image file.
     */
    public static final int Flag_DEBUG_STRIPPED = 0x0200;
    /**
     * If the image file is on removable media, copy and run it from the swap file. This flag should not be set for pure-IL managed PE files.
     */
    public static final int Flag_REMOVABLE_RUN_FROM_SWAP = 0x0400;
    /**
     * If the image file is on a network, copy and run it from the swap file. This flag should not be set for pure-IL managed PE files.
     */
    public static final int Flag_NET_RUN_FROM_SWAP = 0x0800;
    /**
     * The image file is a system file (for example, a device driver). This flag should not be set for pure-IL managed PE files.
     */
    public static final int Flag_SYSTEM = 0x1000;
    /**
     * The image file is a DLL rather than an EXE. It cannot be directly run.
     */
    public static final int Flag_DLL = 0x2000;
    /**
     * The image file should be run on a uniprocessor machine only. This flag should not be set for pure-IL managed PE files.
     */
    public static final int Flag_UP_SYSTEM_ONLY = 0x4000;
    /**
     * Big endian. This flag should not be set for pure-IL managed PE files.
     */
    public static final int Flag_BYTES_REVERSED_HI = 0x8000;

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        this.machine = reader.readUnsignedShort();
        logger.debug("machine: %d",this.machine);
        this.numberOfSections = reader.readUnsignedShort();
        this.timeDateStamp = reader.readUnsignedInt();
        logger.debug(new Date(timeDateStamp * 1000));
        //File pointer of the COFF symbol table. As this table is never used in managed PE files, this field must be set to 0.
        this.pointerToSymbolTable = reader.readUnsignedInt();
        //assert (pointerToSymbolTable == 0);
        //Number of entries in the COFF symbol table. This field must be set to 0 in managed PE files.
        this.numberOfSymbols = reader.readUnsignedInt();
        //assert (numberOfSymbols == 0);

        this.sizeOfOptionalHeader = reader.readUnsignedShort();
        this.characteristics = reader.readUnsignedShort();
    }

    public long getPointerToSymbolTable() {
        return pointerToSymbolTable;
    }

    public long getNumberOfSymbols() {
        return numberOfSymbols;
    }

    public int getMachine() {
        return machine;
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public long getTimeDateStamp() {
        return timeDateStamp;
    }

    public int getSizeOfOptionalHeader() {
        return sizeOfOptionalHeader;
    }

    public int getCharacteristics() {
        return characteristics;
    }

}
