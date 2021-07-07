package inventory.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import inventory.lang.Pantry_Sorter;
import inventory.lang.Parser;
import inventory.lang.Parser.ItemNode;
import inventory.lang.ItemProgramNode;

public class Pantry_SorterTesting {

	@Test
	public void test01()	{
		//	Stringify tests
		Pantry_Sorter pS = new Pantry_Sorter();
		
		assertEquals("No error!", pS.stringifyCode("nE"));
		assertEquals("No error, empty file!", pS.stringifyCode("eS"));
		assertEquals("Error on item name!", pS.stringifyCode("pN"));
		assertEquals("Error on item quantity!", pS.stringifyCode("pQ"));
		assertEquals("Error on best before!", pS.stringifyCode("pBB"));
		assertEquals("Error on category!", pS.stringifyCode("pC"));
		try	{
			pS.stringifyCode("ee");
		}	catch	(RuntimeException e)	{
			assertTrue(true);
		}
	}
	
	@Test
	public void test02()	{
		//	AddMap tests
		Parser reader = new Parser();
		String input = "name1, KG 0.1, 1 2 2020, cat;"
					 + "name2, KG 0.2, 02 01 2022, cate;"
		   		     + "name3, L 0.2, 01 04 2024, water;"
		   		     + "name4, KG 0.3, 02 03 2021, carp;";
		
		String expectedString = "[name1, KG 0.1, 1 2 2020, cat;"
				 + " , name2, KG 0.2, 2 1 2022, cate;"
	   		     + " , name3, L 0.2, 1 4 2024, water;"
	   		     + " , name4, KG 0.3, 2 3 2021, carp;"
	   		     + " , name5, KG 2.0, 1 4 2025, testCat; ]";
		
		String toAddInput = "name5, KG 2.0, 1 4 2025, testCat;";
		
		ItemProgramNode tree = null;
		tree = reader.parseInventory(new Scanner(input));
		
		ItemProgramNode toAddTree = null;
		toAddTree = reader.parseInventory(new Scanner(toAddInput));
		
		ArrayList<ItemNode> inputArray = tree.getItems();
		ArrayList<ItemNode> expected = new ArrayList<ItemNode>();
		ArrayList<ItemNode> output = new ArrayList<ItemNode>();
		
		expected.addAll(inputArray);
		expected.add(toAddTree.getItems().get(0));
		
		Pantry_Sorter ps = new Pantry_Sorter();
		
		output = ps.addMap(inputArray, toAddTree.getItems().get(0));
		
		assertEquals(5, output.size());
		assertTrue(expected.equals(output));
		assertEquals("testCat", output.get(4).getCat());
		assertEquals(expectedString, output.toString());
	}
	
	@Test
	public void test03()	{
		//	Item Adj list tests
		Parser reader = new Parser();
		String inputMain = "name1, KG 0.1, 1 2 2020, cat;"
				 + "name1, KG 0.2, 02 01 2022, cate;"
	   		     + "name1, L 0.2, 01 04 2024, water;"
	   		     + "name4, KG 0.3, 02 03 2021, carp;";
		
		String inputSecond = "name5, KG 2.0, 1 4 2025, testCat;"
				 + "name4, KG 0.5, 02 03 2021, carp;";
		
		ItemProgramNode mainTree = null;
		ItemProgramNode secondTree = null;
		mainTree = reader.parseInventory(new Scanner(inputMain));
		secondTree = reader.parseInventory(new Scanner(inputSecond));

		HashMap<String, ArrayList<ItemNode>> output = 
				new HashMap<String, ArrayList<ItemNode>>();
		HashMap<String, ArrayList<ItemNode>> input = 
				new HashMap<String, ArrayList<ItemNode>>();
		
		Pantry_Sorter ps = new Pantry_Sorter();
		
		output = ps.computeAdjList(mainTree.getItems(), input);
		
		//	Pre inputSecond addition
		
		assertEquals(2, output.size());
		assertEquals(3, output.get("name1").size());
		assertEquals(1, output.get("name4").size());
		assertEquals("name1, L 0.2, 1 4 2024, water; ", 
				output.get("name1").get(2).toString());
		assertEquals("name4, KG 0.3, 2 3 2021, carp; ",
				output.get("name4").get(0).toString());
		
		output = ps.computeAdjList(secondTree.getItems(), output);
		
		//	Post inputSecond addition
		
		assertEquals(3, output.size());
		assertEquals(1, output.get("name5").size());
		assertEquals(2, output.get("name4").size());
		assertEquals("name4, KG 0.5, 2 3 2021, carp; ",
				output.get("name4").get(1).toString());	
	}
	
	@Test
	public void test04()	{
		//	Category adj list tests
		Parser reader = new Parser();
		String inputMain = "name1, KG 0.1, 1 2 2020, cate;"
				 + "name1, KG 0.2, 2 1 2022, cate;"
	   		     + "name1, L 0.2, 1 4 2024, water;"
	   		     + "name4, KG 0.3, 2 3 2021, carp;";
		
		String inputSecond = "name5, KG 2.0, 1 4 2025, grey;"
				 + "name4, KG 0.5, 02 03 2021, carp;";
		
		ItemProgramNode mainTree = null;
		ItemProgramNode secondTree = null;
		mainTree = reader.parseInventory(new Scanner(inputMain));
		secondTree = reader.parseInventory(new Scanner(inputSecond));
		
		HashMap<String, ArrayList<ItemNode>> output = 
				new HashMap<String, ArrayList<ItemNode>>();
		HashMap<String, ArrayList<ItemNode>> input = 
				new HashMap<String, ArrayList<ItemNode>>();
		
		Pantry_Sorter ps = new Pantry_Sorter();
		
		output = ps.computeCatAdj(mainTree.getItems(), input);
		
		//	Pre inputSecond addition
		
		assertEquals(3, output.size());
		assertEquals(2, output.get("cate").size());
		assertEquals(1, output.get("carp").size());
		assertEquals(1, output.get("water").size());
		assertEquals("name4, KG 0.3, 2 3 2021, carp; ", 
				output.get("carp").get(0).toString());
		
		output = ps.computeCatAdj(secondTree.getItems(), output);
		
		assertEquals(4, output.size());
		assertEquals(2, output.get("cate").size());
		assertEquals(2, output.get("carp").size());
		assertEquals(1, output.get("water").size());
		assertEquals(1, output.get("grey").size());
		assertEquals("name5, KG 2.0, 1 4 2025, grey; ", 
				output.get("grey").get(0).toString());
	}
	
}
