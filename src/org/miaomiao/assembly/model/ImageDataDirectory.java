package org.miaomiao.assembly.model;

public class ImageDataDirectory extends RVAAndSize {

    public long getVirtualAddress() {
        return super.getAddress();
    }

}
