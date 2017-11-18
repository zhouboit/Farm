package com.jonbore.main;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * Created by bo.zhou1 on 2017/11/7.
 */
public class StartServer {

    private static Logger logger = Logger.getLogger(StartServer.class);

    /**
     * 启动服务监听
     *
     * @param args
     */
    public static void main(String[] args) {
        logger.info("server starting......");
        Server server = new Server(8080);
        System.out.println(Thread.currentThread().getClass().getResource("/").toString());
        String resourceBase = Thread.currentThread().getClass().getResource("/").toString().replace("target/classes/","src/main/webapp");
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/farm");
        webapp.setResourceBase(resourceBase);
        webapp.setDescriptor(resourceBase + "/WEB-INF/web.xml");
        webapp.setParentLoaderPriority(true);
        webapp.setClassLoader(Thread.currentThread().getContextClassLoader());

        server.setHandler(webapp);
        try {
            server.start();
            logger.info("start successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
