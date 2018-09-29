package backtracking;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import constraints.Constraint;
import values.Value;
import variables.Variable;
import variableset.DomainReduction;
import variableset.SharedDomainVariableSet;

public class Backtracking {
	
	static int x = 0;
	public static <V extends Variable<A>, A extends Value> boolean SOLVE(SharedDomainVariableSet<V, A> variableSet) throws Exception {
		
		return BACKTRACK(variableSet);
	}
	
	private static <V extends Variable<A>, A extends Value> boolean BACKTRACK(SharedDomainVariableSet<V, A> variableSet) {
		//System.out.println(variableSet.getCountSet());
		// return if the variable set is complete
		if (variableSet.isComplete()) {return true;}
		
		// get variable to assign
		V var = variableSet.getUnassignedVariable();
		System.out.println(x + " --> " + variableSet.getCountSet() + ": " + var.getIndex());
		x += 1;
		//System.out.println(var);
		
		// for each possible value
		for (A val : variableSet.getDomain(var)) {

			// add the assignment
			var.assign(val);
			
			// test acceptability and infer the feasibility of the assignment  && INFER(variableSet, var)
			if (INFER(variableSet, var)) {
			//if (true) {	
				try {
					
					// domain reduction, throws exception if invalid
					Map<V, DomainReduction<A>> domainReduction2 = FORWARD_CHECK2(variableSet, var);
					
					//Map<V, DomainReduction<A>> domainReduction = FORWARD_CHECK(variableSet, var);
					
					// add domain reduction to set
					//variableSet.addDomainReduction(domainReduction);
					
					// backtrack
					//boolean result = BACKTRACK(variableSet);
					
					// stop if solution found
					if (INFER(variableSet, var) && BACKTRACK(variableSet)) {
						return true;
					}
					
					// clear domain reduction
					//variableSet.clearDomainReduction(domainReduction);
					variableSet.clearDomainReduction(domainReduction2);
				} catch (Exception e) {
					
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
			Queue<V> variablesToSet = new LinkedList<>();
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
	
	private static <V extends Variable<A>, A extends Value> boolean FIND_COMBINATION(SharedDomainVariableSet<V, A> variableSet, Queue<V> variables, Constraint<V> constraint) {
		if (variables.isEmpty()) {
			return constraint.isAcceptable();
		}
		
		V v = variables.remove();
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
		return false;
	}
	
	private static <V extends Variable<A>, A extends Value> Map<V, DomainReduction<A>> FORWARD_CHECK2(SharedDomainVariableSet<V, A> variableSet, V var) throws Exception {
		/*
		 * For each Y constrained by X, remove values from Y domain that are inconsistent with the value chosen for X
		 */
		
		// map of variable to not possible domain values
		Map<V, DomainReduction<A>> domainReduction = new HashMap<>(256);
		
		// map of constraints to unset variables
		Map<Constraint<V>, Queue<V>> unsetConstraints = new HashMap<>();
		for (Constraint<V> constraint : variableSet.getConstraintsByVariable(var)) {
			
			// get free variables in the constraint
			Queue<V> freeVariables = new LinkedList<>();
			for (V v : constraint.getVariables()) {
				if (!v.isSet()) {
					freeVariables.add(v);
				}
			}
			
			// add only if number of free variables is not empty
			if (!freeVariables.isEmpty()) {
				unsetConstraints.put(constraint, freeVariables);
			}
		}
		
		
		while (true) {
			
			// made at least one reduction for all constraints
			boolean madeReductionAll = false;
			
			// for each constraint on the variable
			for (Constraint<V> constraint : unsetConstraints.keySet()) {
				
				Queue<V> freeVariables = unsetConstraints.get(constraint);
				
				while (true) {
					Map<V, DomainReduction<A>> reductions = new HashMap<>(256);
					
					for (int i = 0; i < freeVariables.size(); i ++) {
						V freeVariable = freeVariables.remove();
						
						reductions.put(freeVariable, new DomainReduction<>(256)); // TODO
						
						List<A> domain = variableSet.getDomain(freeVariable);
						if (domain.isEmpty()) {
							variableSet.clearDomainReduction(domainReduction);
							throw new Exception();
						}
						for (A val : domain) {
							freeVariable.assign(val);
							if (!FIND_COMBINATION(variableSet, freeVariables, constraint)) {
								reductions.get(freeVariable).add(val);
							}
							freeVariable.unassign();
						}
						freeVariables.add(freeVariable);
					}
					
					boolean noReduction = true;
					
					for (V freeVariable : freeVariables) {
						DomainReduction<A> reduc = reductions.get(freeVariable);
						if (!reduc.isEmpty()) {
							variableSet.addDomainReduction(freeVariable, reduc);
							if (!domainReduction.containsKey(freeVariable)) {
								domainReduction.put(freeVariable, reduc);
							} else {
								domainReduction.get(freeVariable).addAll(reduc);
							}
							noReduction = false;
							madeReductionAll = true;
						}
					}
					
					if (noReduction) {
						break;
					}
						
				}
				
			}
			
			if (!madeReductionAll) {
				break;
			}
		}
		
		return domainReduction;
		
	}
	
}
