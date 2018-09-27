package constraints;

import java.util.LinkedList;
import java.util.List;

import variables.Variable;

public abstract class BinaryConstraint<V extends Variable<?>> implements Constraint<V> {
	
	public BinaryConstraint(V v1, V v2) {
		this.variables = new LinkedList<>();
		this.variables.add(v1);
		this.variables.add(v2);
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public List<V> getVariables() {
		return this.variables;
	}
	
	public V v1;
	public V v2;
	protected List<V> variables;
	
}
