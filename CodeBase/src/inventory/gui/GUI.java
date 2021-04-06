package inventory.gui;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import inventory.lang.InventoryApp;

/**
 * @author darianisak
 * GUI class handles all GUI tasks and elements 
 */
public class GUI {
	
	private JFileChooser fileLoader;	//	File loading
	
	
	/**
	 * GUI getter/constructor for instantiating GUI elements; keeps private
	 * 'Main' method for GUI
	 */
	public GUI()	{
		runGUI();
	}
	
	/**
	 * runGUI is responsible for running all GUI related methods in order
	 */
	private void runGUI()	{
		//	Calls userLoadings and sends a selected file to main field
		new InventoryApp().doLoading(userLoading());
	}
	
	/**
	 * Method handles getting file data through a UI
	 * 
	 * https://examples.javacodegeeks.com/desktop-java/swing/
	 * jfilechooser/jfilechooser-swing-example/
	 * 
	 * @return
	 */
	private File userLoading() {
		File returnFile = new File("invalid");
		fileLoader = new JFileChooser();
		fileLoader.setDialogTitle("Choose an inventory file");
		int fileStatus = fileLoader.showOpenDialog(new JFrame());
		if	(fileStatus == JFileChooser.APPROVE_OPTION)	{
			returnFile = fileLoader.getSelectedFile(); 
		}	else {
			System.out.println("Invalid Approve option?");
		}
		return returnFile;
	}
}
