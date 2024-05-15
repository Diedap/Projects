package application;

//Standard JavaFX imports.
import javafx.scene.Scene;

import javafx.util.Duration;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;

//Components in this application: Labels, buttons and textfields.
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

//Layout...
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

//Application quitting.
import javafx.application.Platform;

//An image is required to display an icon.
import javafx.scene.image.Image;


public class TypingTutor22 extends Application {
	//Components that need class scope.
	Label lblTyping, lblErrors, lblShowErrors, lblElapsedTime, lblShowElapsedTime;
	TextField txtfSentence, txtfTyping;
	Button btnStart, btnStop;
	
	//Error count
	int errors = 0;
	
	//Time in seconds 
	int time = 0;
	
	//Sentences to type (hardcoded).
	String[] sentences = new String[5];
	
	//A conter (index for the array).
	int i = 0;
	
	//timer
	Timeline tLine;

	// Constructor - initialise components
	public TypingTutor22() {
		//Initialisation/Instantiation of classes/components.
		
		lblTyping = new Label("Type each sentence into the lower textfield:");
		lblErrors = new Label("Errors:");
		lblShowErrors = new Label();
		lblElapsedTime = new Label("Elapsed time:");
		lblShowElapsedTime = new Label();
		
		txtfSentence = new TextField();
		txtfTyping = new TextField();
		//txtfSentence.setMinWidth(400); // not responsive!
		
		btnStart = new Button("Start");
		btnStart.setMinWidth(60);
		btnStop = new Button("Stop");
		btnStop.setMinWidth(60);	
		
		//create some sentences.
		sentences[0] = "Mary had a little lamb.";
		sentences[1] = "Her fleece was white as snow.";
		sentences[2] = "And everywhere that Mary went";
		sentences[3] = "the Lamb was sure to go.";	
		sentences[4] = "That's all folks!";
		
	}//constructor()
	
	// Event Handling
	@Override
	public void init() {
		//handle events on the start button
		btnStart.setOnAction(event ->{
			//reset the time.
			time = 0;
			//update the time label.
			lblShowElapsedTime.setText(time + "");
			//reset the sentences index
			i=0;
			//clear the errors
			errors = 0;
			//update the errors label.
			lblShowErrors.setText(errors + "");
			//show the first sentence.
			txtfSentence.setText(sentences[i]);
			//and clear the lower text field.
			txtfTyping.clear();
			//Start timing the test.
			tLine = new Timeline(new KeyFrame(Duration.millis(1000), timetrick -> {
					time++;
					lblShowElapsedTime.setText(time + "");
			}));
			tLine.setCycleCount(Animation.INDEFINITE);
			tLine.play();
			//make sure the user cannot press start again.
			btnStart.setDisable(true);
		});
		
		//handle events on the stop button
		btnStop.setOnAction(event -> {
			//stop timer.
			tLine.stop();
			//reenable the start button.
			btnStart.setDisable(false);
		});
		//check if a key is typed.
		txtfTyping.setOnKeyReleased(event -> {
			//check if the start of the sentence is the same.
			if(txtfSentence.getText().startsWith(txtfTyping.getText())) {
				//all correct thus far
				txtfTyping.setStyle("-fx-text-inner-color: black;");
			}
			else {
				//there is an error. flag it (red)
				txtfTyping.setStyle("-fx-text-inner-color: red;");
				//increment the error count
				errors++;
				//increment the error lable
				lblShowErrors.setText(errors + "");
			}
			if(i < sentences.length-1) {
				//check if the sentence is complete (match)
				if(txtfTyping.getText().equals(txtfSentence.getText())) {
					//shoe the next sentence.
					i++;
					txtfSentence.setText(sentences[i]);
					//clear the typing text
					txtfTyping.clear();
				}
			}
		});
		
	}//init()

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Set the title
		primaryStage.setTitle("TypingTutor2022 V1.0");
		
		//Add an icon
		try {
			primaryStage.getIcons().add(new Image("books.png"));
		}
		catch (IllegalArgumentException e) {
			System.out.println("No icon found.");
		}
		
		//Set the default width and height
		primaryStage.setWidth(540);
		primaryStage.setHeight(200);
		
		//Create a layout main layout gridPane
		GridPane gpMain = new GridPane();
		gpMain.setPadding(new Insets(10));
		
		//Spacing
		gpMain.setHgap(30);
		gpMain.setVgap(10);
	
		//Add components to the layout
		gpMain.add(lblTyping, 0, 0);
		gpMain.add(txtfSentence, 0, 1);
		gpMain.add(txtfTyping, 0, 2);
		
		//The errors and elapsed time labels (4)
		gpMain.add(lblErrors, 1, 0);
		gpMain.add(lblShowErrors, 2, 0);
		
		gpMain.add(lblElapsedTime, 1, 1);
		gpMain.add(lblShowElapsedTime, 2, 1);
		
		//Put the buttons into a horizontal box 
		HBox hbButtons = new HBox();
		hbButtons.setSpacing(10);
		hbButtons.getChildren().addAll(btnStart, btnStop);
		
		//Add the buttons container into the 4th row
		gpMain.add(hbButtons, 0, 3);
		
		//Align the buttons right
		hbButtons.setAlignment(Pos.BASELINE_RIGHT);
		
		//Create a scene
		Scene s = new Scene(gpMain); // takes in root container (gpMain)
		
		//Set the scene
		primaryStage.setScene(s);
		
		//Apply a style using a stylesheet.
		//s.getStylesheets().add("typing_style.css"); 
		
		
		txtfTyping.minWidthProperty().bind(primaryStage.widthProperty().subtract(200));
		
		/* Currently not responsive */
		//TODO: Run. Now uncomment the code on line 124 and run again. Compare the responsiveness when window is resized.
		
		//TODO: what happens with txtfSentence?
		
		//TODO: change the code on line 124 to make txtfTyping binded to 1/2 the size of the window
		// *hint* use .divide()
			
				
		//Show the stage
		primaryStage.show();
	
	}//start()


	public static void main(String[] args) {
		//Launch the application.
		launch();
	
	}//main()

}//class