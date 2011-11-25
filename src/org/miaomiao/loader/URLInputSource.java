package org.miaomiao.loader;

import java.io.*;
import java.net.URL;

public class URLInputSource implements InputSource {

    private URL url;

    public URLInputSource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream openSource() throws IOException {
        return getUrl().openStream();
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

}
