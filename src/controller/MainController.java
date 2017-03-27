/*
   Controller for the Main view of the inStock app for the first iteration
   Generates moving averages for a specific timeline of a stock and displays them according to user input
   To be fully implemented in the 2nd iteration:
            Full SearchStock() method based on Yahoo Finance API for top 30 Stocks
            Recommendations() method
            FavoriteStock() method based on user login preferences
            Logout() method will be updated to save any stocks the user wishes to keep
            DisplayError() added to ensure a timeline selected exits for stock selected.
 */

package controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.StockSeries;
import model.TimeInterval;
import model.MovingAverageInterval;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import view.StocksRUs;

import java.io.IOException;

public class MainController {

    boolean isStockGenerated = false;
    boolean isMaDisplayed[], isTimeLineDisplayed[];
    StockSeries currentStock;
    TimeInterval currentTimeLine;
    TimeInterval timeIntervals[];
    MovingAverageInterval maIntervals[];
    XYChart.Series<String, Number> stockSerie;
    XYChart.Series<String, Number> intersectionSerie;
    XYChart.Series<String, Number>[] movingAverageSeries;
    Button timeLineButtons[];
    CheckBox maButtons[];

    @FXML
    TextField searchTextField;

    @FXML
	Label userNameLabel;

    @FXML
    Button timeLineButton_1, timeLineButton_2, timeLineButton_5, timeLineButton_all;

    @FXML
    CheckBox maButton_20, maButton_50, maButton_100, maButton_200;

    @FXML
    LineChart<String, Number> stockChart;
    
    /**
     * Function called when the view is created
     */
    
    @FXML
    private void initialize() {
        // Set graph's attributes
        stockChart.setCreateSymbols(false);
        stockChart.setCursor(Cursor.CROSSHAIR);

		userNameLabel.setText(StocksRUs.getCurrentUser().getEmail());
        
        // Initialize all arrays
    	initializeArrays();
    }
    
    /**
     * Graph and display the stock according to current timeline
     * @Param = TimeLineButton Event
     */
    
    @FXML
    private void graphTimeline(ActionEvent event) {
    	graphTimeLineHelper();
    }
    
    /**
     * Triggering & helper function to load MAs to the graph
     * @param event an ActionEvent sent from MainView
     */
    
    @FXML
    private void graphMovingAverage(ActionEvent event) {
    	graphMovingAverageHelper();
    }
    
    /**
     * Allows the user to change between all stocks
     * @param event an ActionEvent sent from MainView
     */
    
    @FXML
    private void selectStock(ActionEvent event) {
    	
    	// Get clicked button information
    	Button clickedButton = (Button)event.getSource();

    	// Ensures no computation will be done if same stock is selected
    	if (currentStock == null || currentStock.getName().compareTo(clickedButton.getText()) != 0) {
    		
    		// Change current stock
	    	currentStock = new StockSeries(clickedButton.getText(), clickedButton.getId());
	    	
			clearData();
	
	        // Set graph's name
	        stockChart.setTitle(currentStock.getName());
	    	     
	        // Arm default timeline
	    	timeLineButtons[0].arm();
	    	
	    	generateSeries();
	    	
	    	graphTimeLineHelper();
    	}
    }
    
    /**
     *  1st Iteration function that will logout the current user and bring them back to the log in page.
     *  2nd Iteration will incl saving user information for their favorite stock.
     *  @param event Action Event
     */
    
    @FXML
    private void logout(ActionEvent event) {
    	navigateToLogin(event);
    }
    
    /**
     * Helper function so it can be called when both timeline and stock are changed
     */
    
    private void graphTimeLineHelper() {
   	 
    	//****************TESTING TIME********************
    	long startTime = System.currentTimeMillis();
    	
    	clearData();

        // Loop for all timeline Buttons
        for (int i = 0; i < timeLineButtons.length; i++) {
		    // Filter which timeline is picked
		    if(timeLineButtons[i].isArmed() && !isTimeLineDisplayed[i]) {
		    	// Updates current Timeline
		        currentTimeLine = timeIntervals[i];
		        
		        System.out.println("TEST");
		        
		        // Generates stock info and set up name for the title
		        stockSerie.getData().addAll(currentStock.getPricesInRange(currentTimeLine).getData());	            
	            
		        // Add the correct timeline name to legend
		        switch (i) {
			        case 0: stockSerie.setName("Closing Prices: One Year"); break;
			        case 1: stockSerie.setName("Closing Prices: Two Years"); break;
			        case 2: stockSerie.setName("Closing Prices: Five Years"); break;
			        case 3: stockSerie.setName("Closing Prices: All Time"); break;
		        }
		        
		        // Loads currently selected MAs with new timeline
		        graphMovingAverageHelper();
		        
		        // makes sure only 1 button is selected
		        timeLineButtons[i].disarm();
		        
		        isTimeLineDisplayed[i] = true;
		        
		        // Break out as soon as it finds that 1 button is armed
		        break;
		    }
        }
        
        //****************TIME TESTING**********************
        long endTime = System.currentTimeMillis();
        System.out.println("The Time of the entire GraphTimeline Method " + ((endTime-startTime)/1000.0) + "\n");
    }
    
    /**
     * Displays MA based on the info generated by the graphMovingAverages() method
     * Checks for all 4 checkboxes at once
     */
    
    private void graphMovingAverageHelper() {
    	if (isStockGenerated) {
    		
    		/*******************DISPLAY MAs*******************/
    		
    		// Loop for all MA buttons
    		for (int i = 0; i < maButtons.length; i++) {
				if (maButtons[i].isSelected()) {  // Ensures checkbox is activated (checked)
					if (!isMaDisplayed[i]) {  // Ensures MA isn't displayed
						movingAverageSeries[i].getData().addAll(currentStock.getMovingAverage(maIntervals[i], currentTimeLine).getData()); // Adds MA to chart
						isMaDisplayed[i] = true; // Set MA to displayed
					}
				}
				else if (isMaDisplayed[i]) { // Ensures that MA isn't displayed
					movingAverageSeries[i].getData().remove(0, movingAverageSeries[i].getData().size()); // Removes MA from chart
					isMaDisplayed[i] = false; // Set MA to not displayed
				}
    		}
    		
    		/*******************DISPLAY INTERSECTIONS*******************/
    		
    		// Checks if both 20 days and 200 days MAs are selected to display recommendations
    		if (maButtons[0].isSelected() && maButtons[3].isSelected()) {
    			// Create a new series for intersections of both MAs
    			XYChart.Series<String, Number> tempSerie = currentStock.getIntersectionsList(currentTimeLine);			
// TO BE ADDED
//				boolean test[] = current.getIntersectValue();
    			
    			// Loops for all data points in the intersections
    			for (int i = 0 ; i < tempSerie.getData().size(); i++) {
    				// Creates a new pane at each intersection
        			StackPane tempPane = new StackPane();
        			tempPane.setPrefWidth(10);
        			tempPane.setPrefHeight(10);
        			// Create a background fill
        			BackgroundFill fill = new BackgroundFill(Color.GREEN, new CornerRadii(5), Insets.EMPTY);
// TO BE ADDED
//        			if (test[i])
//        				fill = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
//        			else
//        				fill = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
        			
        			// Set pane color
        			tempPane.setBackground(new Background(fill));
        			// Overwrite symbols in the graph
    				tempSerie.getData().get(i).setNode(tempPane);
    			}
    			
    			// Add all intersections with their panes to the graph
    			intersectionSerie.getData().addAll(tempSerie.getData());
    		}
    		else {
    			// Remove all intersections from the graph if both MAs aren't selected
    			intersectionSerie.getData().remove(0, intersectionSerie.getData().size());
    		}
    	}
    }
    
    /**
     * Generates the series for the first selected stock.
     * Series remain the same for all selected stocks afterwards
     */

	@SuppressWarnings("unchecked")
	private void generateSeries() {		
		
		// Checks if first time generating graph
		if (!isStockGenerated) {
			
			// Create all series
			stockSerie = new XYChart.Series<String, Number>();
	    	movingAverageSeries[0] = new XYChart.Series<String, Number>();
	    	movingAverageSeries[1] = new XYChart.Series<String, Number>();
	    	movingAverageSeries[2] = new XYChart.Series<String, Number>();
	    	movingAverageSeries[3] = new XYChart.Series<String, Number>();
	    	intersectionSerie = new XYChart.Series<String, Number>();
	        
	    	// Set all series names
	    	stockSerie.setName("Closing Prices: One Year");
	    	movingAverageSeries[0].setName("Moving Average: 20 Days");
	    	movingAverageSeries[1].setName("Moving Average: 50 Days");
	    	movingAverageSeries[2].setName("Moving Average: 100 Days");
	    	movingAverageSeries[3].setName("Moving Average: 200 Days");
	    	intersectionSerie.setName("Moving Average Intersections");
	    	
	    	// Add all series to graph
	    	stockChart.getData().addAll
	    	(
    			stockSerie,
    			movingAverageSeries[0],
    			movingAverageSeries[1],
    			movingAverageSeries[2],
    			movingAverageSeries[3],
    			intersectionSerie
	    	);
	    	
	    	// Ensures stock doesn't get generated multiple times
	    	isStockGenerated = true;
		}
    }
	
	/**
	 * Add all buttons to their respective arrays and other arrays
	 */
	
	@SuppressWarnings("unchecked")
	private void initializeArrays() {
		
		// Add timeline buttons
        timeLineButtons = new Button[4];
        timeLineButtons[0] = timeLineButton_1;
        timeLineButtons[1] = timeLineButton_2;
        timeLineButtons[2] = timeLineButton_5;
        timeLineButtons[3] = timeLineButton_all;
        
        // Add MA buttons
        maButtons = new CheckBox[4];
        maButtons[0] = maButton_20;
        maButtons[1] = maButton_50;
        maButtons[2] = maButton_100;
        maButtons[3] = maButton_200;
        
        // Add time intervals
        timeIntervals = new TimeInterval[4];
        timeIntervals[0] = TimeInterval.OneYear;
        timeIntervals[1] = TimeInterval.TwoYears;
        timeIntervals[2] = TimeInterval.FiveYears;
        timeIntervals[3] = TimeInterval.AllTime;
        
        // Add MA intervals
        maIntervals = new MovingAverageInterval[4];
        maIntervals[0] = MovingAverageInterval.TwentyDay;
        maIntervals[1] = MovingAverageInterval.FiftyDay;
        maIntervals[2] = MovingAverageInterval.HundredDay;
        maIntervals[3] = MovingAverageInterval.TwoHundredDay;
        
        // Instantiate display check array for timelines
        isTimeLineDisplayed = new boolean[4];
        
        // Instantiate display check array for MAs
        isMaDisplayed = new boolean[4];
        
		// Initialize MAs array
    	movingAverageSeries = new XYChart.Series[4];
	}
	
	/**
	 * Clears all data from graph for new stock or timeline
	 */
	
	private void clearData() {
    	
    	// Remove current closing prices
    	if (stockSerie != null && stockSerie.getData().size() > 0)
    		stockSerie.getData().remove(0, stockSerie.getData().size());
    	
        // Removes current MAs
        for (XYChart.Series<String, Number> ma : movingAverageSeries) {
        	if (ma != null && ma.getData() != null && ma.getData().size() > 0)
        		ma.getData().remove(0, ma.getData().size());
        }
        
    	// Resets timeline display checks
    	for (int i = 0; i < isTimeLineDisplayed.length; i++)
    		isTimeLineDisplayed[i] = false;
    	
    	// Resets MAs display checks
        for (int i = 0; i < isMaDisplayed.length; i++)
        	isMaDisplayed[i] = false;
        
        // Removes current intersections
        if (intersectionSerie != null && intersectionSerie.getData().size() > 0)
        	intersectionSerie.getData().remove(0, intersectionSerie.getData().size());    	
	}

    /**
     *  Stage change method to support the logout method
     */
    
    private void navigateToLogin(ActionEvent event) {
        Parent loginView = null;
        
        try {
            loginView = FXMLLoader.load(getClass().getResource("../view/LoginView.fxml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        Scene loginScene = new Scene(loginView, 1280, 720);
        Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
}