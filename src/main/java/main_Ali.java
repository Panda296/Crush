import Controller.mainContro;
import Util.AliDbUtil;
import Util.LocalDbUtil;
import Util.PathUtil;
import Util.ProUtil;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class main_Ali extends Application implements Initializable {
    @FXML
    private ComboBox comBox;


    @Override
    public void start(Stage primaryStage) throws Exception {


        URL location = getClass().getResource("Fxml/main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();


//        Parent root = FXMLLoader.load(getClass().getResource("Fxml/main.fxml"));
        primaryStage.setTitle("Powerd by Aliyun");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("start");

        mainContro mContro = fxmlLoader.getController();
        mContro.initComBox();

        try {
            initPath();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize");
    }

    /**
     * 设置数据库路径
     *
     * @throws Exception
     */
    private void initPath() throws Exception {
        String dataPath = ProUtil.of().getDataPath();
        if (dataPath == null) {
            String path = PathUtil.of().pickPath();
            LOGGER.info("PickedPath" + path);
        } else {
            String path = PathUtil.of().getDataPath();
            LOGGER.info("ProPath" + path);
        }


    }
}
