
package Controller;

import Bean.ConsumeBean;
import Bean.DbTag;
import Bean.IronBean;
import Util.AlertUtil;
import Util.CacheUtil;
import Util.LocalDbUtil;
import com.alibaba.fastjson.JSONObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

public class SecondController {

    @FXML
    private ComboBox<String> item;
    @FXML
    private DatePicker datepick;
    @FXML
    private TextField _1_1, _1_2, _2_1, _2_2, count, itemInput, ironInput, ironOut, belt, dryselect;
    @FXML
    private Text itemdetail;
    @FXML
    private TableColumn<Object, Object> date, col_item, count_1, count_2, count_3, count_4, count_5, count_6, count_7, col_id, iron_in, iron_out, iron_date, iron_id,
            id_s, data_s, item_s, count_1_s, count_2_s, count_3_s, count_4_s, count_5_s, count_6_s, total_s;
    @FXML
    private TableView<ConsumeBean> tableView, tv_s;
    @FXML
    private TableView<IronBean> iron_table;
    @FXML
    private CheckBox isSpecial;
    private Double price;
    private String number;


    public void onTextChanged() {
        itemInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals(oldValue)) {
                    System.out.println("TextChange Listener");
                    searchLikeNewValue(newValue);
                }
            }
        });
        item.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                JSONObject obj = ListsDetail.getJSONObject(newValue);
                String s = obj.getString("存货单位");
                price = obj.getDouble("结存单价");
                number = obj.getString("number");
                DecimalFormat format = new DecimalFormat("#0.000");
                format.format(price);
                itemdetail.setText("单位: " + s + "  单价: " + format.format(price));

            }
        });


    }


    private JSONObject ListsDetail;

    private void searchLikeNewValue(String newValue) {
        Connection conn = LocalDbUtil.of().getConn();
        ObservableList<String> list = FXCollections.observableArrayList();
        ListsDetail = new JSONObject();
        try {
            Statement sql = conn.createStatement();
            ResultSet query = sql.executeQuery("select * from List where Items like '*" + newValue + "*'");
            while (query.next()) {
                JSONObject obj = new JSONObject();
                obj.put("存货单位", query.getString("存货单位"));
                obj.put("结存单价", query.getString("结存单价"));
                obj.put("number", query.getString("存货编码"));
                ListsDetail.put(query.getString("Items"), obj);
                list.add(query.getString("Items"));
            }
            System.out.println("list.toString() = " + list.toString());
            query.close();
            sql.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        item.setItems(list);
        item.show();

    }

    /**
     * 存储消耗数据
     */
    @FXML
    private void onLoad() {
        System.out.println(datepick.getValue().toString());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time = format.format(new Date());
        System.out.println("time = " + time);
        String date = datepick.getValue().toString();
        String selectItem = item.getValue();
        /*耗材选定判断*/
        if (selectItem == null) {
            AlertUtil.of().showAlert("请选定耗材");
            return;
        }
        double item_count;
        double count_1_1;
        double count_1_2;
        double count_2_1;
        double count_2_2;
        double selectcount;
        double beltcount;

        /*数量格式判断*/
        try {
            item_count = Double.parseDouble(count.getText());
            count_1_1 = Double.parseDouble(_1_1.getText());
            count_1_2 = Double.parseDouble(_1_2.getText());
            count_2_1 = Double.parseDouble(_2_1.getText());
            count_2_2 = Double.parseDouble(_2_2.getText());
            selectcount = Double.parseDouble(dryselect.getText());
            beltcount = Double.parseDouble(belt.getText());
            if (item_count == 0) {
                item_count = count_1_1 + count_1_2 + count_2_1 + count_2_2 + selectcount + beltcount;
            }
        } catch (NumberFormatException e) {
            AlertUtil.of().showAlert("数据格式错误");
            return;
        }

        if (count_1_1 == 0 && count_1_2 == 0 && count_2_1 == 0 && count_2_2 == 0 && selectcount == 0 && beltcount == 0) {
            if (item_count == 0) {
                AlertUtil.of().showAlert("所有数据均为 0 ,请检查输入数据");
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String msg = date + " " + time + "\t\n" +
                "消耗: " + selectItem + " >>> " + item_count + " 单位,\t\n" +
                "其中粗碎消耗: >>> " + count_1_1 + "单位,\t\n" +
                "中碎消耗: >>> " + count_1_2 + " 单位,\t\n" +
                "细碎消耗: >>> " + count_2_1 + " 单位 ,\t\n" +
                "筛分消耗: >>> " + count_2_2 + " 单位,\t\n" +
                "干选消耗: >>> " + selectcount + " 单位,\t\n" +
                "皮带消耗: >>> " + beltcount + " 单位,\t\n" +
                "请核实数据是否正确,点击确定将保存数据.";
        alert.setContentText(msg);
        alert.setTitle("数据核实");
        alert.setHeaderText("请确认:");
        alert.initOwner(null);
        Optional<ButtonType> wait = alert.showAndWait();

        String consume_data = "consume_data";
        if (isSpecial.isSelected()) {
            consume_data = "special_consume";
        }

        if (wait.get().getButtonData().isDefaultButton()) {
            System.out.println("点击了确认");
            String insertdate = date + " " + time;
            String sql = "insert into " + consume_data + " (data,item,count_1_1,count_1_2,count_2_1,count_2_2,totalcount," +
                    "seleccount,beltcount,totalprice,numberid,price,price_1,price_2,price_3,price_4,price_5,price_6) values(#" +
                    insertdate + "#,'" +
                    item.getValue() + "'," +
                    count_1_1 + "," +
                    count_1_2 + "," +
                    count_2_1 + "," +
                    count_2_2 + "," +
                    item_count + "," +
                    selectcount + "," +
                    beltcount + ","
                    + item_count * price + ","
                    + "'" + number +"',"+
                    price + "," +
                    count_1_1 * price + "," +
                    count_1_2 * price + "," +
                    count_2_1 * price + "," +
                    count_2_2 * price + "," +
                    selectcount * price + "," +
                    beltcount * price  +
                    ")";

            System.out.println("sql = " + sql);


            int insert = LocalDbUtil.of().insert(sql);
            if (insert > 0) {
                initDbData();
                cleanInput();
            }
            cleanInput();
        }


    }


    /**
     * riqi
     */
    public void initDatePick() {
        datepick.setValue(LocalDate.now());
        datepick.setEditable(false);

    }


    @FXML
    private void onSaveIron() {
        LocalDate date = datepick.getValue();
        double OutIron;
        double inputIron;
        try {
            OutIron = Double.parseDouble(ironOut.getText());
            inputIron = Double.parseDouble(ironInput.getText());
            String sql = "insert into iron (data,input,out) values (#"
                    + date + "#,"
                    + inputIron + ","
                    + OutIron + ")";
            System.out.println("sql = " + sql);
            int i = LocalDbUtil.of().insertIron(sql);
            if (i > 0) {
                initDbData();
                cleanInput();
            }
        } catch (NumberFormatException e) {
            AlertUtil.of().showAlert(e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * 重新定位数据库位置
     */
    @FXML
    private void onReloadDb() {
        CacheUtil.of().pickPath();
    }


    private void cleanInput() {
//        _1_1, _1_2, _2_1, _2_2, count, itemInput, ironInput, ironOut, belt, dryselect;
        _1_1.setText("0");
        _1_2.setText("0");
        _2_1.setText("0");
        _2_2.setText("0");
        count.setText("0");
        ironInput.setText("0");
        ironOut.setText("0");
        belt.setText("0");
        dryselect.setText("0");
    }

    /**
     * 展示数据库中数据
     */
    public void initDbData() {
        ObservableList<ConsumeBean> dbData = LocalDbUtil.of().refreshDbData(DbTag.DB_CONSUME);
        Collections.reverse(dbData);
        tableView.setItems(dbData);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_item.setCellValueFactory(new PropertyValueFactory<>("item"));
        count_1.setCellValueFactory(new PropertyValueFactory<>("count_1_1"));
        count_2.setCellValueFactory(new PropertyValueFactory<>("count_1_2"));
        count_3.setCellValueFactory(new PropertyValueFactory<>("count_2_1"));
        count_4.setCellValueFactory(new PropertyValueFactory<>("count_2_2"));
        count_5.setCellValueFactory(new PropertyValueFactory<>("selectCount"));
        count_6.setCellValueFactory(new PropertyValueFactory<>("beltCount"));
        count_7.setCellValueFactory(new PropertyValueFactory<>("totalCount"));

        ObservableList<IronBean> ironDb = LocalDbUtil.of().refreshIronData();
        Collections.reverse(ironDb);
        iron_table.setItems(ironDb);
        iron_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        iron_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        iron_in.setCellValueFactory(new PropertyValueFactory<>("ironInput"));
        iron_out.setCellValueFactory(new PropertyValueFactory<>("ironOut"));


        ObservableList<ConsumeBean> spData = LocalDbUtil.of().refreshDbData(DbTag.DB_SPECIAL);
        Collections.reverse(spData);
        tv_s.setItems(spData);
        id_s.setCellValueFactory(new PropertyValueFactory<>("id"));
        data_s.setCellValueFactory(new PropertyValueFactory<>("date"));
        item_s.setCellValueFactory(new PropertyValueFactory<>("item"));
        count_1_s.setCellValueFactory(new PropertyValueFactory<>("count_1_1"));
        count_2_s.setCellValueFactory(new PropertyValueFactory<>("count_1_2"));
        count_3_s.setCellValueFactory(new PropertyValueFactory<>("count_2_1"));
        count_4_s.setCellValueFactory(new PropertyValueFactory<>("count_2_2"));
        count_5_s.setCellValueFactory(new PropertyValueFactory<>("selectCount"));
        count_6_s.setCellValueFactory(new PropertyValueFactory<>("beltCount"));
        total_s.setCellValueFactory(new PropertyValueFactory<>("totalCount"));


    }

    /**
     * 删除consume数据
     */
    @FXML
    private void delete_consume() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认");
        ConsumeBean consumeBean = tableView.getSelectionModel().getSelectedItem();
        if (consumeBean == null) {
            return;
        }
        System.out.println("consumeBean = " + consumeBean);
        int id = consumeBean.getId();
        alert.setContentText("是否删除第" + id + "条数据?");
        Optional<ButtonType> type = alert.showAndWait();
        if (type.get().equals(ButtonType.OK)) {
            int i = LocalDbUtil.of().deleteConsume(id);
            if (i > 0) {
                initDbData();
            }
        }


    }

    /**
     * 删除iron数据
     */
    @FXML
    private void delete_iron() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认");
        IronBean ironBean = iron_table.getSelectionModel().getSelectedItem();
        if (ironBean == null) {
            return;
        }
        int id = ironBean.getId();
        alert.setContentText("是否删除第" + id + "条数据?");
        Optional<ButtonType> type = alert.showAndWait();
        if (type.get().equals(ButtonType.OK)) {
            int i = LocalDbUtil.of().deleteIron(id);
            if (i > 0) {
                initDbData();
            }
        }
    }

    /**
     * 删除技改等项目数据
     */
    @FXML
    private void delete_special() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除确认");
        ConsumeBean bean = tv_s.getSelectionModel().getSelectedItem();
        if (bean == null) {
            return;
        }
        int id = bean.getId();
        alert.setContentText("是否删除第" + id + "条数据?");
        Optional<ButtonType> type = alert.showAndWait();
        if (type.get().equals(ButtonType.OK)) {
            int i = LocalDbUtil.of().deleteSpecial(id);
            if (i > 0) {
                initDbData();
            }
        }
    }
}


