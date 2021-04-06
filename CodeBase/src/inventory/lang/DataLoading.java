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
	private boolean schemaChecked = false;
	
	/**
	 * 'Main' method for dataLoading
	 * @param invenFile
	 */
	public DataLoading(File invenFile)	{
		FileReader readObj = createReader(invenFile);
		if	(readObj == null) throw new NullPointerException();
		else	doLoading(readObj);
	}
	
	/**
	 * Adds more testing control to process of loading
	 * @param reader
	 */
	private void doLoading(FileReader reader)	{
		try {
			createItems(reader);
		} catch (IOException e) {
			System.out.println("An IOException was thrown; possibly invalid" +
							" file schema used");
			e.printStackTrace();
		}	
	}
	
	/**
	 * Getter for generating a FileReader Object
	 * @param inputFile
	 * @return
	 */
	private FileReader createReader(File inputFile)	{
		try	(FileReader retReader = new FileReader(inputFile);){
			return retReader;
		}	catch (IOException e) {
			System.out.println("Error occured in dataLoading.createReader(File)");
			e.printStackTrace();
		}
		return null;
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
		try	(BufferedReader readObj = new BufferedReader(fileRead)){
			String tempStr = "";
			//	Assignment statement mixed with a conditional
			while	((tempStr = readObj.readLine()) != null)	{
				//	Checks file schema is valid, else branch is standard run
				//	TODO refactor this, no field should be needed
				if (!this.schemaChecked)	{
					schemaCheck(tempStr, this.expectedInvSchema);
					//	Checks if it was valid, else throws IOException
					if	(this.schemaChecked) continue;
					else	throw new IOException();
				}	else	{
					
				}
			}
			
			
		}	catch(IOException e)	{
			System.out.println("Error occured in dataLoading.createItems(FileReader)");
			e.printStackTrace();
		}
		return new ArrayList<Item>();
	}
	
	/**
	 * Checks that the provided files schema is valid
	 * @param schema
	 * @return
	 * @throws IOException 
	 */
	private boolean schemaCheck(String schema, String expected) {
		if	(schema.equals(expected))	{
			this.schemaChecked = true;
			return true;
		}
		else {
			System.out.println("Invalid file schema IOException");
			return false;	
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
