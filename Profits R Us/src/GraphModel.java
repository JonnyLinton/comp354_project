import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javafx.scene.chart.XYChart;

public class GraphModel
{
	
	private XYChart.Series<String, Number> stockPriceSeries;
	
	//*****THIS NEEDS TO BE UPDATE TO FIT UML MODEL*****
	
	public void createStockPriceSeries(int timeline)
	{
		stockPriceSeries  = new XYChart.Series<String, Number>();
		String tempDailyStockData;
		String[] columnDataArray;
        LinkedList<Stock> allStockValues = new LinkedList<Stock>();
        LinkedList<Stock> smoothStockValues = new LinkedList<Stock>();
        Queue<Double> queueLineSmoothing = new LinkedList<Double>();
        double lineSmoothing = 0;
        int count=0; 
		
        try
		{
        	//Load File csv data into csvFile
			Scanner csvFile = new Scanner(new File("Sample data.csv"));
			csvFile.useDelimiter(",");
			
			//Skip Header data
			csvFile.nextLine();
			
			//Create LinkedList of Data
			if(timeline == 0)
			{
				while(csvFile.hasNextLine())
				{
					tempDailyStockData = csvFile.nextLine();
					columnDataArray = tempDailyStockData.split(",");
					allStockValues.add(new Stock(columnDataArray[0], Double.parseDouble(columnDataArray[6])));
				}
			}
			else
			{
				for (int i = 0; i < timeline * 365; i++)
				{
					tempDailyStockData = csvFile.nextLine();
					columnDataArray = tempDailyStockData.split(",");
					allStockValues.add(new Stock(columnDataArray[0], Double.parseDouble(columnDataArray[6])));
				}
			}
				
			csvFile.close();
		}
    	catch (FileNotFoundException e)
		{
			e.printStackTrace();
	    }

		//Give's us the closest amount of data points to 300 for the graph
        int dataPointDivider = allStockValues.size()/300;
		Stock temp;

        for (int i = allStockValues.size() ; i > 0 ; i--)
        {
        	temp = allStockValues.removeLast();
        	
        	if(queueLineSmoothing.size()< dataPointDivider)
        	{
				queueLineSmoothing.add(temp.getPrice());
				lineSmoothing += temp.getPrice();
			}
			else
			{
				if(count%dataPointDivider == 0)
				{
					smoothStockValues.add(new Stock(temp.getDate(), lineSmoothing/dataPointDivider));
				}

				lineSmoothing -= queueLineSmoothing.poll();
				queueLineSmoothing.add(temp.getPrice());
				lineSmoothing += temp.getPrice();
				count++;
			}
        }

        stockPriceSeries.getData().clear();

    	for (int i = smoothStockValues.size(); i > 0; i--)
    	{
    		temp = smoothStockValues.remove();
			stockPriceSeries.getData().add(new XYChart.Data<String, Number>(temp.getDate(), temp.getPrice()));
    	}
	}

	public XYChart.Series<String, Number> getStockPriceSeries()
	{
		return stockPriceSeries;
	}
}