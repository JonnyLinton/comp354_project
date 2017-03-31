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

    // the number of total Data points for All time data list
    private final int ALL_TIME_DATA_POINTS = 500;

    // the amount the data points are being divided by for the 5 year data
    private final int FIVE_YEAR_DIVIDER = 2;
    
    // stores the all-time adjusted closing prices of the selected stock into a linkedlist
    private LinkedList<StockEntry> data;
    
    // stores an array of booleans of whether the intersection is at a positive or negative
    private ArrayList<Boolean> intersectionDirection;
    
//------------------------------STOCK CONTRUCTORS-----------------------------------
    
    /**
     * Default constructor
     * Sets everything to empty, initializes array with default moving average objects
     */
	public Stock()
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
     */
	public Stock(String name, String ticker)
    {
        this.name = name;
        this.ticker = ticker;

        data = new LinkedList<>(this.fetchStockData(TimeInterval.AllTime));
    }

//------------------------------PUBLIC STOCK METHODS-----------------------------------
    
    /**
     * OUTPUT SERIES OF PRICES
     * Cuts the data and outputs a list
     * @param timeInterval
     * @return series of closing prices
     */
    public XYChart.Series<String, Number> getPricesInRange(TimeInterval timeInterval)
    {
   
    	XYChart.Series<String, Number> series = new XYChart.Series<>();
        LinkedList<StockEntry> list = new LinkedList<>(data);
        list = truncateList(list, timeInterval);
       
        if (TimeInterval.AllTime == timeInterval)
        	list = this.removeDataPoints(list);
       
        if (TimeInterval.FiveYears == timeInterval)
        {
        	list = this.removeDataPoints5Year(list);
        }
        
       	series = listToSeries(list);
       	
        return series;
    }
   
    /**
     * TODO: TO BE REMOVED
     *
     * Outputs the intersection Series of two given Moving Averages
     * @param interval
     * @return
     */
    public XYChart.Series<String, Number> getIntersectionsList(TimeInterval interval)
    {
    	Boolean shortOnTop;
    	StockEntry shortTermStock;
    	StockEntry longTermStock;
    	double shortTermPrice;
    	double longTermPrice;
    	XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
    	
    	LinkedList<StockEntry> intersectionList = new LinkedList<StockEntry>();
    	LinkedList<StockEntry> shortList = new LinkedList<StockEntry>(computeMovingAverages(MovingAverageInterval.TwentyDay,data));
    	shortList = truncateList(shortList, interval);
    	LinkedList<StockEntry> longList = new LinkedList<StockEntry>(computeMovingAverages(MovingAverageInterval.TwoHundredDay,data));
    	longList = truncateList(longList, interval);
    	if(interval == TimeInterval.AllTime)
    	{
    		shortList = removeDataPoints(shortList);
    		longList = removeDataPoints(longList);
    	}
    	if(interval == TimeInterval.FiveYears)
    	{
    		shortList = removeDataPoints5Year(shortList);
    		longList = removeDataPoints5Year(longList);
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
    	 series = listToSeries(intersectionList);
         
         return series;
    }
    
    /**
     * Outputs the intersection Series of two given Moving Averages
     * @param shortMA
     * @param longMA
     * @param interval
     * @return
     */
    public XYChart.Series<String, Number> getIntersectionsList(MovingAverageInterval shortMA, MovingAverageInterval longMA, TimeInterval interval)
  
    {
    	Boolean shortOnTop;
    	StockEntry shortTermStock;
    	StockEntry longTermStock;
    	double shortTermPrice;
    	double longTermPrice;
    	XYChart.Series<String, Number> series;
    	
    	LinkedList<StockEntry> intersectionList = new LinkedList<>();
    	LinkedList<StockEntry> shortList = new LinkedList<>(computeMovingAverages(shortMA, data));
    	shortList = truncateList(shortList, interval);
    	LinkedList<StockEntry> longList = new LinkedList<>(computeMovingAverages(longMA, data));
    	longList = truncateList(longList, interval);
    	if(interval == TimeInterval.AllTime)
    	{
    		shortList = removeDataPoints(shortList);
    		longList = removeDataPoints(longList);
    	}
    	if(interval == TimeInterval.FiveYears)
    	{
    		shortList = removeDataPoints5Year(shortList);
    		longList = removeDataPoints5Year(longList);
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
    	 series = listToSeries(intersectionList);
         
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
        XYChart.Series<String, Number> series;
        LinkedList<StockEntry> list = new LinkedList<>(data);
        
        switch(interval)
        {
            case TwentyDay:
            	list = computeMovingAverages(interval, list);
          
                break;

            case FiftyDay:
            	list = computeMovingAverages(interval, list);

                break;

            case HundredDay:
            	list = computeMovingAverages(interval, list);
             
                break;

            case TwoHundredDay:
            	list = computeMovingAverages(interval, list);
         
                
                break;
        }
        list = new LinkedList<StockEntry>(truncateList(list, timeInterval));
        if (TimeInterval.AllTime == timeInterval)
        	list = this.removeDataPoints(list);
        if (TimeInterval.FiveYears == timeInterval)
        	list = this.removeDataPoints5Year(list);
        series = listToSeries(list);

        return series;
    }

    public Recommendation getRecommendation()
    {
    	
    	return Recommendation.BUY;
    }
//------------------------------PRIVATE STOCK METHODS-----------------------------------

    /**
     * Removes Data points for all time Data
     * @param allTimeDataList
     * @return
     */
    private LinkedList<StockEntry> removeDataPoints(LinkedList<StockEntry> allTimeDataList){

        // here data size is the size of the array holding all the values - maybe it should be size of the list passed ?
    	if (allTimeDataList.size() > 1000){
		    LinkedList<StockEntry> tempData = new LinkedList<>(allTimeDataList);
		    LinkedList<StockEntry> truncatedDataPoints = new LinkedList<>();
		    Queue<Double> window = new LinkedList<>();

		    double total = 0;
		    int count = 0;
		    StockEntry currentStockEntry;
            int dataPointDivider = this.data.size() / ALL_TIME_DATA_POINTS;

//		   TODO: pass the timeline through somehow, and combine removeDataPoints5Year and this method together with the following
//            if (all time) {
//                dataPointDivider = this.data.size() / ALL_TIME_DATA_POINTS;
//            }
//            else {
//                dataPointDivider = FIVE_YEAR_DIVIDER;
//            }

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
    		return allTimeDataList;
    }

    // TODO: REMOVE THIS, once the removeDataPoints is fixed
    private LinkedList<StockEntry> removeDataPoints5Year(LinkedList<StockEntry> list){

        LinkedList<StockEntry> tempList = new LinkedList<>(list);
        LinkedList<StockEntry> returningList = new LinkedList<>();
        Queue<Double> queueTemp = new LinkedList<>();
        double total = 0;
        int count = 0;
        StockEntry temp;
        int dataPointDivider = FIVE_YEAR_DIVIDER;  //Give's us the closest amount of data points to 300 for the graph

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
    private LinkedList<StockEntry> fetchStockData(TimeInterval interval)
    {
    	LinkedList<StockEntry> allTimeDataPoints = new LinkedList<>();
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

                allTimeDataPoints.add(new StockEntry(columns[0], Double.parseDouble(columns[6])));
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

        return allTimeDataPoints;
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
    	// TODO: We only get the alltime data -- please refactor
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
     * @param allTimeDataPoints
     * @param timeInterval
     * @return A Linked List with the desired data over specified time
     */
    private LinkedList<StockEntry> truncateList(LinkedList<StockEntry> allTimeDataPoints, TimeInterval timeInterval)
    {
        // no need to truncate if all the data is needed
        if(timeInterval == TimeInterval.AllTime)
        {
            return allTimeDataPoints;
        }

        LinkedList<StockEntry> truncatedData = new LinkedList<>();
        LinkedList<StockEntry> allTimeDataPointsTemp = new LinkedList<>(allTimeDataPoints);

        // TODO: make all these timeFrames constants to hide it from the TA.
        // TODO: "Nice To Have" => make the dates into Calendar objects, and do this properly.
        int timeFrame = 0;

        // this will be changed for the second iteration
        switch(timeInterval)
        {
            case OneYear:
                timeFrame = 254;
                break;

            case TwoYears:
                timeFrame = 505;
                break;

            case FiveYears:
                timeFrame = 1259;
                break;
            case AllTime:
            	break;

        }

        int i = 0;
        for(StockEntry entries : allTimeDataPointsTemp)
        {
            if(i == timeFrame)
            {
                break;
            }
            else
            {
                truncatedData.add(entries);
                i++;
            }
        }

        return truncatedData;
    }

//------------------------------GETTERS AND SETTERS-----------------------------------
    public String getName()
    {
        return this.name;
    }

}