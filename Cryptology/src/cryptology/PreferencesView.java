package cryptology;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PreferencesView extends Stage {
	
	private final String[] CATEGORY_NAMES = {"Global", "Substitution", "Transposition"};
	private HBox			mainHBox;
	private VBox			preferenceEditorVBox;
	
	public PreferencesView(Controller controller) {
		VBox contentVBox = new VBox();
		contentVBox.setPadding(new Insets(3));
		Scene scene = new Scene(contentVBox);
		
		this.setScene(scene);
		this.setTitle("Preferences");
		
		mainHBox = new HBox();
		mainHBox.setSpacing(20);
		
		preferenceEditorVBox = new VBox();
		
		ListView<String> categoryList = new ListView<String>();
		categoryList.getSelectionModel().selectedItemProperty().addListener(new CategoryListChangeListener());
		ObservableList<String> categories = FXCollections.observableArrayList(CATEGORY_NAMES);
		categoryList.setItems(categories);
		
		// Add objects to mainPane
		mainHBox.getChildren().addAll(categoryList, preferenceEditorVBox);
		
		// Add objects to contentPane
		contentVBox.getChildren().add(mainHBox);
		this.setMinHeight(300);
		this.setMinWidth(500);
		show();
	}
	
	private class CategoryListChangeListener implements ChangeListener<String> {
		
		@Override
		public void changed(ObservableValue<? extends String> obsVal, String oldVal, String newVal) {
			//System.out.println(newVal);
			switch (newVal) {
				case "Global":
					System.out.println("case: Global");
					displayGlobalVBox();
					break;
				case "Substitution":
					System.out.println("case: Substitution");
					displaySubstitutionVBox();
					break;
				case "Transposition":
					System.out.println("case: Transposition");
					displayTranspositionVBox();
					break;
			}
			
		}
		
		private void displayGlobalVBox() {
			if (mainHBox.getChildren().contains(preferenceEditorVBox)) {
				mainHBox.getChildren().remove(preferenceEditorVBox);
			}
			
			preferenceEditorVBox = new VBox();
			Label label = new Label("Global Preferences");
			
			CheckBox paddingCheckBox = new CheckBox("Cipher Text Padding");
			paddingCheckBox.setSelected(true);
			CheckBox detailsCheckBox = new CheckBox("Display Encipher/Decipher Details");
			
			paddingCheckBox.selectedProperty().addListener(new PaddingChangeListener());
			detailsCheckBox.selectedProperty().addListener(new DetailsChangeListener());
			
			preferenceEditorVBox.getChildren().addAll(label, paddingCheckBox, detailsCheckBox);
			//preferenceEditorVBox.setPrefWidth(100);
			
			mainHBox.getChildren().add(preferenceEditorVBox);
			
		}
		
		private void displaySubstitutionVBox() {
			if (mainHBox.getChildren().contains(preferenceEditorVBox)) {
				mainHBox.getChildren().remove(preferenceEditorVBox);
			}
			
			preferenceEditorVBox = new VBox();
			Label label = new Label("Substitution");
			preferenceEditorVBox.getChildren().add(label);
			mainHBox.getChildren().add(preferenceEditorVBox);
		}
		
		private void displayTranspositionVBox() {
			if (mainHBox.getChildren().contains(preferenceEditorVBox)) {
				mainHBox.getChildren().remove(preferenceEditorVBox);
			}
			
			preferenceEditorVBox = new VBox();
			Label label = new Label("Transposition");
			preferenceEditorVBox.getChildren().add(label);
			mainHBox.getChildren().add(preferenceEditorVBox);
		}
		
		private class PaddingChangeListener implements ChangeListener<Boolean> {

			@Override
			public void changed(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {
				if (newVal) {
					System.out.println("Padding: true");
				} else if (!newVal) {
					System.out.println("Padding: false");
				}
			}
		}
		
		private class DetailsChangeListener implements ChangeListener<Boolean> {

			@Override
			public void changed(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {
				if (newVal) {
					System.out.println("Details: true");
				} else if (!newVal) {
					System.out.println("Details: false");
				}
			}
		}
	}
	
	
}
