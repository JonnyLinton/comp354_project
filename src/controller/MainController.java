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

    @FXML
    public void showStock(ActionEvent searchButtonPressed) {

        // Checks if a stock has already been generated
        if (!isStockGenerated) {

            // Create stock object from model class
            currentStock = new Stock("Dummy Stock", "STOK", "src/resources/Sample data.csv");

            // Create closing prices serie from stock object
            XYChart.Series<String, Number> stockSerie = currentStock.getPricesInRange(TimeInterval.FiveYears);
            stockSerie.setName("Closing Prices");

            // Add closing price serie to the graph
            stockChart.getData().add(stockSerie);

            currentTimeLine = TimeInterval.FiveYears;

			/* Create moving average series array of size 4
			 * Initiate every index with a new moving average serie from stock object
			 * Give every serie a name
			 */
			/*
			@SuppressWarnings("unchecked")
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
			*/
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
//	public void showDefaultStock() {
//		currentStock.getPricesInRange(TimeInterval.OneYear);
//	}
    @FXML
    private void graphTimeline(ActionEvent timeLineButtonPressed) {
        System.out.println("test0");
        stockChart.getData().clear();

        if(timeLineButton_1.isPressed()) {
            System.out.print("test1");
            stockChart.getData().add(currentStock.getPricesInRange(TimeInterval.OneYear));
            currentTimeLine = TimeInterval.OneYear;
        }
        else if(timeLineButton_2.isPressed()) {
            System.out.print("test2");
            stockChart.getData().add(currentStock.getPricesInRange(TimeInterval.TwoYears));
            currentTimeLine = TimeInterval.TwoYears;
        }
        else if(timeLineButton_5.isPressed()) {
            System.out.print("test3");
            stockChart.getData().add(currentStock.getPricesInRange(TimeInterval.FiveYears));
            currentTimeLine = TimeInterval.FiveYears;
        }
        else if(timeLineButton_all.isPressed()) {
            System.out.print("test4");
            stockChart.getData().add(currentStock.getPricesInRange(TimeInterval.AllTime));
            currentTimeLine = TimeInterval.AllTime;
        }
    }



    @FXML
    private void graphMovingAverageTwenty(ActionEvent movingAverageInterval20CheckboxPressed) {

        if (maButton_20.isSelected())
            stockChart.getData().add(currentStock.getMovingAverage(MovingAverageInterval.TwentyDay, currentTimeLine));
        else if (!maButton_20.isSelected()) {
            System.out.println("test4");
            stockChart.getData().remove(currentStock.getMovingAverage(MovingAverageInterval.TwentyDay, currentTimeLine));
            }
        }

    @FXML
    private void graphMovingAverageFifty(ActionEvent movingAverageIntervalFiftyCheckboxPressed) {
        if (maButton_50.isSelected())
            stockChart.getData().add(currentStock.getMovingAverage(MovingAverageInterval.FiftyDay, currentTimeLine));
        else if (!maButton_50.isSelected())
            stockChart.getData().remove(currentStock.getMovingAverage(MovingAverageInterval.FiftyDay, currentTimeLine));
        }

    @FXML
    private void graphMovingAverageHundred(ActionEvent movingAverageIntervalHundredCheckboxPressed) {
        if (maButton_100.isSelected())
            stockChart.getData().add(currentStock.getMovingAverage(MovingAverageInterval.HundredDay, currentTimeLine));
        else if (!maButton_100.isSelected())
            stockChart.getData().remove(currentStock.getMovingAverage(MovingAverageInterval.HundredDay, currentTimeLine));
        }

    @FXML
    private void graphMovingAverageTwoHundred(ActionEvent movingAverageIntervalTwoHundredCheckboxPressed) {
        if (maButton_200.isSelected())
            stockChart.getData().add(currentStock.getMovingAverage(MovingAverageInterval.TwoHundredDay, currentTimeLine));
        else if (!maButton_200.isSelected())
            stockChart.getData().remove(currentStock.getMovingAverage(MovingAverageInterval.TwoHundredDay, currentTimeLine));
        }


}
//	private void getRecommendations(String recommendation) {
//
//	}


