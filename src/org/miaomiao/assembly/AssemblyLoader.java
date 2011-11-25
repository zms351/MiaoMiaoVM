package org.miaomiao.assembly;

import org.miaomiao.loader.InputSource;

public abstract class AssemblyLoader {

    public abstract Assembly loadAssembly(InputSource source) throws LoadException;

}
