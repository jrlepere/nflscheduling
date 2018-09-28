package backtracking;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
		for (A val : variableSet.getDomain(var)) {
			
			// add the assignment
			var.assign(val);
			
			// test acceptability and infer the feasibility of the assignment
			if (variableSet.isAcceptable(var) && INFER(variableSet, var)) {
				
				// domain reduction
				Map<V, List<A>> domainReduction = FORWARD_CHECK(variableSet, var);
				
				// add domain reduction to set
				variableSet.addDomainReduction(domainReduction);
				
				// backtrack
				boolean result = BACKTRACK(variableSet);
				
				// clear domain reduction
				variableSet.clearDomainReduction(domainReduction);
				
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
		List<Constraint<V>> constraints = variableSet.getConstraintsByVariable(variable);
		
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
		for (A a : variableSet.getDomain(v)) {
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
	
	private static <V extends Variable<A>, A extends Value> Map<V, List<A>> FORWARD_CHECK(SharedDomainVariableSet<V, A> variableSet, V var) {
		/*
		 * For each Y constrained by X, remove values from Y domain that are inconsistent with the value chosen for X
		 */
		
		// map of variable to not possibilities
		Map<V, List<A>> domainReduction = new TreeMap<>();
		
		// get all constraints on the variable
		List<Constraint<V>> constraints = variableSet.getConstraintsByVariable(var);
		
		for (Constraint<V> constraint : constraints) {
			
			// get free variables in the constraint
			List<V> freeVariables = new LinkedList<>();
			for (V v : constraint.getVariables()) {
				if (!v.isSet()) {
					freeVariables.add(v);
				}
			}
			
			// domain reduction only if exactly one variable contributing
			// to the acceptance of the constraint
			if (freeVariables.size() == 1) {
				V v = freeVariables.remove(0);
				List<A> reductions = new LinkedList<>();
				for (A val : variableSet.getDomain(var)) {
					v.assign(val);
					if (!constraint.isAcceptable()) {
						reductions.add(val);
					}
					v.unassign();
				}
				domainReduction.put(v, reductions);
			}
			
		}
		
		return domainReduction;
		
	}
	

	
}
