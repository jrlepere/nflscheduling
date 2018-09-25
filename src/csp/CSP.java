package csp;

import java.util.List;

import constraintset.constraint.Constraint;
import domains.Domain;
import values.Value;
import variableset.VariableSet;
import variableset.variables.Variable;

public class CSP<VS extends VariableSet<V>, D extends Domain<A>, V extends Variable<A,R>, A extends Value, R extends Value> {
	
	public VS solve(VS variableSet, D domain, List<Constraint> constraints) throws Exception {
		
		// initialization
		this.variableSet = variableSet;
		this.domain = domain;
		this.constrains = constraints;
		
		// backtrack to the solution
		this.backTracking();
		
		this.test = 0;
		
		// return the solution
		return this.variableSet;
	}
	
	private boolean backTracking() throws Exception {
		
		// testing
		int t = this.variableSet.getNumberSet();
		if (t > this.test) {
			this.test = t;
			System.out.println(this.test);
		}
		
		// test current constraints are satisfied
		for (Constraint c : this.constrains) {
			if (!c.isAcceptable()) {
				return false;
			}
		}
		
		// stop when variable set is completely filled
		if (this.variableSet.isComplete()) {return true;}
		
		// get the next variable to set
		Variable<A, R> var = this.variableSet.getVariableToSet();
		
		// array of values tried for this variable, by the value index
		int[] valuesTried = new int[256]; // TODO
		
		// try each available value in the domain
		while (true) {
			
			// get the next value to set
			A val = this.domain.getNext(valuesTried);
			
			// if value is null, there are no more possible values for the current configuration
			if (val == null) break;
			
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
				valuesTried[val.getIndex()] += 1;
			}
			
		}
		
		// free up the variable
		var.freeVariable();
		
		// none of the values worked, previous assignment was invalid
		return false;
		
	}
	
	private int test;
	private VS variableSet;
	private D domain;
	private List<Constraint> constrains;
	
}
