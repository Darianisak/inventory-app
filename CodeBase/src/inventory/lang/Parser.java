package inventory.lang;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Parser {

	/**
	 * Top down parse method; transposes a file to a Scanner, to be modified
	 * out of method
	 * @param pathof is the designated file path
	 * @return a complete ABS Tree of the input file
	 */
	public ItemProgramNode parse(File pathof, GUI display) {
		Scanner fScan = null;
		try	{
			//fScan = new Scanner(pathof);
			//	Apply delimiting to the scanner; tokens may only follow one
			//	another if they are (){},;
			//fScan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");
			//display.getFieldAdd();
			System.out.println("passed setETxt");
			//display.run();
			
		}	catch	(Exception e)	{
			//	Write the stack trace to the GUI's text area
			//display.getTextArea().setText(e.toString());
		}
		
		return null;
	}
	
}
