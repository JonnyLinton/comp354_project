import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class StockController {
	
	private StockModel stockModel = new StockModel();
	private StockView stockView = new StockView();
	private Stage stage;

	public StockController(StockModel stockModel, StockView stockView, Stage stage){
		this.stockView = stockView;
		this.stockModel = stockModel;
		this.stage = stage;
		this.stockModel.createStockPriceSeries(5);
		this.stockView.setChart(this.stockModel.getStockPriceSeries());
		this.stockView.addTimelineHandler(new TimelineHandler());
		this.stockView.addLoginHandler(new LoginHandler());		
		this.stockView.addRegisterHandler(new RegisterHandler());		
		stage.setScene(stockView.welcomeScene());
	}
	
	public class TimelineHandler implements EventHandler<ActionEvent>{
		
		public void handle(ActionEvent arg0) {
			int timeline;
			try{
				timeline = stockView.getTimeline();
				stockModel.createStockPriceSeries(timeline);
			}
			catch(NumberFormatException exception)
			{}
		}
	}
	public class RegisterHandler implements EventHandler<ActionEvent>{
			
		public void handle(ActionEvent arg0) {
			System.out.println("test");
			stage.setScene(stockView.graphScene());
			
		}
	}
	public class LoginHandler implements EventHandler<ActionEvent>{
			
		public void handle(ActionEvent arg0) {
			stage.setScene(stockView.graphScene());
		}
	}
}
