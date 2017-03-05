import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StockMVC extends Application {

	public static void main(String[] args){
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		StockModel stockModel = new StockModel();
		StockView stockView = new StockView();
		StockController stockController = new StockController(stockModel, stockView);
		
			primaryStage.setTitle("Profits R Us");
		    Scene scene  = new Scene(stockView.asParent(), 1800,1000);
	        scene.getStylesheets().add("style.css");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	}

}
