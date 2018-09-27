package variableset;

import java.util.List;

import constraints.Constraint;
import values.Value;
import variables.Variable;

public interface VariableSet<V extends Variable<A>, A extends Value> extends Iterable<V> {

	public V getUnassignedVariable();

	public boolean isComplete();
	
	public boolean isAcceptable(V v);
	
	public int getCountSet();
	
	public List<Constraint<V>> getConstraintsByVariable(V v);
	
}
