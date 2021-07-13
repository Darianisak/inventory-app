package inventory.lang;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Stack;

import inventory.lang.Parser.ItemNode;

import java.util.HashMap;
import java.util.ArrayList;

public class Pantry_Sorter extends GUI{

	private WriteOut writeObj = new WriteOut();
	
	private ItemProgramNode itemTree = null;
	private HashMap<String, ArrayList<ItemNode>> itemAdjList = 
			new HashMap<String, ArrayList<ItemNode>>();
	private HashMap<String, ArrayList<ItemNode>> categoryAdjList = 
			new HashMap<String, ArrayList<ItemNode>>();
	private PrefixSearch searchTree = null;
	
	/**
	 * Main function for running the program. As it is an extension of GUI,
	 * the instantation call calls the super method, thus running the GUI
	 * 
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
		case "SAVE":
		//	When save is called, the itemList contained within this.itemTree
		//	is written out to a file, with the name specified by the query
		//	field. If no name is supplied than "$01" will be used. This
		//	method does not support overwriting and as such duplicate file
		//	names will cause the save process to be aborted with a termination
		//	message.
		this.writeObj.mainWrite(this.itemTree, input);
		String parseCode = this.writeObj.getErrorCode();
		if	(parseCode == "nE" || parseCode == "eS")	{
			reportSuccess("File saving success: " + stringifyCode(parseCode));
			return;
		}	else	{
			reportError("File writing failure: " + stringifyCode(parseCode));
			return;
		}
		case "SEARCH":
			//	TODO ~ Needs Trie node for prefix matching
			break;
		case "REMOVE":
			//	Remove is done by parsing the contents of the query field into
			//	an itemTree, and then verifying that the items of that item
			//	tree are contained within other program data structures. If they
			//	are found, then remove them. Owing to this implementation using
			//	the parser as a verification method, multi element deletion is
			//	supported by default, though be advised that one parser error
			//	will cause the process to return without removing anything.
			String toRemove = input;
			Parser removeParser = new Parser();
			ItemProgramNode removeTree = null;
			if	(input != "")	{
				removeTree = removeParser.parseInventory(new java.util.Scanner(toRemove));
				if	(removeParser.getErrorCode() ==  "nE")	{
					//	The input tokens were valid, so check that it is present
					//	This outer for loop ensures that all elements specified
					//	for deletion are removed.
					for	(ItemNode item : removeTree.getItems())	{
						//	Check that the item is within the itemAdjList, which
						//	should be the computationally fastest way of verifying.
						//	If the element is found in the adjlist, it is present
						//	program wide, so remove accordingly.
						if	(this.itemAdjList.get(item.getName()).contains(item))	{
							this.itemTree.getItems().remove(item);
							this.itemAdjList.get(item.getName()).remove(item);
							this.categoryAdjList.get(item.getCat()).remove(item);
						}	else	{
							reportError("The item: " + item.toString() + " was "
									+ "not found wihtin the itemAdjList.");
						}
					}
					return;
				}	else	{
					//	Branch where the input supplied by the query field did
					//	not conform to the parsers grammar rules and the process
					//	was subsequently terminated.
					reportError("The item specified in the query field did not have"
							+ " a valid format: itemName, KG/L 0.0, DD MM YYYY, category; "
							+ "The input failed from error code: " + 
							stringifyCode(removeParser.getErrorCode()));
					return;
				}
			}	
			return;
		case "ADD":
			//	Adding is done via parsing a scanner to the parse class, as such
			//	item nodes are received in ArrayList format. As such, multiple
			//	item adding is supported by default, IF the user formats their
			//	input string correctly. Support for empty elements is NOT
			//	supported.	
			if	(input == "") return;
			String toAdd = input;
			ItemProgramNode addTree = null;
			Parser addParser = new Parser();
			addTree = addParser.parseInventory(new java.util.Scanner(toAdd));
			
			//	To validate the input, a similiar process to onLoad()'s way of
			//	validating. There shouldn't
			if	(addParser.getErrorCode() == "nE")	{
				for	(ItemNode item : addTree.getItems())
					this.itemTree.addItem(item);
				computeAdjList(itemTree.getItems(), this.itemAdjList);
				computeCatAdj(itemTree.getItems(), this.categoryAdjList);
				reportItemList(this.itemTree.getItems());
			}	else	{
				reportError("The item specified in the query field did not have"
						+ " a valid format: itemName, KG/L 0.0, DD MM YYYY, category; "
						+ "The input failed from error code: " + 
						stringifyCode(addParser.getErrorCode()));
			}
			return;
		default:
			//	This branch should never be reached, but in case it is, end 
			//	execution and print event stack
			reportError(buttonPressed + " does not match a case required by the"
					+ " 'Active' variant of onAction().\n" + events.toString());
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
		case "SORT":
			//	When pressed, sort applies date sorting to the entirety of the
			//	itemTree itemList. By applying sorting to this list, the result
			//	of the sort can be exported out via the save functionality,
			//	allowing sorts to be preserved between loads of the application.
			this.itemTree.getItems().sort((i1, i2) -> i1.compareTo(i2));
			reportItemList(this.itemTree.getItems());
			break;
		case "DISPLAY":
			//	When pressed, the GUI will output the contents of the itemtree
			//	sub node list. At current, only printing the tree list is
			//	supported, additionally functionality will be required for the
			//	two adj list types.
			reportItemList(this.itemTree.getItems());
			return;
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
				this.itemTree = tempTree;
				//	Success branch, could possible have been an empty file.
				reportSuccess("File loading success: " + stringifyCode(parseCode));
				//	Call adjacency list methods for pre computation structure
				//	building. These methods are not called in the case of an
				//	empty list.
				if	(parseCode != "eS")	{
					//	If the scanner was not empty, compute the adjacency list
					//	of itemNames::items.					
					this.itemAdjList = 
							computeAdjList(this.itemTree.getItems(), this.itemAdjList);
					//	If the scanner was not empty, compute the adjacency list
					//	of categories::items.
					this.categoryAdjList = 
							computeCatAdj(this.itemTree.getItems(), this.categoryAdjList);
					
					//	TODO ~ trie testing
					System.out.println("pre trie gen");
					generateSearchTree();
					System.out.println("post trie gen");
					
					
					
					
				}	else	{
					//	TODO May be problematic, not sure yet though
					this.itemTree.getItems().removeAll(null);
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
	
	public void generateSearchTree()	{
		PrefixSearch prefTree = new PrefixSearch();
		//	for each item in itemTree, add to prefTree ~ if no error code, assign to priv
		//	else return with error report
		for	(ItemNode item : this.itemTree.getItems())	{
			String toAdd = item.toString();
			prefTree.addToTree(item.toString(), item);
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
		case "fAE":	return "Filename already exists!";
		case "fC":	return "File creation error!";
		case "fNF":	return "File not found!";
		//	An unsupported error code has been supplied, which means the
		//	implementation needs to be updated.
		default:	throw new RuntimeException("Unsupported Error Code provided"
					+ " by Parser.class or WriteOut.class");
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
	 * reportItemList is a setter method for outputing the contents of a
	 * specified itemlist out to the GUI.
	 * 
	 * @param toReport is the ArrayList of ItemNodes to print out.
	 */
	public void reportItemList(ArrayList<ItemNode> toReport)	{
		//	Used to clear the display prior to the inventory write.
		getTextOutputArea().setText(null);
		getTextOutputArea().setForeground(Color.BLUE);
		for	(int i = toReport.size() - 1 ; i >= 0 ; i--)	{
			getTextOutputArea().insert(toReport.get(i).toString().concat("\n"), 0);
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
