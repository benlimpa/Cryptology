package cryptology;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PreferencesView extends Stage {
	
	private final String[] CATEGORY_NAMES = {"Global", "Substitution", "Transposition"};
	
	public PreferencesView() {
		VBox contentPane = new VBox();
		contentPane.setPadding(new Insets(3));
		Scene scene = new Scene(contentPane);
		
		this.setScene(scene);
		this.setTitle("Preferences");
		
		HBox mainPane = new HBox();
		
		ListView<String> categoryList = new ListView<String>();
		ObservableList<String> categories = FXCollections.observableArrayList(CATEGORY_NAMES);
		categoryList.setItems(categories);
		
		// Add objects to mainPane
		mainPane.getChildren().add(categoryList);
		
		// Add objects to contentPane
		contentPane.getChildren().add(mainPane);
	}
}
