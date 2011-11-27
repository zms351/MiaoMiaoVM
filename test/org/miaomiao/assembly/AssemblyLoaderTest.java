package org.miaomiao.assembly;

import org.miaomiao.loader.FileInputSource;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;

public class AssemblyLoaderTest {

    @AfterClass
    private void after() throws Exception {
        System.out.println("Test finished");
        System.out.println(System.in.read());
    }

    @Test
    public void testLoad() throws Exception {
        File file = new File("/Users/zms/workspace/mt/out/clr/ParseTest-Debug.exe");
        AssemblyLoader loader = AssemblyLoader.getDefaultAssemblyLoader(null);
        Assembly assembly = loader.loadAssembly(new FileInputSource(file));
        Assert.assertNotNull(assembly);
    }

}
