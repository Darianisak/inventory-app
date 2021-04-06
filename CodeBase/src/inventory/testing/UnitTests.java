package inventory.testing;
import inventory.lang.DataLoading;

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
}
