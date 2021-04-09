package inventory.customObjects;
import java.util.*;

/**
 * Custom 'calendar' method with simple compareTo implements
 * Allows for the comparison of best before dates of Item objects
 * @author darianisak
 * 
 * I was not aware of a java.util Date class prior to writing this class,
 * All references to Date within this program use this custom implementation,
 * not the java.util version
 */
public class Date implements Comparable<Date>{

	private String textDate;	//	Raw data representing the best before
	private boolean validDate = true;	//	Flag value used to remove invalid
										//	dated items from the overall list
										//	after the fact
	private int year;	//	Year of best before
	private int month;	//	Month of best before
	private int day;	//	Day of best before
	private HashMap<Integer, Integer> monthToDays;	//	Stores a mapping of 
													//	months to the amount
													//	of days within said month
	
	/**
	 * Constructor method for Date Object
	 * @param date
	 */
	public Date(String date)	{
		this.textDate = date;
		computeFields();
	}

	/**
	 * Method that takes plain text date supplied as the best before
	 * of an Item object, and rewrites it as comparable integers
	 * Standard format is DD MM YYYY
	 * The purpose, more or less, of this method is to assign  int values to
	 * the class fields and determine if this a valid date; indicated by toggling
	 * the validDate flag
	 */
	private void computeFields() {
		ArrayList<String> tempList = splitDate();
		//	Prevents compute power being wasted on an already invalid date
		if	(!validDate)	return;
		//	Index 0 - d/m, 1 - d/m, 2 - year
		//	Sets the year value and builds a date month relation map accordingly
		this.year = Integer.parseInt(tempList.get(2));
		monthDayMapping();
		//	Formats / culls data according to order and magnitude of dates
		//	and months
		if	(Integer.parseInt(tempList.get(1)) > 12 &&
			 Integer.parseInt(tempList.get(0)) < 13)	{
			//	If temp[1] is greater than 12 and temp[0] is less than 12, 
			//	switch format to MM DD YYYY
			this.month = Integer.parseInt(tempList.get(0));
			this.day = Integer.parseInt(tempList.get(1));
		}	else if (Integer.parseInt(tempList.get(0)) < 32 &&
					Integer.parseInt(tempList.get(1)) < 13){
				//	If temp[0] is less than 32 and temp[1] is less
				//	than 13, format is DD MM YYYY
				this.month = Integer.parseInt(tempList.get(1));
				this.day = Integer.parseInt(tempList.get(0));
		}	else	{
			//	Fail state where a date can be ruled as invalid without
			//	further processing
			System.out.println("\nInvalid date from initial check. \ntempList[0]: " +
					tempList.get(0) + "\ntempList[1]: " + tempList.get(1) + 
					"\ntempList[2]: " + tempList.get(2) + "\n");
			this.validDate = false;
			return;
		}
		//	Date validation - sets to false if invalid, which flags item for
		//	removal from file structure
		if	(!validDateMonth())	{
			System.out.println("\nInvalid date from further checking. \ntempList[0]: " +
					tempList.get(0) + "\ntempList[1]: " + tempList.get(1) + 
					"\ntempList[2]: " + tempList.get(2) + "\n");
			this.validDate = false;
			return;
		}
	}
	
	/**
	 * Modified version of DataLoading.lineToItem() which deals with
	 * splitting a text based date value into it's component day, month
	 * and year fields.
	 * @return
	 */
	private ArrayList<String> splitDate(){
		ArrayList<String> info = new ArrayList<String>();
		Scanner lineScanner = new Scanner(this.textDate);
		
		while	(lineScanner.hasNext()) {
			info.add(lineScanner.next());
		}
		lineScanner.close();
		if	(info.size() != 3)	this.validDate = false;
		if	(!splitDateValidation(info))	this.validDate = false;
		return info;
	}
	
	/**
	 * This method is problematic no matter how its handled because theres
	 * arguments for the other approach no matter what.
	 * This method checks that the array entries in splitDate are comprised
	 * purely of numerical data and that there are no non expected chars.
	 * Operates on a whitelist principle rather than a blacklist principle
	 */
	private boolean splitDateValidation(ArrayList<String> dateInfo)	{
		for	(String s : dateInfo) {
			//	Converts current array string to an integer, if not strictly int
			//	should throw an exception
			try	{
				Integer.parseInt(s);
			}	catch	(NumberFormatException e)	{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines if the supplied date and month form a valid pairing or not
	 * Can lead to the Item for which this Date object is associated being 
	 * removed via the toggling of the validDate flag
	 * @return
	 */
	private boolean validDateMonth()	{
		//	Filters out negative dates to prevent map null pointers
		if	(this.month < 1 || this.day < 1 || this.year < 1 || this.month > 12 ||
				this.day > 31)	return false;
		//	Conditional checks if the date is less than or equal the amount
		//	of days within the given month. Returns true is so, false otherwise
		if	(this.day <= this.monthToDays.get(this.month)) return true;
		else	return false;
	}
	
	/**
	 * Initialises a map with the amount of days to each map, variable to 
	 * leap year status
	 * https://www.englishclub.com/vocabulary/time-months-of-year.htm
	 */
	private void monthDayMapping()	{
		this.monthToDays = new HashMap<Integer, Integer>();
		this.monthToDays.put(1, 31);
		//	Applies the right amount of days dependant on leap status
		if	(isLeapYear(this.year))	this.monthToDays.put(2, 29);
		else	this.monthToDays.put(2, 28);
		this.monthToDays.put(3, 31);
		this.monthToDays.put(4, 30);
		this.monthToDays.put(5, 31);
		this.monthToDays.put(6, 30);
		this.monthToDays.put(7, 31);
		this.monthToDays.put(8, 31);
		this.monthToDays.put(9, 30);
		this.monthToDays.put(10, 31);
		this.monthToDays.put(11, 30);
		this.monthToDays.put(12, 31);
	}
	
	/**
	 * isLeapYear implementation taken from
	 * https://stackoverflow.com/questions/1021324/
	 * java-code-for-calculating-leap-year
	 * @param year
	 * @return
	 */
	public boolean isLeapYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
	}
	
	/**
	 * Getter method for returning the validity state of a date
	 * @return
	 */
	public boolean getValidFlag()	{
		return this.validDate;
	}
	
	@Override
	public String toString() {
		return textDate + " date status: " + validDate;
	}
	
	@Override
	/**
	 * compareTo method override. A return of 0 means the two dates are
	 * equal, a return of -1 means this is sooner than o, while a return of
	 * 1 means that o is sooner than this
	 * A sooner date will have a lower number
	 */
	public int compareTo(Date o) {
		if	(this.year != o.year)	{
			//	If current year is sooner, return -1, otherwise 1
			return (this.year < o.year) ? -1 : 1;
		}	else	{
			//	Same year branch
			if	(this.month != o.month)	{
				//	If current month is sooner, return -1, else 1
				return (this.month < o.month) ? -1 : 1;
			}	else	{
				//	Same month branch
				if	(this.day != o.day)	{
					//	If current day sooner, return -1, else 1
					return	(this.day < o.day) ? -1 : 1;
				}	else	{
					//	same day branch
					return 0;
				}
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Date other = (Date) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (monthToDays == null) {
			if (other.monthToDays != null)
				return false;
		} else if (!monthToDays.equals(other.monthToDays))
			return false;
		if (textDate == null) {
			if (other.textDate != null)
				return false;
		} else if (!textDate.equals(other.textDate))
			return false;
		if (validDate != other.validDate)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	//**  **  **  **  **//
	
	//	Following code is an assortment of getters used for JUnit tests
	//	This is not to be used for any purpose besides testing
	
	/**
	 * Polymorphic constructor used for testing
	 * @param day
	 * @param month
	 * @param year
	 */
	public Date(int day, int month, int year)	{
		System.out.println("This constructor, Date(int, int, int) should " +
				"be used for testing only");
		this.textDate = day + " " + month + " " + year;
		this.day = day;
		this.month = month;
		this.year = year;
		monthDayMapping();
	}
	
	/**	
	 * Polymorphic Date constructor used for invalid testing of splitDate() method
	 * @param date
	 * @param test
	 */
	public Date(String date, int day, int month, int year) {
		System.out.println("This constructor, Date(int, int, int) should " +
				"be used for testing only");
		this.textDate = date;
		this.day = day;
		this.month = month;
		this.year = year;
		monthDayMapping();
	}
	
	/**
	 * Public getter to access validDateMonth() for testing purposes
	 * @return
	 */
	public boolean validGetter()	{
		return validDateMonth();
	}
	
	/**
	 * Public getter to access split date for testing purposes
	 * @return
	 */
	public ArrayList<String> splitGetter()	{
		return splitDate();
	}
	
	/**
	 * Public getter to access split date validation method for testing
	 * purposes
	 * @param test
	 * @return
	 */
	public boolean splitValidGetter(ArrayList<String> test)	{
		return splitDateValidation(test);
	}
}
