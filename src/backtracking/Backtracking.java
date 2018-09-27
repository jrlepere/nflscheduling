package backtracking;

import java.util.LinkedList;
import java.util.List;

import constraints.Constraint;
import values.Value;
import variables.Variable;
import variableset.SharedDomainVariableSet;

public class Backtracking {
	
	public static <V extends Variable<A>, A extends Value> boolean SOLVE(SharedDomainVariableSet<V, A> variableSet) throws Exception {
		
		return BACKTRACK(variableSet);
	}
	
	private static <V extends Variable<A>, A extends Value> boolean BACKTRACK(SharedDomainVariableSet<V, A> variableSet) {
		System.out.println(variableSet.getCountSet());
		// return if the variable set is complete
		if (variableSet.isComplete()) {return true;}
		
		// get variable to assign
		V var = variableSet.getUnassignedVariable();
		
		// for each possible value
		for (A val : new LinkedList<>(variableSet.getDomain())) {
			
			// add the assignment
			var.assign(val);
			
			if (variableSet.isAcceptable(var) && INFER(variableSet, var)) {
				// backtrack
				boolean result = BACKTRACK(variableSet);
				
				// stop if solution found
				if (result) {
					return result;
				}
			}
			
			// clean assignment
			var.unassign();
			
		}
		
		// no assignment found
		return false;
		
	}
	
	private static <V extends Variable<A>, A extends Value> boolean INFER(SharedDomainVariableSet<V, A> variableSet, V variable) {
		/*
		 * Just test that the assignment is feasable,
		 * ie can place some combination for other variables
		 */
		
		// get all constraints on the variable
		List<Constraint<V>> constraints = new LinkedList<>(variableSet.getConstraintsByVariable(variable));
		
		for (Constraint<V> constraint : constraints) {
			// get all the variables that need to be set for this constraint
			List<V> variablesToSet = new LinkedList<>();
			for (V v : constraint.getVariables()) {
				if (!v.isSet()) {
					variablesToSet.add(v);
				}
			}
			// try and find some combination of the unset variables to make the constraint valid
			if (!FIND_COMBINATION(variableSet, variablesToSet, constraint)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static <V extends Variable<A>, A extends Value> boolean FIND_COMBINATION(SharedDomainVariableSet<V, A> variableSet, List<V> variables, Constraint<V> constraint) {
		if (variables.isEmpty()) {
			return constraint.isAcceptable();
		}
		
		V v = variables.remove(0);
		for (A a : new LinkedList<>(variableSet.getDomain())) {
			v.assign(a);
			boolean acceptable = FIND_COMBINATION(variableSet, variables, constraint);
			v.unassign();
			// if we've found one acceptable, stop and return, else keep searching
			if (acceptable) {
				return true;
			}
		}
		
		// backtracking
		return true;
	}
	
}
