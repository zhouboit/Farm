package com.jonbore.frame;

import com.jonbore.utils.common.PropertiesUtil;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created with IntelliJ IDEA.
 * Description: jetty server
 * User: Jonbo
 * Date: 2017-12-24
 * Time: 20:31
 */
public class JettyServer {

    private static Logger logger = Logger.getLogger(JettyServer.class);

    /**
     * init a jetty server, use the port and contextPath
     *
     * @param port        server listen port
     * @param contextPath webApp publish root prefix,start with '/'
     * @param serverName  init jetty server name, distinguish to other server
     */
    public static void startServer(int port, String contextPath, String serverName) {
        logger.info(serverName + " server starting......");
        PropertiesUtil.init();
        //port contextPath
        Server server = new Server(port);
        String webBase = Thread.currentThread().getClass().getResource("/").toString().replace("target/classes/", PropertiesUtil.getProperties("web.res.path"));
        String resourceBase = Thread.currentThread().getClass().getResource("/").toString().replace("target/classes/", PropertiesUtil.getProperties("configs.res.path"));
        logger.info(serverName + " server webPath:" + webBase);
        logger.info(serverName + " server configsPath:" + resourceBase);
        System.setProperty(PropertiesUtil.getProperties("configs.res.path.sys.key"), resourceBase);
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath(contextPath);
        webapp.setResourceBase(webBase);
        webapp.setDescriptor(webBase + "/WEB-INF/web.xml");
        webapp.setParentLoaderPriority(true);
        webapp.setClassLoader(Thread.currentThread().getContextClassLoader());

        server.setHandler(webapp);
        try {
            server.start();
            logger.info(serverName + " server start successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
