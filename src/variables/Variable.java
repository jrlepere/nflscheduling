package variables;

import java.util.List;

import values.Value;

public interface Variable<A extends Value> {
	
	public List<A> orderDomainValues();
	
	public void assign(A value);
	
	public void unassign();
	
	public boolean isSet();
	
	public int getIndex();
	
}
