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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import model.Stock;
import model.TimeInterval;
import model.MovingAverageInterval;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MainController {

    boolean isStockGenerated = false;
    boolean isMaDisplayed[];
    Stock currentStock;
    TimeInterval currentTimeLine;
    TimeInterval timeIntervals[];
    XYChart.Series<String, Number> stockSerie;
    XYChart.Series<String, Number>[] movingAverageSeries;

    @FXML
    TextField searchTextField;

    @FXML
    Button timeLineButton_1, timeLineButton_2, timeLineButton_5, timeLineButton_all;
    
    Button timeLineButtons[];

    @FXML
    CheckBox maButton_20, maButton_50, maButton_100, maButton_200;
    
    CheckBox maButtons[];

    @FXML
    LineChart<String, Number> stockChart;

    /**
     * For 1st Iteration, default stock will be outputted with a timeline of 5 years,
     */

    @FXML
    public void initialize(){
    	
    	// Checks if a stock has already been generated
    	if (!isStockGenerated) {
    		
            // Create stock object from model class
            currentStock = new Stock("Dummy Stock", "STOK", "src/resources/Sample data.csv");

            // Create closing prices serie from stock object
            stockSerie = currentStock.getPricesInRange(TimeInterval.FiveYears);
            stockSerie.setName("Closing Prices: Five Years");

            // Add closing price serie to the graph and initialize
            // corresponding TimeLine for computing Moving Averages.
            stockChart.getData().add(stockSerie);
            currentTimeLine = TimeInterval.FiveYears;
            
            isMaDisplayed = new boolean[4];
            
            for (int i = 0; i < isMaDisplayed.length; i++) {
            	isMaDisplayed[i] = false;
            }
            
            // Add all buttons to their respective arrays and other arrays
            timeLineButtons = new Button[4];
            timeLineButtons[0] = timeLineButton_1;
            timeLineButtons[1] = timeLineButton_2;
            timeLineButtons[2] = timeLineButton_5;
            timeLineButtons[3] = timeLineButton_all;
            maButtons = new CheckBox[4];
            maButtons[0] = maButton_20;
            maButtons[1] = maButton_50;
            maButtons[2] = maButton_100;
            maButtons[3] = maButton_200;
            timeIntervals = new TimeInterval[4];
            timeIntervals[0] = TimeInterval.OneYear;
            timeIntervals[1] = TimeInterval.TwoYears;
            timeIntervals[2] = TimeInterval.FiveYears;
            timeIntervals[3] = TimeInterval.AllTime;

            // Set graph's attributes
            stockChart.setCreateSymbols(false);
            stockChart.setTitle(currentStock.getName());
            stockChart.setCursor(Cursor.CROSSHAIR);
            
    		// Initiate the MA array
        	movingAverageSeries = new XYChart.Series[4];
        	            
        	// Generates all moving averages
        	generateMovingAverages();

            // Prevents creation of multiple stock series
            isStockGenerated = true;
        }
    }
    
    /**
     * Graph and display the stock according to current timeline
     * @Param = TimeLineButton Event
     */
    
    @FXML
    private void graphTimeline(ActionEvent timeLineButtonPressed) {
        // Clear any previous graph data
        stockChart.getData().clear();
        // Loop for all Timeline Buttons
        for (int i = 0; i < timeLineButtons.length; i++) {
		    // Filter which timeline is picked
		    if(timeLineButtons[i].isArmed()) {
		    	// Updates current Timeline
		        currentTimeLine = timeIntervals[i];
		        // Generates stock info and set up name for the title
		        stockSerie = currentStock.getPricesInRange(currentTimeLine);
		        switch (i) {
			        case 0: stockSerie.setName("Closing Prices: One Year"); break;
			        case 1: stockSerie.setName("Closing Prices: Two Years"); break;
			        case 2: stockSerie.setName("Closing Prices: Five Years"); break;
			        case 3: stockSerie.setName("Closing Prices: All Time"); break;
		        }
		        // Displays the corresponding Stock timeline
		        stockChart.getData().add(stockSerie);
		        // Ensure Moving Averages are not displayed from previous Stock
		        resetMovingAverages();
		        // Generates the MovingAverages in an array.
		        generateMovingAverages();
		        // Loads selected MAs
		        movingAveragesLoader();
		    }
        }
    }
    
    /**
     * Triggering & helper function to load MAs to the graph
     * @param movingAverageCheckboxSelected an ActionEvent sent from MainView
     */
    
    @FXML
    private void graphMovingAverages(ActionEvent movingAverageCheckboxSelected) {
    	movingAveragesLoader();
    }
    
    /**
     * Displays MA based on the info generated by the graphMovingAverages() method
     * Checks for all 4 checkboxes at once
     */
    
    private void movingAveragesLoader() {
    	if (isStockGenerated) {
    		// Loop for all MA buttons
    		for (int i = 0; i < maButtons.length; i++) {
				if (maButtons[i].isSelected()) {  // Ensures checkbox is activated (checked)
					if (!isMaDisplayed[i]) {  // Ensures MA isn't displayed
						stockChart.getData().add(movingAverageSeries[i]); // Adds MA to chart
						isMaDisplayed[i] = true; // Set MA to displayed
					}
				}
				else if (isMaDisplayed[i]) { // Ensures that MA isn't displayed
					stockChart.getData().remove(movingAverageSeries[i]); // Removes MA from chart
					isMaDisplayed[i] = false; // Set MA to not displayed
				}
    		}
    	}
    }

    /**
     *  The following 4 methods, displays MA based on the info generated by the generateMovingAverages() method.
     *  @params = MovingAverageIntervalcheckbox an Action Event sent from MainView (based on their values; 20, 50 , 100 , 200)
     */
    
    @FXML
    private void graphMovingAverageTwenty(ActionEvent movingAverageInterval20CheckboxPressed) {

        if (maButton_20.isSelected()){  //Ensures checkbox is activated (checked)
            stockChart.getData().add(movingAverageSeries[0]);  //Displays MA to graph
        }
        else if (!maButton_20.isSelected()) { //Ensures if checkbox is deselected
            stockChart.getData().remove(movingAverageSeries[0]); //Removes MA form the graph
        }
    }

    @FXML
    private void graphMovingAverageFifty(ActionEvent movingAverageIntervalFiftyCheckboxPressed) {
        if (maButton_50.isSelected()){
            stockChart.getData().add(movingAverageSeries[1]);
        }
        else if (!maButton_50.isSelected()){
            stockChart.getData().remove(movingAverageSeries[1]);
        }
    }

    @FXML
    private void graphMovingAverageHundred(ActionEvent movingAverageIntervalHundredCheckboxPressed) {
        if (maButton_100.isSelected()) {
            stockChart.getData().add(movingAverageSeries[2]);
        }
        else if (!maButton_100.isSelected())
            stockChart.getData().remove(movingAverageSeries[2]);
    }

    @FXML
    private void graphMovingAverageTwoHundred(ActionEvent movingAverageIntervalTwoHundredCheckboxPressed) {
        if (maButton_200.isSelected()) {
            stockChart.getData().add(movingAverageSeries[3]);
        }
        else if (!maButton_200.isSelected())
            stockChart.getData().remove(movingAverageSeries[3]);
    }

    /**
     *  Generates the Moving Averages info from methods in Stock the class. Each MA is created and initialized
     *  in an array of Series according to the last Timeline (currentTimeline) selected from the last Stock created.
     */
    
    private void generateMovingAverages(){
    	
    	// Initiate all MA for current timeline
        movingAverageSeries[0] = currentStock.getMovingAverage(MovingAverageInterval.TwentyDay, currentTimeLine);
        movingAverageSeries[1] = currentStock.getMovingAverage(MovingAverageInterval.FiftyDay, currentTimeLine);
        movingAverageSeries[2] = currentStock.getMovingAverage(MovingAverageInterval.HundredDay, currentTimeLine);
        movingAverageSeries[3] = currentStock.getMovingAverage(MovingAverageInterval.TwoHundredDay, currentTimeLine);
        
    	// Initiate all MA names
    	movingAverageSeries[0].setName("Moving Average: 20 Days");
    	movingAverageSeries[1].setName("Moving Average: 50 Days");
    	movingAverageSeries[2].setName("Moving Average: 100 Days");
    	movingAverageSeries[3].setName("Moving Average: 200 Days");
    }

    /**
     *  Reverts all the moving averages
     */
    
    private void resetMovingAverages(){
    	
        for (int i = 0; i < isMaDisplayed.length; i++) {
        	isMaDisplayed[i] = false;
        }
    }

    /**
     *  1st Iteration function that will logout the current user and bring them back to the log in page.
     *  2nd Iteration will incl saving user information for their favorite stock.
     *  @param logoutButtonPressed Action Event
     */
    
    @FXML
    private void logout(ActionEvent logoutButtonPressed) {
            navigateToLogin(logoutButtonPressed);
        }

    /**
     *  Stage change method to support the logout method
     */
    
    private void navigateToLogin(ActionEvent event) {
        Parent loginView = null;
        try {
            loginView = FXMLLoader.load(getClass().getResource("../view/LoginView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene loginScene = new Scene(loginView, 1280, 720);
        Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
}