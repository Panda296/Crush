package Util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class LocalDbUtil {

    static LocalDbUtil localDbUtil = null;

    private LocalDbUtil() {
    }

    public static LocalDbUtil of() {
        if (localDbUtil == null) {
            return new LocalDbUtil();
        }
        return localDbUtil;
    }

    public Connection getConn(String path) {
        String url = "jdbc:ucanaccess://" + path + "\\导入测试.accdb";
        LOGGER.info("url=" + url);
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        Connection connection = null;
        try {
            Class.forName(driver);
            try {
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.info("数据库链接失败" + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.info("数据库驱动加载失败" + e.getMessage());
        }

        return connection;
    }

//    public ObservableList<String> getLevelItem() throws Exception {
//        ObservableList<String> list = FXCollections.observableArrayList();
//        Connection con = getConn();
//        try {
//            Statement sql = con.createStatement();
//            String sqlText = "select 材料名称 from Sheet1";
//            ResultSet query = sql.executeQuery(sqlText);
//            while (query.next()) {
//                for (int i = 1; i < query.getMetaData().getColumnCount() + 1; i++) {
//                    list.add(query.getString(i));
//                }
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        System.out.println(list.toString());
//        return list;
//
//    }

    /**
     * 插入数据
     *
     * @param sqltext
     */
    public void insert(String sqltext,String path) {
        Connection conn = getConn(path);
        try {
            Statement sql = conn.createStatement();
            int i = sql.executeUpdate(sqltext);
            if (i > 0) {
                System.out.println("数据保存成功");
            } else {
                System.out.println("数据保存失败");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
