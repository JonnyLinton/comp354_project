package model;

import java.util.HashMap;

/**
 * Created by Palmirouze on 2017-03-08.
 */
public class Stock
{
    class MovingAverage
    {
        private MovingAverageInterval interval;
        private HashMap<String, Double> data;

        // default: 20 days
        public MovingAverage()
        {
            this.interval = MovingAverageInterval.TwentyDay;
            data = null;
        }

        public MovingAverage(MovingAverageInterval interval, HashMap<String, Double> data)
        {
            this.interval = interval;
            this.data = data;
        }

        public MovingAverageInterval getInterval() {
            return interval;
        }

        public void setInterval(MovingAverageInterval interval) {
            this.interval = interval;
        }

        public HashMap<String, Double> getData() {
            return data;
        }

        public void setData(HashMap<String, Double> data) {
            this.data = data;
        }
    }

    private String name;
    private String ticker;
    private HashMap<String, Double> prices;
    private MovingAverage[] movingAverages;


    public Stock(String name, String ticker, String filePath)
    {
        this.name = name;
        this.ticker = ticker;

        // populate hashmap of prices with csv data from filepath


    }

    // I assume no constructor taking a map of prices


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTicker()
    {
        return ticker;
    }

    public void setTicker(String ticker)
    {
        this.ticker = ticker;
    }

    public void getPricesInRange(TimeInterval timeInterval)
    {

    }

    public MovingAverage getMovingAverage(MovingAverageInterval interval)
    {
        return new MovingAverage();
    }

    public void setMovingAverageOverInterval(MovingAverageInterval interval)
    {

    }
}
