package org.miaomiao.assembly.model;

import org.miaomiao.assembly.LoadException;
import org.miaomiao.loader.InputStreamReader;

import java.io.IOException;

public abstract class BaseDataModel {

    public abstract void parse(InputStreamReader reader) throws IOException,LoadException;

}
