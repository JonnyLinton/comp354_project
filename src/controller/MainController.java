package controller;

import model.Stock;
import model.TimeInterval;
import model.MovingAverageInterval;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

public class MainController {
	
	Stock currentStock;
	boolean isStockGenerated = false;
	
	@FXML
	TextField searchTextField;
	
	@FXML
	LineChart<String, Number> stockChart;
	
	//For Iteration 2
	public void searchStock(String currentStock){	
    }
	
	@FXML
	public void showStock(ActionEvent searchButtonPressed) {
		if (!isStockGenerated) {
			currentStock = new Stock("Dummy Stock", "STOK", "C:\\Users\\Jacques\\workspace\\comp354_project\\src\\resources\\Sample data.csv");
			
			XYChart.Series<String, Number> stockSerie = currentStock.getPricesInRange(TimeInterval.TwoYears);
			
			XYChart.Series<String, Number> movingAverageSeries[] =  new XYChart.Series[4];
			
			movingAverageSeries[0] = currentStock.getMovingAverage(MovingAverageInterval.TwentyDay, TimeInterval.TwoYears);
			movingAverageSeries[1] = currentStock.getMovingAverage(MovingAverageInterval.FiftyDay, TimeInterval.TwoYears);
			movingAverageSeries[2] = currentStock.getMovingAverage(MovingAverageInterval.HundredDay, TimeInterval.TwoYears);
			movingAverageSeries[3] = currentStock.getMovingAverage(MovingAverageInterval.TwoHundredDay, TimeInterval.TwoYears);
			
			stockChart.getData().add(stockSerie);
			
			for (XYChart.Series<String, Number> movingAverageSerie : movingAverageSeries)
				stockChart.getData().add(movingAverageSerie);
			
			isStockGenerated = true;
		}
	}
	
	public void showDefaultStock(Stock currentStock){
		currentStock.getPricesInRange(TimeInterval.OneYear);
	}
	
	private void graphTimeline(Stock currentStock, TimeInterval timeLength){
		currentStock.getPricesInRange(timeLength);
	}
	
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
//    }
		
		
		
//	}
	private void getRecommendations(String recom){
		
	}
}
