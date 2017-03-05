import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class GraphController {
	
	private GraphModel graphModel;
	private GraphView graphView;
	private Stage stage;
	
	public GraphController(GraphModel graphModel, GraphView graphView, Stage stage){
		this.graphView = graphView;
		this.graphModel = graphModel;
		this.stage = stage;
		this.graphModel.createStockPriceSeries(5);
		this.graphView.setChart(this.graphModel.getStockPriceSeries());
		this.graphView.addTimelineHandler(new TimelineHandler());
	}
	
	public class TimelineHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent arg0) {
		
			try{
				System.out.println("Test1");
				graphModel.createStockPriceSeries(graphView.getTimeline());
				graphView.setChart(graphModel.getStockPriceSeries());
			
			}
			catch(NumberFormatException exception)
			{}
		}
	}

}
