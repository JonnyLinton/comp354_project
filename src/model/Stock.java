package model;

import javafx.scene.chart.XYChart;
import java.io.File;
import java.util.*;

/**
 * Created by Palmirouze on 2017-03-08.
 */
public class Stock
{
    // -- INNER CLASS MOVING AVERAGE --
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
    
    // the amount the data points are being divided by for the 5 year data
    public final int FIVEYEARDIVIDER = 2;
    
    // the number of total Data points for All time data list
    public final int ALLTIMEDATAPOINTS = 500;
    
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
//
//        // set each MA to its right interval and fills it with the right computed values
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
    public XYChart.Series<String, Number> getPricesInRange(TimeInterval timeInterval)
    {
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        LinkedList<StockEntry> truncatedList = new LinkedList<StockEntry>();

        truncatedList = truncateList(this.data, timeInterval);
        //*************************************************************
        if (TimeInterval.AllTime == timeInterval)
        	truncatedList = this.RemoveDataPoints(truncatedList);
        if (TimeInterval.FiveYears == timeInterval)
        	truncatedList = this.RemoveDataPoints5Year(truncatedList);
        
        	
        //*************************************************************

        series = listToSerie(truncatedList);

        return series;
    }


    /** OUTPUT SERIES OF MA
     * Prob need to adapt to selected timeline of graph (return 254 MAs if timeline is one year for stock)
     * Get moving average serie based on interval
     * @param interval
     * @return Series of moving average over interval
     */
    public XYChart.Series<String, Number> getMovingAverage(MovingAverageInterval interval, TimeInterval timeInterval)
    {
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        LinkedList<StockEntry> truncatedList;
        
        switch(interval)
        {
            case TwentyDay:
                truncatedList = truncateList(this.movingAverages[0].movingAverageDataList, timeInterval);
              //*************************************************************
                if (TimeInterval.AllTime == timeInterval)
                	truncatedList = this.RemoveDataPoints(truncatedList);
                if (TimeInterval.FiveYears == timeInterval)
                	truncatedList = this.RemoveDataPoints5Year(truncatedList);
              //*************************************************************
                series = listToSerie(truncatedList);
                break;

            case FiftyDay:
                truncatedList = truncateList(this.movingAverages[1].movingAverageDataList, timeInterval);
              //*************************************************************
                if (TimeInterval.AllTime == timeInterval)
                	truncatedList = this.RemoveDataPoints(truncatedList);
                if (TimeInterval.FiveYears == timeInterval)
                	truncatedList = this.RemoveDataPoints5Year(truncatedList);
              //*************************************************************
                series = listToSerie(truncatedList);
                break;

            case HundredDay:
                truncatedList = truncateList(this.movingAverages[2].movingAverageDataList, timeInterval);
              //*************************************************************
                if (TimeInterval.AllTime == timeInterval)
                	truncatedList = this.RemoveDataPoints(truncatedList);
                if (TimeInterval.FiveYears == timeInterval)
                	truncatedList = this.RemoveDataPoints5Year(truncatedList);
              //*************************************************************
                series = listToSerie(truncatedList);
                break;

            case TwoHundredDay:
                truncatedList = truncateList(this.movingAverages[3].movingAverageDataList, timeInterval);
              //*************************************************************
                if (TimeInterval.AllTime == timeInterval)
                	truncatedList = this.RemoveDataPoints(truncatedList);
                if (TimeInterval.FiveYears == timeInterval)
                	truncatedList = this.RemoveDataPoints5Year(truncatedList);
              //*************************************************************
                series = listToSerie(truncatedList);
                break;
        }

        return series;
    }


    /**
     * Takes a list and outputs a series containing the same data
     * @param list
     * @return A Series object to be plugged in the chart
     */
    public XYChart.Series<String, Number> listToSerie(LinkedList<StockEntry> list)
    {
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();

        // reverse the list because we read the csv from most recent to oldest prices
        Collections.reverse(list);

        // iterates over the passed list and adds the data to the series
        for(StockEntry entries : list)
        {
            series.getData().add(new XYChart.Data<String, Number>(entries.getDate(), entries.getValue()));
        }

        return series;
    }


    /**
     * Reduces the size of a list to hold only the data relevant to the time interval
     * @param list
     * @param timeInterval
     * @return A Linked List with the desired data over specified time
     */
    private LinkedList<StockEntry> truncateList(LinkedList<StockEntry> list, TimeInterval timeInterval)
    {

        // no need to truncate if all the data is needed
        if(timeInterval == TimeInterval.AllTime)
        {
            return list;
        }


        LinkedList<StockEntry> tempList = new LinkedList<StockEntry>();
        LinkedList<StockEntry> tempFullData = new LinkedList<StockEntry>(list);

        int TIMEFRAME = 0;

        // this will be changed for the second iteration
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
            case AllTime:
            	break;
            
        }

        int i = 0;
        for(StockEntry entries : tempFullData)
        {
            if(i == TIMEFRAME)
            {
                break;
            }
            else
            {
                tempList.add(entries);
                i++;
            }
        }

        return tempList;
    }


    /**
     * Calculates the moving averages over a time interval
     * @param interval
     * @return A Linked List containing all the moving averages for the stock over the interval
     */
    public LinkedList<StockEntry> computeMovingAverages(int interval)
    {
        LinkedList<StockEntry> movingAverageList = new LinkedList<StockEntry>();
        Queue<StockEntry> queueTemp = new LinkedList<StockEntry>();

        // holds the sum of the stock prices
        double movingAverage = 0;

        int count = 0;

        // we use this to break when we don't have enough days to get a moving average
        // (when we reach the end)
        int remaining = this.dataSize;

        for(StockEntry entries : this.data)
        {

            if(remaining < interval)
            {
                break;
            }

            if(count < interval)
            {
                queueTemp.add(entries);
                movingAverage += entries.getValue();
                count++;
            }

            if(count == interval)
            {
                double ma = movingAverage / interval;
                StockEntry tempEntry = new StockEntry(queueTemp.peek().getDate(), ma);
                movingAverageList.add(tempEntry);

                movingAverage -= queueTemp.peek().getValue();
                queueTemp.poll();
                count--;
            }

            remaining--;

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

    private LinkedList<StockEntry> RemoveDataPoints(LinkedList<StockEntry> list){

        // here data size is the size of the array holding all the values - maybe it should be size of the list passed ?
    	if (this.dataSize> 1000){
		    LinkedList<StockEntry> tempList = new LinkedList<StockEntry>(list);
		    LinkedList<StockEntry> returningList = new LinkedList<StockEntry>();
		    Queue<Double> queueTemp = new LinkedList<Double>();

		    double total = 0;
		    int count = 0;
		    StockEntry temp;

		    int dataPointDivider = this.dataSize/ALLTIMEDATAPOINTS;

		    for (int i = tempList.size() ; i > 0 ; i--){
		
		        temp = tempList.removeFirst();
		
		        if(queueTemp.size()< dataPointDivider){
		            queueTemp.add(temp.getValue());
		            total += temp.getValue();
		        }
		        else{
		
		            if(count%dataPointDivider == 0)
		                returningList.add(new StockEntry(temp.getDate(), total/dataPointDivider));
		
		            total -= queueTemp.poll();
		            queueTemp.add(temp.getValue());
		            total += temp.getValue();
		            count++;
		        }
		    }
		    return returningList;
    	}
    	else
    		return list;
    }
    private LinkedList<StockEntry> RemoveDataPoints5Year(LinkedList<StockEntry> list){

	    LinkedList<StockEntry> tempList = new LinkedList<StockEntry>(list);
	    LinkedList<StockEntry> returningList = new LinkedList<StockEntry>();
	    Queue<Double> queueTemp = new LinkedList<Double>();
	    double total = 0;
	    int count = 0;
	    StockEntry temp;
	    int dataPointDivider = FIVEYEARDIVIDER;  //Give's us the closest amount of data points to 300 for the graph

	    for (int i = tempList.size() ; i > 0 ; i--){
	
	        temp = tempList.removeFirst();
	
	        if(queueTemp.size()< dataPointDivider){
	            queueTemp.add(temp.getValue());
	            total += temp.getValue();
	        }
	        else{
	
	            if(count%dataPointDivider == 0)
	                returningList.add(new StockEntry(temp.getDate(), total/dataPointDivider));
	
	            total -= queueTemp.poll();
	            queueTemp.add(temp.getValue());
	            total += temp.getValue();
	            count++;
	        }
	    }
	    return returningList;
    	
    }
}