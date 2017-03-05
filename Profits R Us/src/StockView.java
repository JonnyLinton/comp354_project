import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StockView {
	
	private ComboBox<String> timelineButton = new ComboBox<String>();
	private CheckBox mA20 = new CheckBox("20 Day Moving Average");
	private CheckBox mA50 = new CheckBox("50 Day Moving Average");
	private CheckBox mA100 = new CheckBox("100 Day Moving Average");
	private CheckBox mA200 = new CheckBox("200 Day Moving Average");
	private Label recommendation = new Label();
	private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
    private BorderPane layout = new BorderPane();
    private HBox selectionBox = new HBox(15);
    private HBox recommendBox = new HBox();
    
    public StockView(){
    	recommendation.setText("Recommendation: Sell the Stock!!!");
    	recommendBox.setAlignment(Pos.CENTER);
    	layout.setPadding(new Insets(10));
    	timelineButton.getItems().addAll("1 year", "5 year", "10 year","max");
    	selectionBox.getChildren().addAll(timelineButton, mA20, mA50, mA100, mA200);
    	recommendBox.getChildren().add(recommendation);
    	layout.setTop(selectionBox);
    	layout.setCenter(lineChart);
    	layout.setBottom(recommendBox);
    }
    public Parent asParent(){
    	return layout;
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
 
   public void setChart(XYChart.Series<String, Number> stockPriceSeries){
	   lineChart.getData().add(stockPriceSeries);
   }
   
   public void addTimelineHandler(EventHandler<ActionEvent> timelineHandler){
	   timelineButton.setOnAction(timelineHandler);
   }
}