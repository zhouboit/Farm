package com.jonbore.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jonbo on 2017/11/11.
 */
public class DispatchServlet extends HttpServlet{
    public static final Logger log = LoggerFactory.getLogger(DispatchServlet.class);
    private static final long serialVersionUID = -2871793876774691057L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            ServiceBeanContext.getInstance().loadContext("application.xml");
        } catch (Exception e) {
            log.error("启动初始化applicationContext.xml失败",e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
