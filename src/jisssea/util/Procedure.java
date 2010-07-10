package jisssea.util;

public interface Procedure<T> {

	/**
	 * @param a
	 * @return false if you wish to stop iterating
	 */
	public boolean f(T a);
	
}
