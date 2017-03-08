import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GraphView {

	private Label recommendation;
    private Scene graphScene;
    private LineChart<String,Number> lineChart;
    private ComboBox<String> timelineButton;
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
 
 
	public GraphView(){
		
		//CREATE GRAPH SCENE
		recommendation = new Label();
		timelineButton = new ComboBox<String>();
	    HBox selectionBox = new HBox(15);
	    HBox recommendBox = new HBox();
		BorderPane graphBorderLayout = new BorderPane();
		final CategoryAxis xAxis = new CategoryAxis();
	    final NumberAxis yAxis = new NumberAxis();
	    lineChart = new LineChart<String,Number>(xAxis,yAxis);
		CheckBox mA20 = new CheckBox("20 Day Moving Average");
		CheckBox mA50 = new CheckBox("50 Day Moving Average");
		CheckBox mA100 = new CheckBox("100 Day Moving Average");
		CheckBox mA200 = new CheckBox("200 Day Moving Average");
		recommendation.setText("Recommendation: Sell the Stock!!!");
    	recommendBox.setAlignment(Pos.CENTER);
    	graphBorderLayout.setPadding(new Insets(10));
    	timelineButton.getItems().addAll("1 year", "5 year", "10 year","max");
    	selectionBox.getChildren().addAll(timelineButton, mA20, mA50, mA100, mA200);
    	recommendBox.getChildren().add(recommendation);
    	graphBorderLayout.setTop(selectionBox);
    	graphBorderLayout.setCenter(lineChart);
    	graphBorderLayout.setBottom(recommendBox);
    	graphScene  = new Scene(graphBorderLayout, 1800,1000);
    	//graphScene.getStylesheets().add("style.css");

		lineChart.setCreateSymbols(false);
	}
	 public Scene graphScene(){
	    	return graphScene;
	    }
	 public void setChart(XYChart.Series<String, Number> stockPriceSeries){
		 lineChart.getData().clear();
		   lineChart.getData().add(stockPriceSeries);
	 }
	public int getTimeline(){
	   String selection = timelineButton.getSelectionModel().getSelectedItem().toString();
	   switch (selection){
	   case "1 year":
		   return 1;
	   case "5 year":
		   return 5;
	   case "10 year":
		   return 10;
	   case  "max":
			   return 0;
		   default:
			   return 0;	   
		   }
	}
	public void addTimelineHandler(EventHandler<ActionEvent> timelineHandler){
		timelineButton.setOnAction(timelineHandler);
	}
	
	

}
