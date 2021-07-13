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
	 * @return
	 */
	public String getErrorCode()	{
		return this.errorStatus;
	}
}
