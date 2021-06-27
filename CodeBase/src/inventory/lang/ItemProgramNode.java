package inventory.lang;

import java.util.ArrayList;

import inventory.lang.Parser.ItemNode;

/**
 * Interface doesn't need to define any shared functionality, just relations
 * @author darianisak
 *
 */
public interface ItemProgramNode {
	
	public ArrayList<ItemNode> getItems();
	
}
