package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * URL书写格式
 * "jdbc:mysql://外网地址/数据库"
 */
public class AliDbUtil {

    String username = "normaluser";
    String psw = "Ujiangling296";
    static AliDbUtil util = null;

    private AliDbUtil() {

    }

    public static AliDbUtil of() {
        if (util == null) {
            return new AliDbUtil();
        }
        return util;
    }

    public Connection getConn() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://rm-2zes59o3348k8zg52ho.mysql.rds.aliyuncs.com/mjavafx", username, psw);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
