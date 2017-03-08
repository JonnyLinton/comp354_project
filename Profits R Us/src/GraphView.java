import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GraphView {

	//Attributes
	private Label recommendation;
    private Scene graphScene;
    private LineChart<String,Number> lineChart;
    private ComboBox<String> timelineButton;
    private ToggleButton oneYear;
	private ToggleButton fiveYear;
	private ToggleButton tenYear;
	private ToggleButton max;
	public final ToggleGroup timelineGroup;
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    private Button logoutButton;
 
    //constructor
	public GraphView(){
		
		//Toggle Group for timeline
		oneYear = new ToggleButton("1Y");
		oneYear.setUserData(1);
		fiveYear = new ToggleButton("5Y");
		fiveYear.setUserData(5);
		tenYear = new ToggleButton("10Y");
		tenYear.setUserData(10);
		max = new ToggleButton("max");
		max.setUserData(0);
	    timelineGroup = new ToggleGroup();
		oneYear.setToggleGroup(timelineGroup);
		fiveYear.setToggleGroup(timelineGroup);
		fiveYear.setSelected(true);
		tenYear.setToggleGroup(timelineGroup);
		max.setToggleGroup(timelineGroup);
		
		//CREATE GRAPH SCENE
		logoutButton = new Button("Logout");
		recommendation = new Label();
		timelineButton = new ComboBox<String>();
	    HBox selectionBox = new HBox(15);
	    HBox recommendBox = new HBox();
	    Label movingAverageLabel = new Label("Moving Averages: ");
		BorderPane graphBorderLayout = new BorderPane();
		final CategoryAxis xAxis = new CategoryAxis();
	    final NumberAxis yAxis = new NumberAxis();
	    lineChart = new LineChart<String,Number>(xAxis,yAxis);
		CheckBox mA20 = new CheckBox("20 Day");
		CheckBox mA50 = new CheckBox("50 Day");
		CheckBox mA100 = new CheckBox("100 Day");
		CheckBox mA200 = new CheckBox("200 Day");
		recommendation.setText("Recommendation: Sell the Stock!!!");
    	recommendBox.setAlignment(Pos.CENTER);
    	graphBorderLayout.setPadding(new Insets(10));
    	timelineButton.getItems().addAll("1 year", "5 year", "10 year","max");
    	selectionBox.getChildren().addAll(oneYear,fiveYear,tenYear,max,movingAverageLabel, mA20, mA50, mA100, mA200, logoutButton);

    	recommendBox.getChildren().add(recommendation);
    	graphBorderLayout.setTop(selectionBox);
    	graphBorderLayout.setCenter(lineChart);
    	graphBorderLayout.setBottom(recommendBox);
    	graphScene  = new Scene(graphBorderLayout, 800,600);
    	graphScene.getStylesheets().add("style.css");
	}
	 public Scene graphScene(){
	    	return graphScene;
	    }
	 public void setChart(XYChart.Series<String, Number> stockPriceSeries){
		 lineChart.getData().clear();
		   lineChart.getData().add(stockPriceSeries);
	 }

	public int getTimeline(){
		   return Integer.parseInt(timelineGroup.getSelectedToggle().getUserData().toString());
		}
	public void addTimelineHandler(EventHandler<ActionEvent> timelineHandler){
		oneYear.setOnAction(timelineHandler);
		fiveYear.setOnAction(timelineHandler);
		tenYear.setOnAction(timelineHandler);
		max.setOnAction(timelineHandler);
		
	}
	public void addLogoutHandler(EventHandler<ActionEvent> logoutHandler){
		logoutButton.setOnAction(logoutHandler);
	}
	
	

}
