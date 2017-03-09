package model;

import javafx.scene.chart.XYChart;

import java.io.File;

import java.util.*;



/**
 * Created by Palmirouze on 2017-03-08.
 */
public class Stock
{
    class MovingAverage
    {
        private MovingAverageInterval interval;
        private List<StockEntry> movingAverageDataList;

        // default: 20 days
        public MovingAverage()
        {
            this.interval = MovingAverageInterval.TwentyDay;
            movingAverageDataList = null;
        }

        /**
         * TODO
         * @param interval
         * @param parentList
         */
        public MovingAverage(MovingAverageInterval interval, List<StockEntry> parentList)
        {
            this.interval = interval;

            switch(interval)
            {
                case TwentyDay:
                    // make a list of 20 day MA with parent list
                    break;

                case FiftyDay:
                    // make a list of 50 day MA with parent list
                    break;

                case HundredDay:
                    // make a list of 100 day MA with parent list
                    break;

                case TwoHundredDay:
                    // make a list of 200 day MA with parent list
                    break;


            }
        }

        public MovingAverageInterval getInterval() {
            return interval;
        }

        public void setInterval(MovingAverageInterval interval) {
            this.interval = interval;
        }

        public List<StockEntry> getData() {
            return movingAverageDataList;
        }

        public void setData(List<StockEntry> data) {
            this.movingAverageDataList = data;
        }


    }

    private String name;
    private String ticker;
    private List<StockEntry> data;
    private int dataSize;
    private MovingAverage[] movingAverages;


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
        movingAverages[0] = new MovingAverage(MovingAverageInterval.TwentyDay, this.data);
        movingAverages[1] = new MovingAverage(MovingAverageInterval.FiftyDay, this.data);
        movingAverages[2] = new MovingAverage(MovingAverageInterval.HundredDay, this.data);
        movingAverages[3] = new MovingAverage(MovingAverageInterval.TwoHundredDay, this.data);
    }


    /**
     *
     * @param timeInterval
     * @return
     */
    public XYChart.Series getPricesInRange(TimeInterval timeInterval)
    {

        XYChart.Series series = new XYChart.Series();


        switch(timeInterval)
        {
            case OneYear:




                break;

            case TwoYears:



                break;


            case FiveYears:



                break;


            case AllTime:



                break;
        }

        return series;
    }


    /**
     * TODO
     * Get moving average serie based on interval
     * @param interval
     * @return Series of moving average over interval
     */
    public XYChart.Series getMovingAverage(MovingAverageInterval interval)
    {
        XYChart.Series series = new XYChart.Series();

        switch(interval)
        {
            case TwentyDay:
                series = putMovingAverageInSeries(0, this.movingAverages, series);
                break;

            case FiftyDay:
                series = putMovingAverageInSeries(1, this.movingAverages, series);
                break;

            case HundredDay:
                series = putMovingAverageInSeries(2, this.movingAverages, series);
                break;

            case TwoHundredDay:
                series = putMovingAverageInSeries(3, this.movingAverages, series);
                break;
        }

        return series;
    }


    // HELPER FUNCTIONS:


    /**
     * Populates the list from the CSV file
     * @param dataList
     * @param filePath
     * @return Size of the list
     */
    public static int populateList(List<StockEntry> dataList, String filePath)
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


    /**
     *TODO
     * @param index
     * @param array
     * @param series
     * @return Series of moving averages to plug in the graph
     */
    public static XYChart.Series putMovingAverageInSeries(int index, MovingAverage[] array, XYChart.Series series)
    {
        for(StockEntry entries : array[index].movingAverageDataList)
        {
            series.getData().add(new XYChart.Data(entries.getDate(), entries.getValue()));
        }

        return series;
    }



    // MAIN FOR TRYOUTS
    public static void main(String [] args)
    {
        Stock exampleStock = new Stock("Dummy Stock", "STOK", "Sample data.csv");

        // prints all the entries
        for(StockEntry entry: exampleStock.data)
        {
            System.out.println(entry.toString());
        }

    }



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

    public List<StockEntry> getData() {
        return data;
    }

    public void setData(List<StockEntry> data) {
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


    /** HELPER FOR MA INNER CLASS
     * Helps put MAs in list when creating a new MA object for the right time interval
     * @param interval
     * @return
     */
    private LinkedList<StockEntry> computeMovingAverages(int interval)
    {

        StockEntry temp;




        return new LinkedList<>();
    }



}
