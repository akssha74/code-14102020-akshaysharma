package com.relayr.pcs.logging;

import java.io.IOException;
import java.util.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class GlobalLogger {
    public static Logger logger;
    public Handler fileHandler;
    SimpleFormatter plainText;

    private GlobalLogger() throws IOException{
        logger = Logger.getLogger(GlobalLogger.class.getName());
        plainText = new SimpleFormatter();
    }
    private static Logger getLogger(){
        if(logger == null){
            try {
                new GlobalLogger();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
    public static void log(Level level, String msg){
        getLogger().log(level, msg);
    }
}