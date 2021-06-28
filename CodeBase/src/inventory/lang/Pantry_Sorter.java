package inventory.lang;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Stack;

import inventory.lang.Parser.ItemNode;
import inventory.lang.Parser.ProgramNode;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

public class Pantry_Sorter extends GUI{

	private ActionClass actionObject = new ActionClass();
	private Parser parseObj = new Parser();
	
	private ItemProgramNode itemTree = null;
	private HashMap<String, ItemNode> itemAdjList = null;
	
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
		
		//	TODO ~ add proper handling for throwables in catch clauses
		try	{
			this.itemTree = this.parseObj.topLevelParser(items);
		}	catch	(FileNotFoundException e)	{
			//	Scanner error
		}	catch	(ItemListException e)	{
			//	Grammar error/ Parser error
		}
		
		this.itemAdjList = new HashMap<String, ItemNode>();
		//	TODO this is crap, the above call needs a try catch for runtimes
		//	additionally need to handle adj lists properly and empty trees
		System.out.println("done");
		//reportSuccess("The file was successfully loaded and all supporting\n"
		//		+ "assets were generated.\nThere were " + this.itemAdjList.size()
		//		+ " items loaded.\n If this number is zero, the file was empty.");
	}
	
	private void computeAdjList()	{
		
	}
	
	private void computeCatAdj()	{
		
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
		getTextOutputArea().insert(error.concat("\n"), 0);		
	}
	
	/**
	 * setter for reporting a success progress message to the screen. For more
	 * detailed process explanation, see the javaDoc for "reportError". This
	 * method should be used to indicate that, for example, items are finished
	 * loading, etc.
	 * 
	 * @param message to print to screen in the color green.
	 */
	public void reportSuccess(String message)	{
		getTextOutputArea().setForeground(Color.GREEN);
		getTextOutputArea().insert(message.concat("\n"), 0);	
	}
	
	/**
	 * setter for reporting normal functions by the program. For more detailed
	 * process explanation, see the javaDoc for "reportError". This method 
	 * should be used to, for example, show all the items that the user has
	 * loaded, etc.
	 * 
	 * @param message to print to screen in the color black.
	 */
	public void reportStandard(String message)	{
		getTextOutputArea().setForeground(Color.BLACK);
		getTextOutputArea().insert(message.concat("\n"), 0);	
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
