package inventory.lang;

import java.util.ArrayList;

import inventory.lang.Parser.ItemNode;

public interface ItemProgramNode {
	
	/**
	 * getter for implementations child node list.
	 * 
	 * @return the node list of children.
	 */
	public ArrayList<ItemNode> getItems();
	
}
