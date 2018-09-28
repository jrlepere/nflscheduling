package variableset;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

import constraints.Constraint;
import values.Value;
import variables.Variable;

public abstract class SharedDomainVariableSet<V extends Variable<A>, A extends Value>
	implements VariableSet<V, A>{
	
	public SharedDomainVariableSet() {
		freeVariables = new PriorityQueue<>(new Comparator<V>() {
			public int compare(V arg0, V arg1) {
				int arg0constraintCount = 0;
				int arg1constraintCount = 0;
				if (constraints.containsKey(arg0)) {
					arg0constraintCount = constraints.get(arg0).size();
				}
				if (constraints.containsKey(arg1)) {
					arg1constraintCount = constraints.get(arg1).size();
				}
				return arg1constraintCount - arg0constraintCount;
			}
		});
		setVariables = new LinkedList<>();
		sharedDomain = new LinkedList<>();
		constraints = new TreeMap<>();
		domainReductions = new TreeMap<>();
	}
	
	public List<A> getDomain(V v) {
		if (!this.domainReductions.containsKey(v)) {
			return new LinkedList<>(this.sharedDomain);
		}
		List<A> domainReduction = this.domainReductions.get(v);
		if (domainReduction.isEmpty()) {
			return new LinkedList<>(this.sharedDomain);
		}
		List<A> domain = new LinkedList<>();
		for (A a : this.sharedDomain) {
			if (!domainReduction.contains(a)) {
				domain.add(a);
			}
		}
		return domain;
		
	}
	
	public void notifyAssignment(V variable, A value) {
		this.freeVariables.remove(variable);
		this.setVariables.add(variable);
		this.sharedDomain.remove(value);
	}
	
	public void notifyUnassignment(V variable, A value) {
		this.freeVariables.add(variable);
		this.setVariables.remove(variable);
		this.sharedDomain.add(value);
	}
	
	public void addDomainReduction(Map<V, List<A>> reductions) {
		for (V v : reductions.keySet()) {
			if (!this.domainReductions.containsKey(v)) {
				this.domainReductions.put(v, new LinkedList<>(reductions.get(v)));
			} else {
				this.domainReductions.get(v).addAll(reductions.get(v));
			}
		}
	}
	
	public void clearDomainReduction(Map<V, List<A>> reductions) {
		for (V v : reductions.keySet()) {
			this.domainReductions.get(v).removeAll(reductions.get(v));
		}
	}
	
	public V getUnassignedVariable() {
		return freeVariables.remove();
	}

	public boolean isComplete() {
		return freeVariables.isEmpty();
	}
	
	public boolean isAcceptable(V v) {
		if (!constraints.containsKey(v)) {
			return true;
		}
		for (Constraint<V> constraint : constraints.get(v)) {
			if (!constraint.isAcceptable()) {
				return false;
			}
		}
		return true;
	}
	
	public Iterator<V> iterator() {
		List<V> allVariables = new LinkedList<>();
		allVariables.addAll(freeVariables);
		allVariables.addAll(setVariables);
		return allVariables.iterator();
	}
	
	public int getCountSet() {
		return setVariables.size();
	}
	
	public List<Constraint<V>> getConstraintsByVariable(V v) {
		if (constraints.containsKey(v)) {
			return constraints.get(v);
		} else {
			return new LinkedList<>();
		}
	}
	
	public String toString() {
		String s = "";
		for (V v : setVariables) {
			s += v.toString() + "\n";
		}
		return s;
	}
	
	protected Queue<V> freeVariables;
	protected List<V> setVariables;
	protected List<A> sharedDomain;
	protected Map<V, List<Constraint<V>>> constraints;
	private Map<V, List<A>> domainReductions; // TODO initialize by each variable

}
