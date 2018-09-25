package values;

/**
 * Interface for the Variable values.
 * @author jlepere2
 * @date 09/22/2018
 */
public interface Value {

	/**
	 * Gets the unique index [0,n-1] where n is the total number of values in the domain for the variable.
	 * @return the unique index.
	 */
	public int getIndex();
	
}
