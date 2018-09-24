package csp;

import java.util.LinkedList;
import java.util.List;

import constraints.Constraint;
import domains.Domain;
import values.Value;
import variableset.VariableSet;
import variableset.variables.Variable;

public class CSP<VS extends VariableSet<V>, D extends Domain<A>, V extends Variable<A,R>, A extends Value, R extends Value> {
	
	public VS solve(VS variableSet, D domain, List<Constraint<VS>> constraints) throws Exception {
		
		// initialization
		this.variableSet = variableSet;
		this.domain = domain;
		this.constrains = constraints;
		
		// backtrack to the solution
		this.backTracking();
		
		// return the solution
		return this.variableSet;
	}
	
	private boolean backTracking() throws Exception {
		
		// stop when variable set is completely filled
		if (this.variableSet.isComplete()) {return true;}
		
		// test current constraints are satisfied
		for (Constraint<VS> c : this.constrains) {
			if (!c.isAcceptable(this.variableSet)) {
				return false;
			}
		}
		
		// get the next variable to set
		Variable<A, R> var = this.variableSet.getVariableToSet();
		
		// list of values tried for this variable
		List<A> valuesTried = new LinkedList<>();
		
		// try each available value in the domain
		while (this.domain.hasNext()) {
			
			// get the next value to set
			A val = this.domain.getNext(valuesTried);
			
			// set the variable to the value
			var.setVariable(val);
			
			// test the assignment
			if (backTracking()) {
				// good assignment
				return true;
			} else {
				/*
				 * bad assignment
				 * add value back to global domain
				 * add value to list of values tried for this variable
				 */
				this.domain.addBack(val);
				valuesTried.add(val);
			}
			
		}
		
		// none of the values worked, previous assignment was invalid
		return false;
		
	}
	
	private VS variableSet;
	private D domain;
	private List<Constraint<VS>> constrains;
	
}
