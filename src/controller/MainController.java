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

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Stock;
import model.TimeInterval;
import model.MovingAverageInterval;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import view.StocksRUs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

public class MainController {

	private boolean isStockGenerated = false;
	private boolean isMovingAverageSelected[], isTimeLineDisplayed[];
    private Stock currentStock;
	private TimeInterval timeIntervals[];
	private MovingAverageInterval movingAverageIntervals[];
	private XYChart.Series<String, Number> stockSeries;
	private XYChart.Series<String, Number> buyIntersectionSeries;
	private XYChart.Series<String, Number> sellIntersectionSeries;
	private XYChart.Series<String, Number>[] movingAverageSeries;
	private Button timelineButtons[];

    @FXML
	private Label username, recommendation;

    @FXML
	private Button timeLineButton_1, timeLineButton_2, timeLineButton_5, timeLineButton_all;
    
    @FXML
    private ComboBox<String> maDropDown_1, maDropDown_2;

    @FXML
	private LineChart<String, Number> stockChart;
    
    /**
     * Function called when the view is created
     */
    @FXML
    private void initialize() {
        // Set graph's attributes
        stockChart.setCreateSymbols(false);

		username.setText("Logged in as " + StocksRUs.getCurrentUser().getEmail());

        // Initialize all arrays
    	initializeButtons();
    }
    
    /**
     * Graph and display the stock according to current timeline
     * @Param = TimeLineButton Event
     */
    @FXML
    private void timelineSelected(ActionEvent event) {
    	if (isStockGenerated)
    		graphClosingPrices();
    }
    
    /**
     * Triggering & helper function to load MAs to the graph
     * @param event an ActionEvent sent from MainView
     */
    @SuppressWarnings("unchecked")
	@FXML
    private void movingAverageSelected(ActionEvent event) {
    	
    	if (isStockGenerated) {
	        ObservableList<Node> contentsOfHBox = ((Node)event.getSource()).getParent().getChildrenUnmodifiable();
	        ComboBox<String> selectedMovingAverageDropdown = (ComboBox<String>)event.getSource();
	        ComboBox<String> otherMovingAverageDropdown = null;
	        Class<? extends Label> labelClass = new Label().getClass();
	        Class<? extends Button> buttonClass = new Button().getClass();
	        String selectedMovingAverage = (String)selectedMovingAverageDropdown.getValue();
	        
	        for (int i=0; i < contentsOfHBox.size(); i++) {
	            if (!contentsOfHBox.get(i).equals(selectedMovingAverageDropdown)
	            		&& !contentsOfHBox.get(i).getClass().equals(labelClass)
	            		&& !contentsOfHBox.get(i).getClass().equals(buttonClass)) {
	                otherMovingAverageDropdown = (ComboBox<String>)contentsOfHBox.get(i);
	            }
	        }
	        
	        EventHandler<ActionEvent> mainController = selectedMovingAverageDropdown.getOnAction();
	        selectedMovingAverageDropdown.setOnAction(null);
	        otherMovingAverageDropdown.setOnAction(null);
	        otherMovingAverageDropdown.getItems().remove(selectedMovingAverage);
	
	        if (otherMovingAverageDropdown.getItems().size() < 3) {
	            Platform.runLater(() -> {
	                resetMovingAverageDropdowns();
	            });
	
	        }
	        
	        selectedMovingAverageDropdown.setOnAction(mainController);
	        otherMovingAverageDropdown.setOnAction(mainController);
	
	        switch ((String)selectedMovingAverageDropdown.getValue()) {
	            case "20 Days":
	                isMovingAverageSelected[0] = true;
	                isMovingAverageSelected[1] = false;
	                isMovingAverageSelected[2] = false;
	                isMovingAverageSelected[3] = false;
	                break;
	            case "50 Days":
	                isMovingAverageSelected[1] = true;
	                isMovingAverageSelected[2] = false;
	                isMovingAverageSelected[3] = false;
	                isMovingAverageSelected[0] = false;
	                break;
	            case "100 Days":
	            	isMovingAverageSelected[2] = true;
	            	isMovingAverageSelected[3] = false;
	            	isMovingAverageSelected[0] = false;
	            	isMovingAverageSelected[1] = false;
	                break;
	            case "200 Days":
	            	isMovingAverageSelected[3] = true;
	            	isMovingAverageSelected[0] = false;
	            	isMovingAverageSelected[1] = false;
	            	isMovingAverageSelected[2] = false;
	                break;
	        }
	        
	        if (otherMovingAverageDropdown.getValue() != null) {
	        	 switch ((String)otherMovingAverageDropdown.getValue()) {
		             case "20 Days":
		                 isMovingAverageSelected[0] = true;
		                 break;
		             case "50 Days":
		                 isMovingAverageSelected[1] = true;
		                 break;
		             case "100 Days":
		             	isMovingAverageSelected[2] = true;
		                 break;
		             case "200 Days":
		             	isMovingAverageSelected[3] = true;
		                 break;
		         }
	        }
        }
    }
    
    private void resetMovingAverageDropdowns() {
        ObservableList<String> dropdownContents = FXCollections.observableArrayList("20 Days", "50 Days", "100 Days", "200 Days");
        maDropDown_1.getItems().setAll(dropdownContents);
        maDropDown_2.getItems().setAll(dropdownContents);
    }
    
    private void resetMovingAverageDropdownsSelection() {
    	EventHandler<ActionEvent> maController_1 = maDropDown_1.getOnAction();
    	EventHandler<ActionEvent> maController_2 = maDropDown_2.getOnAction();
    	
        maDropDown_1.setOnAction(null);
        maDropDown_2.setOnAction(null);
    	
    	maDropDown_1.getSelectionModel().clearSelection();
    	maDropDown_2.getSelectionModel().clearSelection();
    	
    	resetMovingAverageDropdowns();
    	
        maDropDown_1.setOnAction(maController_1);
        maDropDown_2.setOnAction(maController_2);
    }
    
    /**
     * Allows the user to change between all stocks
     * @param event an ActionEvent sent from MainView
     */
    @FXML
    private void selectStock(ActionEvent event) {
    	
    	// Get clicked button information
    	Button clickedButton = (Button) event.getSource();

    	// Ensures no computation will be done if same stock is selected
    	if (currentStock == null || currentStock.getName().compareTo(clickedButton.getText()) != 0) {
    		
    		// Change current stock
	    	currentStock = new Stock(clickedButton.getText(), clickedButton.getId());

	        // Set graph's name
	        stockChart.setTitle(currentStock.getName());
	    	     
	        // Arm default timeline
	    	timelineButtons[0].arm();

	    	if (!isStockGenerated)
	    		generateSeries();
	    	
	    	graphClosingPrices();
	    	
	    	resetMovingAverageDropdownsSelection();
	    	
	    	currentStock.getIntersectionsList(MovingAverageInterval.FiftyDay, MovingAverageInterval.TwoHundredDay);
	    	
	    	if (currentStock.getRecommendation()) {
	    		recommendation.setText("Buy");
	    		recommendation.setTextFill(Color.GREEN);
	    	}
	    	else {
	    		recommendation.setText("Sell");
	    		recommendation.setTextFill(Color.RED);
	    	}
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
    private void graphClosingPrices() {
    	clearData();

        // Loop for all timeline Buttons
        for (int i = 0; i < timelineButtons.length; i++) {
		    // Filter which timeline is picked
		    if(timelineButtons[i].isArmed() && !isTimeLineDisplayed[i]) {
		    	// Updates current Timeline
		        currentStock.setTimeline(timeIntervals[i]);

		        // Generates stock info and set up name for the title
		        stockSeries.getData().addAll(currentStock.getPricesInRange().getData());
	            
		        // Add the correct timeline name to legend
		        switch (i) {
			        case 0: stockSeries.setName("Closing Prices: One Year"); break;
			        case 1: stockSeries.setName("Closing Prices: Two Years"); break;
			        case 2: stockSeries.setName("Closing Prices: Five Years"); break;
			        case 3: stockSeries.setName("Closing Prices: All Time"); break;
		        }
		        
		        // Loads currently selected MAs with new timeline
		        //graphMovingAverage();
		        
		        // makes sure only 1 button is selected
		        timelineButtons[i].disarm();
		        
		        isTimeLineDisplayed[i] = true;
		        
		        // Break out as soon as it finds that 1 button is armed
		        break;
		    }
        }
    }
    
    /**
     * Displays MA based on the info generated by the graphMovingAverages() method
     * Checks for all 4 checkboxes at once
     */

    @FXML
    private void graphMovingAverage(ActionEvent event) {
    	
    	if (isStockGenerated) {
	    	for (int i = 0; i < 4; i++) {
	    		if (isMovingAverageSelected[i]) {
	    			movingAverageSeries[i].getData().addAll(currentStock.getMovingAverage(movingAverageIntervals[i]).getData());
	    		}
	    		else
	    			movingAverageSeries[i].getData().removeAll(movingAverageSeries[i].getData());
	    	}
	    	
	    	
	    	for (int i = 0; i < 4; i++) {
	    		isMovingAverageSelected[i] = false;
	    	}
	    	
	    	resetMovingAverageDropdownsSelection();
    	}
    }
    
//    private void graphMovingAverage() {
//		/*******************DISPLAY MAs*******************/
//		
//		// Loop for all MA buttons
//		for (int i = 0; i < movingAverageButtons.length; i++) {
//			if (movingAverageButtons[i].isSelected()) {  // Ensures checkbox is activated (checked)
//				if (!isMovingAverageDisplayed[i]) {  // Ensures MA isn't displayed
//					movingAverageSeries[i].getData().addAll(currentStock.getMovingAverage(movingAverageIntervals[i]).getData()); // Adds MA to chart
//					isMovingAverageDisplayed[i] = true; // Set MA to displayed
//				}
//			}
//			else if (isMovingAverageDisplayed[i]) { // Ensures that MA isn't displayed
//				movingAverageSeries[i].getData().remove(0, movingAverageSeries[i].getData().size()); // Removes MA from chart
//				isMovingAverageDisplayed[i] = false; // Set MA to not displayed
//			}
//		}
//		
//		/*******************DISPLAY INTERSECTIONS*******************/
//		
//		// Checks if both 20 days and 200 days MAs are selected to display recommendations
//		// TODO: change to accommodate any 2 MAs
//		if (movingAverageButtons[0].isSelected() && movingAverageButtons[3].isSelected()) {
//			// Create a new series for intersections of both MAs
//			XYChart.Series<String, Number> tempIntersectionsSeries = currentStock.getIntersectionsList(MovingAverageInterval.TwentyDay, MovingAverageInterval.TwoHundredDay);
//// TO BE ADDED
////				boolean test[] = current.getIntersectValue();
//			
//			// Loops for all data points in the intersections
//			for (int i = 0 ; i < tempIntersectionsSeries.getData().size(); i++) {
//				// Creates a new pane at each intersection
//    			StackPane tempPane = new StackPane();
//    			tempPane.setPrefWidth(10);
//    			tempPane.setPrefHeight(10);
//    			// Create a background fill
//    			BackgroundFill fill = new BackgroundFill(Color.GREEN, new CornerRadii(5), Insets.EMPTY);
//// TO BE ADDED
////        			if (test[i])
////        				fill = new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY);
////        			else
////        				fill = new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY);
//    			
//    			// Set pane color
//    			tempPane.setBackground(new Background(fill));
//    			// Overwrite symbols in the graph
//				tempIntersectionsSeries.getData().get(i).setNode(tempPane);
//			}
//			
//			// Add all intersections with their panes to the graph
//			buyIntersectionSeries.getData().addAll(tempIntersectionsSeries.getData());
//		}
//		else {
//			// Remove all intersections from the graph if both MAs aren't selected
//			buyIntersectionSeries.getData().remove(0, buyIntersectionSeries.getData().size());
//		}
//		
//    }
    
    /**
     * Generates the series for the first selected stock.
     * Series remain the same for all selected stocks afterwards
     */

	@SuppressWarnings("unchecked")
	private void generateSeries() {		

		// Create all series
		stockSeries = new XYChart.Series<>();
    	movingAverageSeries[0] = new XYChart.Series<>();
    	movingAverageSeries[1] = new XYChart.Series<>();
    	movingAverageSeries[2] = new XYChart.Series<>();
    	movingAverageSeries[3] = new XYChart.Series<>();
    	buyIntersectionSeries = new XYChart.Series<>();
    	sellIntersectionSeries = new XYChart.Series<>();
        
    	// Set all series names
    	stockSeries.setName("Closing Prices: One Year");
    	movingAverageSeries[0].setName("Moving Average: 20 Days");
    	movingAverageSeries[1].setName("Moving Average: 50 Days");
    	movingAverageSeries[2].setName("Moving Average: 100 Days");
    	movingAverageSeries[3].setName("Moving Average: 200 Days");
    	buyIntersectionSeries.setName("Buy Recommendations");
    	sellIntersectionSeries.setName("Sell Recommendations");
    	
    	// Add all series to graph
    	stockChart.getData().addAll
    	(
			stockSeries,
			movingAverageSeries[0],
			movingAverageSeries[1],
			movingAverageSeries[2],
			movingAverageSeries[3],
			buyIntersectionSeries,
			sellIntersectionSeries
    	);
    	
    	// Ensures stock doesn't get generated multiple times
    	isStockGenerated = true;
    }
	
	/**
	 * Add all buttons to their respective arrays and other arrays
	 */
	@SuppressWarnings("unchecked")
	private void initializeButtons() {
		
		// Add timeline buttons
        timelineButtons = new Button[4];
        timelineButtons[0] = timeLineButton_1;
        timelineButtons[1] = timeLineButton_2;
        timelineButtons[2] = timeLineButton_5;
        timelineButtons[3] = timeLineButton_all;

        maDropDown_1.getItems().add("20 Days");
        maDropDown_1.getItems().add("50 Days");
        maDropDown_1.getItems().add("100 Days");
        maDropDown_1.getItems().add("200 Days");

        maDropDown_2.getItems().add("20 Days");
        maDropDown_2.getItems().add("50 Days");
        maDropDown_2.getItems().add("100 Days");
        maDropDown_2.getItems().add("200 Days");
        
        // Add time intervals
        timeIntervals = new TimeInterval[4];
        timeIntervals[0] = TimeInterval.OneYear;
        timeIntervals[1] = TimeInterval.TwoYears;
        timeIntervals[2] = TimeInterval.FiveYears;
        timeIntervals[3] = TimeInterval.AllTime;
        
        // Add MA intervals
        movingAverageIntervals = new MovingAverageInterval[4];
        movingAverageIntervals[0] = MovingAverageInterval.TwentyDay;
        movingAverageIntervals[1] = MovingAverageInterval.FiftyDay;
        movingAverageIntervals[2] = MovingAverageInterval.HundredDay;
        movingAverageIntervals[3] = MovingAverageInterval.TwoHundredDay;
        
        // Instantiate display check array for timelines
        isTimeLineDisplayed = new boolean[4];
        
        // Instantiate display check array for MAs
        isMovingAverageSelected = new boolean[4];
        
		// Initialize MAs array
    	movingAverageSeries = new XYChart.Series[4];
	}
	
	/**
	 * Clears all data from graph for new stock or timeline
	 */
	private void clearData() {
    	
    	// Remove current closing prices
    	if (stockSeries != null && stockSeries.getData().size() > 0)
    		stockSeries.getData().remove(0, stockSeries.getData().size());
    	
        // Removes current MAs
        for (XYChart.Series<String, Number> ma : movingAverageSeries) {
        	if (ma != null && ma.getData() != null && ma.getData().size() > 0)
        		ma.getData().remove(0, ma.getData().size());
        }
        
    	// Resets timeline display checks
		// TODO: remove this? SIMON
    	for (int i = 0; i < isTimeLineDisplayed.length; i++)
    		isTimeLineDisplayed[i] = false;
    	
    	// Resets MAs display checks
        for (int i = 0; i < isMovingAverageSelected.length; i++)
        	isMovingAverageSelected[i] = false;
        
        // Removes current intersections
        if (buyIntersectionSeries != null && buyIntersectionSeries.getData().size() > 0)
        	buyIntersectionSeries.getData().remove(0, buyIntersectionSeries.getData().size());
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