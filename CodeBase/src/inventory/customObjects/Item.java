package inventory.customObjects;

/**
 * @author darianisak
 * Class represents an object found within the pantry
 *	TODO make this generic or something
 */
public class Item {

	private String category;	//	Category of current item
	private String itemName;	//	Current items name
	private String quantity;	//	Amount contained within current item
	private Date bestBefore;	//	Expirey date of current item
	
	/**
	 * Constructor method for pantry/fridge item
	 * @param cat
	 * @param name
	 * @param amount
	 * @param bBefore
	 */
	public Item(String cat, String name, String amount, String bBefore) {
		this.category = cat;
		this.itemName = name;
		this.quantity = amount;
		this.bestBefore = new Date(bBefore);
	}
	
	/**
	 * Getter for accessing an items category. A category of -1 is an
	 * invalid item code
	 * @return
	 */
	public String getCat()	{
		return this.category;
	}
	
	/**
	 * Getter for accessing an items bestBefore date field
	 * @return
	 */
	public Date getBB()	{
		return this.bestBefore;
	}
	
	@Override
	public String toString() {
		return "item [category=" + category + ", itemName=" + itemName + ", quantity=" + quantity + ", bestBefore="
				+ bestBefore + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bestBefore == null) ? 0 : bestBefore.hashCode());
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (bestBefore == null) {
			if (other.bestBefore != null)
				return false;
		} else if (!bestBefore.equals(other.bestBefore))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
	
}
