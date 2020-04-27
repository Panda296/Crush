import Controller.SecondController;
import Controller.mainContro;
import Util.PathUtil;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class main_Ali extends Application implements Initializable {
    @FXML
    private ComboBox comBox;
    private mainContro mContro;


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("Fxml/main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();


//        Parent root = FXMLLoader.load(getClass().getResource("Fxml/main.fxml"));
        primaryStage.setTitle("Powerd by 龙兴集团");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("start");

        mainContro mContro = fxmlLoader.getController();
        initUI(mContro);

        mContro.setDB_PATH(PathUtil.of().pickPath());
        System.out.println("controller.getDB_PATH() = " + mContro.getDB_PATH());
        makeBackUp(mContro);

//        try {
//            initPath();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 打开数据库时复制一分出来
     *
     * @param mContro
     */
    private void makeBackUp(mainContro mContro) {
        try {
            FileUtils.copyFile(new File(mContro.getDB_PATH() + "\\选矿厂.accdb"), new File("backup.accdb"));
            FileUtils.copyFileToDirectory(new File(mContro.getDB_PATH() + "\\选矿厂.accdb"), new File(mContro.getDB_PATH()+"\\backup"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("备份文件时出现错误");

        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 初始化界面显示
     *
     * @param mContro
     */
    private void initUI(mainContro mContro) {
        mContro.initComBox();
        mContro.onTextChanged();
        mContro.initDatePick();

    }


    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize");
    }

    /**
     * 设置数据库路径
     *
     * @throws Exception
     */
    private void initPath() {
        System.out.println("getClass().getResource(\"\") = " + getClass().getResource(""));
        URL resource = getClass().getResource("cache.json");
        System.out.println("resource.toString() = " + resource.toString());
        String localDbPath = PathUtil.of().getLocalDbPath(resource);
        if (localDbPath == null) {
            String selectPath = PathUtil.of().pickPath();
            PathUtil.of().savePathToCache(selectPath);
        }

//        String dataPath = ProUtil.of().getDataPath();
//        if (dataPath == null) {
//            String path = PathUtil.of().pickPath();
//            LOGGER.info("PickedPath" + path);
//        } else {
//            String path = PathUtil.of().getDataPath();
//            LOGGER.info("ProPath" + path);
//        }


    }
}
