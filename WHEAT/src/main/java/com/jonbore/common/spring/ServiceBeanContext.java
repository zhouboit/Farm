package com.jonbore.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceBeanContext {

	private static ServiceBeanContext context = null;

	private static ApplicationContext ctx=null;

	private ServiceBeanContext(){

	}

	public static ServiceBeanContext getInstance() {
		if( context == null ){
			context = new ServiceBeanContext();
		}
		return context;
	}

	public void loadContext(String path){
		ctx = new ClassPathXmlApplicationContext(path);
	}

	public <T> T getBean(String bean) {
        Object o = ctx.getBean(bean);
        if (o != null) {
            return (T) o;
        }
        return null;
    }
}
