package Controller;

import Util.LocalDbUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mainContro {


    @FXML
    private ComboBox comBox;
    @FXML
    private Button load;
    @FXML
    private DatePicker datepick;

    /**
     * 初始化ComBox
     */
    public void initComBox() {
        ObservableList<String> levelItem = LocalDbUtil.of().getLevelItem();
        comBox.setItems(levelItem);
        comBox.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    System.out.println("TextChange Listener");
                    searchLikeNewValue(newValue);
                }
            }
        });

        comBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("select Listener");
//                comBox.setItems(levelItem);
            }
        });
    }


    /**
     * 根据combox中的文字查询数据库
     *
     * @param newValue
     */
    private ObservableList<String> list;

    private void searchLikeNewValue(String newValue) {
        Connection conn = LocalDbUtil.of().getConn();
        list = FXCollections.observableArrayList();
        try {
            Statement sql = conn.createStatement();
            ResultSet query = sql.executeQuery("select 材料名称 from Sheet1 where 材料名称 like '*" + newValue + "*'");
            while (query.next()) {
                for (int i = 1; i < query.getMetaData().getColumnCount() + 1; i++) {
                    list.add(query.getString("材料名称"));
                }
            }
            System.out.println("list.toString() = " + list.toString());
            comBox.setItems(list);
            comBox.show();
            query.close();
            sql.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private void onLoad() {
        System.out.println(datepick.getValue().toString());
    }


}
