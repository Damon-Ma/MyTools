package com.damon.Util;


import org.apache.log4j.Logger;
import okhttp3.logging.HttpLoggingInterceptor;
/**
 * @ClassName Log
 * @Description TODO
 * @Author Damon
 * @Date 2018/12/6
 * @Version 1.0
 **/
public class Log implements HttpLoggingInterceptor.Logger{

    public static Logger logger = Logger.getLogger("");

    @Override
    public void log(String s) {
        if (s.startsWith("--> POST")||s.startsWith("--> GET")){
            logger.info("==========================================================================================");
        }
        logger.info("| "+s);
        if (s.startsWith("<-- END HTTP")){
            logger.info("==========================================================================================");
        }
    }
}
