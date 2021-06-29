package inventory.lang;

public class SolidQuan implements Quantity {

	private String prefix = "KG";
	private Double amount = 0.0;
	
	/**
	 * Constructor for creating an object of type SolidQuan.
	 * 
	 * @param amount is the weight this object should be set to.
	 */
	public SolidQuan(Double amount)	{
		this.amount = amount;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public Double getAmount() {
		return this.amount;
	}
	
	@Override
	public String toString()	{
		return prefix + " " + amount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
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
		SolidQuan other = (SolidQuan) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		return true;
	}

}
