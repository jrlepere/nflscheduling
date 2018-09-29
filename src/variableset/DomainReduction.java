package variableset;

import java.util.List;

import values.Value;

public class DomainReduction <A extends Value> {

	public DomainReduction(int domainSize) {
		reducedValues = new int[domainSize];
		size = 0;
	}
	
	public boolean contains(A value) {
		return reducedValues[value.getIndex()] != 0;
	}
	
	public void add(A value) {
		if (reducedValues[value.getIndex()] == 0) {
			reducedValues[value.getIndex()] = 1;
			size += 1;
		}
	}
	
	public void addAll(List<A> values) {
		for (A value : values) {
			add(value);
		}
	}
	
	public void addAll(DomainReduction<A> reduction) {
		if (reduction.isEmpty()) {
			return;
		}
		for (int i = 0; i < reduction.reducedValues.length; i ++) {
			if (reduction.reducedValues[i] != 0 && this.reducedValues[i] == 0) {
				reducedValues[i] = 1;
				size += 1;
			}
		}
	}
	
	public void remove(A value) {
		if (reducedValues[value.getIndex()] == 1) {
			reducedValues[value.getIndex()] = 0;
			size -= 1;
		}
	}
	
	public void removeAll(List<A> values) {
		for (A value : values) {
			remove(value);
		}
	}
	
	public void removeAll(DomainReduction<A> reduction) {
		if (reduction.isEmpty()) {
			return;
		}
		for (int i = 0; i < reduction.reducedValues.length; i ++) {
			if (reducedValues[i] == 0 && reduction.reducedValues[i] == 1) {
				System.out.println("ERR");
			}
			if (reduction.reducedValues[i] != 0 && this.reducedValues[i] == 1) {
				reducedValues[i] = 0;
				size -= 1;
			}
			
		}
	}
	
	public int size() {
		return 0;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	private int[] reducedValues;
	private int size;
	
}
