package view;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class MainView {

    private TextField searchTextField;
    private CheckBox timeInterval;
    private RadioButton movingAverageInterval;
    private Button searchButton;
    private Button analyzeButton;
    private Button logoutButton;
    private Label recommendationsLabel;
    private LineChart<String, Double> lineChart;

    public MainView() {
        GridPane loginLayout = new GridPane();
        searchTextField = new TextField();
        timeInterval = new CheckBox();
        movingAverageInterval = new RadioButton();
        searchButton = new Button("Search");
        analyzeButton = new Button("Analyze");
        logoutButton = new Button("Log out");
        recommendationsLabel = new Label();


    }

}