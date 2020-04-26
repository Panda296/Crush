package Util;

import com.alibaba.fastjson.JSONObject;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    public String pickPath() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("请选择数据存储路径");
        File file = chooser.showDialog(new Stage());
        if (file != null) {
//            PathUtil.of().savePathToCache(file.getPath());
            return file.getPath();
        } else {
            return "";
        }
    }

    /**
     * 将选择的数据库路径缓存到笨的
     * @param path
     */
    public void savePathToCache(String path) {
        JSONObject object = new JSONObject();
        object.put("path", path);
        String cachePath = object.toString();
        String file1 = getClass().getResource("cache.json").getFile();
        System.out.println("file1 = " + file1);
        File file = new File(file1);
        try {
            FileUtils.writeStringToFile(file, cachePath, "UTF-8",false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getDataPath()  {
        if (ProUtil.of().getDataPath().equals(null)) {
            return pickPath();
        }
        return ProUtil.of().getDataPath();
    }

    /**
     * 文件路径选择器
     * @return
     */
    public String pickFilePath() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("请选择所要链接数据库");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            ProUtil.of().saveDataPath(file.getPath());
            return file.getPath();
        }
        return "";
    }

    /**
     * 获取缓存的数据库路径
     * @return
     * @param resources
     */
    public String getLocalDbPath(URL resources){
        String file1 = resources.getFile();
        System.out.println("file1 = " + file1);
        File file = new File(file1);
        try {
            String cacheString = FileUtils.readFileToString(file, "UTF-8");
            JSONObject parse = (JSONObject) JSONObject.parse(cacheString);
            String path = parse.getString("path");
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
