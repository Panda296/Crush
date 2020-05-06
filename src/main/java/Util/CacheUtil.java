package Util;

import com.alibaba.fastjson.JSONObject;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author
 * @Date Greated in 15:59$ 2020/4/29$
 * @Description
 */
public class CacheUtil {
    private static CacheUtil util;

    private CacheUtil() {
    }

    public static CacheUtil of() {
        if (util == null) {
            return new CacheUtil();
        } else {
            return util;
        }
    }

    synchronized
    public String getDbPath() {
        try {
            String property = System.getProperty("user.dir");
            System.out.println("property = " + property);
            String file = FileUtils.readFileToString(new File(property, "cache.json"), "UTF-8");
            JSONObject parse = (JSONObject) JSONObject.parse(file);
            String path = (String) parse.get("path");
            if (path == null) {
                return pickPath();
            } else {
                return path;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String pickPath() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(new Stage());
        String path = saveDbPath(file.getPath());
        return path;
    }

    private String saveDbPath(String path) {
        JSONObject object = new JSONObject();
        object.put("path", path);
        try {
            FileUtils.writeStringToFile(new File(System.getProperty("user.dir"), "cache.json"), object.toString(), "UTF-8", false);
            return path;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("数据写入缓存失败");
        }
        return "";
    }

}
