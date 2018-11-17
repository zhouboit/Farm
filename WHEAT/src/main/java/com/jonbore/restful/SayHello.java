package com.jonbore.restful;

import com.alibaba.fastjson.JSON;
import com.jonbore.entity.wheat.User;
import com.jonbore.utils.file.FileUtils;
import com.jonbore.util.KafkaProducer;
import com.jonbore.utils.file.ZipArchiveUtil;
import jersey.repackaged.com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author bo.zhou1
 * @date 2017/11/7
 */
@Path("hello")
public class SayHello {

    private static Logger logger = LoggerFactory.getLogger(SayHello.class);

    @GET
    @Path("sayHello")
    @Produces("text/plain")
    public String sayHello() {
        User user = new User();
        user.setName("jonbore");
        user.setPassword("123456");
        logger.info("name: " + user.getName() + "; password: " + user.getPassword());
        return "name: " + user.getName() + "; password: " + user.getPassword();
    }

    @POST
    @Path("hello")
    @Produces("text/plain")
    public String sayHello(User user) {
        user.setName(user.getName() + System.currentTimeMillis());
        user.setPassword(user.getPassword() + System.currentTimeMillis());
        logger.info("name: " + user.getName() + "; password: " + user.getPassword());
        return "name: " + user.getName() + "; password: " + user.getPassword();
    }

    @GET
    @Path("zip")
    @Produces("text/plain")
    public String extractZip() {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dataStr = simpleDateFormat.format(new Date());
        String dir = "F:/170211/" + dataStr;
        String htmlPath = dir + "/zipHtml.html";
        String jsPath = dir + "/zipJS.js";
        try {
            FileUtils.writeToFile("this is html file" + dataStr, htmlPath);
            FileUtils.writeToFile("this is javaScript file" + dataStr, jsPath);
            ZipArchiveUtil.zip(dir, dir + ".zip", "GBK");
            File file = new File(dir);
            File[] files = file.listFiles();
            result = JSON.toJSONString(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GET
    @Path("write")
    @Produces("text/plain")
    public String writeFile() {
        String result = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dataStr = simpleDateFormat.format(new Date());
        String dir = "F:/" + dataStr;
        String htmlPath = dir + "/zipHtml.html";
        String jsPath = dir + "/zipJS.js";
        try {
            FileUtils.writeToFile("this is html file" + dataStr, htmlPath);
            FileUtils.writeToFile("this is javaScript file" + dataStr, jsPath);
            File file = new File(dir);
            File[] files = file.listFiles();
            result = JSON.toJSONString(files);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GET
    @Path("kafka")
    @Produces("text/plain")
    public String writeFile(@QueryParam("key") String key, @QueryParam("value") String value) {
        String result = "";
        KafkaProducer kafkaProducer = new KafkaProducer();
        kafkaProducer.producerCreate(key, value);
        Map<String, Object> params = Maps.newHashMap();
        params.put("key", key);
        params.put("value", value);
        result = JSON.toJSONString(params);
        return result;
    }
}
