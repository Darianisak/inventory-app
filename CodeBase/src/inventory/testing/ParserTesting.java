package inventory.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;
import java.util.Scanner;

import inventory.lang.Parser;
import inventory.lang.ItemListException;
import inventory.lang.ItemProgramNode;

public class ParserTesting {
	
	@Test
	public void test01()	{
		//	Tests valid, single item.
		String testString = "name, KG 0.1, 01 04 2020, Dry;";
		Scanner tokens = new Scanner(testString);
		ItemProgramNode tree = null;
		Boolean threwExcp = false;
		try	{
			tree = new Parser().parseInventory(tokens);
		}	catch	(ItemListException e) {
			threwExcp = true;
		}
		assertFalse(threwExcp);
		assertEquals(1, tree.getItems().size());
	}
	
	@Test
	public void test02()	{
		//	Tests valid, double line.
		String testString = "name, KG 0.1, 01 04 2020, Dry;"
						  + "name, L  0.2, 01 04 2020, Wet;";
		Scanner tokens = new Scanner(testString);
		ItemProgramNode tree = null;
		Boolean threwExcp = false;
		try	{
			tree = new Parser().parseInventory(tokens);
		}	catch	(ItemListException e) {
			threwExcp = true;
		}
		assertFalse(threwExcp);
		assertEquals(2, tree.getItems().size());
	}
	
	@Test
	public void test03() {
		//	Tests invalid, no items, invalid at name.
		String testString1 = "name; KG";
		String testString2 = ", KG";
		String testString3 = "name KG";
		ItemProgramNode tree = null;
		Boolean threw = false;
		
		Scanner tokens = new Scanner(testString1);
		try	{
			tree = new Parser().parseInventory(tokens);
		}	catch	(ItemListException e)	{
			System.out.println("itemException caught");
			threw = true;
		}	
		
		
		System.out.println("post tree null check");
		assertTrue(threw);
		assertEquals(0, tree.getItems().size());

	}
	
}
