package inventory.lang;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;

public class Pantry_Sorter extends GUI{

	
	/**
	 * Main function for running the program. As it is an extension of GUI,
	 * the instantation call calls the super method, thus running the GUI
	 * @param args is the set of arguments passed in at runtime
	 */
	public static void main(String[] args)	{
		new Pantry_Sorter();
	}

	@Override
	protected void onSearch() {
		// TODO Auto-generated method stub
		getTextOutputArea().setText("fucking work");
		
	}

	@Override
	protected void onLoad(File nodes) {
		// TODO Auto-generated method stub
		
	}
	
}
