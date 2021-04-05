package inventory.lang;
import java.io.File;

import inventory.gui.GUI;
import inventory.lang.dataLoading;

/**
 * @author darianisak
 *
 */
public class InventoryApp {
	
	private File inventoryFile;

	/**
	 * Main method for running the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new GUI();
	}
	
	/**	
	 * Method that assigns the active inventory file to a field
	 * Once this is done, method hands off to dataLoading class
	 * @param invenFile
	 */
	public void doLoading(File invenFile)	{
		this.inventoryFile = invenFile;
		new dataLoading(invenFile);
	}
}
