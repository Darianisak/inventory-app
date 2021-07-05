package inventory.lang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import inventory.lang.Parser.ItemNode;

public class WriteOut {
	
	private String errorStatus = "nE";
	
	/**
	 * mainWrite is essentially the 'main' method of the WriteOut class and is
	 * used to make ordered calls to the other writing and file generation
	 * methods.
	 * 
	 * @param toWrite is the tree of pantry items that the user has loaded into
	 * the application and edited.
	 * @param fileName is what the user would like the output file to be named.
	 * This implementation must use unique file names as it cannot overwrite.
	 */
	public void mainWrite(ItemProgramNode toWrite, String fileName) {
		if	(fileName == "")	fileName = "$01";
		File temp = createNew(fileName);
		if	(temp != null)	{
			//	Branch where no errors were signalled during the operation of
			//	createNew().
			writer(temp, toWrite, fileName);
			return;
		}
		//	Branch where for one reason or another an error was flagged and the
		//	process shouldn't be pursued further in this run.
		return;
	}
	
	/**
	 * createNew is a helper method used for generating user specific output
	 * files for the purpose of writing the ItemProgramNode tree out to a new
	 * file.
	 * 
	 * @param fileName is what the user would like the output file to be named.
	 * This implementation must use unique file names as it cannot overwrite.
	 * @return the file to be used by other methods in this class, OR null if
	 * an error was flagged at any point.
	 */
	public File createNew(String fileName)	{
		try	{
			File fileObj = new File(fileName + ".txt");
			if	(fileObj.createNewFile()) {
				//	Successful file creation branch.
				this.errorStatus = "nE";
				return fileObj;
			}	else	{
				//	Branch where the specified file name already exists within
				//	the directory. This shouldn't occur owing to the use of the
				//	user def names, but it doesn't hurt to flag it if it does.
				//	"fAE" ~ file already exists.
				this.errorStatus = "fAE";
				return null;
			}
		}	catch	(IOException e)	{
			//	Branch where some exception has caused file creation to fail.
			//	"fC" ~ file creation error.
			this.errorStatus = "fC";
			return null;
		}
	}
	
	/**
	 * writer is the core method in creating output files. Using the filename
	 * specified by the user, this method takes the ItemProgramNode found in
	 * src and outputs it to the dest file.
	 * 
	 * @param dest is the created file that we are writing src out to.
	 * @param src is the ItemProgramNode to write to the file.
	 * @param fileName is what the user would like the output file to be named.
	 * This implementation must use unique file names as it cannot overwrite.
	 */
	public void writer(File dest, ItemProgramNode src, String fileName)	{
		try	{
			//	File writing step.
			FileWriter writeObj = new FileWriter(fileName + ".txt");			
			writeObj.write(stringify(src.getItems()));
			writeObj.close();
			
			//	Validation step.
			Parser validation = new Parser();
			try	{
				validation.topLevelParser(dest);
			}	catch	(FileNotFoundException e)	{
				//	Branch where the file specified is not valid, somehow.
				this.errorStatus = "fNF";
				return;
			}
			String validationCode = validation.getErrorCode();
			
			if	(validationCode != this.errorStatus && validationCode != "eS")	{					
				//	If this branch is entered, it means that a parser error
				//	has occured, in which case the output file will not be
				//	valid. As such, the dest file may as well be deleted.
				
				//	In theory, this branch should never be entered, which is
				//	why I don't test for it. This is because the input of this
				//	class is always assumed to be safe; a tree can not be
				//	submitted for saving without first having been verified as
				//	without parser errors.
				
				this.errorStatus = validationCode;
				if	(dest.delete())	return;
				else	throw new RuntimeException("Failure to delete invalid"
						+ " file in WriteOut.writer()");
			}	else	{
				//	If this branch is entered, than there should be no problems
				//	though code assignment is still performed incase of eS / nE
				this.errorStatus = validationCode;
				return;
			}
		}	catch	(IOException e)	{
			//	Branch where file writing failed due to an IOException being
			//	thrown. "fW" ~ file write error.
			this.errorStatus = "fW";
			return;
		}
	}

	/**
	 * getErrorCode is a getter method for determining the point at which the
	 * writer failed EARLIEST.
	 * 
	 * @return "nE" - no Error; "fAE" - file name already exists; "fC" - file
	 * creation error; "fNF" - file not found error.
	 * 
	 * Further error codes are not thrown by WriteOut.class and
	 * are instead generated during the validation step. For these error codes,
	 * see Parser.getErrorCode()
	 */
	public String getErrorCode()	{
		return this.errorStatus;
	}

	/**
	 * 
	 * @param items
	 * @return
	 */
	public String stringify(ArrayList<ItemNode> items)	{
		if	(this.errorStatus == "eS" || items.get(0) == null) return "";	
		else	{
			String output = "";
			for	(ItemNode item : items) {
				output = output.concat(item.toString());
			}
			return output;
		}
	}
}
