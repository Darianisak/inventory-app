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
	private HashMap<String, ArrayList<ItemNode>> itemAdjList = 
			new HashMap<String, ArrayList<ItemNode>>();
	private HashMap<String, ArrayList<ItemNode>> categoryAdjList = 
			new HashMap<String, ArrayList<ItemNode>>();
	
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
		Parser fileToTree = new Parser();
		ItemProgramNode tempTree = null;
		try	{
			tempTree = fileToTree.topLevelParser(items);
			String parseCode = fileToTree.getErrorCode();
			//	Generates output messages.
			if	(parseCode == "nE" || parseCode == "eS")	{
				//	Success branch, could possible have been an empty file.
				reportSuccess("File loading success: " + stringifyCode(parseCode));
				//	Call adjacency list methods for pre computation structure
				//	building. These methods are not called in the case of an
				//	empty list.
				if	(parseCode != "eS")	{
					computeAdjList(this.itemTree.getItems(), this.itemAdjList);
					
					this.categoryAdjList = computeCatAdj
									(this.itemTree.getItems(), this.categoryAdjList);
				}
				return;
			}	else	{
				reportError("File loading failure: " + stringifyCode(parseCode));
				return;
			}
		}	catch	(FileNotFoundException e)	{
			//	Branch where a FileNotFoundException is caught after being thrown
			//	from topLevelParser's scanner call.
			reportError("The file supplied was invalid. Please choose another.");
			//	Return out of the process to prevent tree assignment of a faulty
			//	file.
			return;
		}
	}
	
	/**
	 * Helper method for generating a message for GUI output according to the
	 * supplied error code from Parser.class.
	 * 
	 * @param code as supplied by Parser.class
	 * @return Strings relaying information about the supplied code.
	 */
	public String stringifyCode(String code)	{
		switch	(code)	{
		case "nE": 	return "No error!";
		case "eS": 	return "No error, empty file!";
		case "pN": 	return "Error on item name!";
		case "pQ":	return "Error on item quantity!";
		case "pBB":	return "Error on best before!";
		case "pC":	return "Error on category!";
		//	An unsupported error code has been supplied, which means the
		//	implementation needs to be updated.
		default:	throw new RuntimeException("Unsupported Error Code provided"
					+ " by Parser.class.");
		}
	}
	
	/**
	 * Setter method for adding an array of item elements into the applications
	 * item adjacency list. This is fundamental for search functionality.
	 * Assumes the supplied list does not contain any null elements.
	 * 
	 * @param toAdd is the item/items to add.
	 * @param target is the 'destination' array. This method does not modify
	 * target, instead returning a new array list with all the elements of
	 * target, plus toAdd.
	 * @return an Adjacency list of itemName::ItemNode pairings
	 */
	public HashMap<String, ArrayList<ItemNode>> computeAdjList(
			ArrayList<ItemNode> toAdd, HashMap<String, ArrayList<ItemNode>> target)	{
		
		//	Temp HashMap used to return the modified item adj list.
		HashMap<String, ArrayList<ItemNode>> returnMap = 
				new HashMap<String, ArrayList<ItemNode>>();
		
		returnMap.putAll(target);

		for	(ItemNode item : toAdd)	{
			String setName = item.getName();
			//	Conditional statement ensures that if setName is a new key, then
			//	a null pointer will not be thrown.
			if	(returnMap.containsKey(setName))		
				returnMap.put(setName, addMap(returnMap.get(setName), item));
			else
				returnMap.put(setName, addMap(new ArrayList<ItemNode>(), item));
		}
		return returnMap;
	}
	
	/**
	 * Setter method for adding an array of item elements into the applications
	 * item adjacency list of categories. This is fundamental for search functionality.
	 * Assumes the supplied list does not contain any null elements.
	 * 
	 * @param toAdd is the item/items to add.
	 * @param target is the 'destination' array. This method does not modify
	 * target, instead returning a new array list with all the elements of
	 * target, plus toAdd.
	 * @return an Adjacency list of itemCategory::ItemNode pairings
	 */
	public HashMap<String, ArrayList<ItemNode>> computeCatAdj(
			ArrayList<ItemNode> toAdd, HashMap<String, ArrayList<ItemNode>> target)	{
		
		//	Temp HashMap used to return the modified category adj list.
		HashMap<String, ArrayList<ItemNode>> returnMap = 
				new HashMap<String, ArrayList<ItemNode>>();
		
		returnMap.putAll(target);
		//	Matches each item in toAdd to a category based arraylist for quick
		//	searching.
		for	(ItemNode item : toAdd)	{
			String setName = item.getCat();
			//	Conditional statement ensures that if setName is a new key, then
			//	a null pointer will not be thrown.
			if	(returnMap.containsKey(setName))
				returnMap.put(setName, addMap(returnMap.get(setName), item));
			else
				returnMap.put(setName, addMap(new ArrayList<ItemNode>(), item));
		}
		return returnMap;
	}
	
	/**
	 * Method that adds an element an ArrayList. Used purely for updating 
	 * HashMap values.
	 * 
	 * @param former values contained within the HashMap's ArrayList.
	 * @param toAdd is the value that needs to added to the HashMap's ArrayList.
	 * @return a new ArrayList with both toAdd, and the contents of former.
	 */
	public ArrayList<ItemNode> addMap(ArrayList<ItemNode> former, ItemNode toAdd)	{
		ArrayList<ItemNode> tempList = new ArrayList<ItemNode>();
		tempList.addAll(former);
		tempList.add(toAdd);
		return tempList;
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
