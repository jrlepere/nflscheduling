package domains;

import java.util.List;

import values.Value;

public interface Domain<V extends Value> {
	
	/**
	 * Gets the next value from the domain, or null if there are none left.
	 * @param valuesTried values that have already been tried and therefore cannot be returned.
	 * @return the next value, or null if there are none left.
	 */
	public V getNext(List<V> valuesTried);
	
	public void addBack(V value);
	
}
