package model;

/**
 * Created by Palmirouze on 2017-03-09.
 */
public class StockEntry {

    private String date;
    private Double value;

    public StockEntry(String date, Double value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String toString() {
        return this.date + " - " + this.getValue();
    }
}
