package com.jonbore.main;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;


/**
 * Created by bo.zhou1 on 2017/11/7.
 */
public class StartServer {

    /**
     * 启动服务监听
     * @param args
     */
    public static void main(String [] args) {
        Server server = new Server(8080);
        ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
        servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", PackagesResourceConfig.class.getCanonicalName());
        servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "com.jonbore.restful");
        Context context = new Context(server, null);
        context.addServlet(servletHolder, "/*");
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
