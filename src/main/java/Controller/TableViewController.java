package Controller;

import Bean.ConsumeBean;
import Util.LocalDbUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private TableColumn date;

    @FXML
    private TableColumn item;

    @FXML
    private TableColumn count_1, count_2, count_3, count_4, count_5, count_6, count_7;


    @FXML
    private TableView tableView;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        System.out.println("initialize");
        initUI();
    }

    public void initUI() {
        ObservableList<ConsumeBean> dbData = LocalDbUtil.of().refreshDbData();
        System.out.println("dbData = " + dbData);
        tableView.setItems(dbData);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        item.setCellValueFactory(new PropertyValueFactory<>("item"));
        count_1.setCellValueFactory(new PropertyValueFactory<>("count_1_1"));
        count_2.setCellValueFactory(new PropertyValueFactory<>("count_1_2"));
        count_3.setCellValueFactory(new PropertyValueFactory<>("count_2_1"));
        count_4.setCellValueFactory(new PropertyValueFactory<>("count_2_2"));
        count_5.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
        count_6.setCellValueFactory(new PropertyValueFactory<>("selectCount"));
        count_7.setCellValueFactory(new PropertyValueFactory<>("bellCount"));
    }

    @FXML
    private void onClick() {
        TablePosition o = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
        TableColumn tableColumn = o.getTableColumn();
        Object data = tableColumn.getCellData(0);
        System.out.println("data = " + data);
    }
}
