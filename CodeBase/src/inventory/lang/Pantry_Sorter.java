package inventory.lang;

import java.awt.Color;
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
		String buttonPressed = stackCheck(events);
		switch(buttonPressed)	{
		case "SEARCH":
			//	TODO
			break;
		case "REMOVE":
			//	TODO
			break;
		case "ADD":
			//	TODO
			break;
		default:
			//	This branch should never be reached, but in case it is, end 
			//	execution and print event stack
			reportError(buttonPressed + " does not match a case required by the"
					+ " 'Static' variant of onAction().\n" + events.toString());
			throw new RuntimeException();
		}
		
	}
	
	@Override
	protected void onAction(Stack<String> events)	{
		//	Method for "Static" button related actions
		String buttonPressed = stackCheck(events);
		switch(buttonPressed)	{
		case "SAVE":
			//	TODO
			break;
		case "SORT":
			//	TODO
			break;
		default:
			//	This branch should never be reached, but in case it is, end 
			//	execution and print event stack
			reportError(buttonPressed + " does not match a case required by the"
					+ " 'Static' variant of onAction().\n" + events.toString());
			throw new RuntimeException();
		}
	}

	@Override
	protected void onLoad(File items) {
		// TODO Auto-generated method stub
		System.out.println("load called in pantry");
		reportError("error method called");
	}
	
	/**
	 * setter for reporting error messages to the GUI output screen. The method
	 * first changes the text color to red, followed by printing the message
	 * pointed to by the error arg. Care should be taken to reset the color
	 * back to black if this method is called during normal operation and not
	 * explicity during a failure case.
	 * 
	 * @param error contains an error message status to print to the GUI
	 */
	public void reportError(String error)	{
		getTextOutputArea().setForeground(Color.RED);
		getTextOutputArea().setText(error);		
	}
	
	/**
	 * TODO
	 * 
	 * @param events
	 * @return
	 */
	public String stackCheck(Stack<String> events)	{
		try	{
			return events.peek();
		}	catch	(EmptyStackException e)	{
			//	If this branch is reached, it means that somewhere prior
			//	an action being called, the stack was tampered with.
			reportError("Event Queue was read and found to be empty.\n" 
						+ events.toString());
			throw new RuntimeException();
		}
	}
}
