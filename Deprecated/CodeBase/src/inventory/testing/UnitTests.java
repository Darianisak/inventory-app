package inventory.testing;
import inventory.lang.DataLoading;
import inventory.customObjects.Date;
import inventory.customObjects.Item;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.*;
import java.util.*;

/**
 * Test class for performing automatic unit tests of the program
 * @author darianisak
 *
 */
public class UnitTests {
	
	/**
	 * DataLoading.validSchema() Tests
	 */
	
	@Test
	public void test01_validSchema()	{
		String expected = "testA  testB  testC  testD";
		DataLoading testObj = new DataLoading();
		assertTrue(testObj.test_accessSchemaCheck(expected, expected));
	}
	@Test
	public void test02_invalidSchema()	{
		String expected = "testA  testB  testC  testD";
		String actual = "testB  testC  testD  testA";
		DataLoading testObj = new DataLoading();
		assertFalse(testObj.test_accessSchemaCheck(actual, expected));
	}
	@Test
	public void test03_invalidSchema_Length()	{
		String expected = "testA  testB  testC  testD";
		String actual = "testB  testC";
		DataLoading testObj = new DataLoading();
		assertFalse(testObj.test_accessSchemaCheck(actual, expected));
	}
	
	/**
	 * Date.compareTo() Tests
	 * A return of 0 means the two dates are
	 * equal, a return of -1 means this is sooner than o, while a return of
	 * 1 means that o is sooner than this
	 * A sooner date will have a lower number
	 */
	
	@Test
	public void test04_compareTo_soonerYea()	{
		Date later = new Date(2, 4, 2022);
		Date sooner = new Date(2, 4, 2021);
		int returnVal = sooner.compareTo(later);
		assertTrue(returnVal == -1);
	}
	@Test
	public void test05_compareTo_laterYear()	{
		Date later = new Date(2, 4, 2022);
		Date sooner = new Date(2, 4, 2021);
		int returnVal = later.compareTo(sooner);
		assertTrue(returnVal == 1);
	}
	@Test
	public void test06_compareTo_sameDate()	{
		Date one = new Date(2, 4, 2022);
		Date two = new Date(2, 4, 2022);
		int returnVal = one.compareTo(two);
		assertTrue(returnVal == 0);
	}
	@Test
	public void test07_compareTo_soonerMonth()	{
		Date later = new Date(2, 4, 2021);
		Date sooner = new Date(2, 3, 2021);
		int returnVal = sooner.compareTo(later);
		assertTrue(returnVal == -1);
	}
	@Test
	public void test08_compareTo_laterMonth()	{
		Date later = new Date(2, 4, 2021);
		Date sooner = new Date(2, 3, 2021);
		int returnVal = later.compareTo(sooner);
		assertTrue(returnVal == 1);
	}
	@Test
	public void test09_compareTo_soonerDay()	{
		Date later = new Date(3, 3, 2021);
		Date sooner = new Date(2, 3, 2021);
		int returnVal = sooner.compareTo(later);
		assertTrue(returnVal == -1);
	}
	@Test
	public void test10_compareTo_laterDay()	{
		Date later = new Date(3, 3, 2021);
		Date sooner = new Date(2, 3, 2021);
		int returnVal = later.compareTo(sooner);
		assertTrue(returnVal == 1);
	}
	@Test
	public void test42_compareTo_wildCardSooner_years()	{
		Date later = new Date(31, 10, 2050);
		Date sooner = new Date(31, 5, 2000);
		int returnVal = sooner.compareTo(later);
		assertTrue(returnVal == -1);
	}
	@Test
	public void test11_compareTo_wildCardLater_years()	{
		Date later = new Date(31, 10, 2050);
		Date sooner = new Date(31, 5, 2000);
		int returnVal = later.compareTo(sooner);
		assertTrue(returnVal == 1);
	}
	@Test
	public void test12_compareTo_wildCardSooner_months()	{
		Date later = new Date(31, 10, 2021);
		Date sooner = new Date(31, 5, 2021);
		int returnVal = sooner.compareTo(later);
		assertTrue(returnVal == -1);
	}
	@Test
	public void test13_compareTo_wildCardLater_months()	{
		Date later = new Date(31, 10, 2021);
		Date sooner = new Date(31, 5, 2021);
		int returnVal = later.compareTo(sooner);
		assertTrue(returnVal == 1);
	}
	@Test
	public void test14_compareTo_wildCardSooner_days()	{
		Date later = new Date(29, 5, 2021);
		Date sooner = new Date(16, 5, 2021);
		int returnVal = sooner.compareTo(later);
		assertTrue(returnVal == -1);
	}
	@Test
	public void test15_compareTo_wildCardLater_days()	{
		Date later = new Date(29, 5, 2021);
		Date sooner = new Date(16, 5, 2021);
		int returnVal = later.compareTo(sooner);
		assertTrue(returnVal == 1);
	}
	
	//	Date.isLeapYear() Tests
	
	@Test
	public void test16_isLeapYear_valid()	{
		Date dateObj = new Date(1, 1, 1);
		assertTrue(dateObj.isLeapYear(2020));
		assertTrue(dateObj.isLeapYear(2024));
		assertTrue(dateObj.isLeapYear(2028));
		assertTrue(dateObj.isLeapYear(2032));
	}
	@Test
	public void test17_isLeapYear_invalid()	{
		Date dateObj = new Date(1, 1, 1);
		assertFalse(dateObj.isLeapYear(2021));
		assertFalse(dateObj.isLeapYear(2022));
		assertFalse(dateObj.isLeapYear(2023));
		assertFalse(dateObj.isLeapYear(2027));
	}
	
	//	Date.validDateMonth() Tests
	
	@Test
	public void test18_validDateMonth_validNonLeap()	{
		Date dateObj = new Date(28, 2, 2021);
		assertTrue(dateObj.validGetter());
	}
	@Test
	public void test19_validDateMonth_validNonLeap02()	{
		Date dateObj= new Date(13, 12, 2021);
		assertTrue(dateObj.validGetter());
	}
	@Test
	public void test20_validDateMonth_nonValidNonLeap()	{
		Date dateObj = new Date(29, 2, 2021);
		assertFalse(dateObj.validGetter());
	}
	@Test
	public void test21_validDateMonth_nonValidNonLeap02()	{
		Date dateObj = new Date(32, 13, 2021);
		assertFalse(dateObj.validGetter());
	}
	@Test
	public void test22_validDateMonth_validLeap()	{
		Date dateObj = new Date(29, 2, 2020);
		assertTrue(dateObj.validGetter());
	}
	@Test
	public void test23_validDateMonth_validLeap02()	{
		Date dateObj = new Date(1, 3, 2020);
		assertTrue(dateObj.validGetter());
	}
	@Test
	public void test24_validDateMonth_nonValidLeap()	{
		Date dateObj = new Date(30, 2, 2020);
		assertFalse(dateObj.validGetter());
	}
	@Test
	public void test25_validDateMonth_nonValidLeap02()	{
		Date dateObj = new Date(32, 13, 2020);
		assertFalse(dateObj.validGetter());
	}
	@Test
	public void test26_validDateMonth_negDay()	{
		Date dateObj = new Date(-1, 1, 2021);
		assertFalse(dateObj.validGetter());
	}
	@Test
	public void test27_validDateMonth_negMonth()	{
		Date dateObj = new Date(1, -1, 2021);
		assertFalse(dateObj.validGetter());
	}
	@Test
	public void test28_validDateMonth_negYear()	{
		Date dateObj = new Date(1, 1, -2021);
		assertFalse(dateObj.validGetter());
	}
	
	//	Date.splitDate() Tests
	
	@Test
	public void test29_splitDate_valid()	{
		Date dateObj = new Date(1, 2, 3);
		ArrayList<String> retVal = new ArrayList<String>(dateObj.splitGetter());
		assertTrue(retVal.size() == 3);
		assertTrue(retVal.get(0).equals("1"));
		assertTrue(retVal.get(1).equals("2"));
		assertTrue(retVal.get(2).equals("3"));
		assertTrue(dateObj.getValidFlag());
	}
	@Test
	public void test30_splitDate_invalid_greaterThan()	{
		Date dateObj = new Date("1 2 3 4", 1, 2, 3);
		ArrayList<String> retVal = new ArrayList<String>(dateObj.splitGetter());
		assertTrue(retVal.size() == 4);
		assertFalse(dateObj.getValidFlag());
	}
	@Test
	public void test31_splitDate_invalid_lessThan()	{
		Date dateObj = new Date("1 2", 1, 2, 3);
		ArrayList<String> retVal = new ArrayList<String>(dateObj.splitGetter());
		assertTrue(retVal.size() == 2);
		assertFalse(dateObj.getValidFlag());
	}
	@Test
	public void test32_splitDate_invalid_miscChars()	{
		//	This program uses the approach of whitelist over blacklist and 
		//	therefore if a date field does not match the expected type format
		//	it will be flagged as invalid
		Date dateObj = new Date("1,& 2 3", 1, 2, 3);
		ArrayList<String> retVal = new ArrayList<String>(dateObj.splitGetter());
		assertTrue(retVal.size() == 3);
		assertFalse(dateObj.getValidFlag());
	}
	
	//	Date.splitDateValidation() Tests
	
	@Test
	public void test33_splitDateValidation_valid()	{
		Date dateObj = new Date(1, 1, 1);
		ArrayList<String> input = new ArrayList<String>();
		input.add("1");
		input.add("2");
		input.add("3");
		assertTrue(dateObj.splitValidGetter(input));
	}
	@Test
	public void test34_splitDateValidation_invalid()	{
		Date dateObj = new Date(1, 1, 1);
		ArrayList<String> input = new ArrayList<String>();
		input.add("1d&^*");
		input.add("2");
		input.add("3");
		assertFalse(dateObj.splitValidGetter(input));

	}
	
	//	Date Class Integration Tests
	
	@Test
	public void test35_DateIntegration_nonLeapValid()	{
		Date dateObj = new Date("28 02 2021");
		assertTrue(dateObj.getValidFlag());
	}
	@Test
	public void test36_DateIntegration_LeapValid()	{
		Date dateObj = new Date("29 02 2020");
		assertTrue(dateObj.getValidFlag());
	}
	@Test
	public void test37_DateIntegration_nonLeapInvalid()	{
		Date dateObj = new Date("29 02 2021");
		assertFalse(dateObj.getValidFlag());
	}
	@Test
	public void test38_DateIntegration_LeapInvalid()	{
		Date dateObj = new Date("30 02 2020");
		assertFalse(dateObj.getValidFlag());
	}
	@Test
	public void test39_DateIntegration_wildCardInvalid01()	{
		Date dateObj = new Date("01&*/ 04 2021");
		assertFalse(dateObj.getValidFlag());
	}
	@Test
	public void test40_DateIntegration_wildCardInvalid02()	{
		Date dateObj = new Date("01f 04l 2021");
		assertFalse(dateObj.getValidFlag());
	}
	@Test
	public void test41_DateIntegration_MMDDYYYY_valid()	{
		Date dateObj = new Date("12 25 2021");
		assertTrue(dateObj.getValidFlag());
	}
	@Test
	public void test43_DateIntegration_MMDDYYYY_validLeap()	{
		//	Test42 is by test10
		Date dateObj = new Date("02 29 2020");
		assertTrue(dateObj.getValidFlag());
	}
	
	//	DataLoading.lineToItem() Tests
	
	@Test
	public void test44_lineToItem_validSize()	{
		String temp = "test1	test2	test3	1 1 1";
		DataLoading loadObj = new DataLoading();
		Item tempItem = loadObj.lineToItemGetter(temp);
		assertTrue(tempItem.getCat().equals("test1"));
	}
	@Test
	public void test45_lineToItem_invalidSizeGreater()	{
		String temp = "test1	test2	test3	1 1 1	test5";
		DataLoading loadObj = new DataLoading();
		Item tempItem = loadObj.lineToItemGetter(temp);
		assertTrue(tempItem.getCat().equals("-1"));
	}
	@Test
	public void test46_lineToItem_invalidDelim()	{
		String temp = "Test1 test2 test3 1 1 1";
		DataLoading loadObj = new DataLoading();
		Item tempItem = loadObj.lineToItemGetter(temp);
		assertTrue(tempItem.getCat().equals("-1"));
	}
	
	//	DataLoading Integration Tests - These tests rely on repository files
	//	within the test files folder
	
	@Test
	public void test47_DataLoading_NoSchemaOrElements()	{
		File testFile = new File("/Users/darianculver/Documents/GitHub/inventory-app/TestFiles/No_Schema_Or_Elements.txt");
		DataLoading dataObj = new DataLoading(testFile);
		Item tempItem = new Item("Test1", "Test2", "Test3", "1 1 1");
		ArrayList<Item> checkArr = new ArrayList<Item>();
		try	{
			checkArr = dataObj.getFileContents();
		}	catch	(NullPointerException e) {
			checkArr.add(tempItem);
		}
		assertTrue(checkArr.size() == 1);
	}
	@Test
	public void test48_DataLoading_NoSchemaThreeElements()	{
		File testFile = new File("/Users/darianculver/Documents/GitHub/inventory-app/TestFiles/No_Schema_Three_Elements.txt");
		DataLoading dataObj = new DataLoading(testFile);
		Item tempItem = new Item("Test1", "Test2", "Test3", "1 1 1");
		ArrayList<Item> checkArr = new ArrayList<Item>();
		try	{
			checkArr = dataObj.getFileContents();
		}	catch	(NullPointerException e) {
			checkArr.add(tempItem);
		}
		assertTrue(checkArr.size() == 1);
	}
	@Test
	public void test49_DataLoading_EightValidElements()	{
		File testFile = new File("/Users/darianculver/Documents/GitHub/inventory-app/TestFiles/Schema_Eight_Elements.txt");
		DataLoading dataObj = new DataLoading(testFile);
		Item tempItem = new Item("Test1", "Test2", "Test3", "1 1 1");
		ArrayList<Item> checkArr = new ArrayList<Item>();
		try	{
			checkArr = dataObj.getFileContents();
		}	catch	(NullPointerException e) {
			checkArr.add(tempItem);
		}
		assertTrue(checkArr.size() == 8);
		assertTrue(checkArr.get(0).getBB().equals(new Date("1 1 1")));
		assertTrue(checkArr.get(1).getBB().equals(new Date("2 2 2")));
		assertTrue(checkArr.get(2).getBB().equals(new Date("3 3 3")));
		assertTrue(checkArr.get(3).getBB().equals(new Date("28 02 2021")));
		assertTrue(checkArr.get(4).getBB().equals(new Date("29 02 2020")));
		assertTrue(checkArr.get(5).getBB().equals(new Date("11 20 2020")));
		assertTrue(checkArr.get(6).getBB().equals(new Date("12 20 2020")));
		assertTrue(checkArr.get(7).getBB().equals(new Date("02 29 2020")));
	}
	@Test
	public void test50_DataLoading_SchemaNoElements()	{
		File testFile = new File("/Users/darianculver/Documents/GitHub/inventory-app/TestFiles/Schema_No_Elements.txt");
		DataLoading dataObj = new DataLoading(testFile);
		Item tempItem = new Item("Test1", "Test2", "Test3", "1 1 1");
		ArrayList<Item> checkArr = new ArrayList<Item>();
		try	{
			checkArr = dataObj.getFileContents();
		}	catch	(NullPointerException e) {
			checkArr.add(tempItem);
		}
		assertTrue(checkArr.size() == 1);
	}
	@Test
	public void test51_DataLoading_SchemaThreeElements()	{
		File testFile = new File("/Users/darianculver/Documents/GitHub/inventory-app/TestFiles/Schema_Three_elements.txt");
		DataLoading dataObj = new DataLoading(testFile);
		Item tempItem = new Item("Test1", "Test2", "Test3", "1 1 1");
		ArrayList<Item> checkArr = new ArrayList<Item>();
		try	{
			checkArr = dataObj.getFileContents();
		}	catch	(NullPointerException e) {
			checkArr.add(tempItem);
		}
		assertTrue(checkArr.size() == 3);
		assertTrue(checkArr.get(0).getBB().equals(new Date("1 1 1")));
		assertTrue(checkArr.get(1).getBB().equals(new Date("2 2 2")));
		assertTrue(checkArr.get(2).getBB().equals(new Date("3 3 3")));
	}
}
