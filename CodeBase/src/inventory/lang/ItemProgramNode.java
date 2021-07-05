package inventory.lang;

import java.util.ArrayList;

import inventory.lang.Parser.ItemNode;

public interface ItemProgramNode {
	
	/**
	 * getter method for retrieving the children nodes of this ProgramNode.
	 * Children nodes in this case are individual items, specified by the
	 * user as being apart of their pantry etc.
	 * 
	 * @return an ArrayList of ItemNodes, aka child nodes.
	 */
	public ArrayList<ItemNode> getItems();
	
	/**
	 * setter method for adding items/nodes to the ArrayList of items.
	 * 
	 * @param itemToAdd is a child node of ProgramNode
	 */
	public void addItem(ItemNode toAdd);
	
	@Override
	public String toString();
}
