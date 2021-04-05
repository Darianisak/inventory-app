package inventory.customObjects;

import java.util.Calendar;

/**
 * @author darianisak
 * Class represents an object found within the pantry
 *	TODO make this generic or something
 */
public class item {

	private String category;
	private String itemName;
	private String quantity;
	private Calendar bestBefore;
	
	/**
	 * Constructor method for pantry/fridge item
	 * @param cat
	 * @param name
	 * @param amount
	 * @param bBefore
	 */
	public item(String cat, String name, String amount, Calendar bBefore) {
		this.category = cat;
		this.itemName = name;
		this.quantity = amount;
		this.bestBefore = bBefore;
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
		item other = (item) obj;
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
