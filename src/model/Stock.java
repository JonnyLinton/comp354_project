package model;

import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.*;

public class Stock
{
//------------------------------STOCK ATTRIBUTES-----------------------------------

    // holds name of stock
    private String name;

    // holds ticker of stock
    private String ticker;

    
    // stores the all-time adjusted closing prices of the selected stock into a linkedlist
    private LinkedList<StockEntry> data;
    
    // stores an array of booleans of whether the intersection is at a positive or negative
    private ArrayList<Boolean> intersectionDirection;
    
    // stores the current timeline of the stock being graphed
    private TimeInterval currentTimeline;

    
//------------------------------STOCK CONTRUCTORS-----------------------------------
    
    /**
     * Default constructor
     * Sets everything to empty, initializes array with default moving average objects
     */
	public Stock()
    {
        this.name = "";
        this.ticker = "";
        this.currentTimeline = TimeInterval.OneYear;
    }

    /**
     * Main constructor for stock object
     * Sets name and ticker
     * Fills the linked list with data from the csv (filePath)
     * Initializes array of MA and computes the data
     * @param name
     * @param ticker
     */
	public Stock(String name, String ticker)
    {
        this.name = name;
        this.ticker = ticker;
        this.currentTimeline = TimeInterval.OneYear;
        data = new LinkedList<>(this.fetchStockData());
    }


//------------------------------PUBLIC STOCK METHODS-----------------------------------
  
    /**
     * OUTPUT SERIES OF PRICES
     * Cuts the data and outputs a list
     * @return series of closing prices
     */
    public XYChart.Series<String, Number> getPricesInRange()
    {
    	XYChart.Series<String, Number> series;
        LinkedList<StockEntry> tempData = new LinkedList<>(data);
       
        tempData = truncateList(tempData);
        tempData = this.removeDataPoints(tempData);
       	series = listToSeries(tempData);
       	
        return series;
    }
    
    public List<Boolean> getIntersectionData() {
    	List<Boolean> tempList = new ArrayList<>(intersectionDirection);
    	
    	Collections.reverse(tempList);
    	
    	return tempList;
    }
    
    /**
     * Outputs the intersection Series of two given Moving Averages
     * @param shortMA
     * @param longMA
     * @return a series of intersection points of the two moving averages parameters
     */
    public XYChart.Series<String, Number> getIntersectionsList(MovingAverageInterval shortMA, MovingAverageInterval longMA)
  
    {
    	Boolean shortOnTop;
    	StockEntry shortTermStock;
    	StockEntry longTermStock;
    	double shortTermPrice;
    	double longTermPrice;
    	XYChart.Series<String, Number> series;
    	
    	LinkedList<StockEntry> intersectionList = new LinkedList<>();
    	LinkedList<StockEntry> shortList;
    	if(shortMA.equals(longMA))
    	{
    		shortList = new LinkedList<>(data);
    	}
    	else
    		shortList = new LinkedList<>(computeMovingAverages(shortMA, data));
    		
    	shortList = truncateList(shortList);
    	LinkedList<StockEntry> longList = new LinkedList<>(computeMovingAverages(longMA, data));
    	longList = truncateList(longList);
    	shortList = removeDataPoints(shortList);
    	longList = removeDataPoints(longList);
    	
    	intersectionDirection = new ArrayList<>();
    	
    	//Starts removing stocks from today and moves backwards
    	shortTermStock = shortList.remove();
    	longTermStock = longList.remove();
    	
    	shortTermPrice = shortTermStock.getValue();
    	longTermPrice = longTermStock.getValue();
    	
    	if(shortTermPrice > longTermPrice)
    		shortOnTop = true;
    	else
    		shortOnTop = false;
    	
    	while(!longList.isEmpty() && !shortList.isEmpty())
    	{
    		shortTermStock = shortList.remove();
        	longTermStock = longList.remove();
        	
        	shortTermPrice = shortTermStock.getValue();
        	longTermPrice = longTermStock.getValue();
        	
        	if(shortOnTop && shortTermPrice < longTermPrice)
        	{
        		intersectionList.add(shortTermStock);
        		intersectionDirection.add(true);
        		shortOnTop = false;
        	}
        	if(!shortOnTop && shortTermPrice > longTermPrice)
        	{
        		intersectionList.add(shortTermStock);
        		intersectionDirection.add(false);
        		shortOnTop = true;
        	}	
    	}
    	 series = listToSeries(intersectionList);
         
         return series;
    }
    
    /** OUTPUT SERIES OF MA
     * Prob need to adapt to selected timeline of graph (return 254 MAs if timeline is one year for stock)
     * Get moving average serie based on interval
     * @param interval
     * @return Series of moving average over interval
     */
    public XYChart.Series<String, Number> getMovingAverage(MovingAverageInterval interval)
    {
        XYChart.Series<String, Number> series;
        LinkedList<StockEntry> tempData = new LinkedList<>(data);
        
        switch(interval)
        {
            case TwentyDay:
            	tempData = computeMovingAverages(interval, tempData);
          
                break;

            case FiftyDay:
            	tempData = computeMovingAverages(interval, tempData);

                break;

            case HundredDay:
            	tempData = computeMovingAverages(interval, tempData);
             
                break;

            case TwoHundredDay:
            	tempData = computeMovingAverages(interval, tempData);
         
                
                break;
        }
        tempData = new LinkedList<>(truncateList(tempData));
        tempData = this.removeDataPoints(tempData);
        series = listToSeries(tempData);

        return series;
    }

    public int getRecommendation()
    {	
    	if (intersectionDirection == null || intersectionDirection.size() <= 0) {
    		return 0;
    	}
    	else if (intersectionDirection.get(0)) {
    		return 1;
    	}
    	else
    		return 2;
    }
//------------------------------PRIVATE STOCK METHODS-----------------------------------

    /**
     * Removes Data points for all time Data
     * @param allDataPoints
     * @return
     */
    private LinkedList<StockEntry> removeDataPoints(LinkedList<StockEntry> allDataPoints){
        // the number of total Data points for All time data list
        final int ALL_TIME_DATA_POINTS = 500;
        // the maximum number of all time data points before it gets smoothed
        final int MAX_DATA_POINTS = 1000;
        // the amount the data points are being divided by for the 5 year data
        final int FIVE_YEAR_DIVIDER = 2;

        // here data size is the size of the array holding all the values - maybe it should be size of the list passed ?
    	if (allDataPoints.size() > MAX_DATA_POINTS){
		    LinkedList<StockEntry> tempData = new LinkedList<>(allDataPoints);
		    LinkedList<StockEntry> truncatedDataPoints = new LinkedList<>();
		    Queue<Double> window = new LinkedList<>();

		    double total = 0;
		    int count = 0;
		    StockEntry currentStockEntry;
            int dataPointDivider;

            if (TimeInterval.AllTime == currentTimeline) {
                dataPointDivider = this.data.size() / ALL_TIME_DATA_POINTS;
            }
            else {
                dataPointDivider = FIVE_YEAR_DIVIDER;
            }

		    for (int i = tempData.size() ; i > 0 ; i--){

		        currentStockEntry = tempData.removeFirst();

		        if(window.size()< dataPointDivider){
		            window.add(currentStockEntry.getValue());
		            total += currentStockEntry.getValue();
		        }
		        else{

		            if(count%dataPointDivider == 0)
		                truncatedDataPoints.add(new StockEntry(currentStockEntry.getDate(), total/dataPointDivider));

		            total -= window.poll();
		            window.add(currentStockEntry.getValue());
		            total += currentStockEntry.getValue();
		            count++;
		        }
		    }
		    return truncatedDataPoints;
    	}
    	else
    		return allDataPoints;
    }

    /**
     * Calculates the moving averages over a time interval
     * @return A Linked List containing all the moving averages for the stock over the interval
     */
    private LinkedList<StockEntry> computeMovingAverages(MovingAverageInterval movingAverageInterval, LinkedList<StockEntry> allTimeDataList)
    {
    	//Change from movingAverageInterval to an integer of days
        int interval = 0;
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
        }

        //Create a temporary list and a queue to compute moving average
        LinkedList<StockEntry> movingAverageList = new LinkedList<>();
        Queue<StockEntry> window = new LinkedList<>();

        // holds the sum of the stock prices
        double movingAverage = 0;
        int count = 0;

        for(StockEntry entries : allTimeDataList)
        {
            if(count < interval)
            {
                window.add(entries);
                movingAverage += entries.getValue();
                count++;
            }
            if(count == interval)
            {
                double ma = movingAverage / interval;
                StockEntry tempEntry = new StockEntry(window.peek().getDate(), ma);
                movingAverageList.add(tempEntry);

                movingAverage -= window.peek().getValue();
                window.poll();
                count--;
            }
        }
        return movingAverageList;
    }

    /**
     * Takes a list and outputs a series containing the same data
     * @param list
     * @return A Series object to be plugged in the chart
     */
    private XYChart.Series<String, Number> listToSeries(LinkedList<StockEntry> list)
    {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // reverse the list because we read the csv from most recent to oldest prices
        Collections.reverse(list);

        // iterates over the passed list and adds the data to the series
        for(StockEntry entries : list)
        {
            series.getData().add(new XYChart.Data<>(entries.getDate(), entries.getValue()));
        }

        return series;
    }

    /**
     * HELPER FOR STOCK CLASS
     * Populates the list from the CSV file
     * @return Size of the list
     */
    private LinkedList<StockEntry> fetchStockData()
    {
    	LinkedList<StockEntry> allTimeDataPoints = new LinkedList<>();
        Scanner csvScanner = null;

        try
        {
            csvScanner = new Scanner(new URL("http://chart.finance.yahoo.com/table.csv?s=" + this.ticker + "&ignore=.csv").openStream());

            csvScanner.nextLine(); // skip first line

            String each_line;
            String[] columns;

            while(csvScanner.hasNextLine())
            {
                each_line = csvScanner.nextLine();
                columns = each_line.split(",");

                allTimeDataPoints.add(new StockEntry(columns[0], Double.parseDouble(columns[6])));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (csvScanner != null) {
                csvScanner.close();
            }
        }

        return allTimeDataPoints;
    }
    /**
     * Reduces the size of a list to hold only the data relevant to the time interval
     * @param allTimeDataPoints
     * @return A Linked List with the desired data over specified time
     */
    private LinkedList<StockEntry> truncateList(LinkedList<StockEntry> allTimeDataPoints) {
        LinkedList<StockEntry> tempAllTime = new LinkedList<>(allTimeDataPoints);
        LinkedList<StockEntry> truncatedData = new LinkedList<>();
        Calendar cal = Calendar.getInstance();

        switch(currentTimeline) {
            case OneYear:
                cal.add(Calendar.YEAR, -1);
                break;
            case TwoYears:
                cal.add(Calendar.YEAR, -2);
                break;
            case FiveYears:
                cal.add(Calendar.YEAR, -5);
                break;
            case AllTime:
                return allTimeDataPoints;
        }

        Date stoppingDate = cal.getTime();
        while (tempAllTime.peekFirst().getComparableDate().after(stoppingDate)) {
            truncatedData.add(tempAllTime.pop());
        }
        return truncatedData;
    }

//------------------------------GETTERS AND SETTERS-----------------------------------
    
    /**
     * Accessor that returns the name of the current stock
     * @return the name of the current stock
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Mutator to set the timeline of the current stock
     * @param currentTimeline
     */
	public void setTimeline(TimeInterval currentTimeline)
	{
		this.currentTimeline = currentTimeline;
	}
	
	/**
	 * Accessor for the timeline of the current stock
	 * @return the current timeline
	 */
	public TimeInterval getCurrentTimeline()
	{
		return currentTimeline;
	}

    public String getTicker() {
        return ticker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}