import Controller.SecondController;
import Util.CacheUtil;
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
    private SecondController mContro;


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL location = getClass().getResource("Fxml/second.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Powerd by 龙兴集团");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("start");

        mContro = fxmlLoader.getController();
//        mContro.initUI();
        initUI(mContro);


        String dbPath = CacheUtil.of().getDbPath();

        makeBackUp(mContro);

//        try {
//            initPath();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    /**
     * 打开数据库时复制一分出来
     * 数据库名称在切换选矿厂跟碎矿厂的时候需要更改
     *
     * @param mContro
     */
    private void makeBackUp(SecondController mContro) {
        try {
            FileUtils.copyFile(new File(CacheUtil.of().getDbPath()), new File("backup.accdb"));
            File sourceFile = new File(CacheUtil.of().getDbPath());
            System.out.println("sourceFile = " + sourceFile);
            File targetFile = new File(CacheUtil.of().getDbPath().replace("碎矿厂.accdb", "") + "\\backup");
            System.out.println("targetFile = " + targetFile);
            FileUtils.copyFileToDirectory(sourceFile, targetFile);

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
    private void initUI(SecondController mContro) {
        mContro.onTextChanged();
        mContro.initDatePick();
        mContro.initDbData();

    }


    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize");
    }




}
