package Bean;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ConsumeBean {
    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleStringProperty item = new SimpleStringProperty();
    private SimpleDoubleProperty count_1_1 = new SimpleDoubleProperty();
    private SimpleDoubleProperty count_1_2 = new SimpleDoubleProperty();
    private SimpleDoubleProperty count_2_1 = new SimpleDoubleProperty();
    private SimpleDoubleProperty count_2_2 = new SimpleDoubleProperty();
    private SimpleDoubleProperty totalCount = new SimpleDoubleProperty();
    private SimpleDoubleProperty selectCount = new SimpleDoubleProperty();
    private SimpleDoubleProperty beltCount = new SimpleDoubleProperty();

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Override
    public String toString() {
        return "ConsumeBean{" +
                "id=" + id +
                ", date=" + date +
                ", item=" + item +
                ", count_1_1=" + count_1_1 +
                ", count_1_2=" + count_1_2 +
                ", count_2_1=" + count_2_1 +
                ", count_2_2=" + count_2_2 +
                ", totalCount=" + totalCount +
                ", selectCount=" + selectCount +
                ", bellCount=" + beltCount +
                '}';
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

    public String getItem() {
        return item.get();
    }

    public SimpleStringProperty itemProperty() {
        return item;
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public double getCount_1_1() {
        return count_1_1.get();
    }

    public SimpleDoubleProperty count_1_1Property() {
        return count_1_1;
    }

    public void setCount_1_1(double count_1_1) {
        this.count_1_1.set(count_1_1);
    }

    public double getCount_1_2() {
        return count_1_2.get();
    }

    public SimpleDoubleProperty count_1_2Property() {
        return count_1_2;
    }

    public void setCount_1_2(double count_1_2) {
        this.count_1_2.set(count_1_2);
    }

    public double getCount_2_1() {
        return count_2_1.get();
    }

    public SimpleDoubleProperty count_2_1Property() {
        return count_2_1;
    }

    public void setCount_2_1(double count_2_1) {
        this.count_2_1.set(count_2_1);
    }

    public double getCount_2_2() {
        return count_2_2.get();
    }

    public SimpleDoubleProperty count_2_2Property() {
        return count_2_2;
    }

    public void setCount_2_2(double count_2_2) {
        this.count_2_2.set(count_2_2);
    }

    public double getTotalCount() {
        return totalCount.get();
    }

    public SimpleDoubleProperty totalCountProperty() {
        return totalCount;
    }

    public void setTotalCount(double totalCount) {
        this.totalCount.set(totalCount);
    }

    public double getSelectCount() {
        return selectCount.get();
    }

    public SimpleDoubleProperty selectCountProperty() {
        return selectCount;
    }

    public void setSelectCount(double selectCount) {
        this.selectCount.set(selectCount);
    }

    public double getBeltCount() {
        return beltCount.get();
    }

    public SimpleDoubleProperty beltCountProperty() {
        return beltCount;
    }

    public void setBeltCount(double beltCount) {
        this.beltCount.set(beltCount);
    }
}
