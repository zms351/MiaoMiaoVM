package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;
import org.miaomiao.util.Logger;

import java.io.IOException;

public abstract class BaseDataModel {

    protected final Logger logger=Logger.getLogger(this.getClass());

    public abstract void parse(InputStreamReader reader) throws IOException,LoadException;

    protected final Logger getLogger() {
        return logger;
    }

}
