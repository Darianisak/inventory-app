package inventory.lang;

public interface Quantity {
	
	/**
	 * getter for returning the unit of this item. Could be KG or L depending
	 * on specific implementation.
	 * 
	 * @return this items weight postfix/prefix/
	 */
	public String getPrefix();
	
	/**
	 * setter for the quantity value of this item.
	 * 
	 * @param amount the item weighs or has.
	 */
	public void setAmount(Double amount);
	
	/**
	 * getter for an items weight amount.
	 * 
	 * @return the Double representation of the stored weight for this object.
	 */
	public Double getAmount();

}
