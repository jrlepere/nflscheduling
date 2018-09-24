package domains;

import java.util.List;

import values.Value;

public interface Domain<V extends Value> {
	
	public V getNext(List<V> valuesTried) throws Exception;
	
	public void addBack(V value);
	
	public boolean hasNext();
	
}
