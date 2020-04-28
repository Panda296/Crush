
package Controller;

import Util.AlertUtil;
import Util.LocalDbUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class SecondController {

    String DB_PATH;
    @FXML
    private ComboBox<String> item;
    @FXML
    private Button load;
    @FXML
    private DatePicker datepick;

    @FXML
    private TextField _1_1, _1_2, _2_1, _2_2, count, itemInput, ironInput, ironOut;
    @FXML
    private TextArea record;


    private ObservableList<String> levelItem;


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
    }


    /**
     * 初始化ComBox
     */
    public void initComBox() {
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                //获取所有耗材项目
//                levelItem = LocalDbUtil.of().getLevelItem();
//                item.setItems(levelItem);
//            }
//        });

//        AutoCompleteComboBoxListener listener = new AutoCompleteComboBoxListener<item>();

//
//        item.getEditor().textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//
//            }
//        });

//        item.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                System.out.println("select Listener");
////                comBox.setItems(levelItem);
////                item.setItems(levelItem);
//                itemInput.setText("");
//            }
//        });
    }


    /**
     * 根据combox中的文字查询数据库
     *
     * @param newValue
     */
    private ObservableList<String> list;

    private void searchLikeNewValue(String newValue) {
        Connection conn = LocalDbUtil.of().getConn(DB_PATH);
        list = FXCollections.observableArrayList();
        try {
            Statement sql = conn.createStatement();
            ResultSet query = sql.executeQuery("select Items from List where Items like '*" + newValue + "*'");
            while (query.next()) {
                for (int i = 1; i < query.getMetaData().getColumnCount() + 1; i++) {
                    list.add(query.getString("Items"));
                }
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

    public void setDB_PATH(String DB_PATH) {
        this.DB_PATH = DB_PATH;
    }

    public String getDB_PATH() {
        return DB_PATH;
    }

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
        int item_count = 0;
        int count_1_1 = 0;
        int count_1_2 = 0;
        int count_2_1 = 0;
        int count_2_2 = 0;
        /*数量格式判断*/
        try {
            item_count = Integer.parseInt(count.getText());
            count_1_1 = Integer.parseInt(_1_1.getText());
            count_1_2 = Integer.parseInt(_1_2.getText());
            count_2_1 = Integer.parseInt(_2_1.getText());
            count_2_2 = Integer.parseInt(_2_2.getText());
        } catch (NumberFormatException e) {
//            showAlert("数据格式错误");
            AlertUtil.of().showAlert("数据格式错误");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String msg = date + " " + time + "\t\n消耗: " + selectItem + " >>> " + item_count + " 单位,\t\n其中粗碎消耗: >>> " + count_1_1 + "单位,\t\n中碎消耗: >>> " + count_1_2 + " 单位" +
                ",\t\n细碎消耗: >>> " + count_2_1 + " 单位 ,\t\n筛分消耗: >>> " + count_2_2 + " 单位,\t\n请核实数据是否正确,点击确定将保存数据.";
        alert.setContentText(msg);
        alert.setTitle("数据核实");
        alert.setHeaderText("请确认:");
        alert.initOwner(null);
        Optional<ButtonType> wait = alert.showAndWait();
        if (wait.get().getButtonData().isDefaultButton()) {
            System.out.println("点击了确认");
            String insertdate = date + " " + time;

            String sql = "insert into consume_data (data,item,count_1_1,count_1_2,count_2_1,count_2_2,totalcount) values(#" +
                    insertdate + "#,'" +
                    item.getValue() + "'," +
                    count_1_1 + "," +
                    count_1_2 + "," +
                    count_2_1 + "," +
                    count_2_2 + "," +
                    item_count + ")";

            System.out.println("sql = " + sql);

            record.appendText(msg + "\r\n");

            LocalDbUtil.of().insert(sql, DB_PATH);
        }


    }

    /**
     * Alert提示
     *
     * @param msg 信息内容
     */
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
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
        String inputIron = ironInput.getText();
        String OutIron = ironOut.getText();
        String sql = "insert into iron (data,input,out) values (#"
                + date + "#,"
                + inputIron + ","
                + OutIron + ")";
        System.out.println("sql = " + sql);
        LocalDbUtil.of().insertIron(sql, DB_PATH);

    }
}


