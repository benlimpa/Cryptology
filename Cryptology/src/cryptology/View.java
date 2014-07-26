package cryptology;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View {
	
	private Stage primaryStage;
	private Controller controller;
	
	private TextArea clearTextTextArea;
	private TextArea cipherTextTextArea; 
	
	public View(Stage primaryStage, Controller controller) {
		
		this.primaryStage = primaryStage;
		this.controller = controller;
		
		VBox outerPane = new VBox();
		outerPane.setPadding(new Insets(3));
		Scene scene = new Scene(outerPane);
		scene.getStylesheets().add(this.getClass().getResource("/CSS/cryptology.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Cryptology");
		
		// <Common Control Pane
		HBox commonControlsHPane = new HBox();
		
		// <Clear Text Controls
		VBox clearTextVPane = new VBox();
		clearTextTextArea = new TextArea();
		Label clearTextLabel = new Label("Clear Text:");
		
		clearTextVPane.getChildren().addAll(clearTextLabel, clearTextTextArea);
		// End of Clear Text Controls>
		
		// <Cipher Buttons
		VBox cipherButtonVPane = new VBox();
		
		Button encipherButton = new Button("Encipher");
		encipherButton.addEventHandler(ActionEvent.ACTION, new EncipherButtonEventHandler());
		encipherButton.setMinWidth(65);
		
		Button decipherButton = new Button("Decipher");
		decipherButton.addEventHandler(ActionEvent.ACTION, new DecipherButtonEventHandler());
		decipherButton.setMinWidth(65);
		
		cipherButtonVPane.getChildren().addAll(encipherButton, decipherButton);
		cipherButtonVPane.setSpacing(3);
		cipherButtonVPane.setPadding(new Insets(20, 0, 0, 0));
		// End of Cipher Buttons>
		
		// <Cipher Text Controls
		VBox cipherTextVPane = new VBox();
		cipherTextTextArea = new TextArea();
		Label cipherTextLabel = new Label("Cipher Text:");
		
		cipherTextVPane.getChildren().addAll(cipherTextLabel, cipherTextTextArea);
		// End of Cipher Text Controls>
		
		commonControlsHPane.getChildren().addAll(clearTextVPane, cipherButtonVPane, cipherTextVPane);
		// End of Common Control Pane>
		
		outerPane.getChildren().addAll(commonControlsHPane);
		
		primaryStage.show();
	}
	
	private class EncipherButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
		}
	}
	
	private class DecipherButtonEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			
		}
	}
}