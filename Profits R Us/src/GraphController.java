import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class GraphController
{
	
	//Attributes that it needs to manipulate
	private GraphModel graphModel;
	private GraphView graphView;
	private Stage stage;
	private LoginView loginView;
	
	//Constructor initializing important objects and methods
	public GraphController(GraphModel graphModel, GraphView graphView, LoginView loginView, Stage stage)
	{
		this.graphView = graphView;
		this.loginView = loginView;
		this.graphModel = graphModel;
		this.stage = stage;
		this.graphModel.createStockPriceSeries(5);
		this.graphView.setChart(this.graphModel.getStockPriceSeries());
		this.graphView.addTimelineHandler(new TimelineHandler());
		this.graphView.addLogoutHandler(new LogoutHandler());
	}

	//ActionEvent Inner Classes to handle timeline update actions
	public class TimelineHandler implements EventHandler<ActionEvent>
	{
		
		public void handle(ActionEvent arg0)
		{
			try
			{
				System.out.println("Test1");
				graphModel.createStockPriceSeries(graphView.getTimeline());
				graphView.setChart(graphModel.getStockPriceSeries());
			
			}
			catch(NumberFormatException exception) {}
		}
	}

	//ActionEvent Inner Classes to handle logout function
	public class LogoutHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent arg0)
		{
		
			//LOGOUT CODE HERE
			stage.setScene(loginView.welcomeScene());
			
		}
	}
	//NEED TO ADD ACTION EVENT FOR MOVING AVERAGES

}
