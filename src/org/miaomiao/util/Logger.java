package org.miaomiao.util;

import java.util.logging.Level;

public class Logger {

    private java.util.logging.Logger logger;

    private Logger(Class klass) {
        this.logger = java.util.logging.Logger.getLogger(klass.getName());
    }

    public static Logger getLogger(Class klass) {
        return new Logger(klass);
    }

    void out(Level level,String text) {
        if(level==Level.WARNING) {
            logger.warning(text);
        } else {
            logger.info(text);
        }
    }

    void out(Level level,String format,Object... args) {
        out(level,String.format(format,args));
    }

    void out(Level level,Object obj) {
        out(level,String.valueOf(obj));
    }

    public void debug(String format,Object... args) {
        out(Level.FINEST, format,args);
    }

    public void debug(Object obj) {
        out(Level.FINEST,obj);
    }

    public void warn(String format,Object... args) {
        out(Level.WARNING, format,args);
    }

    public void warn(Object obj) {
        out(Level.WARNING,obj);
    }

    public void error(String format,Object... args) {
        out(Level.SEVERE, format,args);
    }

    public void error(Object obj) {
        out(Level.SEVERE,obj);
    }

    public void error(Throwable e) {
        e.printStackTrace(System.err);
        error(e.getMessage());
    }

}
