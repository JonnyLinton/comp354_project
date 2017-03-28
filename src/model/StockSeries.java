package model;

import javafx.scene.chart.XYChart;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class StockSeries
{
//------------------------------STOCK ATTRIBUTES-----------------------------------
	
    // holds name of stock
    private String name;

    // holds ticker of stock
    private String ticker;
    
    // the number of total Data points for All time data list
    public final int ALLTIMEDATAPOINTS = 500; 
    
    // the amount the data points are being divided by for the 5 year data
    public final int FIVEYEARDIVIDER = 2;
    
    // stores the all-time adjusted closing prices of the selected stock into a linkedlist
    private LinkedList<StockEntry> data;
    
    // stores an array of booleans of whether the itersection is at a positive or negative
    private ArrayList<Boolean> intersectionDirection;
    
//------------------------------STOCK CONTRUCTORS-----------------------------------
    
    /**
     * Default constructor
     * Sets everything to empty, initializes array with default moving average objects
     */
	public StockSeries()
    {
        this.name = "";
        this.ticker = "";
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
	public StockSeries(String name, String ticker)
    {
        this.name = name;
        this.ticker = ticker;
        
        long startTime = System.currentTimeMillis();
        data = new LinkedList<StockEntry>(this.fetchStockData(TimeInterval.AllTime));
        long endTime = System.currentTimeMillis();
        System.out.println("Time for Fetching Data is " + ((endTime-startTime)/1000.0));
    }

//------------------------------PUBLIC STOCK METHODS-----------------------------------
    
    /** OUTPUT SERIES OF PRICES
     * Cuts the data and outputs a list
     * @param timeInterval
     * @return series of closing prices
     */
    public XYChart.Series<String, Number> getPricesInRange(TimeInterval timeInterval)
    {
   
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        LinkedList<StockEntry> list = new LinkedList<StockEntry>(data);
        list = truncateList(list, timeInterval);
       
        if (TimeInterval.AllTime == timeInterval)
        	list = this.RemoveDataPoints(list);
       
        if (TimeInterval.FiveYears == timeInterval)
        {
        	list = this.RemoveDataPoints5Year(list);
        }
        
       	series = listToSerie(list);
       	
        return series;
    }
    
    //WORK IN PROGRESSSSSS*************************************************
    public XYChart.Series<String, Number> getIntersectionsList(TimeInterval interval)
    {
    	Boolean shortOnTop;
    	StockEntry shortTermStock;
    	StockEntry longTermStock;
    	double shortTermPrice;
    	double longTermPrice;
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    	
    	LinkedList<StockEntry> intersectionList = new LinkedList<StockEntry>();
    	LinkedList<StockEntry> shortList = new LinkedList<StockEntry>(computeMovingAverages(MovingAverageInterval.TwentyDay,truncateList(data, interval)));
    	LinkedList<StockEntry> longList = new LinkedList<StockEntry>(computeMovingAverages(MovingAverageInterval.TwoHundredDay,truncateList(data, interval)));
    	
    	if(interval == TimeInterval.AllTime)
    	{
    		shortList = RemoveDataPoints(shortList);
    		longList = RemoveDataPoints(longList);
    	}
    	if(interval == TimeInterval.FiveYears)
    	{
    		shortList = RemoveDataPoints5Year(shortList);
    		longList = RemoveDataPoints5Year(longList);
    	}
    	
    	//intersectionDirection = new ArrayList<Boolean>();
    	
    	//Starts removing stocks from today and moves backwards
    	shortTermStock = shortList.remove();
    	longTermStock = longList.remove();
    	
    	shortTermPrice = shortTermStock.getValue();
    	longTermPrice = longTermStock.getValue();
    	
    	if(shortTermPrice > longTermPrice)
    		shortOnTop = true;
    	else
    		shortOnTop = false;
    	
    	while(!longList.isEmpty())
    	{
    		shortTermStock = shortList.remove();
        	longTermStock = longList.remove();
        	
        	shortTermPrice = shortTermStock.getValue();
        	longTermPrice = longTermStock.getValue();
        	
        	if(shortOnTop && shortTermPrice < longTermPrice)
        	{
        		intersectionList.add(shortTermStock);
        		//intersectionDirection.add(true);
        		shortOnTop = false;
        	}
        	if(!shortOnTop && shortTermPrice > longTermPrice)
        	{
        		intersectionList.add(shortTermStock);
        		//intersectionDirection.add(false);
        		shortOnTop = true;
        	}	
    	}
    	 series = listToSerie(intersectionList);
         
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
        LinkedList<StockEntry> list = new LinkedList<StockEntry>(data);
        
        switch(interval)
        {
            case TwentyDay:
            	list = computeMovingAverages(interval, list);
                if (TimeInterval.AllTime == timeInterval)
                	list = this.RemoveDataPoints(list);
                if (TimeInterval.FiveYears == timeInterval)
                	list = this.RemoveDataPoints5Year(list);
                break;

            case FiftyDay:
            	list = computeMovingAverages(interval, list);
                if (TimeInterval.AllTime == timeInterval)
                	list = this.RemoveDataPoints(list);
                if (TimeInterval.FiveYears == timeInterval)
                	list = this.RemoveDataPoints5Year(list);
                break;

            case HundredDay:
            	list = computeMovingAverages(interval, list);
                if (TimeInterval.AllTime == timeInterval)
                	list = this.RemoveDataPoints(list);
                if (TimeInterval.FiveYears == timeInterval)
                	list = this.RemoveDataPoints5Year(list);
                break;

            case TwoHundredDay:
            	list = computeMovingAverages(interval, list);
                if (TimeInterval.AllTime == timeInterval)
                	list = this.RemoveDataPoints(list);
                if (TimeInterval.FiveYears == timeInterval)
                	list = this.RemoveDataPoints5Year(list);
                
                break;
        }
        list = new LinkedList<StockEntry>(truncateList(list, timeInterval));
        series = listToSerie(list);

        return series;
    }

//------------------------------PRIVATE STOCK METHODS-----------------------------------

    /**
     * Removes Data points for all time Data
     * @param list
     * @return
     */
    private LinkedList<StockEntry> RemoveDataPoints(LinkedList<StockEntry> list){

        // here data size is the size of the array holding all the values - maybe it should be size of the list passed ?
    	if (data.size() > 1000){
		    LinkedList<StockEntry> tempList = new LinkedList<StockEntry>(list);
		    LinkedList<StockEntry> returningList = new LinkedList<StockEntry>();
		    Queue<Double> queueTemp = new LinkedList<Double>();

		    double total = 0;
		    int count = 0;
		    StockEntry temp;

		    int dataPointDivider = data.size()/ALLTIMEDATAPOINTS;

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
    /**
     * Calculates the moving averages over a time interval
     * @param interval
     * @return A Linked List containing all the moving averages for the stock over the interval
     */
    private LinkedList<StockEntry> computeMovingAverages(MovingAverageInterval movingAverageInterval, LinkedList<StockEntry> list)
    {
    	//Change from movingAverageInterval to an integer of days
    	int interval;
    	long startTime = System.currentTimeMillis();
        switch(movingAverageInterval)
        {
            case TwentyDay:
               interval = 20;
                break;

            case FiftyDay:
               interval = 50;
                break;

            case HundredDay:
                interval = 100;
                break;

            case TwoHundredDay:
                interval = 200;
                break;
            default:
            	interval = 0;
        }
        
        //Create a temporary list and a queue to compute moving average
        LinkedList<StockEntry> movingAverageList = new LinkedList<StockEntry>();
        Queue<StockEntry> queueTemp = new LinkedList<StockEntry>();

        // holds the sum of the stock prices
        double movingAverage = 0;
        int count = 0;

        for(StockEntry entries : list)
        {
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
        }
        long endTime = System.currentTimeMillis();
        System.out.println("totla time = " + (endTime - startTime));
        return movingAverageList;
    }
    /**
     * Takes a list and outputs a series containing the same data
     * @param list
     * @return A Series object to be plugged in the chart
     */
    private XYChart.Series<String, Number> listToSerie(LinkedList<StockEntry> list)
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

    /** HELPER FOR STOCK CLASS
     * Populates the list from the CSV file
     * @param dataList
     * @param filePath
     * @return Size of the list
     */
    private LinkedList<StockEntry> fetchStockData(TimeInterval interval)
    {
    	LinkedList<StockEntry> temp = new LinkedList<StockEntry>();
        Scanner csvScanner = null;
        try
        {
            csvScanner = new Scanner(new URL(seriesURLBuilder(interval)).openStream());

            csvScanner.nextLine(); // skip first line

            String each_line;
            String[] columns;

            while(csvScanner.hasNextLine())
            {
                each_line = csvScanner.nextLine();
                columns = each_line.split(",");

                temp.add(new StockEntry(columns[0], Double.parseDouble(columns[6])));
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

        return temp;
    }

    /**
     * Builds the url used to collect data from yahoo finance
     * @param interval
     * @return
     */
    private String seriesURLBuilder(TimeInterval interval)
    {
    	Calendar cal = Calendar.getInstance();
    	StringBuilder url = new StringBuilder("http://chart.finance.yahoo.com/table.csv?s=");
    	url.append(this.ticker);
    	if(interval == TimeInterval.AllTime)
    	{
    		url.append("&ignore=.csv");
    	}
    	if(interval == TimeInterval.FiveYears)
    	{
    		url.append("&a=" + cal.get(Calendar.MONTH));
    		url.append("&b=" + cal.get(Calendar.DAY_OF_MONTH));
    		url.append("&c=" + (cal.get(Calendar.YEAR)-5));
    		url.append("&ignore=.csv");
    	}
    	if(interval == TimeInterval.TwoYears)
    	{
    		url.append("&a=" + cal.get(Calendar.MONTH));
    		url.append("&b=" + cal.get(Calendar.DAY_OF_MONTH));
    		url.append("&c=" + (cal.get(Calendar.YEAR)-2));
    		url.append("&ignore=.csv");
    	}
    	if(interval == TimeInterval.OneYear)
    	{
    		url.append("&a=" + cal.get(Calendar.MONTH));
    		url.append("&b=" + cal.get(Calendar.DAY_OF_MONTH));
    		url.append("&c=" + (cal.get(Calendar.YEAR)-1));
    		url.append("&ignore=.csv");
    	}
    	return url.toString();
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
//------------------------------GETTERS AND SETTERS-----------------------------------
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTicker()
    {
        return ticker;
    }
    
    public void setTicker (String ticker)
    {
        this.ticker = ticker;
    }
   
}