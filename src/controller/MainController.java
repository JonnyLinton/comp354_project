package controller;

import model.Stock;
import model.TimeInterval;
import model.MovingAverageInterval;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class MainController {
	
	private Stock currentStock;

	//For Iteration 2
	public void searchStock(String currentStock){	
    }
	
	public void showDefaultStock(Stock currentStock){
		currentStock.getPricesInRange(TimeInterval.OneYear);
	}
	
	private void graphTimeline(Stock currentStock, TimeInterval timeLength){
		currentStock.getPricesInRange(timeLength);
	}
	
	private void graphMovingAverage(ActionEvent event) {
		//TBD
		switch(movingAverageButtonPressed.getEventType().getName()) {
        case "20Day":
        	if(movingAverageButtonPressed.GETSELECTED() == True)
        		currentStock.getMovingAverage(MovingAverageInterval.TwentyDay, get);
        	else
        		currentStock.getMovingAverage(null, null);
        	break;
        case "50day":
        	if(movingAverageButtonPressed.GETSELECTED() == True)
        		currentStock.getMovingAverage(MovingAverageInterval.FiftyDay, get);
        	else
        		currentStock.getMovingAverage(null, null);
            break;
        case "100day":
        	if(movingAverageButtonPressed.GETSELECTED() == True)
        		currentStock.getMovingAverage(MovingAverageInterval.HundredDay, get);
        	else
        		currentStock.getMovingAverage(null, null);
            break;
        case "200day":
        	if(movingAverageButtonPressed.GETSELECTED() == True)
        		currentStock.getMovingAverage(MovingAverageInterval.TwoHundredDay, get);
        	else
        		currentStock.getMovingAverage(null, null);
            break; 
    }
		
		
		
	}
	private void getRecommendations(String recom){
		
	}
}
