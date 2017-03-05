import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StockMVC extends Application {
	
	
	public void start(Stage primaryStage) throws Exception {
		//MVC OBJECT LINKS
		StockModel stockModel = new StockModel();
		StockView stockView = new StockView();
		StockController stockController = new StockController(stockModel, stockView, primaryStage);
	
		//STAGE ATTRIBUTES
		primaryStage.setTitle("Profits R Us");
        primaryStage.show();

	}

}
