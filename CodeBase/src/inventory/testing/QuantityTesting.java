package inventory.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import inventory.lang.LiquidQuan;
import inventory.lang.Quantity;
import inventory.lang.SolidQuan;

public class QuantityTesting {

	@Test
	public void test01()	{
		//	Tests Solid Quantity
		Quantity solid = new SolidQuan(0.0);
		Quantity solid2 = new SolidQuan(0.1);
		Quantity solid3 = new SolidQuan(null);
		Quantity solid4 = new SolidQuan(null);
		Quantity liquid = new LiquidQuan(0.1);
		SolidQuan nullS = null;
		
		solid.setAmount(0.1);
		assertEquals("KG 0.1", solid.toString());
		assertEquals("KG", solid.getPrefix());
		assertEquals(0.1, solid.getAmount());
		assertEquals(solid, solid2);
		assertNotEquals(null, solid);
		assertNotEquals(solid, null);
		assertEquals(solid, solid);
		assertNotEquals(solid, new Object());
		assertNotEquals(solid3, solid);
		assertNotEquals(solid, solid3);
		assertEquals(solid3, solid4);
		assertNotEquals(nullS, solid);
		assertNotEquals(solid, liquid);
		assertEquals(501222778, solid.hashCode());
		assertEquals(3357, solid3.hashCode());
	}
	
	@Test
	public void test02()	{
		//	Tests Liquid Quantity
		Quantity solid = new LiquidQuan(0.0);
		Quantity solid2 = new LiquidQuan(0.1);
		Quantity solid3 = new LiquidQuan(null);
		Quantity solid4 = new LiquidQuan(null);
		Quantity liquid = new SolidQuan(0.1);
		LiquidQuan nullS = null;
		
		solid.setAmount(0.1);
		assertEquals("L 0.1", solid.toString());
		assertEquals("L", solid.getPrefix());
		assertEquals(0.1, solid.getAmount());
		assertEquals(solid, solid2);
		assertNotEquals(null, solid);
		assertNotEquals(solid, null);
		assertEquals(solid, solid);
		assertNotEquals(solid, new Object());
		assertNotEquals(solid3, solid);
		assertNotEquals(solid, solid3);
		assertEquals(solid3, solid4);
		assertNotEquals(nullS, solid);
		assertNotEquals(solid, liquid);
		assertEquals(501220458, solid.hashCode());
		assertEquals(1037, solid3.hashCode());
	}
	
}
