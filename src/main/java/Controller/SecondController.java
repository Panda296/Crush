
package Controller;

import Listener.AutoCompleteComboBoxListener;
import Util.LocalDbUtil;
import javafx.application.Platform;
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
    private TextField _1_1, _1_2, _2_1, _2_2, count, itemInput;
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
        int item_count = Integer.parseInt(count.getText());
        int count_1_1 = Integer.parseInt(_1_1.getText());
        int count_1_2 = Integer.parseInt(_1_2.getText());
        int count_2_1 = Integer.parseInt(_2_1.getText());
        int count_2_2 = Integer.parseInt(_2_2.getText());


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String msg = date + " " + time + " ,消耗: " + selectItem + " " + item_count + " 单位,其中一系一段消耗: " + count_1_1 + " 单位,一系二段消耗: " + count_1_2 + " 单位" +
                ",二系一段消耗: " + count_2_1 + " 单位 ,二系二段消耗: " + count_2_2 + " 单位,请核实数据是否正确,点击确定将保存数据.";
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
     * riqi
     */
    public void initDatePick() {
        datepick.setValue(LocalDate.now());
        datepick.setEditable(false);

    }
}


