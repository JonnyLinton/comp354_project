/*
   Controller for the Main view of the inStock app for the first iteration
   Generates moving averages for a specific timeline of a stock and displays them according to user input
   To be fully implemented in the 2nd iteration:
            Full SearchStock method based on Yahoo Finance API for top 30 Stocks
            Recommendations method
            Favorite Stock based in user login
 */

package controller;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    TimeInterval currentTimeLine;
    XYChart.Series<String, Number> stockSerie;
    XYChart.Series<String, Number>[] movingAverageSeries;

    @FXML
    TextField searchTextField;

    @FXML
    Button timeLineButton_1;
    @FXML
    Button timeLineButton_2;
    @FXML
    Button timeLineButton_5;
    @FXML
    Button timeLineButton_all;

    @FXML
    CheckBox maButton_20;
    @FXML
    CheckBox maButton_50;
    @FXML
    CheckBox maButton_100;
    @FXML
    CheckBox maButton_200;

    @FXML
    LineChart<String, Number> stockChart;



    /*
        For 1st Iteration, default stock will be outputted with a timeline of 5 years,
        after the Search Button is launched.
        @param = SearchButtonPressed Event
        @return = Default stock with a timeline of 5 years.
     */

    @FXML
    public void showDefaultStock(ActionEvent searchButtonPressed) {

        // Checks if a stock has already been generated
        if (!isStockGenerated) {

            // Create stock object from model class
            currentStock = new Stock("Dummy Stock", "STOK", "src/resources/Sample data.csv");

            // Create closing prices serie from stock object
            stockSerie = currentStock.getPricesInRange(TimeInterval.FiveYears);
            stockSerie.setName("Closing Prices: Five Years");

            // Add closing price serie to the graph and initialize
            // correspondingTimeLine for computing Moving Averages.
            stockChart.getData().add(stockSerie);
            currentTimeLine = TimeInterval.FiveYears;

            //Graphs all Moving Averages
            graphMovingAverages();

            // Set graph's attributes
            stockChart.setCreateSymbols(false);
            stockChart.setTitle(currentStock.getName());
            stockChart.setCursor(Cursor.CROSSHAIR);

            // Prevents creation of multiple stock series
            isStockGenerated = true;
        }
    }
    /*
        Graph and display the stock according to the timeline chosen.
        @Param = TimeLineButton Event
     */
    @FXML
    private void graphTimeline(ActionEvent timeLineButtonPressed) {
        //clear any previous graph data
        stockChart.getData().clear();


        if(timeLineButton_1.isArmed()) {  //filter which timeline is picked
            stockSerie = currentStock.getPricesInRange(TimeInterval.OneYear);
            stockSerie.setName("Closing Prices: One Year");

            //Displays the corresponding Stock timeline
            stockChart.getData().add(stockSerie);
            currentTimeLine = TimeInterval.OneYear;

            //Ensure Moving Averages are not displayed from previous Stock
            setCheckBoxToNone();

            //Generates the MovingAverages in an array.
            graphMovingAverages();

            //Displays title of timeline in header
            stockChart.setTitle(currentStock.getName());

        }
        else if(timeLineButton_2.isArmed()) {

            stockSerie = currentStock.getPricesInRange(TimeInterval.TwoYears);
            stockSerie.setName("Closing Prices: Two Years");

            stockChart.getData().add(stockSerie);
            currentTimeLine = TimeInterval.TwoYears;

            setCheckBoxToNone();

            graphMovingAverages();

            stockChart.setTitle(currentStock.getName());

        }
        else if(timeLineButton_5.isArmed()) {

            stockSerie = currentStock.getPricesInRange(TimeInterval.FiveYears);
            stockSerie.setName("Closing Prices: Five Years");

            stockChart.getData().add(stockSerie);
            currentTimeLine = TimeInterval.FiveYears;

            setCheckBoxToNone();

            graphMovingAverages();

            stockChart.setTitle(currentStock.getName());

        }
        else if(timeLineButton_all.isArmed()) {

            stockSerie = currentStock.getPricesInRange(TimeInterval.AllTime);
            stockSerie.setName("Closing Prices: All Time");

            stockChart.getData().add(stockSerie);
            currentTimeLine = TimeInterval.AllTime;

            setCheckBoxToNone();

            graphMovingAverages();

            stockChart.setTitle(currentStock.getName());

        }
    }

    /*
        The following 4 methods, displays the Ma based on the info generated by the graphMovingAverages() method.
        @params = MovingAverageIntervalcheckbox(based on their values; 20, 50 , 100 , 200)
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
        if (maButton_100.isSelected())
            stockChart.getData().add(movingAverageSeries[2]);
        else if (!maButton_100.isSelected())
            stockChart.getData().remove(movingAverageSeries[2]);
    }

    @FXML
    private void graphMovingAverageTwoHundred(ActionEvent movingAverageIntervalTwoHundredCheckboxPressed) {
        if (maButton_200.isSelected())
            stockChart.getData().add(movingAverageSeries[3]);
        else if (!maButton_200.isSelected())
            stockChart.getData().remove(movingAverageSeries[3]);
    }
    // END OF GRAPHMOVINGAVERAGEINTERVAL METHODS //


    /*
        Generates the Moving Averages info from methods in Stock the class. Each MA is created and initialized
        in an array of Series according to the last Timeline (currentTimeline) selected from the last Stock created.
     */
    private void graphMovingAverages(){
        movingAverageSeries = new XYChart.Series[4];
        movingAverageSeries[0] = currentStock.getMovingAverage(MovingAverageInterval.TwentyDay, currentTimeLine);
        movingAverageSeries[0].setName("20 days MA");
        movingAverageSeries[1] = currentStock.getMovingAverage(MovingAverageInterval.FiftyDay, currentTimeLine);
        movingAverageSeries[1].setName("50 days MA");
        movingAverageSeries[2] = currentStock.getMovingAverage(MovingAverageInterval.HundredDay, currentTimeLine);
        movingAverageSeries[2].setName("100 days MA");
        movingAverageSeries[3] = currentStock.getMovingAverage(MovingAverageInterval.TwoHundredDay, currentTimeLine);
        movingAverageSeries[3].setName("200 days MA");
    }

    /*
        Reverts all checkboxes to de-selected, for a new timeline stock view.
     */
    private void setCheckBoxToNone(){
        maButton_20.setSelected(false);
        maButton_50.setSelected(false);
        maButton_100.setSelected(false);
        maButton_200.setSelected(false);
    }


}
