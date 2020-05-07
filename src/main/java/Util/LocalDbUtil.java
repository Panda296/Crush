package Util;

import Bean.ConsumeBean;
import Bean.IronBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;


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

    public Connection getConn() {
        String url = "jdbc:ucanaccess://" + CacheUtil.of().getDbPath();
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        Connection connection = null;
        try {
            Class.forName(driver);
            try {
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }


    /**
     * 插入数据
     *
     * @param sqltext
     */
    public int insert(String sqltext) {
        Connection conn = getConn();
        int i = 0;
        try {
            Statement sql = conn.createStatement();
            i = sql.executeUpdate(sqltext);
            if (i > 0) {
                showAlert("数据保存成功");

            } else {
                showAlert("数据保存失败");
            }
            sql.close();
            conn.close();
            refreshDbData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 存储金矿石跟产出量
     *
     * @param sqltext
     */
    public int insertIron(String sqltext) {
        Connection conn = getConn();
        int i = 0;
        try {
            Statement sql = conn.createStatement();
            i = sql.executeUpdate(sqltext);
            if (i > 0) {
                showAlert("数据保存成功");
                sql.close();
                conn.close();
            } else {
                showAlert("数据保存失败");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        refreshIronData();
        return i;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }

    /**
     * 删除最后一条数据
     */
    public void delete() {
        Connection conn = getConn();
        try {
            Statement sql = conn.createStatement();
            String sqltext = "delete from consume_data where id = (select max(id) from consume_data)";
            int i = sql.executeUpdate(sqltext);
            if (i > 0) {
                AlertUtil.of().showAlert("数据删除成功！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            AlertUtil.of().showAlert("数据删除失败！");
        }


    }

    /**
     * 获取消耗实时记录
     *
     * @return
     */
    public ObservableList<ConsumeBean> refreshDbData() {
        Connection conn = getConn();
        ObservableList<ConsumeBean> list = FXCollections.observableArrayList();
        try {
            Statement sql = conn.createStatement();
            ResultSet query = sql.executeQuery("select * from consume_data");
            while (query.next()) {
                ConsumeBean bean = new ConsumeBean();
                bean.setId(query.getInt("id"));
                bean.setDate(query.getString("data"));
                bean.setItem(query.getString("item"));
                bean.setCount_1_1(query.getDouble("count_1_1"));
                bean.setCount_1_2(query.getDouble("count_1_2"));
                bean.setCount_2_1(query.getDouble("count_2_1"));
                bean.setCount_2_2(query.getDouble("count_2_2"));
                bean.setTotalCount(query.getDouble("totalcount"));
                bean.setSelectCount(query.getDouble("seleccount"));
                bean.setBeltCount(query.getDouble("beltcount"));
                list.add(bean);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    /**
     * 获取矿产实时记录
     */
    public ObservableList<IronBean> refreshIronData() {
        ObservableList<IronBean> list = FXCollections.observableArrayList();
        Connection conn = getConn();
        try {
            Statement sql = conn.createStatement();
            ResultSet query = sql.executeQuery("select * from iron");
            while (query.next()) {
                IronBean bean = new IronBean();
                bean.setId(query.getInt("id"));
                bean.setDate(query.getString("data"));
                bean.setIronInput(query.getDouble("input"));
                bean.setIronOut(query.getDouble("out"));
                list.add(bean);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    /**
     * 删除consume数据
     *
     * @param id
     * @return
     */
    public int deleteConsume(int id) {
        int i = 0;
        Connection conn = getConn();
        try {
            Statement sql = conn.createStatement();
            i = sql.executeUpdate("delete from consume_data where id=" + id);
            if (i > 0) {
                showAlert("删除成功");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            showAlert("删除失败");
        }
        return i;
    }

    /**
     * 删除Iron数据
     * @param id
     * @return
     */
    public int deleteIron(int id) {
        int i = 0;
        Connection conn = getConn();
        try {
            Statement sql = conn.createStatement();
            i = sql.executeUpdate("delete from iron where id=" + id);
            if (i > 0) {
                showAlert("删除成功");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            showAlert("删除失败");
        }
        return i;
    }
}
