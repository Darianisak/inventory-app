package inventory.testing;
import inventory.lang.DataLoading;
import inventory.customObjects.Date;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
	public void test10_compareTo_wildCardSooner_years()	{
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
		//Date dateObj = news
	}
	
	
}
