package backtracking;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import constraints.Constraint;
import values.Value;
import variables.Variable;
import variableset.DomainReduction;
import variableset.SharedDomainVariableSet;

public class Backtracking {
	
	public static <V extends Variable<A>, A extends Value> boolean SOLVE(SharedDomainVariableSet<V, A> variableSet) throws Exception {
		
		return BACKTRACK(variableSet);
	}
	
	private static <V extends Variable<A>, A extends Value> boolean BACKTRACK(SharedDomainVariableSet<V, A> variableSet) {
		//System.out.println(variableSet.getCountSet());
		// return if the variable set is complete
		if (variableSet.isComplete()) {return true;}
		
		// get variable to assign
		V var = variableSet.getUnassignedVariable();
		System.out.println(variableSet.getCountSet() + ": " + var);
		//System.out.println(var);
		
		// for each possible value
		for (A val : variableSet.getDomain(var)) {

			// add the assignment
			var.assign(val);
			
			// test acceptability and infer the feasibility of the assignment  && INFER(variableSet, var)
			if (variableSet.isAcceptable(var)) {
				
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
		return false;
	}
	
	private static <V extends Variable<A>, A extends Value> Map<V, DomainReduction<A>> FORWARD_CHECK(SharedDomainVariableSet<V, A> variableSet, V var) throws Exception {
		/*
		 * For each Y constrained by X, remove values from Y domain that are inconsistent with the value chosen for X
		 */
		
		// map of variable to not possible domain values
		Map<V, DomainReduction<A>> domainReduction = new TreeMap<>();
		
		// map of variables constrained by the input variable, and their constraints
		Map<V, List<Constraint<V>>> constrainedVariables = new TreeMap<>();
		
		// for each constraint on the variable
		for (Constraint<V> constraint : variableSet.getConstraintsByVariable(var)) {
			
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
				if (!constrainedVariables.containsKey(v)) {
					List<Constraint<V>> vDependentConstraints = new LinkedList<>();
					vDependentConstraints.add(constraint);
					constrainedVariables.put(v, vDependentConstraints);
				} else {
					constrainedVariables.get(v).add(constraint);
				}
			}
			
		}
		
		// for each constrained variable, remove values in domain that make it a failure
		for (V v : constrainedVariables.keySet()) {
			domainReduction.put(v, new DomainReduction<A>(256)); // TODO
			while (true) {
				DomainReduction<A> reductions = new DomainReduction<>(256); // TODO
				for (Constraint<V> c : constrainedVariables.get(v)) {
					List<A> domain = variableSet.getDomain(v);
					if (domain.isEmpty()) {
						variableSet.clearDomainReduction(domainReduction);
						throw new Exception();
					}
					for (A val : domain) {
						v.assign(val);
						if (!c.isAcceptable()) {
							reductions.add(val); // TODO optimize
						}
						v.unassign();
					}
				}
				if (!reductions.isEmpty()) {
					// made some reductions, reduce and try again
					variableSet.addDomainReduction(v, reductions);
					domainReduction.get(v).addAll(reductions);
					//break;
				} else {
					// no reductions were made
					break;
				}
			}
		}
		
		return domainReduction;
		
	}
	
	private static <V extends Variable<A>, A extends Value> Map<V, DomainReduction<A>> FORWARD_CHECK2(SharedDomainVariableSet<V, A> variableSet, V var) throws Exception {
		/*
		 * For each Y constrained by X, remove values from Y domain that are inconsistent with the value chosen for X
		 */
		
		// map of variable to not possible domain values
		Map<V, DomainReduction<A>> domainReduction = new TreeMap<>();
		
		List<Constraint<V>> unsetConstraints = new LinkedList<>();
		for (Constraint<V> constraint : variableSet.getConstraintsByVariable(var)) {
			
			// get free variables in the constraint
			List<V> freeVariables = new LinkedList<>();
			for (V v : constraint.getVariables()) {
				if (!v.isSet()) {
					freeVariables.add(v);
				}
			}
			
			if (!freeVariables.isEmpty()) {
				unsetConstraints.add(constraint);
			}
		}
		
		
		while (true) {
			
			boolean reductionMade = false;
			// for each constraint on the variable
			for (Constraint<V> constraint : unsetConstraints) {
				
				// get free variables in the constraint
				List<V> freeVariables = new LinkedList<>();
				for (V v : constraint.getVariables()) {
					if (!v.isSet()) {
						freeVariables.add(v);
					}
				}
				
				// continue if no variables to set for constraint, assumed to already be acceptable
				if (freeVariables.isEmpty()) {
					continue;
				}
				
				// copy free variables
				List<V> freeVariablesCopy = new LinkedList<>(freeVariables);
				
				while (true) {
					Map<V, DomainReduction<A>> reductions = new TreeMap<>();
					
					for (V freeVariable : freeVariables) {
						
						reductions.put(freeVariable, new DomainReduction<>(256)); // TODO
						
						freeVariablesCopy.remove(freeVariable);
						
						List<A> domain = variableSet.getDomain(freeVariable);
						if (domain.isEmpty()) {
							variableSet.clearDomainReduction(domainReduction);
							throw new Exception();
						}
						for (A val : domain) {
							freeVariable.assign(val);
							if (!FIND_COMBINATION(variableSet, freeVariablesCopy, constraint)) {
								reductions.get(freeVariable).add(val);
							}
							freeVariable.unassign();
						}
						freeVariablesCopy.add(freeVariable);
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
							reductionMade = true;
						}
					}
					
					if (noReduction) {
						break;
					}
						
				}
				
			}
			
			if (!reductionMade) {
				break;
			}
		}
		
		return domainReduction;
		
	}
	
}
