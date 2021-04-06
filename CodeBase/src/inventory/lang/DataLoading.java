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

	private final String expectedInvSchema = 				//	Valid schema for
			"category	itemName	size	bestBefore";	//	inv files
	private final int invSchemaCount = 4;	//	Amount of cols in a valid inv 
											//	file schema
	private boolean schemaChecked = false;	//	Flag if the inv files schema has
											//	been checked
	
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
			ArrayList<Item> file = createItems(invenReader);
		}	catch	(IOException e) {
			System.out.println("Exception thrown in DataLoading");
			e.printStackTrace();
		}
	}
	
	/**
	 * Method converts an inventory file into a list of usable Item objects
	 * This method throws an IOException when an invalid file schema is 
	 * encountered
	 * @param fileRead
	 * @return
	 */
	private ArrayList<Item> createItems(FileReader fileRead) throws IOException	{
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
					Item temp = lineToItem(tempStr);
					if	(!temp.getCat().equals("-1") && temp.getBB().getValidFlag())	
						returnArr.add(temp);	
				}
			}
		}	catch(IOException e)	{
			System.out.println("Error occured in dataLoading.createItems(FileReader)");
			e.printStackTrace();
		}
		System.out.println(returnArr);
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
		ArrayList<String> info = new ArrayList<String>();
		Scanner lineScanner = new Scanner(tempStr);
		//	File fields are delimited via tab chars
		lineScanner.useDelimiter("\\t");
		
		//	Reads the current line into a string array, which is used to create
		//	the final object. If the row elements exceeds schema defined amount
		//	returns meangingless value for later sanitization

		while	(lineScanner.hasNext()) {
			info.add(lineScanner.next());
		}
		lineScanner.close();
		
		//	Generates return item
		if	(info.size() != this.invSchemaCount)	{
			return new Item("-1", "", "", "");
		}	else	{
			return new Item(info.get(0), info.get(1), info.get(2), info.get(3));
		}
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
