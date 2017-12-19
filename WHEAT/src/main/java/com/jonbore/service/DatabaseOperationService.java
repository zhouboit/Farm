package com.jonbore.service;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by bo.zhou1 on 2017/11/8.
 */
public class DatabaseOperationService {

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConn(){
        Connection connection = null;
        try {
            String url="jdbc:mysql://192.168.5.12:3306/mes_db_zb?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
            String user = "root";
            String password = "123456";
            //1.加载驱动
            Driver driver = (Driver)Class.forName("com.mysql.jdbc.Driver").newInstance();
            //2.得到连接
            Properties info = new Properties();
            info.setProperty("user", user);
            info.setProperty("password", password);
            connection = driver.connect(url, info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 获取测试数据
     *
     * @return
     */
    public static String getData() throws SQLException {
        String result = "";
        Connection con = getConn();
        if(!con.isClosed())
            System.out.println("Succeeded connecting to the Database!");
        //2.创建statement类对象，用来执行SQL语句！！
        Statement statement = con.createStatement();
        //要执行的SQL语句
        String sql = "select * from mes_user";
        //3.ResultSet类，用来存放获取的结果集！！
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            Map<String, Object> data = new HashMap<>();
        }
        rs.close();
        con.close();
        return result;
    }
}
