package inventory.gui;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import inventory.lang.InventoryApp;

/**
 * @author darianisak
 * GUI class handles all GUI tasks and elements 
 */
public class GUI {
	
	private JFileChooser fileLoader;	//	File loading
	private JFrame programFrame;	//	Stores the frame the programs GUI is held
									//	within
	//private JPanel
	private String	appName = "OptiPantry";	//	App name - stored in field for 
									//	quick changes
	
	
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
		//new InventoryApp().doLoading(userLoading());
		fullGUI();
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
		try	{
			File returnFile = new File("invalid");
			fileLoader = new JFileChooser();
			fileLoader.setDialogTitle("Choose an inventory file");
			int fileStatus = fileLoader.showOpenDialog(new JFrame());
			if	(fileStatus == JFileChooser.APPROVE_OPTION)	{
				returnFile = fileLoader.getSelectedFile(); 
			}	else {
				throw new FileNotFoundException();
			}
			return returnFile;
		}	catch	(FileNotFoundException e)	{
			//	Handles the case where a user does not select a file and 
			//	immediately closes the program
			System.exit(0);	
			return new File("invalid");
		}
	}
	
	/**
	 * Fully fledged GUI class for applicaiton
	 */
	private	void fullGUI()	{
		programFrame = new JFrame(this.appName);
		programFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		programFrame.setSize(new Dimension(1080, 720));
		
		programFrame.setVisible(true);
	}
}
