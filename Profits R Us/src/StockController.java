import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StockController {
	
	private StockModel stockModel = new StockModel();
	private StockView stockView = new StockView();
	
	public StockController(StockModel stockModel, StockView stockView){
		this.stockView = stockView;
		this.stockModel = stockModel;
		this.stockModel.createStockPriceSeries(5);
		this.stockView.setChart(this.stockModel.getStockPriceSeries());
		
		this.stockView.addTimelineHandler(new TimelineHandler());
	}
	
	class TimelineHandler implements EventHandler<ActionEvent>{
		
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
}
