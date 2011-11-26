package org.miaomiao.assembly;

import org.jetbrains.annotations.Nullable;
import org.miaomiao.loader.InputSource;

import java.io.IOException;
import java.util.Properties;

public abstract class AssemblyLoader {

    public abstract Assembly loadAssembly(InputSource source) throws LoadException, IOException;

    public static AssemblyLoader getDefaultAssemblyLoader(@Nullable Properties props) {
        return new DefaultAssemblyLoader(props);
    }

}
