package view;

import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class MainView {

    private TextField searchTextField;
    private CheckBox[] movingAverageIntervalCheckboxes;
    private Button[] timeLineButtons;
    private Button searchButton;
    private Button analyzeButton;
    private Label recommendationsLabel;
    private Label timelineLabel;
    private Label movingAverageLabel;
    private LineChart<String, Number> lineChart;
    private Scene mainScene;
    private GridPane grid;

    public MainView() {
        grid = new GridPane();
        searchTextField = new TextField();
        movingAverageIntervalCheckboxes = new CheckBox[4];
        timeLineButtons = new Button[4];
        timeLineButtons[0] = new Button("1Y");
        timeLineButtons[1] = new Button("2Y");
        timeLineButtons[2] = new Button("5Y");
        timeLineButtons[3] = new Button("All");

        movingAverageIntervalCheckboxes[0] = new CheckBox("20 Days");
        movingAverageIntervalCheckboxes[1] = new CheckBox("50 Days");
        movingAverageIntervalCheckboxes[2] = new CheckBox("100 Days");
        movingAverageIntervalCheckboxes[3] = new CheckBox("200 Days");

        searchButton = new Button("Search");
        analyzeButton = new Button("Analyze");

        recommendationsLabel = new Label();
        timelineLabel = new Label();
        movingAverageLabel = new Label();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Price");
        lineChart = new LineChart<String, Number>(xAxis, yAxis);
        recommendationsLabel = new Label();

        grid.add(searchTextField, 0, 0, 3, 1);
        grid.add(searchButton, 3, 0);
        grid.add(lineChart, 0, 1, 4, 1);

        grid.add(timelineLabel, 0, 2);

        for(int i = 0; i < timeLineButtons.length; i++) {
            grid.add(timeLineButtons[i], i + 1, 2);
        }
        grid.add(movingAverageLabel, 0, 3);
        for (int i = 0; i < movingAverageIntervalCheckboxes.length; i++) {
            grid.add(movingAverageIntervalCheckboxes[i], i + 1, 3);
        }

        mainScene = new Scene(grid, 800, 600);

        grid.setGridLinesVisible(true);
    }

    public Scene getScene() {
        return mainScene;
    }

}