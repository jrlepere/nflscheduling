package constraintset.constraint;

/**
 * An interface for Constraint definition.
 * @author jlepere2
 * @date 09/22/2018
 */
public interface Constraint {
	
	/**
	 * Tests if the set of configured variables is acceptable.
	 * @param set the set of variables.
	 * @return true if the variable configuration is acceptable, false otherwise.
	 */
	public boolean isAcceptable();
	
}
