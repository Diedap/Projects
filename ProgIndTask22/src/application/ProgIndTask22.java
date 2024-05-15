package application;
	
//Standard javafx imports.
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.scene.Scene;


//Imports for components in this application.
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ProgressBar;


//Imports for layout.
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class ProgIndTask22 extends Application {
	//Declare variables/components that need class scope.
	Label lblProgress;
	ProgressIndicator progInd;
	Button btnStart, btnCancel;
	
	//the one of task
	Task<Void> task;
	
	public ProgIndTask22() {
		//Instantiate components with 'new'.
		lblProgress = new Label("Progress:");
		
		progInd = new ProgressIndicator(0);
		
		progInd.setScaleX(1.1);
		progInd.setScaleY(1.1);
		progInd.setStyle("-fx-accent: green;");
			
		btnStart = new Button("Start");
		btnCancel = new Button("Cancel");
		
		//Manage button sizes.
		btnStart.setMinWidth(60);
		btnCancel.setMinWidth(60);
		
		
	}//constructor()
	
	@Override
	public void init() {
		btnStart.setOnAction(event -> startTask());
		btnCancel.setOnAction(event -> cancelTask());
	}//init()
	
	private void startTask() {
		task = new Task<Void>() {
			@Override
			public Void call() {
				final long max = 100000000;
				//loop to simulate a long task
				for(long i=1; i<=max; i++) {
					if(isCancelled()) {
						updateProgress(0, max);
						break;
					}
					//update the prog of the task
					updateProgress(i,max);
				}
				return null;
			}
		};
		//update the progress of the prog indicator
		progInd.progressProperty().bind(task.progressProperty());
		//start the thread
		new Thread(task).start();
	}
	
	private void cancelTask() {
		//sets a flag that the task has been cancelled
		task.cancel();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//set title
		primaryStage.setTitle("Progress Task - Threads");
		//set the width and height.
		primaryStage.setWidth(200);
		primaryStage.setHeight(400);
		//create the VBoxlayout
		VBox vbMain = new VBox();
		//Center align vbMain
		vbMain.setAlignment(Pos.CENTER);
		//spacing and padding for vbMain
		vbMain.setSpacing(25);
		vbMain.setPadding(new Insets(10));
		//add components to the layout
		vbMain.getChildren().addAll(lblProgress, progInd, btnStart, btnCancel);
		//create scene
		Scene s = new Scene (vbMain);
		//set the scene
		primaryStage.setScene(s);
		//show the stage
		primaryStage.show();
	}//start()


	public static void main(String[] args) {
		launch(args);
	}//main()

}//class
