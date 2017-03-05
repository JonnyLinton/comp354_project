import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private BorderPane graphBorderLayout = new BorderPane();
    private BorderPane welcomeBorderLayout = new BorderPane();
    private HBox selectionBox = new HBox(15);
    private HBox recommendBox = new HBox();
    private HBox emailBox = new HBox();
    private HBox passwordBox = new HBox();
    private HBox loginRegisterBox = new HBox();
    private VBox loginVBox = new VBox();
    private Label loginLabel = new Label("Login");
    private Label emailLabel = new Label ("Email:");
    private Label passwordLabel = new Label ("Password:");
    private TextField emailTextField = new TextField();
    private TextField passwordTextField = new TextField();
    private Button loginButton = new Button("Login Form");
    private Button registerButton = new Button("Login");
    private Scene graphScene;
	private Scene welcomeScene;
    
    public StockView(){
    	
    	//Welcome Scene
    	emailBox.getChildren().addAll(emailLabel, emailTextField);
    	emailBox.setAlignment(Pos.CENTER);
    	passwordBox.getChildren().addAll(passwordLabel, passwordTextField);
    	passwordBox.setAlignment(Pos.CENTER);
    	loginRegisterBox.getChildren().addAll(loginButton, registerButton);
		loginRegisterBox.setAlignment(Pos.CENTER);
    	loginVBox.getChildren().addAll(loginLabel, emailBox, passwordBox, loginRegisterBox);
    	loginVBox.setAlignment(Pos.CENTER);
    	welcomeBorderLayout.setCenter(loginVBox);
       	welcomeScene = new Scene(welcomeBorderLayout, 1800,1000);
    	
    	//Graph Scene
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
    	graphScene.getStylesheets().add("style.css");
    }
    public Scene graphScene(){
    	return graphScene;
    }
    public Scene welcomeScene(){
    	return welcomeScene;
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
   public void addLoginHandler(EventHandler<ActionEvent> loginHandler){
	   loginButton.setOnAction(loginHandler);
   }
   public void addTimelineHandler(EventHandler<ActionEvent> timelineHandler){
	   timelineButton.setOnAction(timelineHandler);
   }
  
   public void addRegisterHandler(EventHandler<ActionEvent> registerHandler){
	   registerButton.setOnAction(registerHandler);
   }
}