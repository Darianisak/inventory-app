package inventory.lang;

import java.util.HashMap;
import java.util.ArrayList;

import inventory.lang.Parser.ItemNode;

public class PrefixSearch {

	private HashMap<Character, PrefixSearch> children = 
			new HashMap<Character, PrefixSearch>();
	private boolean completeItem = false;
	private ItemNode itemAtNode = null;
	private String errorStatus = "nE";
	
	/**
	 * https://iq.opengenus.org/tries/
	 * @param searchTerm
	 * @param item
	 */
	public void addItem(String word, ItemNode item) {
	    char[] termToAdd = word.toCharArray();
	    PrefixSearch current = this;
	    
	    for	(char term : termToAdd)	{
	    	if	(!current.children.containsKey(term))	{
	    		//	Branch where the key 'term' is not present in current.children
	    		current.children.put(term, new PrefixSearch());
	    		//	Points current to the newly added node
	    		current = current.children.get(term);
	    	}	else	{
	    		//	Branch where the key 'term' is present in current.children
	    		current = current.children.get(term);
	    	}
	    }
	    //	Current is now at the final node, so add Item and toggle the
	    //	completeItem flag
	    current.itemAtNode = item;
	    current.completeItem = true;
	}
	
	/**
	 * 
	 * @param toFind
	 * @return
	 */
	public ArrayList<ItemNode> search(String toFind)	{
		char[] termToFind = toFind.toCharArray();
		PrefixSearch current = this;
		ArrayList<ItemNode> returnList = new ArrayList<ItemNode>();
		
		for	(char term : termToFind)	{
			if	(current.children.keySet().contains(term))	{
				//	Branch where the current char was found - valid so far
				current = current.children.get(term); 
			}	else	{
				//	Branch where the current char was not found - invalid
				return null;
			}
		}
		if	(current.completeItem)	{
			//	Explicit search path:	This branch is taken when toFind is
			//	equal to the full toString of an element, and thus, just
			//	return the found item as an ArrayList with 1 item.
			
			System.out.println("reached complete branch");
			
			returnList.add(itemAtNode);
			return returnList;
		}	else	{
			
			System.out.println("reached pref branch");
			
			returnList.addAll(deeperSearch(current));
			return returnList;
		}
	}
	
	private ArrayList<ItemNode> deeperSearch(PrefixSearch branch)	{
		if	(branch == null) return null;
		ArrayList<ItemNode> returnList = new ArrayList<ItemNode>();
		for	(Character term : branch.children.keySet())	{
			
			System.out.println("term of for: " + term);
			
			if	(branch.children.get(term).completeItem) {
				//	End state branch where nodes are added recursively to the
				//	search results.
				returnList.add(branch.children.get(term).itemAtNode);
				return returnList;
			}	else	{
				
				System.out.println("has gone into else bracnh");
				
				//	Branch where recursive call is made to traverse down the
				//	children tree to get all search nodes.
				returnList.addAll(deeperSearch(branch.children.get(term)));
				return returnList;
			}
		}
		//	Branch where a major error has occurred : TODO write better javadoc here
		this.errorStatus = "sI";
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String getErrorCode()	{
		return this.errorStatus;
	}
}
