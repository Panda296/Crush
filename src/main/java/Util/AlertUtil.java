package Util;

import javafx.scene.control.Alert;

public class AlertUtil {

    private AlertUtil() {
    }

    static AlertUtil util;

    public static AlertUtil of() {
        if (util == null) {
            return new AlertUtil();
        }
        return util;
    }

    public void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}
