package inventory.lang;

import java.awt.Color;
import java.io.File;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.HashMap;

public class Pantry_Sorter extends GUI{

	private ActionClass actionObject = new ActionClass();
	private Parser parseObj = new Parser();
	
	private ItemProgramNode itemTree = null;
	private HashMap<String, ItemProgramNode> itemAdjList = null;
	
	/**
	 * Main function for running the program. As it is an extension of GUI,
	 * the instantation call calls the super method, thus running the GUI
	 * @param args is the set of arguments passed in at runtime
	 */
	public static void main(String[] args)	{new Pantry_Sorter();}

	@Override
	protected void onAction(Stack<String> events, String input) {
		//	Method used for "active" buttons
		String buttonPressed = "";
		try	{
			//	Validates that the eventStack actually had a button press recorded
			buttonPressed = stackCheck(events);
		}	catch	(RuntimeException e)	{
			//	If this branch is taken, it means that the event stack was empty
			//	and that the function should cease computation.
			return;
		}
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
		String buttonPressed = "";
		try	{
			//	Validates that the eventStack actually had a button press recorded
			buttonPressed = stackCheck(events);
		}	catch	(RuntimeException e)	{
			//	If this branch is taken, it means that the event stack was empty
			//	and that the function should cease computation.
			return;
		}
		switch(buttonPressed)	{
		case "SAVE":
			//	TODO
			break;
		case "SORT":
			//	TODO
			break;
		case "DISPLAY":
			//	TODO ~ Contents of itemList won't be displayed until this button
			//	is called. Additionally, need to figure out a way of enabling
			//	the user to scroll through data.
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
		//	Generate the item tree
		this.itemTree = this.parseObj.topLevelParser(items);
		if	(this.itemTree != null)	{
			//	Generate the unsorted adjacency list to save on computation
			//	later
			//	TODO
		}
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
	 * stackCheck is a helper method that validates the contents of the stack
	 * prior to performing any actions. It checks that there is a recorded
	 * event within the eventStack, and if not, prints an error message to the
	 * JTextArea.
	 * 
	 * @param events is a stack of all buttons pressed recently; this stack
	 * does not include references to the queryField.
	 * @return the most recently pushed element of the stack.
	 */
	public String stackCheck(Stack<String> events)	{
		try	{
			return events.peek();
		}	catch	(EmptyStackException e)	{
			//	If this branch is reached, it means that somewhere prior
			//	an action being called, the stack was tampered with, or
			//	that the user never pressed a button prior to entering
			//	data to the Query Field.
			reportError("Event Queue was read and found to be empty.\n"
					+ "For the program to function, you must have pressed a"
					+ "\nbutton prior to entering text in the Query Field.");
			throw new RuntimeException();
		}
	}
}
