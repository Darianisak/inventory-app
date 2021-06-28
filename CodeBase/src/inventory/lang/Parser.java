package inventory.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Parser	{

	//	TODO ~ add throws documentation to class
	
	/**
	 * Top down parse method; transposes a file to a Scanner, to be modified
	 * out of method. When calling this method, make sure it is set up to handle
	 * both FileNotFoundExceptions, which are thrown by the Scanner, and
	 * ItemListExceptions, which can be thrown during most parse methods as a
	 * result of ignoring grammar rules.
	 * 
	 * @param pathof is the designated file path
	 * @return a complete ABS Tree of the input file
	 */
	public ItemProgramNode topLevelParser(File items) 
			throws ItemListException, FileNotFoundException {
		Scanner fScan = null;
		fScan = new Scanner(items);
		ItemProgramNode itemTree = parseInventory(fScan);
		fScan.close();
		return itemTree;
	}
	
	/**
	 * TODO
	 * @param iScan
	 * @return
	 * @throws ItemListException
	 */
	public ItemProgramNode parseInventory(Scanner iScan) throws ItemListException {
		ProgramNode itemTree = new ProgramNode();
		//	Sets a delimitter where the only consecutive tokens allowed
		//	are , and ;
		iScan.useDelimiter("\\s+|(?=[,;])|(?<=[,;])");
		while	(iScan.hasNext())	{
			itemTree.addItem(parseItem(iScan));		
		}
		return itemTree;
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
	 * @throws
	 */
	@SuppressWarnings("finally")
	private ItemNode parseItem(Scanner token) throws ItemListException {
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
		}	catch (ItemListException e)	{
			System.out.println("itemexcp caught in parseItem");
			throw new ItemListException();
		}	finally	{ 
			
			//	TODO ~ this does not handle errors right at all
			//	https://stackoverflow.com/questions/7219963/return-a-value-and-throw-an-exception
			
			//	If this method doesn't return, i.e. due to an ItemListException
			//	being thrown by a lower level parser, it results in a lot of
			//	NullPointerExceptions. As such, the only way I can think to
			//	handle this is to use a finally branch to force the method to
			//	return no matter what.
			return currentItem;
		}
		
	}
	
	/**
	 * parseName is a parse method following the grammar::=
	 * a-zA-Z0-9 ","
	 * 
	 * @param token is any string, ideally representing an item name.
	 * @return the name of the item.
	 */
	private String parseName(Scanner token) throws ItemListException	{
		if	(token.hasNext()) {
			String retVal = token.next();
			//	Evaluate whether the next token is ',' and thus meets the
			//	grammar.
			if	(token.hasNext(","))	{
				token.next();
				return retVal;
			}	
		}
		System.out.println("exception");
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
	private LocalDate parseBestBefore(Scanner token)	throws ItemListException	{
		if	(token.hasNextInt()) {
			//	Gets DD
			int day = token.nextInt();
			if	(token.hasNextInt())	{
				//	Gets MM
				int month = token.nextInt();
				if	(token.hasNextInt())	{
					//	Gets YYYY
					int year = token.nextInt();
					if	(token.hasNext(","))	{
						token.next();
						return LocalDate.of(year, month, day);
					}
				}
			}
		}
		//	Tokens do not conform to grammar rules.
		throw new ItemListException();
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
		if (token.hasNext()) {
			String catName = token.next();
			if	(token.hasNext(";"))	{
				token.next();
				return catName;
			}
		}
		//	Tokens do not conform to grammar rules.
		throw new ItemListException();
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
			return this.items;
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
				output.concat(item.toString());
			}
			return output;
		}
	}
	
	class ItemNode	{
		
		private String itemName = "null";
		private Quantity amount = null;
		private LocalDate bestBefore= null;
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
		public void setDate(LocalDate bb)	{this.bestBefore = bb;}
		
		/**
		 * getDate is a getter method for accessing the best before date of an
		 * item.
		 * 
		 * @return the best before date of this item.
		 */
		public LocalDate getDate()	{return this.bestBefore;}
		
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
		
		@Override
		public String toString()	{
			return this.itemName + ", " + this.amount.toString() + 
					this.bestBefore.getDayOfMonth() + " " + this.bestBefore.getMonthValue() +
					" " + this.bestBefore.getYear() + ", " + this.category + "; ";
		}
	}
}
