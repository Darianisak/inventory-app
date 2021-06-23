package inventory.lang;

import java.io.File;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

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
	protected void onAction(Stack<String> events, String input) {
		//	Method used for "active" buttons
		
		// TODO Auto-generated method stub
		if	(!events.isEmpty())	getTextOutputArea().setText(events.pop());
		System.out.println("inputField: " + input + "\nnewLine fuck");
		
		getTextOutputArea().setText("Line1Fuck\nLine2Fuck");
		
	}
	
	@Override
	protected void onAction(Stack<String> events)	{
		//	Method used for "Static" buttons
		String lastEvent = "";
		try	{
			lastEvent = events.peek();
		}	catch	(EmptyStackException e)	{
			
		}
	}

	@Override
	protected void onLoad(File items) {
		// TODO Auto-generated method stub
		System.out.println("load called in pantry");
	}
	
	/**
	 * setter for TODO
	 * 
	 * @param error
	 */
	public void reportError(String error)	{
		getTextOutputArea().setText(error);
	}
	
}
