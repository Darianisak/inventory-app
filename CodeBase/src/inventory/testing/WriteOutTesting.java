package inventory.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import inventory.lang.Parser;
import inventory.lang.ItemProgramNode;
import inventory.lang.WriteOut;

/**
 * This testing class is tricky. Simply running JUnit does not verify whether
 * it is working properly or not, instead you have to run it in coverage mode
 * and check that all assertions are highlighted green. I'm not sure why this
 * is the case, but I think it has something to do with file handling.
 * 
 * @author darianisak
 *
 */
public class WriteOutTesting {

	@SuppressWarnings("finally")
	@Test
	public void test01()	{
		//	Tests that createNew functions as expected.
		String fName = "$testFile";
		WriteOut writeObj = new WriteOut();
		
		File temp = writeObj.createNew(fName);
		File temp2 = null;
		try	{
			assertEquals("nE", writeObj.getErrorCode());
			assertTrue(temp.exists());
			writeObj.createNew(fName);
			assertEquals("fAE", writeObj.getErrorCode());
			fName = "$testFile2";
			temp2 = writeObj.createNew(fName);
			assertEquals("nE", writeObj.getErrorCode());
			assertTrue(temp2.exists());
		}	finally	{
			//	Ensures that the files are deleted post the tests/
			if	(temp.delete() && temp2.delete())	return;
			else	throw new RuntimeException("Test file deletion failure");
		}
	}
	
	@Test
	public void test02()	{
		//	Tests that stringify works as expected.
		String testString = "muesli, KG 0.5, 16 5 2023, DryFood;"
				  + " banana, KG 1.0, 5 1 2021, Fruit; ";
		String testString2 = "";
		Parser ps = new Parser();
		ItemProgramNode tempTree = ps.parseInventory(new Scanner(testString));
		
		WriteOut writeObj = new WriteOut();
		String temp = writeObj.stringify(tempTree.getItems());
		assertEquals(testString, temp);
		
		tempTree = ps.parseInventory(new Scanner(testString2));
		temp = writeObj.stringify(tempTree.getItems());
		assertEquals(testString2, temp);
	}
	
	@SuppressWarnings("finally")
	@Test
	public void test03()	{
		//	Tests that writer works as expected - Valid data with an 
		//	element full scanner.
		String testString = "muesli, KG 0.5, 16 5 2023, DryFood;"
				  + " banana, KG 1.0, 5 1 2021, Fruit; ";
		
		Parser ps = new Parser();
		ItemProgramNode tempTree = null;
		WriteOut writeObj = new WriteOut();
		
		String fName = "$tempTest0301";
		File temp = writeObj.createNew(fName);
		
		try	{
			//	Valid data ~ scanner with elements.
			tempTree = ps.parseInventory(new Scanner(testString));
			writeObj.writer(temp, tempTree, fName);
			assertTrue(temp.exists());
			assertEquals("nE", writeObj.getErrorCode());
			ItemProgramNode validation = ps.topLevelParser(temp);
			assertEquals(tempTree.getItems().toString(), validation.getItems().toString());
		}	catch	(FileNotFoundException e)	{
			throw new RuntimeException();
		}	finally	{
			//	Ensures that the files are deleted post the tests/
			if	(temp.delete())	return;
			else	throw new RuntimeException("Test file deletion failure");
		}			
	}
	
	@SuppressWarnings("finally")
	@Test
	public void test04()	{
		//	Tests that the writer functions as expected - Valid data with an
		//	empty scanner. Should return error status "eS"
		String testString = "";
		Parser ps = new Parser();
		ItemProgramNode tempTree = null;
		WriteOut writeObj = new WriteOut();
		
		String fName = "$tempTest0401";
		File temp = writeObj.createNew(fName);
		
		try	{
			//	Valid data ~ scanner without any elements.
			tempTree = ps.parseInventory(new Scanner(testString));
			writeObj.writer(temp, tempTree, fName);
			assertTrue(temp.exists());			
			assertEquals("eS", writeObj.getErrorCode());
			ItemProgramNode validation = ps.topLevelParser(temp);
			assertEquals(tempTree.getItems().toString(), validation.getItems().toString());
		}	catch	(FileNotFoundException e)	{
			throw new RuntimeException();
		}	finally	{
			//	Ensures that the files are deleted post the tests/
			if	(temp.delete())	return;
			else	throw new RuntimeException("Test file deletion failure");
		}
	}
}
