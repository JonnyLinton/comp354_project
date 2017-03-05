import javafx.application.Application;
import javafx.stage.Stage;

public class MVC extends Application{
	
	private Stage primaryStage;

	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage primaryStage) throws Exception {
		LoginModel loginModel = new LoginModel();
		LoginView loginView = new LoginView();
		GraphModel graphModel = new GraphModel();
		GraphView graphView = new GraphView();
		LoginController loginController = new LoginController(loginView, loginModel,graphView, primaryStage);
		GraphController graphController = new GraphController(graphModel, graphView, primaryStage);
		primaryStage.show();
	}
	public Stage getStage(){
		return primaryStage;
	}
}
