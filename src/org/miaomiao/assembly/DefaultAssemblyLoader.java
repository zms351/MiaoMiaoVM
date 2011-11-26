package org.miaomiao.assembly;

import org.miaomiao.assembly.model.ExeModel;
import org.miaomiao.loader.*;

import java.io.IOException;
import java.util.Properties;

class DefaultAssemblyLoader extends AssemblyLoader {

    private Properties props;

    public DefaultAssemblyLoader(Properties props) {
        this.props = props == null ? new Properties() : props;
    }

    public Properties getProps() {
        return props;
    }

    @Override
    public Assembly loadAssembly(InputSource source) throws LoadException, IOException {
        ExeModel model = new ExeModel();
        model.parse(new InputStreamReader(source));
        return model;
    }

}
