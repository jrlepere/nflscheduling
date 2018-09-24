package variableset.variables;

import values.Value;

/**
 * Variable object for CSP.
 * @author jlepere2
 * @date 09/22/2018
 * @param <A> The type of object to pass to the variable setter.
 * @param <R> The type of object to be returned form the variable getter.
 * 
 * Done in the <A, R> manner to allow for static information to be stored within
 *  the Variable that does not be to be passed to the setter but can be returned
 *  by the getter.
 */
public interface Variable<A extends Value, R extends Value> {
	
	/**
	 * Sets the value for the variable.
	 * @param a the value.
	 */
	void setVariable(A a);
	
	/**
	 * Gets the value from the variable.
	 * @return the value.
	 */
	R getVariable();
	
	/**
	 * Returns if the variable as been set.
	 * @return true if the variable has been set, false otherwise.
	 */
	boolean isSet();
	
}
