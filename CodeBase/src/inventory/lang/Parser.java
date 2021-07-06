package inventory.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Parser	{

	private String errorStatus = "nE";
	
	/**
	 * Top down parse method; transposes a file to a Scanner, to be modified
	 * out of method. When calling this method, make sure it is set up to handle
	 * both FileNotFoundExceptions, which are thrown by the Scanner, and
	 * ItemListExceptions, which can be thrown during most parse methods as a
	 * result of ignoring grammar rules.
	 * 
	 * @param items is the designated file path
	 * @return a complete ABS Tree of the input file
	 * @throws FileNotFoundException when the Scanner fails to accept the
	 * specified file directory.
	 */
	public ItemProgramNode topLevelParser(File items) throws FileNotFoundException {
		Scanner fScan = null;
		fScan = new Scanner(items);
		ItemProgramNode itemTree = parseInventory(fScan);
		fScan.close();
		return itemTree;
	}
	
	/**
	 * parseInventory is responsible for converting an input file stream into
	 * an ABS with many item nodes attached. This method has some quirks that
	 * should be noted for any further implementation, such as:
	 * 
	 * Supplying an empty file, perhaps for pure writing, will not return an
	 * empty ABS tree, and instead will return an ABS with 1 child element
	 * and an errorStatus of eS.
	 * 
	 * If a parser error occurs during runtime, say in the middle of a file, no
	 * further items will be added to the itemList of the tree. Instead, a null
	 * object will be added at position 0 of the list, along with any of 4 error
	 * codes.
	 * 
	 * As such, itemList.size() often can return unexpected results if an error
	 * has occured. For example, an empty file will have a list size of 1, but 
	 * so will a file with only 1 item in it. If the parser fails on the first
	 * item, there will be 2 items within the list.
	 * 
	 * On subsequent calls to the parser during a single process, the error code
	 * will be reset to eS and then changed accordingly. This means that error
	 * reporting for multiple calls of the parser is independant of one another.
	 * 
	 * @param iScan is the active scanner, with no token increment applied.
	 * @return an ItemProgramNode, which is an ABS Tree of all the items from
	 * the selected file.
	 */
	public ItemProgramNode parseInventory(Scanner iScan) {
		ProgramNode itemTree = new ProgramNode();
		this.errorStatus = "eS";
		
		//	Sets a delimitter where the only consecutive tokens allowed
		//	are , and ;
		iScan.useDelimiter("\\s+|(?=[,;])|(?<=[,;])");
		while	(iScan.hasNext())	{
			this.errorStatus = "nE";
			itemTree.addItem(parseItem(iScan));		
			
			//	After each element is added, perform parser validation by
			//	checking the assigned variable of errorStatus. If it returns
			//	'nE' or noError, there was no reported parsing error and the
			//	parser can continue to operate. Errors are signalled by the
			//	insertion of a null element at index 0 of the itemList of this
			//	tree. By reporting the errors this way, we can avoid 'void'
			//	returns that would stem from throwing an exception and not returning
			//	a tree. This just means that whatever out of class method that
			//	has called the Parser class will need to manually check for
			//	invalid parsing. 
			
			if	(!this.errorStatus.equals("nE"))	{
				//	Add the null flag item and break out of the loop
				itemTree.getItems().add(0, null);
				break;
			}
		}
		//	Prevents NullPointers arising from empty files.
		if	(this.errorStatus == "eS") itemTree.addItem(null);
			
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
	 */
	private ItemNode parseItem(Scanner token) {
		ItemNode currentItem = new ItemNode();
		//	Parse itemName
		currentItem.setName(parseName(token));
		//	Parse Quantity
		currentItem.setQuan(parseQuan(token));	
		//	Parse BestBefore
		currentItem.setDate(parseBestBefore(token));
		//	Parse Category
		currentItem.setCat(parseCategory(token)); 

		//	This return should be safe. If parsing has failed for any of the
		//	above aspects, those aspects will be set to null, which is equivalent
		//	to their initialization state anyway.
		return currentItem;
		
	}
	
	/**
	 * parseName is a parse method following the grammar::=
	 * a-zA-Z0-9 ","
	 * 
	 * @param token is any string, ideally representing an item name.
	 * @return the name of the item.
	 */
	private String parseName(Scanner token)	{
		if	(token.hasNext() && !token.hasNext("null")) {
			String retVal = token.next();
			//	Evaluate whether the next token is ',' and thus meets the
			//	grammar.
			if	(token.hasNext(","))	{
				token.next();
				return retVal;
			}	
		}
		//	Tokens do not conform to grammar rules.
		if	(this.errorStatus == "nE")	this.errorStatus = "pN";
		return "null";
		
	}
	
	/**
	 * parseQuan is a parse method following the grammar::=
	 * "KG" | "L" 0-9 ","
	 * 
	 * @param token should be a prefix specifying if solid or liquid weight.
	 * @return a quantity object, constructed according to the prefix token
	 * and the volume token.
	 */
	private Quantity parseQuan(Scanner token)	{
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
		if	(this.errorStatus == "nE")	this.errorStatus = "pQ";
		return null;
	}
	
	/**
	 * parseBestBefore is a parse method following the grammar::=
	 * 1-12 1-31 0-9 ","
	 * 
	 * @param token should be a double, representing the month of the best
	 * before.
	 * @return a java date object, indicating the Best before date of the item.
	 */
	private LocalDate parseBestBefore(Scanner token)	{
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
		if	(this.errorStatus == "nE")	this.errorStatus = "pBB";
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
	private String parseCategory(Scanner token)	{
		if (token.hasNext() && !token.hasNext("null")) {
			String catName = token.next();
			if	(token.hasNext(";"))	{
				token.next();
				return catName;
			}
		}
		//	Tokens do not conform to grammar rules.
		if	(this.errorStatus == "nE")	this.errorStatus = "pC";
		return "null";
	}
	
	/**
	 * getErrorCode is a getter method for determining the point at which the
	 * parser failed EARLIEST.
	 * 
	 * @return "nE" - no Error; "pN" - name parser; "pQ" - quantity parser;
	 * "pBB" - best before parser; "pC" - category parser; "eS" - empty scanner.
	 * eS is only returnable if there was no tokens supplied by the file, i.e. 
	 * an empty file.
	 */
	public String getErrorCode()	{
		return this.errorStatus;
	}
	
	//	SubClasses of ItemProgramNode, used for generating the paser tree
	
	class ProgramNode implements ItemProgramNode	{
		
		private ArrayList<ItemNode> items = new ArrayList<ItemNode>();
		
		@Override
		public ArrayList<ItemNode> getItems()	{
			return this.items;
		}
		
		@Override
		public void addItem(ItemNode itemToAdd)	{
			this.items.add(itemToAdd);
		}
	}
	
	public class ItemNode	{
		
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
			return this.itemName + ", " + this.amount.toString() + ", " +
					this.bestBefore.getDayOfMonth() + " " + this.bestBefore.getMonthValue() +
					" " + this.bestBefore.getYear() + ", " + this.category + "; ";
		}
		
		/**
		 * Boolean method for determining if this item was the result of parser
		 * error and therefore invalid.
		 * 
		 * @return true; this item isn't valid, false; this item is valid.
		 */
		public boolean isNull()	{
			if	(this.itemName == "null" || this.category == "null"
				|| this.amount == null   || this.bestBefore == null)	return true;
			else	return false;
		}

		/**
		 * Eclipse generated method for hashcode handling.
		 * 
		 * @return enclosing parser instance information.
		 */
		private Parser getEnclosingInstance() {
			return Parser.this;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((amount == null) ? 0 : amount.hashCode());
			result = prime * result + ((bestBefore == null) ? 0 : bestBefore.hashCode());
			result = prime * result + ((category == null) ? 0 : category.hashCode());
			result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ItemNode other = (ItemNode) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (amount == null) {
				if (other.amount != null)
					return false;
			} else if (!amount.equals(other.amount))
				return false;
			if (bestBefore == null) {
				if (other.bestBefore != null)
					return false;
			} else if (!bestBefore.equals(other.bestBefore))
				return false;
			if (category == null) {
				if (other.category != null)
					return false;
			} else if (!category.equals(other.category))
				return false;
			if (itemName == null) {
				if (other.itemName != null)
					return false;
			} else if (!itemName.equals(other.itemName))
				return false;
			return true;
		}

		//@Override
		public Integer compareTo(ItemNode i2) {
			//	CompareTo implementation for sorting based on best before
			return this.bestBefore.compareTo(i2.bestBefore);
		}

	}
}
