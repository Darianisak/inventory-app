package inventory.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;



public class Parser	{
	
	private Pantry_Sorter printerObject = null;

	/**
	 * Top down parse method; transposes a file to a Scanner, to be modified
	 * out of method.
	 * 
	 * @param pathof is the designated file path
	 * @return a complete ABS Tree of the input file
	 */
	public ItemProgramNode topLevelParser(File items, Pantry_Sorter print) {
		Scanner fScan = null;
		this.printerObject = print;
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
		}	catch	(RuntimeException e)	{
			//	Branch where the parser was unable to complete operations
			this.printerObject.reportError("The parser encountered an error.\n"
					+ "Check that the provided file matches the expected patterns.");
		}
		return null;
	}
	
	private ItemProgramNode parseInventory(Scanner iScan)	{
		ProgramNode itemTree = new ProgramNode();
		
		while	(iScan.hasNext())	{
			itemTree.addItem(parseItem(iScan));
		}
		if	(itemTree.getItems().isEmpty())	{
			//	Informs the user that they specified an empty file to be read.
			this.printerObject.reportError("File specified was empty, so only adding is supported.");
			return itemTree;
		}	else	{
			this.printerObject.reportSuccess("File loaded succesfully.");
			return itemTree;
		}
	}
	
	//	Relevant parse methods
	
	/**
	 * parseItem is a surface level parser which generates ItemNodes. It does
	 * this by off-loading tokens to four further parses in accordance with the
	 * grammar rules already established. For the purposes of this program, it 
	 * seemed easier to have all attributes associated with one node, rather 
	 * than generating a proper tree; it's more of a root system than a tree.
	 * Taking this approach of handling parsing out of method will allow changes
	 * to made more easily if needed in the future; i.e. list items.
	 * 
	 * @param token is 'hopefully' the name of an item, or the first token in
	 * an item's token sequence
	 * @return a complete ItemNode
	 */
	private ItemNode parseItem(Scanner token) {
		ItemNode currentItem = new ItemNode();
		try	{
			//	Parse itemName
			currentItem.setName(parseName(token));
			//	Parse Quantity
			currentItem.setQuan(parseQuan(token));
			//	Parse BestBefore
			currentItem.setDate(parseBestBefore(token));
			//	Parse Category
			currentItem.setCat(parseCategory(token));
		}	catch	(ItemListException e) {
			//	If a parse error is encountered, signal that to the user and
			//	throw a runtime
			this.printerObject.reportError("An error was encountered while parsing: " 
					+ currentItem.toString());
			throw new RuntimeException();
		}
		//	As an error should be flagged, assume currentItem is safe and 
		//	return it to parseInventory.
		return currentItem;
	}
	
	/**
	 * parseName is a parse method following the grammar::=
	 * a-zA-Z0-9 ","
	 * 
	 * @param token is any string, ideally representing an item name.
	 * @return the name of the item.
	 */
	private String parseName(Scanner token) throws ItemListException	{
		String retVal = token.next();
		if	(token.hasNext()) {
			//	Evaluate whether the next token is ',' and thus meets the
			//	grammar.
			if	(token.hasNext(","))	{
				token.next();
				return retVal;
			}	
		}
		//	Tokens do not conform to grammar rules.
		throw new ItemListException();
		
	}
	
	/**
	 * parseQuan is a parse method following the grammar::=
	 * "KG" | "L" 0-9 ","
	 * 
	 * @param token should be a prefix specifying if solid or liquid weight.
	 * @return a quantity object, constructed according to the prefix token
	 * and the volume token.
	 */
	private Quantity parseQuan(Scanner token) throws ItemListException	{
		if	(token.hasNext("KG") || token.hasNext("L"))	{
			String pref = token.next();
			if	(token.hasNextDouble())	{
				Double value = token.nextDouble();
				if	(token.hasNext(","))	{
					//	Ternary that creates and returns Quantity objects in 
					//	accordance with the prefix specified by pref.
					token.next();
					return (pref.equals("KG")) ? 
						new SolidQuan(value) :
						new LiquidQuan(value);
				}	
			}
		}	
		//	Tokens do not conform to grammar rules.
		throw new ItemListException();
	}
	
	/**
	 * parseBestBefore is a parse method following the grammar::=
	 * 1-12 1-31 0-9 ","
	 * 
	 * @param token should be a double, representing the month of the best
	 * before.
	 * @return a java date object, indicating the Best before date of the item.
	 */
	private Date parseBestBefore(Scanner token)	throws ItemListException	{
		
		//	TODO needs redesign, Date is shit apparently https://www.baeldung.com/java-8-date-time-intro
		
		System.out.println("gets to date");
		return null;
	}
	
	/**
	 * parseCategory is a parse method following the grammar::=
	 * a-zA-Z0-9 ";"
	 * 
	 * @param token is any string, ideally representing a category name. These
	 * names are user defined.
	 * @return the category name.
	 */
	private String parseCategory(Scanner token)	throws ItemListException	{
		return null;
	}
	
	//	SubClasses of ItemProgramNode, used for generating the paser tree
	
	class ProgramNode implements ItemProgramNode	{
		
		private ArrayList<ItemNode> items = new ArrayList<ItemNode>();
		
		/**
		 * getter method for retrieving the children nodes of this ProgramNode.
		 * Children nodes in this case are individual items, specified by the
		 * user as being apart of their pantry etc.
		 * 
		 * @return an ArrayList of ItemNodes, aka child nodes.
		 */
		public ArrayList<ItemNode> getItems()	{
			return items;
		}
		
		/**
		 * setter method for adding items/nodes to the ArrayList of items.
		 * 
		 * @param itemToAdd is a child node of ProgramNode
		 */
		public void addItem(ItemNode itemToAdd)	{
			this.items.add(itemToAdd);
		}
		
		@Override
		public String toString()	{
			String output = "";
			for	(ItemNode item : this.items) {
				
				//	TODO rewrite this; override itemNode toString and tidy this
				
				String temp = item.getName() + ", ";
				temp.concat(item.getQuan().getPrefix() + " " + 
							item.getQuan().getAmount() +", "); 
				temp.concat(item.getDate().toString() + ", ");
				temp.concat(item.getCat() + "; ");
				output.concat(temp);
			}
			return output;
		}
	}
	
	class ItemNode	{
		
		private String itemName = "null";
		private Quantity amount = null;
		private Date bestBefore= null;
		private String category = "null";
		
		/**
		 * setName is a setter method for specifying an item's name.
		 * 
		 * @param name is the word used to represent the item name.
		 */
		public void setName(String name)	{this.itemName = name;}
		
		/**
		 * getName is a getter method for accessing an items name.
		 * 
		 * @return the specified name of this item.
		 */
		public String getName()	{return this.itemName;}
		
		/**
		 * setQuan is a setter method for specifying an item's weight.
		 * 
		 * @param amnt is the weight of the item
		 */
		public void setQuan(Quantity amnt)	{this.amount = amnt;}
		
		/**
		 * getQuan is a getter method for accessing an items quantity.
		 * 
		 * @return the specified weight of the item.
		 */
		public Quantity getQuan() {return this.amount;}
		
		/**
		 * setDate is a setter method for specifying an item's best before date.
		 * 
		 * @param bb is the date to specify as the best before.
		 */
		public void setDate(Date bb)	{this.bestBefore = bb;}
		
		/**
		 * getDate is a getter method for accessing the best before date of an
		 * item.
		 * 
		 * @return the best before date of this item.
		 */
		public Date getDate()	{return this.bestBefore;}
		
		/**
		 * setCat is a setter method for specifying the category that an item 
		 * belongs within.
		 * 
		 * @param cat is the category that the item should be assigned to.
		 */
		public void setCat(String cat)	{this.category = cat;}
		
		/**
		 * getCat is a getter method for accessing the category tag assigned to
		 * this item.
		 * 
		 * @return the specified category of this item.
		 */
		public String getCat()	{return this.category;}
	}
}
