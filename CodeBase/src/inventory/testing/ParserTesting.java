package inventory.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;
import java.util.Scanner;

import inventory.lang.Parser;
import inventory.lang.Parser.ItemNode;
import inventory.lang.ItemProgramNode;

public class ParserTesting {
	
	@Test
	public void test01()	{
		//	Tests valid, single item.
		String testString = "name, KG 0.1, 01 04 2020, Dry;";
		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString);
		ItemProgramNode tree = null;
		
		tree = reader.parseInventory(tokens);
		
		assertNotEquals(null, tree.getItems().get(0));
		assertEquals(1, tree.getItems().size());
		assertEquals("nE", reader.getErrorCode());
	}
	
	@Test
	public void test02()	{
		//	Tests valid, double line.
		String testString = "name, KG 0.1, 01 04 2020, Dry;"
						  + "name, L  0.2, 01 04 2020, Wet;";
		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString);
		ItemProgramNode tree = null;
		
		tree = reader.parseInventory(tokens);
		
		assertNotEquals(null, tree.getItems().get(0));
		assertEquals(2, tree.getItems().size());
		assertEquals("nE", reader.getErrorCode());
	}
	
	@Test
	public void test03() {
		//	Tests invalid, 1 item, invalid at name.
		String testString1 = "name; KG";
		String testString2 = ", KG";
		String testString3 = "name KG";

		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString1);
		ItemProgramNode tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pN", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString2);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pN", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString3);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pN", reader.getErrorCode());
	}
	
	@Test
	public void test04()	{
		//	Tests invalid, 1 item, invalid at Quantity.
		String testString1 = "name, KG,";
		String testString2 = "name, L,";
		String testString3 = "name, 0.1,";
		String testString4 = "name, 0.1 KG,";
		String testString5 = "name, KG 0.1;";
		String testString6 = "name, L 0.1;";
		String testString7 = "name, L 0.1 01 04 2020,";
		
		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString1);
		ItemProgramNode tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString2);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString3);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString4);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString5);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString6);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString7);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());
	}
	
	@Test
	public void test05()	{
		//	Tests invalid, 1 item, invalid at Best before.
		String testString1 = "name, KG 0.1, 01";
		String testString2 = "name, KG 0.1, 01,";
		String testString3 = "name, KG 0.1, 01 02";
		String testString4 = "name, KG 0.1, 01 02,";
		String testString5 = "name, KG 0.1, 01 02 2021 cat";
		String testString6 = "name, KG 0.1, 01 02 2021; cat";
		
		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString1);
		ItemProgramNode tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pBB", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString2);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pBB", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString3);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pBB", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString4);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pBB", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString5);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pBB", reader.getErrorCode());
		
		reader = new Parser();
		tokens = new Scanner(testString6);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pBB", reader.getErrorCode());	
	}
	
	@Test
	public void test06()	{
		//	Tests invalid, 1 item, invalid at category.
		String testString1 = "name, KG 0.1, 01 02 2021, cat";
		String testString2 = "name, KG 0.1, 01 02 2021, cat,";
		String testString3 = "name, KG 0.1, 01 02 2021, ;";
		
		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString1);
		ItemProgramNode tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pC", reader.getErrorCode());	
		
		reader = new Parser();
		tokens = new Scanner(testString2);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pC", reader.getErrorCode());	
		
		reader = new Parser();
		tokens = new Scanner(testString3);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pC", reader.getErrorCode());	
	}
	
	@Test
	public void test07()	{
		//	Tests wild cards, i.e. parser failure part way through tokens.
		String testString1 = "";
		String testString2 = "name, ;  name, KG 0.1, 01 02 2020, cat;";
		String testString3 = "name, KG 0.1, 01 02 2020, cat;"
						   + "name, KG 0.2, 02 01 2022, cate;"
						   + "name, ;"
						   + "name, KG 0.3, 02 03 2021, carp;";
		
		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString1);
		ItemProgramNode tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(1 , tree.getItems().size());
		assertEquals("eS", reader.getErrorCode());	
		
		reader = new Parser();
		tokens = new Scanner(testString2);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(2 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());	
		
		reader = new Parser();
		tokens = new Scanner(testString3);
		tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(4 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());		
	}
	
	@Test
	public void test08()	{
		//	Tests valid. Check that items are being created properly.
		String testString1 = "name1, KG 0.1, 1 2 2020, cat;"
				   		   + "name2, KG 0.2, 02 01 2022, cate;"
				   		   + "name3, L 0.2, 01 04 2024, water;"
				   		   + "name4, KG 0.3, 02 03 2021, carp;";
		
		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString1);
		ItemProgramNode tree = null;
		tree = reader.parseInventory(tokens);
		
		assertNotEquals(null, tree.getItems().get(0));
		assertEquals(4 , tree.getItems().size());
		assertEquals("nE", reader.getErrorCode());	
		
		//	Tests for item validity
		
		ItemNode item1 = tree.getItems().get(0);
		ItemNode item2 = tree.getItems().get(1);
		ItemNode item3 = tree.getItems().get(2);
		ItemNode item4 = tree.getItems().get(3);
		
		assertEquals("name1", item1.getName());
		assertEquals("name2", item2.getName());
		assertEquals("name3", item3.getName());
		assertEquals("name4", item4.getName());
		
		assertEquals("KG", item1.getQuan().getPrefix());
		assertEquals("KG", item2.getQuan().getPrefix());
		assertEquals("L", item3.getQuan().getPrefix());
		assertEquals("KG", item4.getQuan().getPrefix());
		
		assertEquals(0.1, item1.getQuan().getAmount());
		assertEquals(0.2, item2.getQuan().getAmount());
		assertEquals(0.2, item3.getQuan().getAmount());
		assertEquals(0.3, item4.getQuan().getAmount());
	
		assertEquals("2020-02-01", item1.getDate().toString());
		assertEquals("2022-01-02", item2.getDate().toString());
		assertEquals("2024-04-01", item3.getDate().toString());
		assertEquals("2021-03-02", item4.getDate().toString());
		
		assertEquals("cat", item1.getCat());
		assertEquals("cate", item2.getCat());
		assertEquals("water", item3.getCat());
		assertEquals("carp", item4.getCat());	
		
		assertEquals("name1, KG 0.1, 1 2 2020, cat; ", item1.toString());
	}
	
	@Test
	public void test09()	{
		//	Test invalid. Check that when a parse error occurs, rejects items
		//	are formed as expected.
		String testString1 = "name1, KG 0.1, 01 02 2020, cat;"
						   + "name, ;";
		
		Parser reader = new Parser();
		Scanner tokens = new Scanner(testString1);
		ItemProgramNode tree = null;
		tree = reader.parseInventory(tokens);
		
		assertEquals(null, tree.getItems().get(0));
		assertEquals(3 , tree.getItems().size());
		assertEquals("pQ", reader.getErrorCode());
		
		ItemNode invalid = tree.getItems().get(2);
		
		assertEquals("name", invalid.getName());
		assertEquals(null, invalid.getQuan());
		assertEquals(null, invalid.getDate());
		assertEquals("null", invalid.getCat());
		
		ItemNode valid = tree.getItems().get(1);
		
		assertEquals("name1", valid.getName());
		assertEquals("KG", valid.getQuan().getPrefix());
		assertEquals(0.1, valid.getQuan().getAmount());
		assertEquals("2020-02-01", valid.getDate().toString());
		assertEquals("cat", valid.getCat());
	}
}
