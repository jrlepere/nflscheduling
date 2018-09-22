package constraints;

import variableset.VariableSet;
import variableset.variables.Variable;

/**
 * An interface for Constraint definition.
 * @author jlepere2
 * @date 09/22/2018
 */
public interface Constraint<V extends Variable<?,?>> {
	
	/**
	 * Tests if the set of configured variables is acceptable.
	 * @param variables the set of variables.
	 * @return true if the variable configuration is acceptable, false otherwise.
	 */
	public boolean isAcceptable(VariableSet<V> variables);
	
}
