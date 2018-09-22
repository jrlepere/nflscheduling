package variableset;

import variableset.variables.Variable;

/**
 * An interface for a set of Variables, defined for problem specific optimization.
 * @author jlepere2
 * @date 09/22/2018
 */
public interface VariableSet<V extends Variable<?,?>> {
	
	/**
	 * Gets the next variable to try and set.
	 * @return the next variable.
	 */
	public V getVariableToSet();
	
	/**
	 * Tests if the variable set is complete.
	 * @return true if the set is complete, false otherwise.
	 */
	public boolean isComplete();
	
}
