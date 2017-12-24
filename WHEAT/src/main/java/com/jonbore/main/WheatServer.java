package com.jonbore.main;

import com.jonbore.frame.JettyServer;
import org.apache.log4j.Logger;


/**
 * Created by bo.zhou1 on 2017/11/7.
 */
public class WheatServer {

    private static Logger logger = Logger.getLogger(WheatServer.class);

    public static void main(String[] args) {
        JettyServer.startServer(8080, "/farm/wheat", "wheat");
    }
}
