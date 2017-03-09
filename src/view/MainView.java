package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class MainView {

	// Instantiating attributes
    private TextField searchTextField;
    private CheckBox[] movingAverageIntervalCheckboxes;
    private Button[] timeLineButtons;
    private Button searchButton;
    private Button analyzeButton; // <-- We don't need anymore?
    private Label recommendationsLabel; // <-- To be implemented
    private Label timelineLabel;
    private Label movingAverageLabel;
    private LineChart<String, Number> lineChart;
    private Scene mainScene;
    private GridPane grid;
    
    private String fontFamily = "Calibri";
    private int fontSize = 15;

    /**
     * Creates a MainView object with all of its components
     */
    
    public MainView() {
    	// Create a grid pane with 6 columns and 4 rows
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);

        searchTextField = new TextField();
        searchTextField.setPrefWidth(500);
        movingAverageIntervalCheckboxes = new CheckBox[4];
        timeLineButtons = new Button[4];
        timeLineButtons[0] = new Button("1 Year");
        timeLineButtons[1] = new Button("2 Year");
        timeLineButtons[2] = new Button("5 Year");
        timeLineButtons[3] = new Button("All Time");

        movingAverageIntervalCheckboxes[0] = new CheckBox("20 Days");
        movingAverageIntervalCheckboxes[1] = new CheckBox("50 Days");
        movingAverageIntervalCheckboxes[2] = new CheckBox("100 Days");
        movingAverageIntervalCheckboxes[3] = new CheckBox("200 Days");

        searchButton = new Button("Search Stock");
        analyzeButton = new Button("Analyze");

        recommendationsLabel = new Label();
        timelineLabel = new Label("Timeline");
        timelineLabel.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));
        movingAverageLabel = new Label("Moving Average");
        movingAverageLabel.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Price");
        lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setPrefWidth(800);
        lineChart.setPrefHeight(600);
        recommendationsLabel = new Label();

        grid.add(searchTextField, 0, 0, 5, 1);
        grid.add(searchButton, 5, 0);
        grid.add(lineChart, 0, 1, 5, 1);

        grid.add(timelineLabel, 0, 2);

        for(int i = 0; i < timeLineButtons.length; i++) {
            grid.add(timeLineButtons[i], i + 1, 2);
        }
        grid.add(movingAverageLabel, 0, 3);
        for (int i = 0; i < movingAverageIntervalCheckboxes.length; i++) {
            grid.add(movingAverageIntervalCheckboxes[i], i + 1, 3);
        }

        mainScene = new Scene(grid, 800, 600);

//        grid.setGridLinesVisible(true);
    }
    
    /**
     * Get the scene for the MainView
     * @return mainScene - Scene object
     */

    public Scene getScene() {
        return mainScene;
    }

}