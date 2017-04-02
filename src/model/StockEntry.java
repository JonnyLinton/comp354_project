package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public Date getComparableDate()
    {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);

        try {
            cal.setTime(dateFormat.parse(this.date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = cal.getTime();
        return currentDate;
    }
}
