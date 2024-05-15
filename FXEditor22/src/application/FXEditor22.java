package application;

// Standard JavaFX imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// Layout Imports
import javafx.scene.layout.BorderPane;

// Imports for components
import javafx.scene.control.MenuBar;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

// Imports for Geometry

// Support for Closing the application

public class FXEditor22 extends Application {
	// declare variables that require class scope
	MenuBar mBar;
	Menu mnuFile, mnuEdit, mnuHelp;
	MenuItem miFileNew, miFileOpen, miFileClose, miFileQuit, miFileSave, miFileSaveAs;
	MenuItem miEditPaste, miEditDelete, miEditSelectAll, miEditUndo, miEditRedo, miEditCopy, miEditCut;
	MenuItem miHelpContents, miHelpAbout;
	
	TextArea txtMain;

	// Constructor - instantiate components
	public FXEditor22() {
		// Instantiate menus and menubar
		mBar = new MenuBar();
		
		// menus (3)
		mnuFile = new Menu("File");
		mnuEdit = new Menu("Edit");
		mnuHelp = new Menu("Help");
		
		// menu items for the file menu
		miFileNew = new MenuItem("New");
		miFileOpen = new MenuItem("Open");
		miFileClose = new MenuItem("Close");
		miFileQuit = new MenuItem("Quit");
		miFileSave = new MenuItem ("Save");
		miFileSaveAs = new MenuItem ("Save AS");
		
		// menu items for the edit menu
		miEditPaste  = new MenuItem("Paste");
		miEditDelete = new MenuItem("Delete");
		miEditSelectAll = new MenuItem("Select All");
		miEditUndo = new MenuItem("Undo");
		miEditRedo  = new MenuItem("Redo");
		miEditCopy = new MenuItem("Copy");
		miEditCut = new MenuItem ("Cut");
		
		// menu items for the help menu
		miHelpContents = new MenuItem("Contents");
		miHelpAbout = new MenuItem("About");
		
		// the main text display area
		txtMain = new TextArea();
		
		
	} // constructor()
	
	// Event handling
	@Override
	public void init() {
		//Handeling events on `Quit` menu item.
		miFileQuit.setOnAction(event -> promptAndQuit());
		
		//Edit Menu Events.
		miEditUndo.setOnAction (event -> txtMain.undo());
		miEditRedo.setOnAction(event -> txtMain.redo());
		miEditCopy.setOnAction(event -> txtMain.copy());;
		miEditCut.setOnAction(event -> txtMain.cut());
		miEditPaste.setOnAction(event -> txtMain.paste());
		
		//File menu events.
		miFileSaveAs.setOnAction(event -> doFileSaveAS());;
		miFileSave.setOnAction(event -> doFileSave());
		miFileOpen.setOnAction(event -> doFileOpen());
		
	}
	
	public void doFileOpen() {
		//use FileChooser - allows selection of a file
		FileChooser fc = new FileChooser();
		
		//Assign a file (chosen by user from dialog).
		File fileToOpen = fc.showOpenDialog(null);
		
		//if dialog is confirmed (ok clicked)
		if (fileToOpen != null) {
			try {
				//accumulate lines from the file into a StringBuilder
				StringBuilder sb = new StringBuilder();
				//use a bufferreader to read from the file
				FileReader file = new FileReader(fileToOpen);
				BufferedReader buf = new BufferedReader(file);
				
				//a string to store lines from the file temporarily
				String text;
				//Alternate through the filer, reading one line at the time
				while((text = buf.readLine()) != null) {
					text = text + "\n";
					//Append the line to the string builder's accumulated text
					sb.append(text);
				}
				//add the entire text ti the txtMain
				txtMain.setText(sb.toString());
				//close the title
				buf.close();
			}
			catch (IOException ioe) {
				System.out.println("Could not open the file.");
				ioe.printStackTrace();
			}
		}
		else {
			//dialog cancelled
			System.out.println("User did not choose a file.");
		}
	}

	public void doFileSave() {
		//use FileOutputStream to write a File.
		try {
			FileOutputStream foutput = new FileOutputStream("mydocument.txt");
			//String myText = "Hello HDC class";
			
			//get text from the text area (user written).
			String myText = txtMain.getText();
			//put into byte array to write in the output stream
			byte[] byteArray = myText.getBytes();
			foutput.write(byteArray);;
			foutput.flush();//empty buffer
			foutput.close();//close the file
			System.out.println("File attempted to write.");
		}
		catch (IOException ioe) {
			System.out.println("Error writing the file.");
			System.out.println("This went wrong: " +  ioe.getMessage());
		}
	}

	public void doFileSaveAS() {
		//Use a file chooser.
		FileChooser fc = new FileChooser();
		File fileToSave = fc.showSaveDialog(null);
		
		if (fileToSave != null) {
			//check if dialog is confirmed and there is a file to save.
			try {
				FileOutputStream fos = new FileOutputStream (fileToSave, true);
				String text = txtMain.getText();
			
			
				//ther file must be saved in bytes.
				byte[] byteArray = text.getBytes();
			
				//now write it to the file
				fos.write(byteArray);
			
				//Flush and Close.
				fos.flush();
				fos.close();
			}
			catch(IOException ioe) {
				//catch an exception and handle it
				System.out.println ("File not saved.");
			}
		}
		else; //do nothing, no file chosen.
	}

	public void promptAndQuit() {
		//if confirmmed, just exit. 
		Platform.exit();
	}

	// layouts + Window setup
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Set the title
		primaryStage.setTitle("JavaFX Editor 2022");
		
		// Set default width and height of window
		primaryStage.setWidth(500);
		primaryStage.setHeight(300);
		
		// create a layout
		BorderPane bpMain = new BorderPane();
		
		// add components to the layout
		bpMain.setCenter(txtMain);
		bpMain.setTop(mBar);
		
		mBar.getMenus().addAll(mnuFile, mnuEdit, mnuHelp);
		
		// Add menu items to the menus
		// File menu
		mnuFile.getItems().addAll(miFileNew, miFileSave, miFileSaveAs, miFileOpen, miFileClose, miFileQuit);
		 
		// Edit menu
		mnuEdit.getItems().addAll(miEditPaste, miEditDelete, miEditSelectAll, miEditUndo, miEditRedo, miEditCopy, miEditCut);
		
		// Help menu
		mnuHelp.getItems().addAll(miHelpContents, miHelpAbout);
		
		// create a scene
		Scene s = new Scene(bpMain); // takes in root layout
		
		// set the scene
		primaryStage.setScene(s);
		
		// show the stage
		primaryStage.show();

	} // start()

	// launch the application
	public static void main(String[] args) {
		// launch the application
		launch(args);
	} // main()

}