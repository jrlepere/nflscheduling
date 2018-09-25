package constraintset.constraint;

import variableset.variables.Variable;

public abstract class BinaryConstraint<V extends Variable<?,?>> implements Constraint {
	
	public BinaryConstraint(V v1, V v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	protected V v1;
	protected V v2;
	
}
