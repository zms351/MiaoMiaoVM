package org.miaomiao.assembly;

import org.miaomiao.loader.FileInputSource;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class AssemblyLoaderTest {

    @Test
    public void testLoad() throws Exception {
        File file = new File("/Users/zms/workspace/mt/out/clr/ParseTest-Debug.exe");
        AssemblyLoader loader = AssemblyLoader.getDefaultAssemblyLoader(null);
        Assembly assembly = loader.loadAssembly(new FileInputSource(file));
        Assert.assertNotNull(assembly);
    }

}
