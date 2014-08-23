package cryptology;

import java.awt.Dimension;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColumnarView extends VBox {

	/*
	 * Class Instance Variables
	 */
	
	
	
	/*
	 * Class Constants
	 */
	
	
	
	/*
	 * Constructor Method
	 */
	
	public ColumnarView(Dimension dimension) {
	
		TabPane tabPane = new TabPane();
		tabPane.setStyle("-fx-font-size: 12pt;");
		getChildren().add(tabPane);
		
		Tab singleTab = new Tab();	
		singleTab.setText("Single");
		singleTab.setContent(new Rectangle(800,400, Color.LIGHTBLUE));
		singleTab.setClosable(false);
		tabPane.getTabs().add(singleTab);
		
	}
}
