package Util;

import java.util.prefs.Preferences;

public class ProUtil {

    private static Preferences root;

    private ProUtil() {
        initPro();
    }

    private void initPro() {
        if (root == null) {
            root = Preferences.userRoot();
        }
    }

    public static ProUtil of() {
        return new ProUtil();
    }

    public void save(String key, String value) {
        root.put(key, value);
    }

    public String get(String key) {
        String get = root.get("key", null);
        return get;
    }

    public int getInt(String key) {
        int get = root.getInt(key, 0);
        return get;
    }

    public double getDouble(String key) {
        double get = root.getDouble(key, 0);
        return get;
    }

    public boolean getBoolean(String key) {
        boolean get = root.getBoolean(key, true);
        return get;
    }


    public void saveDataPath(String path) {
        root.put("path", path + "\\newdata.accdb");
    }

    public String getDataPath() {
        String path = root.get("path", null);
        return path;
    }

}
