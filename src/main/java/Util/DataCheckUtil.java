package Util;

/**
 * @Author
 * @Date Greated in 16:32$ 2020/4/28$
 * @Description
 */
public class DataCheckUtil {
    public double checkFormate(String data) {
        double d = 0;
        try {
            d = Double.valueOf(data);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            AlertUtil.of().showAlert(e.getMessage());
            return 0;
        }
        return d;
    }
}
