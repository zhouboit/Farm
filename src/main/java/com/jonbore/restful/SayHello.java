package com.jonbore.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by bo.zhou1 on 2017/11/7.
 */
@Path("hello")
public class SayHello {

    @GET
    @Path("sayHello")
    @Produces("text/plain")
    public String sayHello(){
        return "helle farmer";
    }
}
