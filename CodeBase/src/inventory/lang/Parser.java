package inventory.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;



public class Parser {
	
	private Pantry_Sorter printerObject = new Pantry_Sorter();

	/**
	 * Top down parse method; transposes a file to a Scanner, to be modified
	 * out of method.
	 * 
	 * @param pathof is the designated file path
	 * @return a complete ABS Tree of the input file
	 */
	public ItemProgramNode topLevelParser(File items) {
		Scanner fScan = null;
		try	{
			fScan = new Scanner(items);
			//	Sets a delimitter where the only consecutive tokens allowed
			//	are , and ;
			fScan.useDelimiter("\\s+|(?=[,;])|(?<=[,;])");
			ItemProgramNode itemTree = parseInventory(fScan);
			fScan.close();
			return itemTree;
		}	catch	(FileNotFoundException e)	{
			//	Branch where the method was supplied with an invalid items arg
			this.printerObject.reportError("The file supplied was invalid.\n "
					+ "Please select a different file.");
		}	catch	(ItemListException e)	{
			//	Branch where the parser was unable to complete operations
			this.printerObject.reportError("The parser encountered an error.\n"
					+ "Check that the provided file matches the expected patterns.");
		}
		return null;
	}
	
	private ItemProgramNode parseInventory(Scanner iScan) throws ItemListException	{
		ProgramNode itemTree = new ProgramNode();
		
		//	TODO ~ don't know if this will work, as it varies from how I handled
		//	the parser in the comp assignment. Can't validate either way until
		//	I write test files
		
		while	(iScan.hasNext())	{
			itemTree.addItem(parseItem(iScan));
		}
		if	(itemTree.getItems().isEmpty())	{
			//	Informs the user that they specified an empty file to be read.
			printerObject.reportError("File specified was empty, so only adding is supported.");
			return itemTree;
		}	else	{
			return itemTree;
		}
	}
	
	//	Relevant parse methods
	
	private ItemProgramNode parseItem(Scanner token) {
		//	TODO
		return null;
	}
	
	//	SubClasses of ItemProgramNode, used for generating the paser tree
	
	class ProgramNode implements ItemProgramNode	{
		
		private ArrayList<ItemProgramNode> items = new ArrayList<ItemProgramNode>();
		
		/**
		 * getter method for retrieving the children nodes of this ProgramNode.
		 * Children nodes in this case are individual items, specified by the
		 * user as being apart of their pantry etc.
		 * 
		 * @return an ArrayList of ItemNodes, aka child nodes.
		 */
		public ArrayList<ItemProgramNode> getItems()	{
			return items;
		}
		
		/**
		 * setter method for adding items/nodes to the ArrayList of items.
		 * 
		 * @param itemToAdd is a child node of ProgramNode
		 */
		public void addItem(ItemProgramNode itemToAdd)	{
			this.items.add(itemToAdd);
		}
		
		@Override
		public String toString()	{
			//	TODO ~ write toString so it outputs in accordance with the grammar
			return "";
		}
	}
	
	class ItemNode implements ItemProgramNode	{
		//	TODO
	}
	
	
}
