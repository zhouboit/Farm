package com.jonbore.main;

import com.jonbore.common.utils.PropertiesUtil;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;


/**
 * Created by bo.zhou1 on 2017/11/7.
 */
public class StartServer {

    private static Logger logger = Logger.getLogger(StartServer.class);

    public static void main(String[] args) {
        logger.info("server starting......");
        PropertiesUtil.init();
        Server server = new Server(8080);
        String webBase = Thread.currentThread().getClass().getResource("/").toString().replace("target/classes/", PropertiesUtil.getProperties("web.res.path"));
        String resourceBase = Thread.currentThread().getClass().getResource("/").toString().replace("target/classes/", PropertiesUtil.getProperties("configs.res.path"));
        logger.info("webPath:" + webBase);
        logger.info("configsPath:" + resourceBase);
        System.setProperty(PropertiesUtil.getProperties("configs.res.path.sys.key"), resourceBase);
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/farm");
        webapp.setResourceBase(webBase);
        webapp.setDescriptor(webBase + "/WEB-INF/web.xml");
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
