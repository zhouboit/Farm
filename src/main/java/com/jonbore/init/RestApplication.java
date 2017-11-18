package com.jonbore.init;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("/rest/*")
public class RestApplication extends ResourceConfig {
	public RestApplication() {
        /**
         * 统一处理后台请求异常
         */
		register(RestConstraint.class);
        register(MultiPartFeature.class);
        register( RestJsonMapperProvider.class );
        register(JacksonFeature.class);
	}
}
