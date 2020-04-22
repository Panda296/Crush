package Util;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class PathUtil {


    private PathUtil() {
    }


    public static PathUtil of() {
        return new PathUtil();
    }

    /**
     * +
     * 选择保存数据路径
     *
     * @return
     */
    public String pickPath() throws Exception {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("请选择数据存储路径");
        File file = chooser.showDialog(new Stage());
        if (file != null) {
            ProUtil.of().saveDataPath(file.getPath());
            return file.getPath();
        } else {
            return "";
        }
    }

    public String getDataPath() {

        return ProUtil.of().getDataPath();
    }
}
