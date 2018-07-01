package com.jonbore.utils.db;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * 数据库连接
 *
 * @author bo.zhou
 * @date 2018/7/1
 */
public class DBUtils {
    public static Logger logger = LoggerFactory.getLogger(DBUtils.class);

    /**
     * 获取数据库连接
     *
     * @param username
     * @param password
     * @param url
     * @param driver
     * @return
     */
    public static Connection getConnection(String username, String password, String url, String driver) {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
            if (!connection.isClosed()) {
                logger.info("初始化数据库连接成功");
            }
        } catch (Exception e) {
            logger.error("初始化数据库连接异常");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 获取数据库中指定表的数据
     *
     * @param username
     * @param password
     * @param url
     * @param driver
     * @param tableName
     * @return
     */
    public static List<Map<String, Object>> getTableData(String username, String password, String url, String driver, String tableName) {
        List<Map<String, Object>> result = Lists.newArrayList();
        try {
            Connection connection = DBUtils.getConnection(username, password, url, driver);
            Statement statement = connection.createStatement();
            String sql = "select * from " + tableName;
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> record = Maps.newHashMap();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    record.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }
                result.add(record);
            }
        } catch (Exception e) {
            logger.error("获取数据表" + tableName + "的数据失败");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 数据库插入数据，每条记录字段值不同
     *
     * @param username
     * @param password
     * @param url
     * @param driver
     * @param tableName
     * @param data
     */
    public static void insertTableColumnData(String username, String password, String url, String driver, String tableName, List<Map<String, Object>> data) {
        try {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+"================================");
            Connection connection = DBUtils.getConnection(username, password, url, driver);
            String sql = "insert into " + tableName + " ";
            String left = " ( ";
            String right = " ) ";
            for (Map<String, Object> datum : data) {
                StringBuilder cols = new StringBuilder();
                StringBuilder placeHolder = new StringBuilder();
                Map<Integer, Object> record = Maps.newHashMap();
                int index = 1;
                for (String s : datum.keySet()) {
                    cols.append(", ").append(s);
                    placeHolder.append(", ?");
                    record.put(index, datum.get(s));
                    index++;
                }
                cols.toString().substring(1);
                placeHolder.toString().substring(1);
                PreparedStatement preparedStatement = connection.prepareStatement(sql.concat(left).
                        concat(cols.toString().substring(1)).
                        concat(right).
                        concat(" values ").
                        concat(left).
                        concat(placeHolder.toString().substring(1).
                                concat(right)));
                for (Integer integer : record.keySet()) {
                    preparedStatement.setObject(integer, record.get(integer));
                }
                System.out.println(preparedStatement.toString().substring(preparedStatement.toString().indexOf(":")));
                preparedStatement.execute();
            }

        } catch (Exception e) {
            logger.error("插入数据失败");
            e.printStackTrace();
        }
    }

    /**
     * 数据库插入数据，每条记录字段值相同
     *
     * @param username
     * @param password
     * @param url
     * @param driver
     * @param tableName
     * @param data
     */
    public static void insertTableData(String username, String password, String url, String driver, String tableName, List<Map<String, Object>> data) {
        try {
            System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+"================================");
            Connection connection = DBUtils.getConnection(username, password, url, driver);
            String sql = "insert into " + tableName + " ";
            String left = " ( ";
            String right = " ) ";
            StringBuilder column = new StringBuilder();
            StringBuilder placeHolder = new StringBuilder();
            for (String s : data.get(0).keySet()) {
                column.append(", ").append(s);
                placeHolder.append(", ?");
            }

            PreparedStatement preparedStatement = connection.
                    prepareStatement(sql.concat(left).
                            concat(column.toString().substring(1)).
                            concat(right).
                            concat(" values ").
                            concat(left).
                            concat(placeHolder.toString().substring(1)).
                            concat(right));
            connection.setAutoCommit(false);
            for (Map<String, Object> datum : data) {
                int index = 1;
                for (String s : datum.keySet()) {
                    preparedStatement.setObject(index, datum.get(s));
                    index++;
                }
                System.out.println(preparedStatement.toString().substring(preparedStatement.toString().indexOf(":")));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);

        } catch (Exception e) {
            logger.error("插入数据失败");
            e.printStackTrace();
        }
    }
}
