package model;

import javafx.scene.chart.XYChart;

import java.io.File;

import java.util.*;



/**
 * Created by Palmirouze on 2017-03-08.
 */
public class Stock
{

    // INNER CLASS MOVING AVERAGE
    class MovingAverage
    {
        // enum type
        private MovingAverageInterval interval;

        // list of StockEntry objects
        private LinkedList<StockEntry> movingAverageDataList;

        /**
         * default constructor for moving average class
         * default interval is 20 days
         * list will not contain anything
         */
        public MovingAverage()
        {
            this.interval = MovingAverageInterval.TwentyDay;
            movingAverageDataList = null;
        }

        /**
         * Constructor for moving average
         * Constructor takes care of computing the MAs
         * @param interval
         */
        public MovingAverage(MovingAverageInterval interval)
        {
            this.interval = interval;

            switch(interval)
            {
                case TwentyDay:
                    this.movingAverageDataList = computeMovingAverages(20);
                    break;

                case FiftyDay:
                    this.movingAverageDataList = computeMovingAverages(50);
                    break;

                case HundredDay:
                    this.movingAverageDataList = computeMovingAverages(100);
                    break;

                case TwoHundredDay:
                    this.movingAverageDataList = computeMovingAverages(200);
                    break;
            }
        }

        // getter/setter
        public MovingAverageInterval getInterval() {
            return interval;
        }

        public void setInterval(MovingAverageInterval interval) {
            this.interval = interval;
        }

        public LinkedList<StockEntry> getData() {
            return movingAverageDataList;
        }

        public void setData(LinkedList<StockEntry> data) {
            this.movingAverageDataList = data;
        }


    }
    // -- END OF INNER CLASS MOVING AVERAGE --


    // -- START OF STOCK CLASS --

    // holds name of stock
    private String name;

    // holds ticker of stock
    private String ticker;

    // list holding all the StockEntry objects (containing pairs of String/Double)
    private LinkedList<StockEntry> data;

    // size of the List
    private int dataSize;

    // array of precomputed moving averages
    private MovingAverage[] movingAverages;

    // CONSTRUCTORS

    /**
     * Default constructor
     * Sets everything to empty, initializes array with default moving average objects
     */
    public Stock()
    {
        this.name = "";
        this.ticker = "";
        this.data = null;
        this.dataSize = 0;
        this.movingAverages = new MovingAverage[4];

        for(int i = 0; i < 4 ; i++)
        {
            movingAverages[i] = new MovingAverage();
        }
    }

    /**
     * Main constructor for stock object
     * Sets name and ticker
     * Fills the linked list with data from the csv (filePath)
     * Initializes array of MA and computes the data
     * @param name
     * @param ticker
     * @param filePath
     */
    public Stock(String name, String ticker, String filePath)
    {
        this.name = name;
        this.ticker = ticker;

        // new linked list
        this.data = new LinkedList<StockEntry>();

        // fills the list with all the values from the csv
        this.dataSize = populateList(data, filePath);

        // initialize array of MAs
        movingAverages = new MovingAverage[4];

        // set each MA to its right interval and fills it with the right computed values
        movingAverages[0] = new MovingAverage(MovingAverageInterval.TwentyDay);
        movingAverages[1] = new MovingAverage(MovingAverageInterval.FiftyDay);
        movingAverages[2] = new MovingAverage(MovingAverageInterval.HundredDay);
        movingAverages[3] = new MovingAverage(MovingAverageInterval.TwoHundredDay);
    }


    // METHODS TO GET SERIES TO PLUG IN GRAPH
    /** OUTPUT SERIES OF PRICES
     * Cuts the data and outputs a list
     * @param timeInterval
     * @return series of closing prices
     */
    public XYChart.Series getPricesInRange(TimeInterval timeInterval)
    {
        XYChart.Series series = new XYChart.Series();
        LinkedList<StockEntry> truncatedList = new LinkedList<StockEntry>();

        if(timeInterval == TimeInterval.AllTime)
        {
            truncatedList = this.data;
        }
        else
        {
            truncatedList = truncateList(this.data, timeInterval);
        }

        series = listToSerie(truncatedList);

        return series;
    }


    /** OUTPUT SERIES OF MA
     * Prob need to adapt to selected timeline of graph (return 254 MAs if timeline is one year for stock)
     * Get moving average serie based on interval
     * @param interval
     * @return Series of moving average over interval
     */
    public XYChart.Series getMovingAverage(MovingAverageInterval interval, TimeInterval timeInterval)
    {
        XYChart.Series series = new XYChart.Series();

        LinkedList<StockEntry> truncatedList;

        switch(interval)
        {
            case TwentyDay:
                truncatedList = truncateList(this.movingAverages[0].movingAverageDataList, timeInterval);
                series = listToSerie(truncatedList);
                break;

            case FiftyDay:
                truncatedList = truncateList(this.movingAverages[1].movingAverageDataList, timeInterval);
                series = listToSerie(truncatedList);
                break;

            case HundredDay:
                truncatedList = truncateList(this.movingAverages[2].movingAverageDataList, timeInterval);
                series = listToSerie(truncatedList);
                break;

            case TwoHundredDay:
                truncatedList = truncateList(this.movingAverages[3].movingAverageDataList, timeInterval);
                series = listToSerie(truncatedList);
                break;
        }

        return series;
    }


    // HELPER METHODS FOR OUTPUTING SERIES/TRUNCATING LISTS
    public XYChart.Series listToSerie(LinkedList<StockEntry> list)
    {
        XYChart.Series series = new XYChart.Series();

        for(StockEntry entries : list)
        {
            series.getData().add(new XYChart.Data(entries.getDate(), entries.getValue()));
        }

        return series;
    }


    private LinkedList<StockEntry> truncateList(LinkedList<StockEntry> list, TimeInterval timeInterval)
    {
        LinkedList<StockEntry> tempList = new LinkedList<StockEntry>();
        LinkedList<StockEntry> tempFullData = list;

        int TIMEFRAME = 0;

        switch(timeInterval)
        {
            case OneYear:
                TIMEFRAME = 254;
                break;

            case TwoYears:
                TIMEFRAME = 505;
                break;

            case FiveYears:
                TIMEFRAME = 1259;
                break;
        }

        for (int i = 0; i < TIMEFRAME; i++)
        {
            tempList.add(tempFullData.removeLast());
        }

        return tempList;
    }





    // HELPER METHODS FOR CREATING STOCK OBJECT

    /** HELPER FUNCTION FOR MOVING AVERAGE CLASS
     * Computes the moving averages values
     * @param interval
     * @return
     */
    private LinkedList<StockEntry> computeMovingAverages(int interval)
    {
        LinkedList<StockEntry> tempList = data;
        LinkedList<StockEntry> movingAverageList = new LinkedList<StockEntry>();
        Queue<Double> queueTemp = new LinkedList<Double>();

        double movingAverageTotal = 0;
        int count = 0;

        StockEntry temp;
        for (int i = tempList.size() ; i > 0 ; i--)
        {
            temp = tempList.removeLast();

            if(queueTemp.size()< movingAverageTotal)
            {
                queueTemp.add(temp.getValue());
                movingAverageTotal += temp.getValue();
            }
            else
            {
                if(count%interval == 0)
                {
                    movingAverageList.add(new StockEntry(temp.getDate(), movingAverageTotal/interval));
                }

                movingAverageTotal -= queueTemp.poll();
                queueTemp.add(temp.getValue());
                movingAverageTotal += temp.getValue();
                count++;
            }
        }

        return movingAverageList;
    }


    /** HELPER FOR STOCK CLASS
     * Populates the list from the CSV file
     * @param dataList
     * @param filePath
     * @return Size of the list
     */
    public static int populateList(LinkedList<StockEntry> dataList, String filePath)
    {
        Scanner csvScanner = null;

        int counter = 0;

        try
        {
            csvScanner = new Scanner(new File(filePath));

            csvScanner.nextLine(); // skip first line

            String each_line;
            String[] columns;

            while(csvScanner.hasNextLine())
            {
                each_line = csvScanner.nextLine();
                columns = each_line.split(",");

                dataList.add(new StockEntry(columns[0], Double.parseDouble(columns[6])));

                counter++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            csvScanner.close();
        }

        return counter;
    }





    // GETTERS AND SETTERS
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

    public LinkedList<StockEntry> getData() {
        return data;
    }

    public void setData(LinkedList<StockEntry> data) {
        this.data = data;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public MovingAverage[] getMovingAverages() {
        return movingAverages;
    }

    public void setMovingAverages(MovingAverage[] movingAverages) {
        this.movingAverages = movingAverages;
    }
}
