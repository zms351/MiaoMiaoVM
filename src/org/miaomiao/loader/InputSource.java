package org.miaomiao.loader;

import java.io.*;

public interface InputSource {

    public InputStream openSource() throws IOException;

}
