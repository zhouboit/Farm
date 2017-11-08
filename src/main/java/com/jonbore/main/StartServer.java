package com.jonbore.main;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;



/**
 * Created by bo.zhou1 on 2017/11/7.
 */
public class StartServer {

    private static Logger logger = Logger.getLogger(StartServer.class);
    /**
     * 启动服务监听
     * @param args
     */
    public static void main(String [] args) {
        logger.info("server starting......");
        Server server = new Server(8080);
        ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
        servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", PackagesResourceConfig.class.getCanonicalName());
        servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "com.jonbore.restful");
        logger.info("loading resource");
        Context context = new Context(server, null);
        context.addServlet(servletHolder, "/*");
        try {
            server.start();
            logger.info("start successfully");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
