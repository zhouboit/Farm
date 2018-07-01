package utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jonbore.utils.EncryptUtil;
import com.jonbore.utils.db.DBUtils;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author bo.zhou
 * @date 2018/7/1
 */
public class DBUtilsTest {

    private static String username = "root";
    private static String password = "123456";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/jonbore";

    @Test
    public void getTableData() {
        List<Map<String, Object>> result = DBUtils.getTableData(username, password, url, driver, "plat_user");
        for (Map<String, Object> stringObjectMap : result) {
            for (String s : stringObjectMap.keySet()) {
                System.out.println(s + " : " + stringObjectMap.get(s));
            }
        }
        System.out.println(result.size() + "条记录");
    }

    @Test
    public void insertTableData() {
        List<Map<String, Object>> data = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> rec = Maps.newHashMap();
            rec.put("password", EncryptUtil.encryptStr("password" + i));
            rec.put("login_name", "login_name" + i);
            rec.put("user_name", "user_name" + i);
            rec.put("id", EncryptUtil.generationID());
            rec.put("create_date", new Date());
            rec.put("update_date", new Date());
            rec.put("current_value", 1000 + i);
            rec.put("max_value", 2000 + i);
            data.add(rec);
        }
        Long firstStart = System.currentTimeMillis();
        DBUtils.insertTableColumnData(username, password, url, driver, "plat_user", data);
        Long firstTime = System.currentTimeMillis() - firstStart;
        for (Map<String, Object> datum : data) {
            datum.put("id", EncryptUtil.generationID());
        }
        Long secondStart = System.currentTimeMillis();
        DBUtils.insertTableData(username, password, url, driver, "plat_user", data);
        Long secondTime = System.currentTimeMillis() - secondStart;
        System.out.println("firstTime: " + firstTime);
        System.out.println("secondTime: " + secondTime);
        System.out.println("reduce: " + (firstTime - secondTime));
    }
}
