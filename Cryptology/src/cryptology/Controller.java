package cryptology;

import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// The Controller will handle the creation and management of cipher objects

public class Controller {
	
	public static final String		DOCUMENT_PATH = "bin/Documents/";
	private final		String		PREFERENCES_NAME = "UserPreferences.xml";
	//private 			Preferences	prefs;
	private 			XMLParser	parser;
	
	private final		String		GLOBAL_TAGNAME = "Global";
	private final		String		BLOCKSIZE_TAGNAME = "BlockSize";
	private final		String		DETAILS_TAGNAME = "Details";
	private final		String		PADDING_TAGNAME = "Padding";
	
	public Controller() {
		
		parser = new XMLParser(DOCUMENT_PATH, PREFERENCES_NAME);
		
	}
	
	public Preferences getPrefs() {
		
		Preferences prefs = new Preferences();
		
		NodeList rootNodeList = parser.getRoot();
		NodeList preferencesNodeList = parser.getNode("Preferences", rootNodeList).getChildNodes();
		// Global Preferences
		NodeList globalNodeList = parser.getNode(GLOBAL_TAGNAME, preferencesNodeList).getChildNodes();
		
		prefs.setBlockSize(Integer.parseInt(parser.getNodeValue(parser.getNode(BLOCKSIZE_TAGNAME, globalNodeList))));
		prefs.setDetails(Boolean.parseBoolean(parser.getNodeValue(parser.getNode(DETAILS_TAGNAME, globalNodeList))));
		prefs.setPadding(Boolean.parseBoolean(parser.getNodeValue(parser.getNode(PADDING_TAGNAME, globalNodeList))));
		
		return prefs;
	}
	
	public void updateDoc(Preferences prefs) {
		
		NodeList rootNodeList = parser.getRoot();
		NodeList preferencesNodeList = parser.getNode("Preferences", rootNodeList).getChildNodes();
		// Global Preferences
		NodeList globalNodeList = parser.getNode(GLOBAL_TAGNAME, preferencesNodeList).getChildNodes();
		
		parser.setNodeValue(BLOCKSIZE_TAGNAME, globalNodeList, String.valueOf(prefs.getBlockSize()));
		parser.setNodeValue(DETAILS_TAGNAME, globalNodeList, String.valueOf(prefs.getDetails()));
		parser.setNodeValue(PADDING_TAGNAME, globalNodeList, String.valueOf(prefs.getPadding()));
		
		parser.updateDoc();
	}
}
