package org.miaomiao.loader;

import java.io.*;

public class FileInputSource extends URLInputSource {

    public FileInputSource(File file) throws IOException {
        super(file.toURI().toURL());
    }

}
