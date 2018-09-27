package constraints;

import java.util.List;

import variables.Variable;

/**
 * An interface for Constraint definition.
 * @author jlepere2
 * @date 09/22/2018
 */
public interface Constraint<V extends Variable<?>> {
	
	/**
	 * Tests if the set of configured variables is acceptable.
	 * @param set the set of variables.
	 * @return true if the variable configuration is acceptable, false otherwise.
	 */
	public boolean isAcceptable();
	
	public List<V> getVariables();
	
}
