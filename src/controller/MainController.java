package controller;

import model.Stock;
import model.TimeInterval;
import model.MovingAverageInterval;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

public class MainController {
	
	boolean isStockGenerated = false;
	
	Stock currentStock;
	
	@FXML
	TextField searchTextField;
	
	@FXML
	LineChart<String, Number> stockChart;
	
	@FXML
	public void showStock(ActionEvent searchButtonPressed) {
		
		// Checks if a stock has already been generated
		if (!isStockGenerated) {
			
			// Create stock object from model class
			currentStock = new Stock("Dummy Stock", "STOK", "C:\\Users\\Jacques\\workspace\\comp354_project\\src\\resources\\Sample data.csv");
			
			// Create closing prices serie from stock object
			XYChart.Series<String, Number> stockSerie = currentStock.getPricesInRange(TimeInterval.FiveYears);
			stockSerie.setName("Closing Prices");
			
			// Add closing price serie to the graph
			stockChart.getData().add(stockSerie);
			
			/* Create moving average series array of size 4
			 * Initiate every index with a new moving average serie from stock object
			 * Give every serie a name
			 */
			XYChart.Series<String, Number> movingAverageSeries[] = new XYChart.Series[4];
			movingAverageSeries[0] = currentStock.getMovingAverage(MovingAverageInterval.TwentyDay, TimeInterval.FiveYears);
			movingAverageSeries[0].setName("20 days MA");
			movingAverageSeries[1] = currentStock.getMovingAverage(MovingAverageInterval.FiftyDay, TimeInterval.FiveYears);
			movingAverageSeries[1].setName("50 days MA");
			movingAverageSeries[2] = currentStock.getMovingAverage(MovingAverageInterval.HundredDay, TimeInterval.FiveYears);
			movingAverageSeries[2].setName("100 days MA");
			movingAverageSeries[3] = currentStock.getMovingAverage(MovingAverageInterval.TwoHundredDay, TimeInterval.FiveYears);
			movingAverageSeries[3].setName("200 days MA");
			
			// Add all moving average series to the graph
			for (XYChart.Series<String, Number> movingAverageSerie : movingAverageSeries) {
				stockChart.getData().add(movingAverageSerie);
			}
			
			// Set graph's attributes
			stockChart.setCreateSymbols(false);
			stockChart.setTitle(currentStock.getName());
			stockChart.setCursor(Cursor.CROSSHAIR);
			
			// Prevents creation of multiple stock series
			isStockGenerated = true;
		}
	}
	
//	public void searchStock(String currentStock) {
//		
//  }
//	
//	public void showDefaultStock(Stock currentStock) {
//		currentStock.getPricesInRange(TimeInterval.OneYear);
//	}
//	
//	private void graphTimeline(Stock currentStock, TimeInterval timeLength) {
//		currentStock.getPricesInRange(timeLength);
//	}
//	
//	private void graphMovingAverage(ActionEvent event) {
//		//TBD
//		switch(movingAverageButtonPressed.getEventType().getName()) {
//        case "20Day":
//        	if(movingAverageButtonPressed.GETSELECTED() == True)
//        		currentStock.getMovingAverage(MovingAverageInterval.TwentyDay, get);
//        	else
//        		currentStock.getMovingAverage(null, null);
//        	break;
//        case "50day":
//        	if(movingAverageButtonPressed.GETSELECTED() == True)
//        		currentStock.getMovingAverage(MovingAverageInterval.FiftyDay, get);
//        	else
//        		currentStock.getMovingAverage(null, null);
//            break;
//        case "100day":
//        	if(movingAverageButtonPressed.GETSELECTED() == True)
//        		currentStock.getMovingAverage(MovingAverageInterval.HundredDay, get);
//        	else
//        		currentStock.getMovingAverage(null, null);
//            break;
//        case "200day":
//        	if(movingAverageButtonPressed.GETSELECTED() == True)
//        		currentStock.getMovingAverage(MovingAverageInterval.TwoHundredDay, get);
//        	else
//        		currentStock.getMovingAverage(null, null);
//            break;
//  }
//
//	private void getRecommendations(String recommendation) {
//		
//	}
}
