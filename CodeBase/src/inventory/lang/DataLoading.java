package inventory.lang;
import inventory.customObjects.Item;

import java.io.*;
import java.util.*;

/**
 * 
 * @author darianisak
 *
 */
public class DataLoading {

	private final String expectedInvSchema = 
			"category	itemName	size	bestBefore";
	private final int invSchemaCount = 4;
	private boolean schemaChecked = false;
	
	/**
	 * Constructor method for dataLoading - calls a 'main' method
	 * @param invenFile
	 */
	public DataLoading(File invenFile)	{
		readData(invenFile);
	}
	
	/**
	 * Effectively the main method for the DataLoading class
	 * @param inven
	 */
	private void readData(File inven)	{
		try	(FileReader invenReader = new FileReader(inven))	{
			//	Path of propagation thru which dataLoading operates
			createItems(invenReader);
		}	catch	(IOException e) {
			System.out.println("Exception thrown in DataLoading");
			e.printStackTrace();
		}
	}
	
	//	Buffered reader gets a line
	//	checks schema is valid
	//	line has four elements delimd by tabs
	//	a line creates an item object
	//	objects are returned
	
	/**
	 * Method converts an inventory file into a list of usable Item objects
	 * This method throws an IOException when an invalid file schema is 
	 * encountered
	 * @param fileRead
	 * @return
	 */
	private List<Item> createItems(FileReader fileRead) throws IOException	{
		ArrayList<Item> returnArr = new ArrayList<Item>();
		try	(BufferedReader readObj = new BufferedReader(fileRead))	{
			String tempStr = "";
			//	Assignment statement mixed with a conditional
			while	((tempStr = readObj.readLine()) != null)	{
				//Checks file schema is valid, else branch is standard run
				if (!this.schemaChecked)	{
					this.schemaChecked = schemaCheck(tempStr, this.expectedInvSchema);
					//	Checks if it was valid, else throws IOException
					if	(!this.schemaChecked) throw new IOException();
				}	else	{
					//	First operation is line two
					lineToItem(tempStr);
				}
			}
		}	catch(IOException e)	{
			System.out.println("Error occured in dataLoading.createItems(FileReader)");
			e.printStackTrace();
		}
		
		
		return returnArr;
	}
	
	/**
	 * Checks that the provided files schema is valid
	 * @param schema
	 * @return
	 * @throws IOException 
	 */
	private boolean schemaCheck(String schema, String expected) {
		if	(schema.equals(expected))	{
			return true;
		}
		else {
			System.out.println("Invalid file schema IOException");
			return false;	
		}
	}
	
	/**
	 * Creates an Item object from a given file string
	 * If a line has more values than this.invSchemaCount
	 * returns a predefined meaningless Item value which can be cleared
	 * from the data after the fact
	 * @param tempStr
	 * @return
	 */
	private Item lineToItem(String tempStr)	{
		//	Local vars
		int counter = 0;
		String[] values = {" "};
		Scanner lineScanner = new Scanner(tempStr);
		lineScanner.useDelimiter("\\t");
		//	Reads the current line into a string array, which is used to create
		//	the final object. If the row elements exceeds schema defined amount
		//	returns meangingless value for later sanitization
		while	(lineScanner.hasNext()) {
			if	(counter == this.invSchemaCount) 
				return new Item("-1", "", "", "");
			System.out.println("line: " + lineScanner.next());
			values[counter] = lineScanner.next();
			counter++;
		}
		return new Item("-1", "", "", "");
		
	}
	
	//**  **  **  **  **//
	
	//	Following code is an assortment of getters used for JUnit tests
	//	This is not to be used for any purpose besides testing
	
	/**
	 * Polymoprphic constructor used for testing
	 */
	public DataLoading()	{
		System.out.println("This constructor is for testing purposes only");
	}
	
	public boolean test_accessSchemaCheck(String actual, String valid)	{
		return schemaCheck(actual, valid);	
	}
	
}
