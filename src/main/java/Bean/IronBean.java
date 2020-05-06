package Bean;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @Author
 * @Date Greated in 20:02$ 2020/5/6$
 * @Description
 */
public class IronBean {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleDoubleProperty ironInput = new SimpleDoubleProperty();
    private SimpleDoubleProperty ironOut = new SimpleDoubleProperty();

    @Override
    public String toString() {
        return "IronBean{" +
                "id=" + id +
                ", date=" + date +
                ", ironInput=" + ironInput +
                ", ironOut=" + ironOut +
                '}';
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public double getIronInput() {
        return ironInput.get();
    }

    public SimpleDoubleProperty ironInputProperty() {
        return ironInput;
    }

    public void setIronInput(double ironInput) {
        this.ironInput.set(ironInput);
    }

    public double getIronOut() {
        return ironOut.get();
    }

    public SimpleDoubleProperty ironOutProperty() {
        return ironOut;
    }

    public void setIronOut(double ironOut) {
        this.ironOut.set(ironOut);
    }
}
