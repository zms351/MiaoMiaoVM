package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;

/**
 * The .text section of a PE file is a read-only section. In a managed PE file, it contains metadata tables, IL code, the Import tables, the common language runtime header, and an unmanaged start-up stub for the CLR. In the image files generated by the IL assembler, this section also contains managed resources, the strong name signature hash, the debug data, and unman- aged export stubs.
 */
public class TextSectionData extends SectionData {

    private CLRuntimeHeader runtimeHeader;
    private PEHeader peHeader;

    public CLRuntimeHeader getRuntimeHeader() {
        return runtimeHeader;
    }

    @Override
    public void parse(InputStreamReader reader) throws IOException, LoadException {
        super.jumpToStart(reader);
        this.peHeader = super.getHeader().getParent().getPeHeader();
        this.parseIAT(reader);
        this.parseCLRuntimeHeader(reader);
    }

    protected void parseIAT(InputStreamReader reader) throws IOException, LoadException {
        ImageDataDirectory entry = peHeader.getIATEntry();
        super.jumpToEntry(entry,reader,peHeader.getBaseOfCode());
        //todo
    }

    protected void parseCLRuntimeHeader(InputStreamReader reader) throws IOException, LoadException {
        ImageDataDirectory entry = peHeader.getCLRHEntry();
        super.jumpToEntry(entry, reader, peHeader.getBaseOfCode());

        if(runtimeHeader==null) {
            runtimeHeader=new CLRuntimeHeader();
        }
        runtimeHeader.logger=this.logger;
        runtimeHeader.parse(reader);
    }

}
