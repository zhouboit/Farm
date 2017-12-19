package com.jonbore.init;



import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import java.text.SimpleDateFormat;

/**
 *
 * Created by Administrator on 2015/12/2.
 */
public class RestJsonMapperProvider implements ContextResolver<ObjectMapper> {

    private ObjectMapper defaultObjectMapper;

    public RestJsonMapperProvider() {
        defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure( SerializationFeature.INDENT_OUTPUT, true );
        defaultObjectMapper.setDateFormat( new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" ) );
        defaultObjectMapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return defaultObjectMapper;
    }
}