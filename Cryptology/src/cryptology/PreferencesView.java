package cryptology;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PreferencesView extends Stage {
	
	private final String[] CATEGORY_NAMES = {"Global", "Substitution", "Transposition"};
	private HBox			mainHBox;
	private VBox			preferenceEditorVBox;
	private Preferences		prefs;
	private Preferences		old_prefs;
	
	public PreferencesView(Controller controller) {
		VBox contentVBox = new VBox();
		contentVBox.setPadding(new Insets(3));
		contentVBox.setSpacing(3);
		
		Scene scene = new Scene(contentVBox);
		scene.getStylesheets().add(this.getClass().getResource("/CSS/cryptology.css").toExternalForm());
		
		this.setScene(scene);
		this.setTitle("Preferences");
		
		prefs = controller.getPrefs();
		old_prefs = controller.getPrefs();
		
		mainHBox = new HBox();
		mainHBox.setSpacing(20);
		
		preferenceEditorVBox = new VBox();
		
		ListView<String> categoryList = new ListView<String>();
		categoryList.getSelectionModel().selectedItemProperty().addListener(new CategoryListChangeListener());
		ObservableList<String> categories = FXCollections.observableArrayList(CATEGORY_NAMES);
		categoryList.setItems(categories);
		categoryList.setPrefWidth(120);
		
		// Add objects to mainPane
		mainHBox.getChildren().addAll(categoryList, preferenceEditorVBox);
		
		// Done and cancel buttons
		HBox doneHBox = new HBox();
		doneHBox.setSpacing(3);
		doneHBox.setAlignment(Pos.BOTTOM_RIGHT);
		
		Button doneButton = new Button("Done");
		Button cancelButton = new Button("Cancel");
		
		doneHBox.getChildren().addAll(doneButton, cancelButton);
		
		doneButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				controller.updateDoc(prefs);
				close();
			}
		});
		
		cancelButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				close();
			}
		});
		
		// Add objects to contentPane
		contentVBox.getChildren().addAll(mainHBox, doneHBox);
		this.setMinHeight(300);
		this.setMinWidth(400);
		
		show();
	}
	
	private class CategoryListChangeListener implements ChangeListener<String> {
		
		@Override
		public void changed(ObservableValue<? extends String> obsVal, String oldVal, String newVal) {
			//System.out.println(newVal);
			switch (newVal) {
				case "Global":
					//System.out.println("case: Global");
					displayGlobalVBox();
					
					break;
				case "Substitution":
					//System.out.println("case: Substitution");
					displaySubstitutionVBox();
					break;
				case "Transposition":
					//System.out.println("case: Transposition");
					displayTranspositionVBox();
					break;
			}
			
		}
		
		private void displayGlobalVBox() {
			if (mainHBox.getChildren().contains(preferenceEditorVBox)) {
				mainHBox.getChildren().remove(preferenceEditorVBox);
			}
			
			preferenceEditorVBox = new VBox();
			preferenceEditorVBox.setSpacing(3);
			
			Label label = new Label("Global Preferences");
			label.setId("titleLabel");
			//label.setStyle("-fx-font-weight: bold");
			
			Separator titleSeparator = new Separator();
			
			CheckBox paddingCheckBox = new CheckBox("Cipher Text Padding");
			if (prefs.getPadding()) {
				paddingCheckBox.setSelected(true);
			}
			
			CheckBox detailsCheckBox = new CheckBox("Display Encipher/Decipher Details");
			if (prefs.getDetails()) {
				detailsCheckBox.setSelected(true);
			}
			
			// Block Size Text Field
			HBox blockSizeHBox = new HBox();
			blockSizeHBox.setSpacing(4);
			blockSizeHBox.setAlignment(Pos.CENTER);
			
			Label blockSizeLabel = new Label("Block Size:");
			
			TextField blockSizeTextField = new TextField(String.valueOf(prefs.getBlockSize())) {
				@Override public void replaceText(int start, int end, String text) {
					// If the replaced text would end up being invalid, then simply
					// ignore this call!
					if (!text.matches("[-a-zA-Z!@#$%^&*()=+_\\[\\]{}\\\\|;:'\",<.>/?`~]")) { // This excludes everything but numbers, could not be done with just including number because it excludes backspace which I couldn't figure out how to include
						super.replaceText(start, end, text);
					}
				}
			 
				@Override public void replaceSelection(String text) {
					if (!text.matches("[-a-zA-Z!@#$%^&*()=+_\\[\\]{}\\\\|;:'\",<.>/?`~]")) { // This excludes everything but numbers, could not be done with just including number because it excludes backspace which I couldn't figure out how to include
						super.replaceSelection(text);
					}
				}
			};
			
			blockSizeHBox.getChildren().addAll(blockSizeLabel, blockSizeTextField);
			
			// Add listeners and event Handlers
			paddingCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {
					prefs.setPadding(newVal);
				}
			});
			
			detailsCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {
					prefs.setDetails(newVal);
				}
			});
			
			blockSizeTextField.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					prefs.setBlockSize(Integer.parseInt(blockSizeTextField.getText()));
				}
			});
			
			blockSizeTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {
					if (!newVal) {
						prefs.setBlockSize(Integer.parseInt(blockSizeTextField.getText()));
					}
				}
			});
			
			preferenceEditorVBox.getChildren().addAll(label, titleSeparator, paddingCheckBox, detailsCheckBox, blockSizeHBox);
			//preferenceEditorVBox.setPrefWidth(100);
			
			mainHBox.getChildren().add(preferenceEditorVBox);
			
		}
		
		private void displaySubstitutionVBox() {
			if (mainHBox.getChildren().contains(preferenceEditorVBox)) {
				mainHBox.getChildren().remove(preferenceEditorVBox);
			}
			
			preferenceEditorVBox = new VBox();
			Label label = new Label("Substitution");
			label.setId("titleLabel");
			preferenceEditorVBox.getChildren().add(label);
			mainHBox.getChildren().add(preferenceEditorVBox);
		}
		
		private void displayTranspositionVBox() {
			if (mainHBox.getChildren().contains(preferenceEditorVBox)) {
				mainHBox.getChildren().remove(preferenceEditorVBox);
			}
			
			preferenceEditorVBox = new VBox();
			Label label = new Label("Transposition");
			label.setId("titleLabel");
			preferenceEditorVBox.getChildren().add(label);
			mainHBox.getChildren().add(preferenceEditorVBox);
		}
	}
	
	
}
